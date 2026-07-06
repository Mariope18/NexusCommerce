package com.nexuscommerce.order.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderLineResponse(
        UUID id,
        String skuCode,
        BigDecimal price,
        Long quantity
) {
}
