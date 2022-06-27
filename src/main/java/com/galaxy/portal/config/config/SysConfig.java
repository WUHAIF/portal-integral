package com.galaxy.portal.config.config;

import com.anscen.analysis.reader.analysis.listener.ReaderFileListener;
import com.anscen.analysis.reader.config.AnalysisProperties;
import com.galaxy.portal.integral.analysis.SendDataToKafkaListenersImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/7/3 10:19
 * @Description: TODO
 */
@Configuration
public class SysConfig {

    @Autowired
    private AnalysisProperties analysisProperties;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Bean
    public ReaderFileListener sendDataToKafkaListeners() {
        SendDataToKafkaListenersImpl sendDataToKafkaListeners = new SendDataToKafkaListenersImpl();
        sendDataToKafkaListeners.setEncode("GBK");
        sendDataToKafkaListeners.setAnalysisProperties(analysisProperties);
        sendDataToKafkaListeners.setKafkaTemplate(kafkaTemplate);
        sendDataToKafkaListeners.setTopic(analysisProperties.getTopic());
        return sendDataToKafkaListeners;
    }

}
