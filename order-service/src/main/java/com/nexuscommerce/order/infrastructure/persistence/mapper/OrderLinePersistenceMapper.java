package com.nexuscommerce.order.infrastructure.persistence.mapper;

import com.nexuscommerce.order.domain.OrderLine;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderLineJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLinePersistenceMapper {

    @Mapping(target = "order", ignore = true)
    OrderLineJpaEntity toEntity(OrderLine domain);

    OrderLine toDomain(OrderLineJpaEntity entity);
}
