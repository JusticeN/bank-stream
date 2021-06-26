package com.db.backoffice.stream;

import com.db.backoffice.config.ConsumerKafkaConfig;
import com.db.backoffice.dto.TradeDto;
import com.db.backoffice.mapper.TradeMapper;
import com.db.backoffice.model.TradeModel;
import com.db.backoffice.repository.TradeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@KafkaListener(topics = "${trade.topic}")
@Slf4j
public class TradeConsumer {

    private final ObjectMapper mapper = new ObjectMapper();
    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    public TradeConsumer(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    @KafkaHandler(isDefault = true)
    public void listenToTopicAsStringAndSaveToDatabase(String data) {
        log.info("#Received Message foo: {}", data);
        TradeDto tradeDto;
        try {
            tradeDto = mapper.readValue(data, TradeDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        log.info("#converted Message tradeDto: {}", tradeDto);
        TradeModel trade = tradeMapper.tradeDtoToTradeModel(tradeDto);
        log.info("#converted Model trade: {}", trade);
        tradeRepository.save(trade);
    }

}
