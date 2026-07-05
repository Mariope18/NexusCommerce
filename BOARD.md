# 📋 Project Board - NexusCommerce

Questo file traccia il ciclo di vita dei ticket del progetto, simulando una board di livello enterprise.

## 🛠 Sprint 1: Fondamenta & Modello di Dominio Core

### 🟢 1. [SETUP] Inizializzazione Repository e Scaffolding
* **Stato:** 🟢 DONE
* **Branch:** `main`

---

### 🟢 2. [FEATURE] Definizione del Dominio Core (Rich Domain Model)
* **Stato:** 🟢 DONE
* **Branch:** `feature/init-progetto-e-dominio`
* **Descrizione:** Creazione del modello di dominio puro per l'Order Service secondo i principi del Domain-Driven Design (DDD) e della Clean Architecture.

---

### 🟢 3. [TEST] Unit Testing del Dominio Core dell'Order Service
* **Stato:** 🟢 DONE
* **Branch:** `feature/test-dominio-ordine`
* **Descrizione:** Scrittura degli unit test con JUnit 5 per blindare la logica di business dell'aggregato Order (stato iniziale, calcolo del totale e resilienza ai valori nulli).
* **Criteri di Accettazione:**
  * Creazione di `OrderTest.java` nel package di test appropriato.
  * Copertura del calcolo corretto del totale con `BigDecimal`.
  * Verifica della corretta gestione dei casi limite (prezzi o quantità nulle).
  * Esecuzione dei test fluida e priva di dipendenze dal framework Spring.


## 🗄 Sprint 2: Infrastruttura, Persistenza e API

### 🟢 4. [INFRA] Configurazione Spring Data JPA e PostgreSQL
* **Stato:** 🟢 DONE
* **Branch:** `feature/configurazione-jpa-postgres`
* **Descrizione:** Configurazione della connessione a PostgreSQL e creazione delle JPA Entities per mantenere il disaccoppiamento dal modello di dominio puro (Clean Architecture).
* **Criteri di Accettazione:**
  * File `application.yml` configurato per PostgreSQL.
  * Creazione del package `infrastructure.persistence.entity`.
  * Creazione di `OrderJpaEntity` e `OrderLineJpaEntity` con le corrette relazioni Hibernate (`@OneToMany`).
  * Creazione di `OrderRepository` (Spring Data JPA).

### 🟢 5. [FEATURE] Implementazione MapStruct e Application Service
* **Stato:** 🟢 DONE
* **Branch:** `feature/mapper-e-service`
* **Descrizione:** Integrazione di MapStruct per la traduzione tra Dominio e Infrastruttura e creazione dell'Application Service per orchestrare la creazione dell'ordine.
* **Criteri di Accettazione:**
  * Configurazione corretta di MapStruct nel `pom.xml`.
  * Creazione dell'interfaccia `OrderMapper` (`@Mapper(componentModel = "spring")`).
  * Creazione di `OrderApplicationService` e relativa implementazione.
  * Implementazione del metodo `createOrder(UUID customerId)` con flusso completo (Istanziazione Dominio -> Mapping -> Salvataggio Repository).

### 🟢 6. [API] Esposizione dell'End-point REST per la Creazione Ordine (DTO Layer)
* **Stato:** 🟢 DONE
* **Branch:** `feature/api-creazione-ordine`
* **Descrizione:** Creazione del livello web (Controller e DTO) per esporre l'endpoint HTTP POST che permetterà ai client esterni (frontend o altri microservizi) di richiedere la creazione di un ordine.
* **Criteri di Accettazione:**
  * Creazione del package `com.nexuscommerce.order.infrastructure.rest`.
  * Definizione dei DTO dedicati all'input e all'output delle API (es. `OrderCreationRequest`).
  * Introduzione della validazione dei dati in ingresso tramite Jakarta Bean Validation (`@NotNull`, ecc.).
  * Implementazione di `OrderController` con mappatura dell'endpoint `POST /api/v1/orders`.

## 🚀 Sprint 3: Evoluzione delle API e Gestione dell'Aggregato

### 🟢 7. [API] Endpoint REST per l'aggiunta di una linea d'ordine
* **Stato:** 🟢 DONE
* **Branch:** `feature/api-aggiunta-linea-ordine`
* **Descrizione:** Esposizione dell'endpoint per aggiungere prodotti a un ordine esistente, implementando il flusso completo di recupero, modifica tramite Dominio puro e persistenza.
* **Criteri di Accettazione:**
  * Creazione del DTO `OrderLineRequest` con validazione Jakarta.
  * Mappatura dell'endpoint `POST /api/v1/orders/{orderId}/lines`.
  * Implementazione del flusso nell'Application Service: `findById` -> Mapping to Domain -> `aggiungiOrderLine()` -> Mapping to Entity -> `save`.