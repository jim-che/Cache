package com.chen.cache.controller;

import com.chen.cache.config.datasource.DataSource;
import com.chen.cache.config.datasource.SourceName;
import com.chen.cache.dto.lang.Result;
import com.chen.cache.entity.StudentEntity;
import com.chen.cache.service.StudentService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenguo
 * @date 2022/3/10 9:02 PM
 */
@RestController
public class StudentController {
    @Resource
    private StudentService studentService;


    @DataSource(SourceName.read)
    @GetMapping("/student/list")
    public Result<List<StudentEntity>> getStudentList() {
        return Result.success(studentService.list());
    }

    @DataSource(SourceName.write)
    @GetMapping("/student/update")
    public Result<StudentEntity> updateById(int id){
        StudentEntity student = studentService.getById(id);
        studentService.updateById(student);
        return Result.success(student);
    }
}
