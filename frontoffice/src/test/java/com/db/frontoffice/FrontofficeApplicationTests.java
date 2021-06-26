package com.db.frontoffice;

import com.db.frontoffice.dto.TradeDto;
import com.db.frontoffice.exception.FailledValidationException;
import com.db.frontoffice.service.TradeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class FrontofficeApplicationTests {

	@Autowired
	private TradeService tradeService;
	@Autowired
	private SampleConsumer sampleConsumer;
	@Value("${app.kafka.topic-name}")
	private String topic;

	@BeforeEach
	public void setUp() throws JsonProcessingException {
		sampleConsumer.resetResult();
	}

	@Test
	void validateAndSendTradeToQueueNullTrade() {
		Assertions.assertThrows(
				NullPointerException.class,
				() -> tradeService.validateAndSendTradeToQueue(null),
				"Trade object is required");
		sleepFor2Seconds();
		List<String> consumerResult = sampleConsumer.getResult();
		Assertions.assertTrue(consumerResult.isEmpty());
	}

	@Test
	void validateAndSendTradeToQueueInvalidTrade() {
		var tradeDto = generateInvalidTradeDtoData();
		Assertions.assertThrows(
				FailledValidationException.class,
				() -> tradeService.validateAndSendTradeToQueue(tradeDto),
				"Trade object is required");
		sleepFor2Seconds();
		List<String> consumerResult = sampleConsumer.getResult();
		Assertions.assertTrue(consumerResult.isEmpty());
	}

	@Test
	void validateAndSendTradeToQueue() throws JsonProcessingException {
		var tradeDto = generateCorrectTradeDtoData();
		var json = new ObjectMapper().writeValueAsString(tradeDto);
		tradeService.validateAndSendTradeToQueue(tradeDto);
		sleepFor2Seconds();
		List<String> consumerResult = sampleConsumer.getResult();
		Assertions.assertFalse(consumerResult.isEmpty());
		Assertions.assertTrue(consumerResult.size() == 1);
		Assertions.assertEquals(json, consumerResult.get(0));
	}

	private void sleepFor2Seconds() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private TradeDto generateInvalidTradeDtoData() {
		var tradeDto = new TradeDto();
		tradeDto.setTradeId(null);
		tradeDto.setVersion(12L);
		tradeDto.setCounterPartyId(null);
		tradeDto.setBookId("bookID");
		tradeDto.setMaturityDate(LocalDate.now());
		tradeDto.setCreatedDate(LocalDate.now());
		tradeDto.setExpired(TradeDto.ExpiredEnum.Y);
		return tradeDto;
	}

	private TradeDto generateCorrectTradeDtoData() {
		var tradeDto = new TradeDto();
		tradeDto.setTradeId("2");
		tradeDto.setVersion(12L);
		tradeDto.setCounterPartyId("CounterID");
		tradeDto.setBookId("bookID");
		tradeDto.setMaturityDate(LocalDate.now());
		tradeDto.setCreatedDate(LocalDate.now());
		tradeDto.setExpired(TradeDto.ExpiredEnum.Y);
		return tradeDto;
	}
}
