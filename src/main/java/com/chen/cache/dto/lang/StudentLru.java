package com.chen.cache.dto.lang;

import com.chen.cache.entity.StudentEntity;
import com.chen.cache.service.StudentService;
import com.chen.cache.utils.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chenguo
 * @date 2022/3/15 9:29 下午
 */
@Component
public class StudentLru extends LinkedHashMap<Integer, StudentEntity> {
    @Resource
    StudentService studentService;
    @Resource
    RedisUtils<StudentEntity> redisUtils;

    public StudentLru(){
        super(16, 0.75F, true);
    }

    public StudentEntity get(int id){
        return super.getOrDefault(id, null);
    }

    public void put(int id) {
        StudentEntity studentEntity = redisUtils.get(EntityName.student.getValue() + id);
        if (studentEntity == null) {
            studentEntity = studentService.getById(id);
        }
        super.put(id, studentEntity);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, StudentEntity> eldest) {
        return size() > 16;
    }
}
