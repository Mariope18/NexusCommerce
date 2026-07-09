package com.nexuscommerce.order.infrastructure.rest;

import com.nexuscommerce.order.application.service.OrderApplicationService;
import com.nexuscommerce.order.domain.Order;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderCreationRequest;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderLineRequest;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderResponse;
import com.nexuscommerce.order.infrastructure.rest.mapper.OrderRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller REST per la gestione delle API dell'Ordine.
 * Gestisce l'input HTTP, valida i DTO, interroga il Service applicativo e converte
 * i modelli di dominio restituiti nei DTO di risposta tramite OrderRestMapper.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderRestMapper orderRestMapper;

    @PostMapping
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderCreationRequest request) {
        UUID orderId = orderApplicationService.createOrder(request.customerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    @PostMapping("{orderId}/lines")
    public ResponseEntity<UUID> addOrderLine(@PathVariable UUID orderId, @Valid @RequestBody OrderLineRequest request) {
        UUID lineId = orderApplicationService.addOrderLine(
                orderId, 
                request.skuCode(), 
                request.price(), 
                request.quantity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(lineId);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID orderId) {
        Order order = orderApplicationService.getOrderById(orderId);
        OrderResponse response = orderRestMapper.toResponse(order);
        return ResponseEntity.ok(response);
    }
}
