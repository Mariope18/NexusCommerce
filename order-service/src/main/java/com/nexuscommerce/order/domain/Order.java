package com.nexuscommerce.order.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Aggregato Root dell'ordine.
 * Protegge i propri invarianti: lo stato può cambiare solo attraverso metodi di business
 * e la collezione di OrderLine non è modificabile dall'esterno.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    private UUID id;

    private UUID customerId;

    private OrderStatus orderStatus;

    private Set<OrderLine> orderLines = new HashSet<>();

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Builder
    private Order(UUID id, UUID customerId) {
        Objects.requireNonNull(id, "L'id dell'ordine non può essere null");
        Objects.requireNonNull(customerId, "Il customerId non può essere null");

        this.id = id;
        this.customerId = customerId;
        this.orderStatus = OrderStatus.PENDING;
    }

    /**
     * Restituisce una vista non modificabile delle linee d'ordine.
     * Impedisce che codice esterno manipoli direttamente la collezione.
     */
    public Set<OrderLine> getOrderLines() {
        return Collections.unmodifiableSet(orderLines);
    }

    /**
     * Aggiunge una linea d'ordine e ricalcola automaticamente il totale.
     * Questo è l'unico modo per aggiungere linee: passando attraverso il dominio.
     */
    public void aggiungiOrderLine(OrderLine orderLine) {
        Objects.requireNonNull(orderLine, "La linea d'ordine non può essere null");
        this.orderLines.add(orderLine);
        ricalcolaTotale();
    }

    /**
     * Transizione di stato: completa l'ordine.
     * Può essere chiamato solo se l'ordine è in stato PENDING.
     */
    public void completaOrdine() {
        if (this.orderStatus != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    String.format("Impossibile completare un ordine in stato %s", this.orderStatus));
        }
        this.orderStatus = OrderStatus.COMPLETED;
    }

    /**
     * Transizione di stato: cancella l'ordine.
     * Può essere chiamato solo se l'ordine è in stato PENDING.
     */
    public void cancellaOrdine() {
        if (this.orderStatus != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    String.format("Impossibile cancellare un ordine in stato %s", this.orderStatus));
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }

    /**
     * Ricalcolo del totale — metodo privato, dettaglio implementativo dell'aggregato.
     */
    private void ricalcolaTotale() {
        this.totalAmount = this.orderLines.stream()
                .map(line -> line.getPrice().multiply(BigDecimal.valueOf(line.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
