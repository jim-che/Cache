package com.chen.cache.service.impl;

import com.chen.cache.entity.CourseEntity;
import com.chen.cache.mapper.CourseDao;
import com.chen.cache.service.CourseService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



/**
 * @author chenguo
 */
@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao, CourseEntity> implements CourseService {

}