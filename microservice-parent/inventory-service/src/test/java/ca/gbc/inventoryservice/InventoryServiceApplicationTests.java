package ca.gbc.inventoryservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

    // Using Testcontainers to spin up a PostgreSQL container  needed for our InventoryService
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");
          //  .withDatabaseName("inventorydb")
            //.withUsername("admin")
            //.withPassword("password");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        postgresContainer.start();
    }

    @Test
    void checkInventoryInStockTest() {
        // BDD-style test for the isInStock endpoint
        String skuCode = "SKU001";
        int quantity = 5;

        // BDD - Behavioral Driven Development
        RestAssured.given()
                .contentType("application/json")
                .queryParam("skuCode", skuCode)
                .queryParam("quantity", quantity)
                .when()
                .get("/api/inventory")
                .then()
                .log().all()
                .statusCode(200)
                .body(Matchers.equalTo("true")); // Adjust if needed based on actual response format
    }

}
