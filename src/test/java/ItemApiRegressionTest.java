import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.*;

class ItemApiRegressionTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void testGetAllItems() {
        RestAssured
            .given()
            .when()
            .get("/api/items")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    void testCreateNewItem() {
        String newItem = "{ \"name\": \"Test Item\", \"description\": \"Test Description\" }";
        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(newItem)
            .when()
            .post("/api/items")
            .then()
            .statusCode(201);
    }

    @Test
    void testUpdateItemById() {
        String updatedItem = "{ \"id\": 1, \"name\": \"Updated Item\", \"description\": \"Updated Description\" }";
        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(updatedItem)
            .when()
            .put("/api/items?id=1")
            .then()
            .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    void testDeleteItemById() {
        RestAssured
            .given()
            .when()
            .delete("/api/items?id=1")
            .then()
            .statusCode(204);
    }
}
