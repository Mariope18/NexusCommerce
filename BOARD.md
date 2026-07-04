# 📋 Project Board - NexusCommerce

Questo file traccia il ciclo di vita dei ticket del progetto, simulando una board di livello enterprise.

## 🛠 Sprint 1: Fondamenta & Modello di Dominio Core

### 🟢 1. [SETUP] Inizializzazione Repository e Scaffolding
* **Stato:** 🟢 DONE
* **Branch:** `main`
* **Descrizione:** Setup iniziale del monorepo e generazione dello scheletro Spring Boot 3.x tramite Spring Initializr con le dipendenze base (Web, JPA, Postgres, Lombok, Validation).
* **Documentazione:** Aggiunto file `README.md` iniziale con la visione dei Bounded Context.

---

### 🟡 2. [FEATURE] Definizione del Dominio Core (Rich Domain Model)
* **Stato:** 🟡 IN PROGRESS
* **Branch:** `feature/init-progetto-e-dominio`
* **Descrizione:** Creazione del modello di dominio puro per l'Order Service secondo i principi del Domain-Driven Design (DDD) e della Clean Architecture.
* **Criteri di Accettazione:**
  * Creazione del package `com.nexuscommerce.order.domain`.
  * Definizione di `OrderStatus` (Enum: `PENDING`, `COMPLETED`, `CANCELLED`).
  * Definizione di `OrderLine` (Classe pura Java con `id`, `skuCode`, `price`, `quantity`).
  * Definizione di `Order` (Aggregate Root con `id`, `customerId`, `status`, `orderLines`, `totalAmount`).
  * La classe `Order` deve esporre la logica di business per aggiungere una linea e ricalcolare internamente il `totalAmount` (Rich Domain Model).
  * Utilizzo corretto dell'incapsulamento e delle annotazioni Lombok mirate (evitare `@Data` acritico).