package com.example.demo.controller;

import com.example.demo.model.Item;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ItemControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void testGetAllItems() {
        given()
        .when()
            .get("/api/items")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("$", notNullValue());
    }

    @Test
    public void testCreateNewItem() {
        Item item = new Item();
        item.setName("Test Item");
        item.setDescription("Test Description");

        given()
            .contentType(ContentType.JSON)
            .body(item)
        .when()
            .post("/api/items")
        .then()
            .statusCode(201)
            .body("name", equalTo("Test Item"))
            .body("description", equalTo("Test Description"));
    }

    @Test
    public void testUpdateItemById() {
        Item item = new Item();
        item.setName("Updated Name");
        item.setDescription("Updated Description");

        int itemId = 1; // Replace with a valid ID in your test DB

        given()
            .contentType(ContentType.JSON)
            .body(item)
        .when()
            .put("/api/items?id=" + itemId)
        .then()
            .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    public void testDeleteItemById() {
        int itemId = 1; // Replace with a valid ID in your test DB

        given()
        .when()
            .delete("/api/items?id=" + itemId)
        .then()
            .statusCode(204);
    }
}
