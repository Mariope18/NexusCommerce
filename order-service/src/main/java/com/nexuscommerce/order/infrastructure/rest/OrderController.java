package com.nexuscommerce.order.infrastructure.rest;

import com.nexuscommerce.order.application.service.OrderApplicationService;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderCreationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderCreationRequest request) {
        UUID customerId = orderApplicationService.createOrder(request.customerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
    }
}
