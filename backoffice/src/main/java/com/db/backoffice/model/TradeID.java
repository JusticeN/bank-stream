package com.db.backoffice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
@Data
public class TradeID implements Serializable {
    private String tradeId;
    private Long version;

    public TradeID() {
    }

    public TradeID(String tradeId, Long version) {
        this.tradeId = tradeId;
        this.version = version;
    }
}
