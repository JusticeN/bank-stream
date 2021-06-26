package com.db.frontoffice.controller;

import com.db.frontoffice.dto.TradeDto;
import com.db.frontoffice.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class TradeController {
    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping(value = "/trade",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity receiveTrade(@RequestBody TradeDto tradeDto) {
        log.info("receiveTrade: {}", tradeDto);
        tradeService.validateAndSendTradeToQueue(tradeDto);
        return ResponseEntity.accepted().build();
    }
}
