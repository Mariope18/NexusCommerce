package com.nexuscommerce.order.application.mapper;

import com.nexuscommerce.order.domain.OrderLine;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderLineJpaEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OrderLineMapper {

    OrderLineJpaEntity toEntity(OrderLine domain);

    OrderLine toDomain(OrderLineJpaEntity entity);
}
