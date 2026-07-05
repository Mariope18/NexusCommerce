package com.nexuscommerce.order.application.service.impl;

import com.nexuscommerce.order.application.mapper.OrderLineMapper;
import com.nexuscommerce.order.application.mapper.OrderMapper;
import com.nexuscommerce.order.application.service.OrderApplicationService;
import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.domain.OrderLine;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderJpaEntity;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderLineJpaEntity;
import com.nexuscommerce.order.infrastructure.persistence.repository.OrderRepository;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderLineRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    private final OrderLineMapper orderLineMapper;

    @Override
    @Transactional
    public UUID createOrder(UUID customerId) {

        Order order = Order.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .build();

        OrderJpaEntity orderEntity = orderMapper.toEntity(order);

        OrderJpaEntity savedOrder = orderRepository.save(orderEntity);

        return savedOrder.getId();
    }

    @Override
    public Order findOrderById(UUID orderId) {

        OrderJpaEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order con id %s non trovato", orderId)));

        return orderMapper.toDomain(orderEntity);
    }

    @Override
    @Transactional
    public UUID createOrderLine(UUID orderId, OrderLineRequest request) {

        OrderJpaEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order con id %s non trovato", orderId)));

        Order order = orderMapper.toDomain(orderEntity);

        OrderLine orderLine = OrderLine.builder()
                .id(UUID.randomUUID())
                .skuCode(request.skuCode())
                .price(request.price())
                .quantity(request.quantity())
                .build();


        order.aggiungiOrderLine(orderLine);

        OrderJpaEntity savedOrder = orderMapper.toEntity(order);

        orderRepository.save(savedOrder);

        return orderLine.getId();
    }
}
