package com.chen.cache.config.datasource;

/**
 * @author chenguo
 * @date 2022/3/10 8:17 PM
 */

public enum SourceName {
    /**
     * read 数据库
     */
    read("read"),
    /**
     * write 数据库
     */
    write("write");

    /**
     * 数据源名称
     */
    private final String value;

    SourceName(String value) {
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
