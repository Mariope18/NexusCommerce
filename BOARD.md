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

### 🟡 3. [TEST] Unit Testing del Dominio Core dell'Order Service
* **Stato:** 🟡 IN PROGRESS
* **Branch:** `feature/test-dominio-ordine`
* **Descrizione:** Scrittura degli unit test con JUnit 5 per blindare la logica di business dell'aggregato Order (stato iniziale, calcolo del totale e resilienza ai valori nulli).
* **Criteri di Accettazione:**
  * Creazione di `OrderTest.java` nel package di test appropriato.
  * Copertura del calcolo corretto del totale con `BigDecimal`.
  * Verifica della corretta gestione dei casi limite (prezzi o quantità nulle).
  * Esecuzione dei test fluida e priva di dipendenze dal framework Spring.