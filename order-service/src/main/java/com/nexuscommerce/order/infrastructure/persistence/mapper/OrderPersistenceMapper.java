package com.nexuscommerce.order.infrastructure.persistence.mapper;

import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.infrastructure.persistence.entity.OrderJpaEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring", uses = OrderLinePersistenceMapper.class)
public interface OrderPersistenceMapper {

    @BeanMapping(builder = @Builder(disableBuilder = true))
    OrderJpaEntity toEntity(Order domain);
    
    Order toDomain(OrderJpaEntity entity);

    void updateEntity(Order domain, @MappingTarget OrderJpaEntity entity);

    @AfterMapping
    default void sincronizzaRelazioneBidirezionale(@MappingTarget OrderJpaEntity entity) {
        if (entity.getOrderLines() != null) {
            entity.getOrderLines().forEach(line -> line.setOrder(entity));
        }
    }
}
