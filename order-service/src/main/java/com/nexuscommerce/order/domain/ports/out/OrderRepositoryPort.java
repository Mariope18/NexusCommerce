package com.nexuscommerce.order.domain.ports.out;

import com.nexuscommerce.order.domain.Order;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta di output per la persistenza degli ordini.
 * Definisce i metodi che lo strato applicativo usa per comunicare con il database.
 * Lavora esclusivamente con modelli di dominio puri.
 */
public interface OrderRepositoryPort {
    
    Order save(Order order);
    
    Optional<Order> findById(UUID id);
}
