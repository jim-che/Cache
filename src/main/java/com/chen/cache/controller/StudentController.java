package com.chen.cache.controller;

import com.chen.cache.config.datasource.DataSource;
import com.chen.cache.config.datasource.SourceName;
import com.chen.cache.config.mq.KafkaSender;
import com.chen.cache.dto.lang.EntityName;
import com.chen.cache.dto.lang.FullList;
import com.chen.cache.dto.lang.Result;
import com.chen.cache.entity.StudentEntity;
import com.chen.cache.service.StudentService;
import com.chen.cache.utils.RedisUtils;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author chenguo
 * @date 2022/3/10 9:02 PM
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private RedisUtils<StudentEntity> redisUtils;

    @Resource
    KafkaSender kafkaSender;

    @Resource
    KafkaTemplate<String, Object> kafkaTemplate;

    Random random = new Random();

    @DataSource(SourceName.read)
    @GetMapping("/list")
    @KafkaListener(topics = "studentList")
    public Result<List<StudentEntity>> getStudentList() {
        System.out.println("Listening to studentList");
        List<StudentEntity> studentList;
        if (redisUtils.hasKey(FullList.studentList.getValue())) {
            studentList = redisUtils.lRange(FullList.studentList.getValue(), 0, -1);
        }else {
            synchronized (this) {
                studentList = studentService.list();
                System.out.println(studentList.getClass());
                redisUtils.lLeftPushAll(FullList.studentList.getValue(), studentList);
                int expire = random.nextInt(30) + 60;
                redisUtils.expire(FullList.studentList.getValue(), expire);
            }
        }
        return Result.success(studentList);
    }

    @DataSource(SourceName.read)
    @GetMapping("/get/{id}")
    public Result<StudentEntity> getStudentById(@PathVariable("id") int id){
        StudentEntity student;
        if(redisUtils.hasKey(EntityName.student.getValue() + id)){
            student = redisUtils.get(EntityName.student.getValue() + id);
        }else{
            synchronized (this){
                student = studentService.getById(id);
                redisUtils.set(EntityName.student.getValue() + id, student);
                int expire = random.nextInt(60) + 60;
                redisUtils.expire(EntityName.student.getValue() + id, expire);
            }
        }
        if(Objects.equals(student, new StudentEntity()) || student == null){
            return Result.failed("查询失败");
        } else {
            return Result.success(student);
        }
    }

    @DataSource(SourceName.write)
    @PostMapping("/update")
    public Result<StudentEntity> updateById(@RequestBody StudentEntity studentEntity) {
        System.out.println(studentEntity);
        boolean flag;
        synchronized (this){
            flag = studentService.updateById(studentEntity);
            redisUtils.del("student" + studentEntity.getSno());
        }
        if(flag){
            return Result.success(studentEntity);
        }else{
            return Result.failed("更新失败");
        }
    }

    @DataSource(SourceName.write)
    @PostMapping("/add")
    public Result<StudentEntity> add(StudentEntity studentEntity) {
        if(redisUtils.hasKey(EntityName.student.getValue() + studentEntity.getSno())){
            return Result.failed("学号已存在");
        }
        boolean flag;
        synchronized (this){
            flag = studentService.save(studentEntity);
            redisUtils.set(EntityName.student.getValue() + studentEntity.getSno(), studentEntity);
            redisUtils.expire(EntityName.student.getValue() + studentEntity.getSno(), 120);
        }
        if(flag){
            return Result.success(studentEntity);
        }else{
            return Result.failed("添加失败");
        }
    }

    @DataSource(SourceName.write)
    @GetMapping("/del")
    public Result<String> deleteById(int id) {
        boolean flag;
        synchronized (this){
            flag = studentService.removeById(id);
            redisUtils.del(EntityName.student.getValue() + id);
        }
        if(flag){
            return Result.success("删除成功");
        }else{
            return Result.failed("删除失败");
        }
    }


}
