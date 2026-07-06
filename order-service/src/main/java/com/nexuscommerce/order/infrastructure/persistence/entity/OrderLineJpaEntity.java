package com.nexuscommerce.order.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    /**
     * Lato "owning" della relazione bidirezionale.
     * È questo campo che Hibernate usa per generare la colonna FK "order_id"
     * nella tabella order_lines, eliminando gli UPDATE extra.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;
}
