package com.chen.cache.dto.lang;

import lombok.Data;

import java.util.Date;

/**
 * @author chenguo
 * @date 2022/3/11 11:49 AM
 */
@Data
public class Message {
    private long id;
    private String message;
    private Date sendTime;
}
