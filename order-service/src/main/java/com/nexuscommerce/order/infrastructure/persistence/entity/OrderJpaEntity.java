package com.nexuscommerce.order.infrastructure.persistence.entity;

import com.nexuscommerce.order.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    /**
     * Lato "inverse" (mappedBy) della relazione bidirezionale.
     * - mappedBy = "order": Hibernate delega la gestione della FK a OrderLineJpaEntity.order
     * - cascade = ALL: le operazioni su Order si propagano alle linee (persist, merge, remove)
     * - orphanRemoval = true: se rimuovi una linea dalla collezione, viene cancellata dal DB
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OrderLineJpaEntity> orderLines = new HashSet<>();

    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // ----- Metodi helper per sincronizzare i due lati della relazione -----

    /**
     * Aggiunge una linea d'ordine mantenendo sincronizzati entrambi i lati
     * della relazione bidirezionale. Senza questo metodo, se fai solo
     * orderLines.add(line) senza settare line.setOrder(this),
     * Hibernate non persisterà la FK.
     */
    public void addOrderLine(OrderLineJpaEntity line) {
        orderLines.add(line);
        line.setOrder(this);
    }

    /**
     * Rimuove una linea d'ordine mantenendo sincronizzati entrambi i lati.
     * orphanRemoval = true fa sì che Hibernate esegua la DELETE sul DB.
     */
    public void removeOrderLine(OrderLineJpaEntity line) {
        orderLines.remove(line);
        line.setOrder(null);
    }
}
