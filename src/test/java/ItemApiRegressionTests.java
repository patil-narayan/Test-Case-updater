import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ItemApiRegressionTests {

    @Test
    public void testGetAllItems() {
        given()
        .when()
            .get("/api/items")
        .then()
            .statusCode(200)
            .body("$", notNullValue());
    }

    @Test
    public void testCreateNewItem() {
        String newItemJson = "{ \"name\": \"Test Item\", \"description\": \"Test Description\" }";
        given()
            .contentType("application/json")
            .body(newItemJson)
        .when()
            .post("/api/items")
        .then()
            .statusCode(201)
            .body("name", equalTo("Test Item"))
            .body("description", equalTo("Test Description"));
    }

    @Test
    public void testUpdateItemById() {
        int itemId = 1; // Use a valid ID from your test data/setup
        String updatedItemJson = "{ \"id\": 1, \"name\": \"Updated Item\", \"description\": \"Updated Description\" }";
        given()
            .contentType("application/json")
            .body(updatedItemJson)
        .when()
            .put("/api/items?id=" + itemId)
        .then()
            .statusCode(anyOf(is(200), is(404))); // 200 if found, 404 if not found
    }

    @Test
    public void testDeleteItemById() {
        int itemId = 1; // Use a valid ID from your test data/setup
        given()
        .when()
            .delete("/api/items?id=" + itemId)
        .then()
            .statusCode(204);
    }
}
