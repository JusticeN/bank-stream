package com.db.frontoffice.controller;

import com.db.frontoffice.dto.TradeDto;
import com.db.frontoffice.exception.FailledValidationException;
import com.db.frontoffice.service.TradeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class TradeController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TradeController.class);

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping(value = "/trade",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity receiveTrade(@RequestBody(required = false) TradeDto tradeDto) {
        log.info("receiveTrade: {}", tradeDto);
        try {
            Objects.requireNonNull(tradeDto, "Trade object is required");
            tradeService.receiveTrade(tradeDto);
        } catch (FailledValidationException exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(exception.getErrorData());
        } catch (NullPointerException nullPointerException) {
            var messages = List.of(nullPointerException.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new FailledValidationException(messages).getErrorData());
        }
        return ResponseEntity.accepted().build();
    }
}
