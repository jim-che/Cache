package com.chen.cache.dto.lang;

/**
 * @author chenguo
 * @date 2022/3/10 9:56 PM
 */

public enum FullList {
    /**
     * student / course / score full list
     */
    studentList("studentList"),
    courseList("courseList"),
    scoreList("scoreList");

    private final String value;

    FullList(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
