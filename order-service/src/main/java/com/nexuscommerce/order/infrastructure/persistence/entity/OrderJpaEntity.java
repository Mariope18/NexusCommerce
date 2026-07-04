package com.nexuscommerce.order.infrastructure.persistence.entity;

import com.nexuscommerce.order.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderJpaEntity {

    @Id
    private UUID id;

    private UUID customerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @Builder.Default
    private Set<OrderLineJpaEntity> orderLines = new HashSet<>();

    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
