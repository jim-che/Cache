package com.chen.cache.service.impl;

import com.chen.cache.entity.StudentEntity;
import com.chen.cache.mapper.StudentDao;
import com.chen.cache.service.StudentService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author chenguo
 */
@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, StudentEntity> implements StudentService {
}