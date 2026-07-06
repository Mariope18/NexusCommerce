package com.nexuscommerce.order.application.mapper;

import com.nexuscommerce.order.domain.OrderLine;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderLineJpaEntity;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderLineResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {

    OrderLineJpaEntity toEntity(OrderLine domain);
    OrderLine toDomain(OrderLineJpaEntity entity);

    OrderLineResponse toResponse(OrderLine domain);
}
