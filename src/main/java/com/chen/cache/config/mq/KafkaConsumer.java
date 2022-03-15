package com.chen.cache.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author chenguo
 * @date 2022/3/11 11:53 AM
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = {"hello2"})
    public void listen(ConsumerRecord<?, ?> record){
        Optional.ofNullable(record.value())
                .ifPresent(message -> {
                    log.info("【+++++++++++++++++ record = {} 】", record);
                    log.info("【+++++++++++++++++ message = {}】", message);
                });
    }
}
