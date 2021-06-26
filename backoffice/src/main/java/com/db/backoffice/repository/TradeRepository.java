package com.db.backoffice.repository;

import com.db.backoffice.model.TradeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeModel, Long> {
}
