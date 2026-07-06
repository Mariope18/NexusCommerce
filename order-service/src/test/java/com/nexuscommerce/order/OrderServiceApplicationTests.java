package com.nexuscommerce.order;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.DisplayName;

@SpringBootTest
@Disabled("Disabilitato temporaneamente in attesa del setup del database")
@DisplayName("Test di integrazione del contesto Spring Boot")
class OrderServiceApplicationTests {

	@Test
	@DisplayName("Il contesto Spring Boot si avvia correttamente")
	void contextLoads() {
	}

}
