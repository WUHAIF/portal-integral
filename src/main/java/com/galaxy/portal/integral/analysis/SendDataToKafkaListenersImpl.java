package com.galaxy.portal.integral.analysis;

import com.anscen.analysis.reader.analysis.listener.ReaderFileListener;
import com.anscen.analysis.reader.analysis.listener.impl.SendDataToKafkaListeners;
import com.anscen.analysis.reader.config.AnalysisProperties;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/7/1 11:21
 * @Description: TODO
 */
@Data
@Slf4j
@AllArgsConstructor
public class SendDataToKafkaListenersImpl extends SendDataToKafkaListeners {
    private static Gson gson = new Gson();

    private String topic;

    private KafkaTemplate kafkaTemplate;

    private AnalysisProperties analysisProperties;


    public SendDataToKafkaListenersImpl(String encode) {
        this.setEncode(encode);
    }

    public SendDataToKafkaListenersImpl() {
        this.setEncode("GBK");
        this.setReadColNum(500);
    }
    private SendDataToKafkaListenersImpl(SendDataToKafkaListenersBuild builder){
        this.setEncode(builder.encode);
        this.setReadColNum(builder.readColNum);
        this.kafkaTemplate=builder.kafkaTemplate;
        this.analysisProperties=builder.analysisProperties;
        this.topic = builder.topic;
    }




    @Override
    public void output(List<String> dataList) throws Exception {
        log.info("===||===" + dataList.toString());
        dataList.stream().forEach(
                data -> {
                    String[] split = data.split(analysisProperties.getSplitStr());
                    List<String> temList = Arrays.asList(split);
                    Map<String, String> rowData = new HashMap<>();
                    Stream.iterate(0, i -> i + 1).limit(temList.size()).forEach(i -> {
                        rowData.put("value" + i, StringUtils.trimToEmpty(temList.get(i)));
                    });
                    rowData.put("tableName", this.getTableName());
                    log.info("topic:" + topic + " ; " + "data:" + gson.toJson(rowData));
                    //发送消息
                    kafkaTemplate.send(topic, gson.toJson(rowData)).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                        @Override
                        public void onFailure(Throwable ex) {
                            System.out.println("发送消息失败："+ex.getMessage());
                        }

                        @Override
                        public void onSuccess(SendResult<String, String> result) {
                            System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                                    + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
                        }
                    });
                }
        );
    }

    @Override
    public ReaderFileListener cloneReaderFileListener(ReaderFileListener readerFileListener) {

        SendDataToKafkaListenersImpl sendDataToKafkaListeners = (SendDataToKafkaListenersImpl)readerFileListener;
        SendDataToKafkaListenersImpl clone = new SendDataToKafkaListenersImpl();
        clone.setEncode(sendDataToKafkaListeners.getEncode());
        clone.setAnalysisProperties(sendDataToKafkaListeners.getAnalysisProperties());
        clone.setKafkaTemplate(sendDataToKafkaListeners.getKafkaTemplate());
        clone.setTableName(sendDataToKafkaListeners.getTableName());
        clone.setTopic(sendDataToKafkaListeners.getTopic());
        return clone;

    }
    public static class SendDataToKafkaListenersBuild {

        private String topic;

        private KafkaTemplate kafkaTemplate;

        private AnalysisProperties analysisProperties;

        private String encode;

        private int readColNum;


        public SendDataToKafkaListenersBuild setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public SendDataToKafkaListenersBuild setKafkaTemplate(KafkaTemplate kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
            return this;
        }

        public SendDataToKafkaListenersBuild setAnalysisProperties(AnalysisProperties analysisProperties) {
            this.analysisProperties = analysisProperties;
            return this;
        }

        public SendDataToKafkaListenersBuild setReadColNum(int readColNum) {
            this.readColNum = readColNum;
            return this;
        }

        public SendDataToKafkaListenersBuild setEncode(String encode) {
            this.encode = encode;
            return this;
        }

        public SendDataToKafkaListenersImpl builder() {
            return new SendDataToKafkaListenersImpl(this);
        }

    }
}
