package com.nexuscommerce.order.application.service;

import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.domain.OrderLine;
import com.nexuscommerce.order.domain.ports.out.OrderRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Servizio applicativo che coordina i casi d'uso relativi agli Ordini.
 * È completamente disaccoppiato dall'infrastruttura (JPA, REST API).
 * Lavora unicamente con oggetti di Dominio ed interfacce (Porte).
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderApplicationService {

    private final OrderRepositoryPort orderRepositoryPort;

    @Transactional
    public UUID createOrder(UUID customerId) {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .build();

        Order savedOrder = orderRepositoryPort.save(order);
        return savedOrder.getId();
    }

    public Order getOrderById(UUID orderId) {
        return orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order con id %s non trovato", orderId)));
    }

    @Transactional
    public UUID addOrderLine(UUID orderId, String skuCode, BigDecimal price, Long quantity) {
        Order order = orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order con id %s non trovato", orderId)));

        OrderLine orderLine = OrderLine.builder()
                .id(UUID.randomUUID())
                .skuCode(skuCode)
                .price(price)
                .quantity(quantity)
                .build();

        order.aggiungiOrderLine(orderLine);

        orderRepositoryPort.save(order);

        return orderLine.getId();
    }
}
