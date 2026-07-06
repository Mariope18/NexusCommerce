package com.nexuscommerce.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order - Aggregato Root")
class OrderTest {

    @Nested
    @DisplayName("Creazione dell'ordine")
    class CreazioneOrdine {

        @Test
        @DisplayName("Un ordine creato con il Builder ha tutti i campi inizializzati correttamente")
        void whenCreatingOrderWithBuilder_thenFieldsAreInitializedCorrectly() {
            UUID orderId = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();

            Order order = Order.builder()
                    .id(orderId)
                    .customerId(customerId)
                    .build();

            assertEquals(orderId, order.getId());
            assertEquals(customerId, order.getCustomerId());
            assertEquals(OrderStatus.PENDING, order.getOrderStatus());
            assertNotNull(order.getOrderLines());
            assertTrue(order.getOrderLines().isEmpty());
            assertEquals(0, BigDecimal.ZERO.compareTo(order.getTotalAmount()));
        }

        @Test
        @DisplayName("Un ordine non può essere creato senza id")
        void whenCreatingOrderWithoutId_thenThrowsException() {
            assertThrows(NullPointerException.class, () ->
                    Order.builder()
                            .id(null)
                            .customerId(UUID.randomUUID())
                            .build()
            );
        }

        @Test
        @DisplayName("Un ordine non può essere creato senza customerId")
        void whenCreatingOrderWithoutCustomerId_thenThrowsException() {
            assertThrows(NullPointerException.class, () ->
                    Order.builder()
                            .id(UUID.randomUUID())
                            .customerId(null)
                            .build()
            );
        }
    }

    @Nested
    @DisplayName("Gestione delle linee d'ordine")
    class GestioneLineeOrdine {

        @Test
        @DisplayName("L'aggiunta di linee d'ordine ricalcola correttamente il totale")
        void whenAddingOrderLine_thenTotalAmountIsCalculatedCorrectly() {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .customerId(UUID.randomUUID())
                    .build();

            OrderLine line1 = OrderLine.builder()
                    .id(UUID.randomUUID())
                    .skuCode("SKU-100")
                    .price(new BigDecimal("10.50"))
                    .quantity(2L) // 10.50 * 2 = 21.00
                    .build();

            OrderLine line2 = OrderLine.builder()
                    .id(UUID.randomUUID())
                    .skuCode("SKU-200")
                    .price(new BigDecimal("5.00"))
                    .quantity(3L) // 5.00 * 3 = 15.00
                    .build();

            order.aggiungiOrderLine(line1);
            order.aggiungiOrderLine(line2);

            assertEquals(2, order.getOrderLines().size());
            assertEquals(0, new BigDecimal("36.00").compareTo(order.getTotalAmount()));
        }

        @Test
        @DisplayName("La collezione restituita da getOrderLines è non modificabile")
        void whenModifyingReturnedOrderLines_thenThrowsException() {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .customerId(UUID.randomUUID())
                    .build();

            assertThrows(UnsupportedOperationException.class, () ->
                    order.getOrderLines().add(
                            OrderLine.builder()
                                    .id(UUID.randomUUID())
                                    .skuCode("SKU-HACK")
                                    .price(BigDecimal.TEN)
                                    .quantity(1L)
                                    .build()
                    )
            );
        }

        @Test
        @DisplayName("Non si può aggiungere una linea d'ordine null")
        void whenAddingNullOrderLine_thenThrowsException() {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .customerId(UUID.randomUUID())
                    .build();

            assertThrows(NullPointerException.class, () -> order.aggiungiOrderLine(null));
        }
    }

    @Nested
    @DisplayName("OrderLine - Validazione alla creazione")
    class ValidazioneOrderLine {

        @Test
        @DisplayName("Non si può creare una OrderLine con prezzo null")
        void whenCreatingOrderLineWithNullPrice_thenThrowsException() {
            assertThrows(NullPointerException.class, () ->
                    OrderLine.builder()
                            .id(UUID.randomUUID())
                            .skuCode("SKU-100")
                            .price(null)
                            .quantity(2L)
                            .build()
            );
        }

        @Test
        @DisplayName("Non si può creare una OrderLine con quantità null")
        void whenCreatingOrderLineWithNullQuantity_thenThrowsException() {
            assertThrows(NullPointerException.class, () ->
                    OrderLine.builder()
                            .id(UUID.randomUUID())
                            .skuCode("SKU-200")
                            .price(new BigDecimal("10.00"))
                            .quantity(null)
                            .build()
            );
        }

        @Test
        @DisplayName("Non si può creare una OrderLine con prezzo negativo")
        void whenCreatingOrderLineWithNegativePrice_thenThrowsException() {
            assertThrows(IllegalArgumentException.class, () ->
                    OrderLine.builder()
                            .id(UUID.randomUUID())
                            .skuCode("SKU-300")
                            .price(new BigDecimal("-5.00"))
                            .quantity(1L)
                            .build()
            );
        }

        @Test
        @DisplayName("Non si può creare una OrderLine con quantità zero o negativa")
        void whenCreatingOrderLineWithZeroQuantity_thenThrowsException() {
            assertThrows(IllegalArgumentException.class, () ->
                    OrderLine.builder()
                            .id(UUID.randomUUID())
                            .skuCode("SKU-400")
                            .price(BigDecimal.TEN)
                            .quantity(0L)
                            .build()
            );
        }
    }

    @Nested
    @DisplayName("Transizioni di stato dell'ordine")
    class TransizioniDiStato {

        @Test
        @DisplayName("Un ordine PENDING può essere completato")
        void whenCompletingPendingOrder_thenStatusIsCompleted() {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .customerId(UUID.randomUUID())
                    .build();

            order.completaOrdine();

            assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        }

        @Test
        @DisplayName("Un ordine PENDING può essere cancellato")
        void whenCancellingPendingOrder_thenStatusIsCancelled() {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .customerId(UUID.randomUUID())
                    .build();

            order.cancellaOrdine();

            assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
        }

        @Test
        @DisplayName("Un ordine COMPLETED non può essere completato di nuovo")
        void whenCompletingCompletedOrder_thenThrowsException() {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .customerId(UUID.randomUUID())
                    .build();

            order.completaOrdine();

            assertThrows(IllegalStateException.class, order::completaOrdine);
        }

        @Test
        @DisplayName("Un ordine COMPLETED non può essere cancellato")
        void whenCancellingCompletedOrder_thenThrowsException() {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .customerId(UUID.randomUUID())
                    .build();

            order.completaOrdine();

            assertThrows(IllegalStateException.class, order::cancellaOrdine);
        }

        @Test
        @DisplayName("Un ordine CANCELLED non può essere completato")
        void whenCompletingCancelledOrder_thenThrowsException() {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .customerId(UUID.randomUUID())
                    .build();

            order.cancellaOrdine();

            assertThrows(IllegalStateException.class, order::completaOrdine);
        }
    }
}
