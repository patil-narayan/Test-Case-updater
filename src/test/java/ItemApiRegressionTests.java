import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ItemApiRegressionTests {

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
        String newItem = "{ \"name\": \"Test Item\", \"description\": \"Test Description\" }";
        given()
            .contentType(ContentType.JSON)
            .body(newItem)
        .when()
            .post("/api/items")
        .then()
            .statusCode(201);
    }

    @Test
    public void testUpdateItemById() {
        String updateItem = "{ \"id\": 1, \"name\": \"Updated Item\", \"description\": \"Updated Description\" }";
        given()
            .contentType(ContentType.JSON)
            .body(updateItem)
        .when()
            .put("/api/items?id=1")
        .then()
            .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    public void testDeleteItemById() {
        given()
        .when()
            .delete("/api/items?id=1")
        .then()
            .statusCode(anyOf(is(204), is(404)));
    }
}
