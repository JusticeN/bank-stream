package com.db.backoffice.mapper;

import com.db.backoffice.dto.TradeDto;
import com.db.backoffice.model.TradeModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    TradeModel tradeDtoToTradeModel(TradeDto tradeDto);
}
