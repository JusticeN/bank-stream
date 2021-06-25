package com.db.frontoffice.service;

import com.db.frontoffice.dto.TradeDto;
import com.db.frontoffice.exception.FailledValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TradeService {

    @Value("${app.kafka.topic-name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public List<String> validateTrade(TradeDto tradeDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TradeDto>> constraintViolations = validator.validate(tradeDto);
        return constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }
    public String convertObjectToJson(TradeDto tradeDto) {
        String msg = null;
        try {
            var om = new ObjectMapper();
            msg = om.writeValueAsString(tradeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public void sendMessage(String msg) {
        var result = kafkaTemplate.send(topicName, msg);
        result.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + msg +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + msg + "] due to : " + ex.getMessage());
            }
        });
    }

    public void receiveTrade(TradeDto tradeDto) throws FailledValidationException {
        List<String> violations = validateTrade(tradeDto);
        if (!violations.isEmpty()) {
            throw new FailledValidationException(violations);
        }
        var tradeDtoToJsonString = convertObjectToJson(tradeDto);
        sendMessage(tradeDtoToJsonString);

    }

}
