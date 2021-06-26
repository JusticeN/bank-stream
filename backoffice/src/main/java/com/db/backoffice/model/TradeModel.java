package com.db.backoffice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class TradeModel {
    @Id @GeneratedValue
    private Long id;
    private String tradeId;
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
