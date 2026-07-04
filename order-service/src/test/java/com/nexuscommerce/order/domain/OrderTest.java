package com.nexuscommerce.order.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void whenCreatingOrderWithBuilder_thenFieldsAreInitializedCorrectly() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        Order order = Order.builder()
                .id(orderId)
                .customerId(customerId)
                .orderStatus(OrderStatus.PENDING)
                .build();

        assertEquals(orderId, order.getId());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        assertNotNull(order.getOrderLines());
        assertTrue(order.getOrderLines().isEmpty());
        assertEquals(0, BigDecimal.ZERO.compareTo(order.getTotalAmount()));
    }

    @Test
    void whenAddingOrderLine_thenTotalAmountIsCalculatedCorrectly() {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .orderStatus(OrderStatus.PENDING)
                .build();

        OrderLine line1 = OrderLine.builder()
                .id(UUID.randomUUID())
                .skuCode("SKU-100")
                .price(new BigDecimal("10.50"))
                .quantity(2L) // 10.50 * 2 = 21.00
                .build();

        OrderLine line2 = OrderLine.builder()
                .id(UUID.randomUUID())
                .skuCode("SKU-200")
                .price(new BigDecimal("5.00"))
                .quantity(3L) // 5.00 * 3 = 15.00
                .build();

        order.aggiungiOrderLine(line1);
        order.aggiungiOrderLine(line2);

        assertEquals(2, order.getOrderLines().size());
        assertEquals(0, new BigDecimal("36.00").compareTo(order.getTotalAmount()));
    }

    @Test
    void whenAddingOrderLineWithNullPriceOrQuantity_thenTotalAmountIsCalculatedSafely() {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .orderStatus(OrderStatus.PENDING)
                .build();

        OrderLine lineWithNullPrice = OrderLine.builder()
                .id(UUID.randomUUID())
                .skuCode("SKU-100")
                .price(null)
                .quantity(2L)
                .build();

        OrderLine lineWithNullQuantity = OrderLine.builder()
                .id(UUID.randomUUID())
                .skuCode("SKU-200")
                .price(new BigDecimal("10.00"))
                .quantity(null)
                .build();

        order.aggiungiOrderLine(lineWithNullPrice);
        order.aggiungiOrderLine(lineWithNullQuantity);

        assertEquals(2, order.getOrderLines().size());
        assertEquals(0, BigDecimal.ZERO.compareTo(order.getTotalAmount()));
    }
}
