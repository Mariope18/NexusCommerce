package com.nexuscommerce.order.infrastructure.rest.mapper;

import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderLineRestMapper.class)
public interface OrderRestMapper {

    OrderResponse toResponse(Order domain);
}
