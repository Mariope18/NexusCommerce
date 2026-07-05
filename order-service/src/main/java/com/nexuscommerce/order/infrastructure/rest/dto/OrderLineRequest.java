package com.nexuscommerce.order.infrastructure.rest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderLineRequest(

        @NotBlank
        String skuCode,

        @NotNull
        @Positive
        BigDecimal price,

        @NotNull
        @Positive
        Long quantity
) {
}
