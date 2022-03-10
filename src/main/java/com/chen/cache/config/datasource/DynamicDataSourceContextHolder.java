package com.chen.cache.config.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chenguo
 * @date 2022/3/10 8:19 PM
 */

public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return SourceName.read.value();
        }
    };

    public static List<Object> dataSourceKeys = new ArrayList<>();

    public static void setDataSourceKey(String key){
        CONTEXT_HOLDER.set(key);
    }

    public static String getDataSourceKey(){
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceKey(){
        CONTEXT_HOLDER.remove();
    }

    public static boolean containsDataSourceKey(String key){
        return dataSourceKeys.contains(key);
    }

    public static void addDataSourceKeys(Collection<?> keys){
        dataSourceKeys.addAll(keys);
    }
}
