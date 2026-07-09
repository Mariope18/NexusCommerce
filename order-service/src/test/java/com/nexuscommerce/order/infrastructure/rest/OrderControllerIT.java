package com.nexuscommerce.order.infrastructure.rest;

import tools.jackson.databind.ObjectMapper;
import com.nexuscommerce.order.AbstractIntegrationTest;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderCreationRequest;
import com.nexuscommerce.order.infrastructure.rest.dto.OrderLineRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test di integrazione per OrderController")
class OrderControllerIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Dovrebbe creare un ordine, aggiungere una linea d'ordine e recuperarlo con successo")
    void shouldManageOrderLifecycle() throws Exception {
        // 1. Creazione dell'ordine
        UUID customerId = UUID.randomUUID();
        OrderCreationRequest creationRequest = new OrderCreationRequest(customerId);

        MvcResult creationResult = mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String orderIdStr = creationResult.getResponse().getContentAsString().replace("\"", "");
        UUID orderId = UUID.fromString(orderIdStr);

        // 2. Aggiunta di una linea d'ordine
        OrderLineRequest lineRequest = new OrderLineRequest("SKU-TEST-123", new BigDecimal("49.99"), 2L);

        mockMvc.perform(post("/api/v1/orders/{orderId}/lines", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lineRequest)))
                .andExpect(status().isCreated());

        // 3. Recupero dell'ordine e verifica dei dettagli
        mockMvc.perform(get("/api/v1/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(orderId.toString())))
                .andExpect(jsonPath("$.customerId", is(customerId.toString())))
                .andExpect(jsonPath("$.orderStatus", is("PENDING")))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.orderLines[0].skuCode", is("SKU-TEST-123")))
                .andExpect(jsonPath("$.orderLines[0].price", is(49.99)))
                .andExpect(jsonPath("$.orderLines[0].quantity", is(2)))
                .andExpect(jsonPath("$.totalAmount", is(99.98)));
    }
}
