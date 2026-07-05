package com.nexuscommerce.order.application.mapper;

import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderJpaEntity toEntity(Order domain);
    Order toDomain(OrderJpaEntity entity);
}
