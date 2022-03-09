package com.chen.cache.service.impl;

import com.chen.cache.entity.ScEntity;
import com.chen.cache.mapper.ScDao;
import com.chen.cache.service.ScService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * @author chenguo
 */
@Service("scService")
public class ScServiceImpl extends ServiceImpl<ScDao, ScEntity> implements ScService {

}