package com.chen.cache.controller;

import com.chen.cache.config.datasource.DataSource;
import com.chen.cache.config.datasource.SourceName;
import com.chen.cache.config.mq.KafkaSender;
import com.chen.cache.dto.lang.*;
import com.chen.cache.entity.StudentEntity;
import com.chen.cache.service.StudentService;
import com.chen.cache.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author chenguo
 * @date 2022/3/10 9:02 PM
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private RedisUtils<StudentEntity> redisUtils;

    @Resource
    KafkaSender kafkaSender;

    @Resource
    private RedisTemplate<String, StudentEntity> redisTemplate;

    @Resource
    Filter filter;

    @Resource
    StudentLru studentLru;

    Random random = new Random();

    @Bean
    @DataSource(SourceName.read)
    public void init(){
        List<StudentEntity> list = studentService.list();
        list.forEach(studentEntity -> filter.add(EntityName.student.getValue() + studentEntity.getSno()));
        log.info("===========================");
        log.info("布隆过滤器学生部分初始化成功");
        log.info("===========================");
    }

    @DataSource(SourceName.read)
    @GetMapping("/list")
    public Result<List<StudentEntity>> getStudentList() {
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
        if(!filter.contains(EntityName.student.getValue()+id)){
            return Result.failed("该学生并不存在");
        }
        StudentEntity student;
        if(studentLru.containsKey(id)){
            studentLru.put(id);
            System.out.println("===========================");
            System.out.println("LRU命中");
            System.out.println("===========================");
            return Result.success(studentLru.get(id));
        }
        if(redisUtils.hasKey(EntityName.student.getValue() + id)){
            student = redisUtils.get(EntityName.student.getValue() + id);
            studentLru.put(id);
        }else{
            synchronized (this){
                student = studentService.getById(id);
                redisUtils.set(EntityName.student.getValue() + id, student);
                int expire = random.nextInt(60) + 60;
                redisUtils.expire(EntityName.student.getValue() + id, expire);
                studentLru.put(id);
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
        if(filter.contains(EntityName.student.getValue() + studentEntity.getSno()) || redisUtils.hasKey(EntityName.student.getValue() + studentEntity.getSno())){
            return Result.failed("学号已存在");
        }
        boolean flag;
        synchronized (this){
            flag = studentService.save(studentEntity);
            redisUtils.set(EntityName.student.getValue() + studentEntity.getSno(), studentEntity);
            int expire = random.nextInt(60) + 60;
            redisUtils.expire(EntityName.student.getValue() + studentEntity.getSno(), expire);
            filter.add(EntityName.student.getValue() + studentEntity.getSno());
            studentLru.put(studentEntity.getSno());
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
        filter.remove(EntityName.student.getValue() + id);
        boolean flag;
        synchronized (this){
            flag = studentService.removeById(id);
            redisUtils.del(EntityName.student.getValue() + id);
            studentLru.remove(id);
        }
        if(flag){
            return Result.success("删除成功");
        }else{
            return Result.failed("删除失败");
        }
    }


}
