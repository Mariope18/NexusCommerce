package com.nexuscommerce.order.application.mapper;

import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderJpaEntity;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring", uses = OrderLineMapper.class)
public interface OrderMapper {

    @BeanMapping(builder = @Builder(disableBuilder = true))
    OrderJpaEntity toEntity(Order domain);
    Order toDomain(OrderJpaEntity entity);

    OrderResponse toResponse(Order domain);

    void updateEntity(Order domain, @MappingTarget OrderJpaEntity entity);

    /**
     * Dopo il mapping Order -> OrderJpaEntity, sincronizza il lato bidirezionale:
     * setta il riferimento "order" su ciascuna OrderLineJpaEntity.
     * Senza questo passo, Hibernate non saprebbe quale ordine possiede le linee
     * e la FK order_id resterebbe null → errore di constraint violation.
     */
    @AfterMapping
    default void sincronizzaRelazioneBidirezionale(@MappingTarget OrderJpaEntity entity) {
        if (entity.getOrderLines() != null) {
            entity.getOrderLines().forEach(line -> line.setOrder(entity));
        }
    }
}
