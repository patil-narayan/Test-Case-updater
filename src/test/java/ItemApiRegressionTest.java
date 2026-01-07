// src/test/java/ItemApiRegressionTest.java
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

public class ItemApiRegressionTest {

    @Test
    public void testGetAllItems() {
        RestAssured.given()
            .when().get("/api/items")
            .then().statusCode(200)
            .body("$", notNullValue());
    }

    @Test
    public void testCreateNewItem() {
        String newItem = "{\"name\":\"Test Item\",\"description\":\"Test Description\"}";
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(newItem)
            .when().post("/api/items")
            .then().statusCode(201)
            .body("name", equalTo("Test Item"))
            .body("description", equalTo("Test Description"));
    }

    @Test
    public void testUpdateItemById() {
        String updatedItem = "{\"id\":1,\"name\":\"Updated Item\",\"description\":\"Updated Description\"}";
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(updatedItem)
            .when().put("/api/items?id=1")
            .then().statusCode(anyOf(is(200), is(404)));
    }

    @Test
    public void testDeleteItemById() {
        RestAssured.given()
            .when().delete("/api/items?id=1")
            .then().statusCode(204);
    }
}
