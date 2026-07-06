package com.nexuscommerce.order.infrastructure.rest.dto;

import com.nexuscommerce.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record OrderResponse(

        UUID id,
        UUID customerId,
        OrderStatus orderStatus,
        Set<OrderLineResponse> orderLines,
        BigDecimal totalAmount

) {
}
