package com.nexuscommerce.order.infrastructure.persistence.repository;

import com.nexuscommerce.order.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderJpaEntity,UUID> {
}
