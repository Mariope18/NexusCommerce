package com.nexuscommerce.order.application.service;

import com.nexuscommerce.order.infrastructure.rest.dto.OrderLineRequest;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderResponse;

import java.util.UUID;

public interface OrderApplicationService {

    UUID createOrder(UUID customerId);

    OrderResponse getOrderById(UUID orderId);

    UUID addOrderLine(UUID orderId, OrderLineRequest request);
}
