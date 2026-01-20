package com.example.demo.controller;

import com.example.demo.model.Item;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testGetAllItems() {
        RestAssured.get("/api/items")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testCreateNewItem() {
        Item item = new Item();
        item.setName("Test Item");
        item.setDescription("Test Description");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(item)
                .post("/api/items")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateItemById() {
        // First, create an item
        Item item = new Item();
        item.setName("Update Item");
        item.setDescription("To be updated");

        int id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(item)
                .post("/api/items")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the item
        item.setName("Updated Name");
        item.setDescription("Updated Description");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(item)
                .put("/api/items?id=" + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteItemById() {
        // First, create an item
        Item item = new Item();
        item.setName("Delete Item");
        item.setDescription("To be deleted");

        int id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(item)
                .post("/api/items")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Delete the item
        RestAssured.delete("/api/items?id=" + id)
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateNonExistentItem() {
        Item item = new Item();
        item.setName("Non-existent");
        item.setDescription("Should not exist");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(item)
                .put("/api/items?id=99999")
                .then()
                .statusCode(404);
    }
}
