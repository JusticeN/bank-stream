package com.db.backoffice.mapper;

import com.db.backoffice.dto.TradeDto;
import com.db.backoffice.model.TradeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    @Mapping(target = "id", ignore = true)
    TradeModel tradeDtoToTradeModel(TradeDto tradeDto);
}
