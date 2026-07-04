package com.nexuscommerce.order.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_lines")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineJpaEntity {

    @Id
    private UUID id;

    private String skuCode;

    private BigDecimal price;

    private Long quantity;
}
