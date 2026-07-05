package com.nexuscommerce.order.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderCreationRequest(

        @NotNull
        UUID customerId
) {
}
