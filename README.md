# NexusCommerce - Enterprise Order Orchestrator

## 📖 Descrizione
NexusCommerce è un'infrastruttura backend distribuita progettata per gestire il ciclo di vita degli ordini, l'orchestrazione dei pagamenti e la gestione della concorrenza del magazzino. Sviluppato seguendo i principi della Clean Architecture e del Domain-Driven Design (DDD).

## 🏗 Architettura e Bounded Contexts
Il sistema è basato su un'architettura a Microservizi (progettato inizialmente come Monolito Modulare). Le comunicazioni inter-servizio avvengono in modo asincrono tramite eventi.

La regola d'oro applicata è il **Database-per-Service**: i servizi non condividono tabelle, ma comunicano tramite ID di riferimento e messaggistica.

### 1. Order Service (Core Domain)
Gestisce la creazione e lo stato dell'ordine.
* `orders`: `id` (UUID), `customer_id` (UUID), `status` (Enum), `total_amount` (Decimal), `created_at` (Timestamp).
* `order_lines`: `id` (UUID), `order_id` (UUID), `sku_code` (String), `price` (Decimal), `quantity` (Integer).

### 2. Inventory Service
Gestisce le giacenze e previene l'overselling tramite locking.
* `inventory`: `id` (UUID), `sku_code` (String), `available_quantity` (Integer), `reserved_quantity` (Integer).

### 3. Payment Service
Gestisce l'integrazione con gateway esterni e l'idempotenza delle transazioni.
* `payment_transactions`: `id` (UUID), `order_id` (UUID), `amount` (Decimal), `status` (Enum), `idempotency_key` (String).

## 🛠 Stack Tecnologico
* **Linguaggio:** Java 21
* **Framework Core:** Spring Boot 3.x
* **Data Access:** Spring Data JPA / Hibernate
* **Database:** PostgreSQL 
* **Messaggistica:** Apache Kafka
* **Resilienza:** Resilience4j
* **Sicurezza:** Spring Security (OAuth2 / JWT)
* **Testing:** JUnit 5, Mockito, Testcontainers

## 🚀 Flusso di Sviluppo
Questo progetto segue una rigorosa metodologia basata su Pull Request. Il branch `main` è protetto.
Ogni feature viene sviluppata su branch dedicati (es. `feature/nome-feature`) e integrata solo a seguito di Code Review.