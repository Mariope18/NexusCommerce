package com.nexuscommerce.order.application.service;

import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderJpaEntity;

import java.util.UUID;

public interface OrderApplicationService {

    UUID createOrder(UUID customerId);
}
