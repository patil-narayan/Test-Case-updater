import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestCaseApiRegressionTests {

    @Test
    public void testGetAllTestCases() {
        given()
        .when()
            .get("/test-cases")
        .then()
            .statusCode(200)
            .body("$", not(empty()));
    }

    @Test
    public void testCreateTestCase() {
        given()
            .contentType("application/json")
            .body("{\"name\": \"New Test\", \"description\": \"A new test case\", \"status\": \"active\"}")
        .when()
            .post("/test-cases")
        .then()
            .statusCode(201)
            .body("name", equalTo("New Test"))
            .body("status", equalTo("active"));
    }

    @Test
    public void testGetTestCaseById() {
        String testCaseId = "123";
        given()
        .when()
            .get("/test-cases/" + testCaseId)
        .then()
            .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    public void testUpdateTestCaseById() {
        String testCaseId = "123";
        given()
            .contentType("application/json")
            .body("{\"name\": \"Updated Test\", \"description\": \"Updated desc\", \"status\": \"inactive\"}")
        .when()
            .put("/test-cases/" + testCaseId)
        .then()
            .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    public void testDeleteTestCaseById() {
        String testCaseId = "123";
        given()
        .when()
            .delete("/test-cases/" + testCaseId)
        .then()
            .statusCode(anyOf(is(204), is(404)));
    }

    @Test
    public void testRemovedSearchEndpoint() {
        given()
        .when()
            .get("/test-cases/search")
        .then()
            .statusCode(404);
    }
}
