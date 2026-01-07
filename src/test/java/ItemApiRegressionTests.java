import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemApiRegressionTests {

    private static int createdItemId;

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
        String newItem = "{ \"name\": \"Test Item\", \"description\": \"A sample item\" }";
        createdItemId =
            given()
                .contentType(ContentType.JSON)
                .body(newItem)
            .when()
                .post("/api/items")
            .then()
                .statusCode(201)
                .extract()
                .path("id");
        Assertions.assertTrue(createdItemId > 0);
    }

    @Test
    @Order(3)
    public void testUpdateItemById() {
        String updatedItem = "{ \"id\": " + createdItemId + ", \"name\": \"Updated Item\", \"description\": \"Updated description\" }";
        given()
            .contentType(ContentType.JSON)
            .body(updatedItem)
        .when()
            .put("/api/items/" + createdItemId)
        .then()
            .statusCode(200)
            .body("name", equalTo("Updated Item"))
            .body("description", equalTo("Updated description"));
    }

    @Test
    @Order(4)
    public void testUpdateItemNotFound() {
        String updatedItem = "{ \"id\": 99999, \"name\": \"Nonexistent\", \"description\": \"Should not exist\" }";
        given()
            .contentType(ContentType.JSON)
            .body(updatedItem)
        .when()
            .put("/api/items/99999")
        .then()
            .statusCode(404);
    }

    @Test
    @Order(5)
    public void testDeleteItemById() {
        given()
        .when()
            .delete("/api/items/" + createdItemId)
        .then()
            .statusCode(204);
    }

    @Test
    @Order(6)
    public void testDeleteItemNotFound() {
        given()
        .when()
            .delete("/api/items/99999")
        .then()
            .statusCode(204); // Assuming API returns 204 even if item not found
    }
}
