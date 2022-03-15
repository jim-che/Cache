package com.chen.cache.dto.lang;

import orestes.bloomfilter.CountingBloomFilter;
import orestes.bloomfilter.FilterBuilder;
import org.springframework.stereotype.Component;

/**
 * @author chenguo
 * @date 2022/3/15 11:41 上午
 */
@Component
public class Filter {
    private final CountingBloomFilter<String> bloomFilter;

    public Filter(){
        bloomFilter = new FilterBuilder(10000,
                0.01).countingBits(8).buildCountingBloomFilter();
    }

    public void add(String word){
        bloomFilter.add(word);
    }

    public boolean contains(String word){
        return bloomFilter.contains(word);
    }

    public void remove(String word){
        bloomFilter.remove(word);
    }
}
