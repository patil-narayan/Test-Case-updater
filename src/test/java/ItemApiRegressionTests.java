import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class ItemApiRegressionTests {

    @Test
    void testGetAllItems() {
        given()
        .when()
            .get("/api/items")
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
            .post("/api/items")
        .then()
            .statusCode(201)
            .body("name", equalTo("Test Item"))
            .body("description", equalTo("Test Description"));
    }

    @Test
    void testGetItemByIdFound() {
        // First, create an item to ensure the ID exists
        int id = given()
            .contentType("application/json")
            .body("{\"name\": \"Test Item\", \"description\": \"Test Description\"}")
        .when()
            .post("/api/items")
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
        .when()
            .get("/api/items/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("name", equalTo("Test Item"))
            .body("description", equalTo("Test Description"));
    }

    @Test
    void testGetItemByIdNotFound() {
        int id = 99999; // Use an ID that doesn't exist
        given()
        .when()
            .get("/api/items/" + id)
        .then()
            .statusCode(404);
    }

    @Test
    void testUpdateItemById() {
        // First, create an item to ensure the ID exists
        int id = given()
            .contentType("application/json")
            .body("{\"name\": \"Original Name\", \"description\": \"Original Description\"}")
        .when()
            .post("/api/items")
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .contentType("application/json")
            .body("{\"name\": \"Updated Name\", \"description\": \"Updated Description\"}")
        .when()
            .put("/api/items/" + id)
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
            .put("/api/items/" + id)
        .then()
            .statusCode(404);
    }

    @Test
    void testDeleteItemById() {
        // First, create an item to ensure the ID exists
        int id = given()
            .contentType("application/json")
            .body("{\"name\": \"Item to Delete\", \"description\": \"Delete Me\"}")
        .when()
            .post("/api/items")
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
        .when()
            .delete("/api/items/" + id)
        .then()
            .statusCode(204);
    }

    @Test
    void testDeleteItemByIdNotFound() {
        int id = 99999; // Use an ID that doesn't exist
        given()
        .when()
            .delete("/api/items/" + id)
        .then()
            .statusCode(204); // If the API returns 204 even for non-existent, adjust as needed
    }
}
