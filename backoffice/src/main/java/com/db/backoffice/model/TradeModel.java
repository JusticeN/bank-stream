package com.db.backoffice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"trade_id", "version"}))
@Entity
@Data
@NoArgsConstructor
@IdClass(TradeID.class)
public class TradeModel {
    @Id
    @Column(name = "trade_id")
    private String tradeId;
    @Id
    @Column(name = "version")
    private Long version;
    private String counterPartyId;
    private String bookId;
    private LocalDate maturityDate;
    private LocalDate createdDate;
    @Column(length = 1)
    private Character expired;
    //    @CreatedDate private LocalDate createdAt;
//    @LastModifiedDate private LocalDate updatedAt;

}
