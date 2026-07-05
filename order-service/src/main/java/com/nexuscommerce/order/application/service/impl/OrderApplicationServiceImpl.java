package com.nexuscommerce.order.application.service.impl;

import com.nexuscommerce.order.application.mapper.OrderMapper;
import com.nexuscommerce.order.application.service.OrderApplicationService;
import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.domain.OrderStatus;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderJpaEntity;
import com.nexuscommerce.order.infrastructure.persistence.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public UUID createOrder(UUID customerId) {

        Order order = Order.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .build();

        OrderJpaEntity orderJpa = orderMapper.toEntity(order);

        OrderJpaEntity savedOrder = orderRepository.save(orderJpa);

        return savedOrder.getId();
    }
}
