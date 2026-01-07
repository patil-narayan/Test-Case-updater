import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemApiRegressionTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @Order(1)
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
    @Order(2)
    public void testCreateNewItem() {
        String newItem = "{ \"name\": \"Test Item\", \"description\": \"Test Description\" }";

        given()
            .contentType(ContentType.JSON)
            .body(newItem)
        .when()
            .post("/api/items")
        .then()
            .statusCode(201)
            .body("name", equalTo("Test Item"))
            .body("description", equalTo("Test Description"));
    }

    @Test
    @Order(3)
    public void testUpdateItemById() {
        String updatedItem = "{ \"name\": \"Updated Item\", \"description\": \"Updated Description\" }";
        int itemId = 1; // Replace with actual ID from create test or DB

        given()
            .contentType(ContentType.JSON)
            .body(updatedItem)
        .when()
            .put("/api/items?id=" + itemId)
        .then()
            .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    @Order(4)
    public void testDeleteItemById() {
        int itemId = 1; // Replace with actual ID from create test or DB

        given()
        .when()
            .delete("/api/items?id=" + itemId)
        .then()
            .statusCode(204);
    }
}
