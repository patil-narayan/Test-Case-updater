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
            .body("$", isA(java.util.List.class));
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
        String updatedItem = "{ \"name\": \"Updated Item\", \"description\": \"Updated Description\" }";
        int id = given()
            .contentType(ContentType.JSON)
            .body("{ \"name\": \"Temp\", \"description\": \"Temp\" }")
        .when()
            .post("/api/items")
        .then()
            .statusCode(201)
            .extract()
            .path("id");
        given()
            .contentType(ContentType.JSON)
            .body(updatedItem)
        .when()
            .put("/api/items?id=" + id)
        .then()
            .statusCode(200);
    }
    @Test
    public void testDeleteItemById() {
        int id = given()
            .contentType(ContentType.JSON)
            .body("{ \"name\": \"Temp\", \"description\": \"Temp\" }")
        .when()
            .post("/api/items")
        .then()
            .statusCode(201)
            .extract()
            .path("id");
        given()
        .when()
            .delete("/api/items?id=" + id)
        .then()
            .statusCode(204);
    }
    @Test
    public void testUpdateNonExistentItem() {
        String updatedItem = "{ \"name\": \"NonExistent\", \"description\": \"NonExistent\" }";
        given()
            .contentType(ContentType.JSON)
            .body(updatedItem)
        .when()
            .put("/api/items?id=999999")
        .then()
            .statusCode(404);
    }
}
