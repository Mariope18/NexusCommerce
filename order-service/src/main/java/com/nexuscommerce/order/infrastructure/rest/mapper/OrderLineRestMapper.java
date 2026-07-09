package com.nexuscommerce.order.infrastructure.rest.mapper;

import com.nexuscommerce.order.domain.OrderLine;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderLineResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderLineRestMapper {

    OrderLineResponse toResponse(OrderLine domain);
}
