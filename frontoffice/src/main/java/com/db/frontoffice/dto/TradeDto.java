package com.db.frontoffice.dto;

import com.db.frontoffice.dto.serializer.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TradeDto {

    public static final String TRADE_ID_NOT_EMPTY_MESSAGE = "tradeId should not be empty";
    public static final String VERSION_NOT_EMPTY_MESSAGE = "version should not be empty";
    public static final String COUNTER_PARTY_ID_NOT_EMPTY_MESSAGE = "counterPartyId should not be empty";
    public static final String BOOK_ID_NOT_EMPTY_MESSAGE = "bookId should not be empty";
    public static final String MATURITY_DATE_ID_NOT_EMPTY_MESSAGE = "maturityDate should not be empty";
    public static final String CREATED_DATE_ID_NOT_EMPTY_MESSAGE = "createdDate should not be empty";
    public static final String EXPIRED_DATE_ID_NOT_EMPTY_MESSAGE = "expired should not be empty";

    @NotNull(message = TRADE_ID_NOT_EMPTY_MESSAGE)
    private String tradeId;
    @NotNull(message = VERSION_NOT_EMPTY_MESSAGE)
    private Long version;
    @NotNull(message = COUNTER_PARTY_ID_NOT_EMPTY_MESSAGE)
    private String counterPartyId;
    @NotNull(message = BOOK_ID_NOT_EMPTY_MESSAGE)
    private String bookId;
    @NotNull(message = MATURITY_DATE_ID_NOT_EMPTY_MESSAGE)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate maturityDate;
    @NotNull(message = CREATED_DATE_ID_NOT_EMPTY_MESSAGE)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdDate;
    @NotNull(message = EXPIRED_DATE_ID_NOT_EMPTY_MESSAGE)
    private Character expired;

    public boolean hasTradeID() { return Objects.nonNull(tradeId); }
    public boolean hasVersion() { return Objects.nonNull(version); }
    public boolean hasCounterPartyId() { return Objects.nonNull(counterPartyId); }
    public boolean hasBookId() { return Objects.nonNull(bookId); }
    public boolean hasMaturityDate() { return Objects.nonNull(maturityDate); }
    public boolean hasCreatedDate() { return Objects.nonNull(createdDate); }
    public boolean hasExpired() { return Objects.nonNull(expired); }
}
