package com.db.backoffice.stream;

import com.db.backoffice.dto.TradeDto;
import com.db.backoffice.mapper.TradeMapper;
import com.db.backoffice.model.TradeID;
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

    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    public TradeConsumer(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    private TradeDto parseStringToTradeDto(String data) {
        TradeDto tradeDto;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            tradeDto = mapper.readValue(data, TradeDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return tradeDto;
    }

    @KafkaHandler(isDefault = true)
    public void listenToTopicAsStringAndSaveToDatabase(String data) {
        log.info("#Received Message: {}", data);
        TradeDto tradeDto = parseStringToTradeDto(data);
        TradeModel trade = tradeMapper.tradeDtoToTradeModel(tradeDto);
        TradeID tradeId = new TradeID(tradeDto.getTradeId(), trade.getVersion());
        if(tradeRepository.existsById(tradeId)) {
            log.info("Can not save duplicate Entity {}", trade);
            return;
        }
        tradeRepository.save(trade);
        log.info("#saved Model trade: {}", trade);
    }

}
