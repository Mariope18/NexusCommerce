package com.nexuscommerce.order.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    private UUID id;

    private UUID customerId;

    @Setter
    private OrderStatus orderStatus;

    private Set<OrderLine> orderLines = new HashSet<>();

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Builder
    private Order(UUID id, UUID customerId) {
        this.id = id;
        this.customerId = customerId;
        this.orderStatus = OrderStatus.PENDING;
    }


    public void aggiungiOrderLine(OrderLine orderLine) {
        this.orderLines.add(orderLine);
        this.totalAmount = calcoloTotalAmount();
    }

    public BigDecimal calcoloTotalAmount() {
        this.totalAmount = this.orderLines.stream()
                .map(line -> {
                    BigDecimal p = line.getPrice() != null ? line.getPrice() : BigDecimal.ZERO;
                    long q = line.getQuantity() != null ? line.getQuantity() : 0L;
                    return p.multiply(BigDecimal.valueOf(q));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return this.totalAmount;
    }
}
