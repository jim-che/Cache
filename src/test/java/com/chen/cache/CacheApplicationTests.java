package com.chen.cache;

import com.chen.cache.entity.StudentEntity;
import com.chen.cache.service.StudentService;
import com.chen.cache.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class CacheApplicationTests {

    @Resource
    private RedisUtils cacheService;

    @Resource
    private StudentService service;

    @Test
    void contextLoads() {
        List<StudentEntity> list = service.list();
        list.forEach(System.out::println);
    }

}
