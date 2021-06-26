package com.db.backoffice.dto;

import com.db.backoffice.dto.serializer.LocalDateDeserializer;
import com.db.backoffice.dto.serializer.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

//@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TradeDto {
    private String tradeId;
    private Long version;
    private String counterPartyId;
    private String bookId;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate maturityDate;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createdDate;
    private Character expired;
}
