# 📋 Project Board - NexusCommerce

Questo file traccia il ciclo di vita dei ticket del progetto, simulando una board di livello enterprise.

## 🛠 Sprint 1: Fondamenta & Modello di Dominio Core

### 🟢 1. [SETUP] Inizializzazione Repository e Scaffolding
* **Stato:** 🟢 DONE
* **Branch:** `main`
* **Descrizione:** Setup iniziale del monorepo e generazione dello scheletro Spring Boot 3.x tramite Spring Initializr con le dipendenze base (Web, JPA, Postgres, Lombok, Validation).
* **Documentazione:** Aggiunto file `README.md` iniziale con la visione dei Bounded Context.

---

### 🟢 2. [FEATURE] Definizione del Dominio Core (Rich Domain Model)
* **Stato:** 🟢 DONE
* **Branch:** `feature/init-progetto-e-dominio`
* **Descrizione:** Creazione del modello di dominio puro per l'Order Service secondo i principi del Domain-Driven Design (DDD) e della Clean Architecture.
* **Criteri di Accettazione:**
  * Creazione del package `com.nexuscommerce.order.domain`.
  * Definizione di `OrderStatus` (Enum: `PENDING`, `COMPLETED`, `CANCELLED`).
  * Definizione di `OrderLine` (Classe pura Java con tipi solidi: `Long` per l'id e `BigDecimal` per il prezzo).
  * Definizione di `Order` (Aggregate Root con logica di ricalcolo del totale protetta e incapsulata).
  * Protezione dello stato tramite l'uso combinato di `@NoArgsConstructor(access = AccessLevel.PROTECTED)` e `@Builder` sul costruttore dedicato.