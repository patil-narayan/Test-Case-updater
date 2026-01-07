import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class ItemApiRegressionTests {

    @Test
    void testGetAllItems() {
        given()
        .when()
            .get("/items")
        .then()
            .statusCode(200)
            .body("$", not(empty()));
    }

    @Test
    void testCreateNewItem() {
        given()
            .contentType("application/json")
            .body("{\"name\": \"Test Item\", \"description\": \"Test Description\"}")
        .when()
            .post("/items")
        .then()
            .statusCode(201)
            .body("name", equalTo("Test Item"))
            .body("description", equalTo("Test Description"));
    }

    

    @Test
    void testUpdateItemById() {
        int id = 1; // Use a valid ID from your test DB
        given()
            .contentType("application/json")
            .body("{\"name\": \"Updated Name\", \"description\": \"Updated Description\"}")
        .when()
            .put("/items/" + id)
        .then()
            .statusCode(200)
            .body("name", equalTo("Updated Name"))
            .body("description", equalTo("Updated Description"));
    }

    @Test
    void testUpdateItemByIdNotFound() {
        int id = 99999; // Use an ID that doesn't exist
        given()
            .contentType("application/json")
            .body("{\"name\": \"Updated Name\", \"description\": \"Updated Description\"}")
        .when()
            .put("/items/" + id)
        .then()
            .statusCode(404);
    }

    @Test
    void testDeleteItemById() {
        int id = 1; // Use a valid ID from your test DB
        given()
        .when()
            .delete("/items/" + id)
        .then()
            .statusCode(204);
    }

   

    @Test
    void testRemovedEndpoint() {
        given()
        .when()
            .post("/old-endpoint")
        .then()
            .statusCode(404);
    }
}
