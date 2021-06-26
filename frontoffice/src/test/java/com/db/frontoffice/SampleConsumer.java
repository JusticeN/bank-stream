package com.db.frontoffice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@KafkaListener(topics = "${app.kafka.topic-name}", groupId = "${app.kafka.group-id}")
public class SampleConsumer {
    private Logger log = LoggerFactory.getLogger(SampleConsumer.class);

    private List<String> result = new ArrayList<>();

    public void resetResult() {
        result.clear();
    }

    public List<String> getResult() {
        return result;
    }

    @KafkaHandler(isDefault = true)
    public void listenToTopicAsStringAndSaveToDatabase(String data) {
        log.info("#Received test Message: {}", data);
        result.add(data);
    }
}
