    @Order(5)
    public void testDeleteItemById_Secure() {
        // Simulate authentication (replace with actual auth if needed)
        String authToken = "valid-auth-token";
        int validId = createdItemId;
        // Validate id is positive integer
        assertTrue(validId > 0, "ID must be positive integer");
        given()
            .header("Authorization", "Bearer " + authToken)
            .pathParam("id", validId)
        .when()
            .delete("/api/items/{id}")
        .then()
            .statusCode(anyOf(is(200), is(204))); // Acceptable status codes
    }