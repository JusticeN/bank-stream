package com.db.frontoffice.service;

import com.db.frontoffice.dto.TradeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TradeServiceTest {
    private TradeService tradeService;

    @BeforeEach
    void setUp() {
        tradeService = new TradeService();
    }

    @Test
    void objectToJson() {
        TradeDto trade;
        trade = TradeDto.builder()
                .tradeId("16754432343")
                .version(12L)
                .counterPartyId("1")
                .bookId("1")
                .maturityDate(LocalDate.of(2021, 1, 1))
                .createdDate(LocalDate.of(2021, 1, 1))
                .expired(TradeDto.ExpiredEnum.N)
                .build();
        String result = tradeService.convertObjectToJson(trade);
        String expected = "{" +
                "\"tradeId\":\"16754432343\"," +
                "\"version\":12," +
                "\"counterPartyId\":\"1\"," +
                "\"bookId\":\"1\"," +
                "\"maturityDate\":\"01/01/2021\"," +
                "\"createdDate\":\"01/01/2021\"," +
                "\"expired\":\"N\"" +
                "}";
        assertEquals(expected, result);
    }

    @Test
    void validateOnlyTradeIDField() {
        TradeDto tradeDto = TradeDto.builder().tradeId("1").build();
        List<String> tradeViolations = tradeService.validateTrade(tradeDto);
        checkTradeDto(tradeViolations, tradeDto);
    }

    @Test
    void validateOnlyDate() {
        TradeDto tradeDto = TradeDto.builder().createdDate(LocalDate.of(2021, 11, 3)).build();
        List<String> tradeViolationsOnlzTradeID = tradeService.validateTrade(tradeDto);
        checkTradeDto(tradeViolationsOnlzTradeID, tradeDto);
    }
    @Test

    void validateTradeAllValid() {
        TradeDto tradeDto = TradeDto.builder()
                .tradeId("16754432343")
                .version(12L)
                .counterPartyId("1")
                .bookId("1")
                .maturityDate(LocalDate.of(2021, 1, 1))
                .createdDate(LocalDate.of(2021, 1, 1))
                .expired(TradeDto.ExpiredEnum.Y)
                .build();;
        List<String> tradeViolations = tradeService.validateTrade(tradeDto);
        checkTradeDto(tradeViolations, tradeDto);
    }

    private void checkTradeDto(List<String> tradeViolations, TradeDto tradeDto) {
        assertNotEquals(tradeDto.hasTradeID(), listContainsElementCheckEmpty(tradeViolations, TradeDto.TRADE_ID_NOT_EMPTY_MESSAGE), TradeDto.TRADE_ID_NOT_EMPTY_MESSAGE);
        assertNotEquals(tradeDto.hasVersion(), listContainsElementCheckEmpty(tradeViolations, TradeDto.VERSION_NOT_EMPTY_MESSAGE), TradeDto.VERSION_NOT_EMPTY_MESSAGE);
        assertNotEquals(tradeDto.hasCounterPartyId(), listContainsElementCheckEmpty(tradeViolations, TradeDto.COUNTER_PARTY_ID_NOT_EMPTY_MESSAGE), TradeDto.COUNTER_PARTY_ID_NOT_EMPTY_MESSAGE);
        assertNotEquals(tradeDto.hasBookId(), listContainsElementCheckEmpty(tradeViolations, TradeDto.BOOK_ID_NOT_EMPTY_MESSAGE), TradeDto.BOOK_ID_NOT_EMPTY_MESSAGE);
        assertNotEquals(tradeDto.hasMaturityDate(), listContainsElementCheckEmpty(tradeViolations, TradeDto.MATURITY_DATE_ID_NOT_EMPTY_MESSAGE), TradeDto.MATURITY_DATE_ID_NOT_EMPTY_MESSAGE);
        assertNotEquals(tradeDto.hasCreatedDate(), listContainsElementCheckEmpty(tradeViolations, TradeDto.CREATED_DATE_ID_NOT_EMPTY_MESSAGE), TradeDto.CREATED_DATE_ID_NOT_EMPTY_MESSAGE);
        assertNotEquals(tradeDto.hasExpired(), listContainsElementCheckEmpty(tradeViolations, TradeDto.EXPIRED_DATE_ID_NOT_EMPTY_MESSAGE), TradeDto.EXPIRED_DATE_ID_NOT_EMPTY_MESSAGE);
    }

    private boolean listContainsElementCheckEmpty(List<String> elements, String item) {
        if (Objects.isNull(elements) || elements.isEmpty() || item.isBlank()) {
            return false;
        }
        return elements.contains(item);
    }
}