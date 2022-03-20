package com.chen.cache.config.bloom;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.cache.dto.lang.EntityName;
import com.chen.cache.dto.lang.Filter;
import com.chen.cache.dto.lang.Result;
import com.chen.cache.entity.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author chenguo
 * @date 2022/3/17 5:05 下午
 */
@Aspect
@Order(-1)
@Slf4j
@Component
public class BloomController {
    @Resource
    Filter filter;

    @Around(value = "@annotation(bloom)", argNames = "pjp,bloom")
    public Object doAfter(ProceedingJoinPoint pjp, Bloom bloom) throws Throwable {
        System.out.println("==========================");
        Object[] args = pjp.getArgs();
        IService bean = SpringUtil.getBean(bloom.bean());
        if("add".equals(bloom.method())){
            Object object = bean.getById((Serializable) args[0]);
            if(object instanceof StudentEntity) {
                filter.add(EntityName.student.getValue() + ((StudentEntity) object).getSno());
            }
        }else if("delete".equals(bloom.method())){
            filter.remove(EntityName.student.getValue() + args[0]);
        }
        else if("get".equals(bloom.method())){
            if(!filter.contains(EntityName.student.getValue() + args[0])){
                throw new RuntimeException("查无此人");
            }
        }
        return pjp.proceed(args);
        /**
         * 此处可以使用策略模式
         */
    }
}
