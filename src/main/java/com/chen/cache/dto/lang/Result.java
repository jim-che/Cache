package com.chen.cache.dto.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenguo
 * @date 2022/3/9 7:57 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    T data;
    String msg;
    int code;

    public Result<T> success(T data, String msg, int code) {
        return new Result<>(data, msg, code);
    }

    public Result<T> success(T data){
        return success(data, "success", 200);
    }

    public Result<T> failed(T data, String msg, int code){
        return new Result<>(data, msg, code);
    }

    public Result<T> failed(String msg){
        return failed(null, msg, 400);
    }

}
