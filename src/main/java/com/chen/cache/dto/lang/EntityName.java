package com.chen.cache.dto.lang;

/**
 * @author chenguo
 * @date 2022/3/10 10:48 PM
 */

public enum EntityName {
    /**
     * student / course / score
     */
    student("student"),
    course("course"),
    score("score")
    ;

    private final String value;

    EntityName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
