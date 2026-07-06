package com.nexuscommerce.order.application.mapper;

import com.nexuscommerce.order.domain.OrderLine;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderLineJpaEntity;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderLineResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {

    /**
     * Dominio -> Entity.
     * Il campo "order" viene ignorato perché è una preoccupazione JPA:
     * sarà settato da OrderMapper.sincronizzaRelazioneBidirezionale().
     */
    @Mapping(target = "order", ignore = true)
    OrderLineJpaEntity toEntity(OrderLine domain);

    OrderLine toDomain(OrderLineJpaEntity entity);

    OrderLineResponse toResponse(OrderLine domain);
}
