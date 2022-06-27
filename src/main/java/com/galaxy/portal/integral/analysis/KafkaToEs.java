package com.galaxy.portal.integral.analysis;

import com.anscen.analysis.reader.analysis.listener.CosumerListener;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/7/5 0:16
 * @Description: TODO
 */
@Slf4j
@Component
public class KafkaToEs {

    @Resource
    private Gson gson;
    @KafkaListener(topics = "${analysis.file.topic}",groupId = "test",id="kafkaToES", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(List<ConsumerRecord<String, String>> records){
        for (ConsumerRecord<String, String> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {
                String message = record.value();
                String topic = record.topic();
                log.info("message1 = {}",  message);
                Map<String,Object> map = gson.fromJson(message.substring(1,message.length()-1),Map.class);
                log.info("message2 = {}",  message);
            }
        }
    }
}
