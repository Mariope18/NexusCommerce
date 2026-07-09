package com.nexuscommerce.order.infrastructure.persistence.adapter;

import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.domain.ports.out.OrderRepositoryPort;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderJpaEntity;
import com.nexuscommerce.order.infrastructure.persistence.mapper.OrderPersistenceMapper;
import com.nexuscommerce.order.infrastructure.persistence.repository.OrderSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adattatore di persistenza che implementa la porta di dominio OrderRepositoryPort.
 * Converte i dati tra il Dominio e l'Infrastruttura JPA ed esegue il salvataggio reale.
 */
@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final OrderSpringRepository orderSpringRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Order save(Order order) {
        Optional<OrderJpaEntity> existingEntityOpt = orderSpringRepository.findById(order.getId());
        
        OrderJpaEntity entityToSave;
        if (existingEntityOpt.isPresent()) {
            OrderJpaEntity existingEntity = existingEntityOpt.get();
            orderPersistenceMapper.updateEntity(order, existingEntity);
            entityToSave = existingEntity;
        } else {
            entityToSave = orderPersistenceMapper.toEntity(order);
        }
        
        OrderJpaEntity savedEntity = orderSpringRepository.save(entityToSave);
        return orderPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderSpringRepository.findById(id)
                .map(orderPersistenceMapper::toDomain);
    }
}
