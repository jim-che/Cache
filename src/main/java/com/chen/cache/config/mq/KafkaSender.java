package com.chen.cache.config.mq;

import com.chen.cache.dto.lang.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author chenguo
 * @date 2022/3/11 11:49 AM
 */
@Component
@Slf4j
public class KafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private Gson gson = new GsonBuilder().create();

    public void send(String msg){
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMessage(msg);
        message.setSendTime(new Date());
        log.info("【++++++++++++++++++ message ：{}】", gson.toJson(message));
        kafkaTemplate.send("hello2",gson.toJson(message));
    }



}
