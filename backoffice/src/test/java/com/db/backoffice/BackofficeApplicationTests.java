package com.db.backoffice;

import com.db.backoffice.dto.TradeDto;
import com.db.backoffice.kafka.TestKafkaProducer;
import com.db.backoffice.model.TradeModel;
import com.db.backoffice.repository.TradeRepository;
import com.db.backoffice.stream.TradeConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.LocalDate;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class BackofficeApplicationTests {

    private Logger log = LoggerFactory.getLogger(BackofficeApplicationTests.class);
    @Autowired
    TestKafkaProducer testKafkaProducer;
    @Autowired
    TradeConsumer tradeConsumer;
    @Value("${trade.topic}")
    private String topic;
    @Autowired
    TradeRepository repository;
    private TradeDto tradeDto;
    private String json;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        tradeDto = new TradeDto();
        tradeDto.setTradeId("2");
        tradeDto.setVersion(12L);
        tradeDto.setCounterPartyId("CounterID");
        tradeDto.setBookId("bookID");
        tradeDto.setMaturityDate(LocalDate.now());
        tradeDto.setCreatedDate(LocalDate.now());
        tradeDto.setExpired('Y');
        json = new ObjectMapper().writeValueAsString(tradeDto);
        repository.deleteAll();
    }

    @Test
    public void testCorrectTradeConsume() {
        testKafkaProducer.send(topic, json);
        sleepFor2Seconds();
        var savedTrades = repository.findAll();
        log.info("savedTrades: {}", savedTrades);
        Assertions.assertFalse(savedTrades.isEmpty());
        Assertions.assertTrue(savedTrades.size() == 1);
        TradeModel tradeModel = savedTrades.get(0);
        assertTrade(tradeModel);
    }

    @Test
    public void testInCorrectTradeConsume() {
        testKafkaProducer.send(topic, "Incorrect Data");
        sleepFor2Seconds();
        var savedTrades = repository.findAll();
        log.info("savedTrades: {}", savedTrades);
        Assertions.assertTrue(savedTrades.isEmpty());
        Assertions.assertTrue(savedTrades.size() == 0);
    }


    @Test
    public void testDuplicateTradeConsume() {
        testKafkaProducer.send(topic, json);
        sleepFor2Seconds();
        sleepFor2Seconds();
        var savedTrades = repository.findAll();
        log.info("savedTrades: {}", savedTrades);
        Assertions.assertFalse(savedTrades.isEmpty());
        Assertions.assertTrue(savedTrades.size() == 1);
        TradeModel tradeModel = savedTrades.get(0);
        assertTrade(tradeModel);
        // save the same trade again
        testKafkaProducer.send(topic, json);
        sleepFor2Seconds();
        savedTrades = repository.findAll();
        Assertions.assertTrue(savedTrades.size() == 1);
        tradeModel = savedTrades.get(0);
        assertTrade(tradeModel);
    }

    private void sleepFor2Seconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void assertTrade(TradeModel actual) {
        Assertions.assertEquals(tradeDto.getTradeId(), actual.getTradeId());
        Assertions.assertEquals(tradeDto.getVersion(), actual.getVersion());
        Assertions.assertEquals(tradeDto.getCounterPartyId(), actual.getCounterPartyId());
        Assertions.assertEquals(tradeDto.getBookId(), actual.getBookId());
        Assertions.assertEquals(tradeDto.getMaturityDate(), actual.getMaturityDate());
        Assertions.assertEquals(tradeDto.getCreatedDate(), actual.getCreatedDate());
        Assertions.assertEquals(tradeDto.getExpired(), actual.getExpired());
    }

}
