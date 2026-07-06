package com.nexuscommerce.order.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Value Object che rappresenta una linea d'ordine.
 * È immutabile: una volta creato, i suoi campi non possono essere modificati.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Solo per JPA/MapStruct, non per uso diretto
public class OrderLine {

    private UUID id;
    private String skuCode;
    private BigDecimal price;
    private Long quantity;

    @Builder
    private OrderLine(UUID id, String skuCode, BigDecimal price, Long quantity) {
        Objects.requireNonNull(id, "L'id della linea d'ordine non può essere null");
        Objects.requireNonNull(skuCode, "Lo skuCode non può essere null");
        Objects.requireNonNull(price, "Il prezzo non può essere null");
        Objects.requireNonNull(quantity, "La quantità non può essere null");

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Il prezzo non può essere negativo");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantità deve essere maggiore di zero");
        }

        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }
}
