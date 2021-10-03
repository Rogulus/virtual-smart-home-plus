package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.catalina.connector.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class FireplaceRouteTest {

    private final String fireplaceEndpoint = "house/device/fireplace";
    private JSONObject defaultFireplaceJson = null;

    {
        try {
            defaultFireplaceJson = new JSONObject()
                    .put("label", "fireplace")
                    .put("enabled", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // === GET ===

    @Test
    void getRequestContentTypeJson() {
        given()
                .when().get(fireplaceEndpoint)
                .then().contentType(ContentType.JSON);
    }

    @Test
    void getRequestNotFound() {
        given()
                .when().get(fireplaceEndpoint + "/notFound")
                .then().statusCode(Response.SC_NOT_FOUND); // 404
    }

    @Test
    void getRequestNonExistentEndpoint() {
        given()
                .when().get(fireplaceEndpoint + "/nonExistent")
                .then().body(Matchers.equalTo(""));
    }

    @Test
    void getRequestWithInvalidParam() {
        given()
                .param("param", "param")
                .when().get(fireplaceEndpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    @Test
    void getRequestStatusCode200() {
        given()
                .when().get(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    @Test
    void emptyGetRequest() throws JSONException {
        given()
                .param("label", defaultFireplaceJson.get("label"))
                .when().get(fireplaceEndpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getUniqueFireplace() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .when().get(fireplaceEndpoint + "/" + defaultFireplaceJson.getString("label"))
                .then().body(Matchers.equalTo(defaultFireplaceJson.toString()));
    }

    // === POST ===

    @Test
    void postRequestWithoutBody() {
        given()
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    void postRequestWithIncorrectJsonBody() throws JSONException {
        JSONObject invalidRequestBody = new JSONObject().put("json", "json");

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody.toString())
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    void postRequestWithNonJsonBody() {
        given()
                .contentType(ContentType.TEXT)
                .body("body")
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void postRequestConflict() {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_CONFLICT); // 409
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void postRequestStatusCode201() {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_CREATED);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void simplePostRequest() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(fireplaceEndpoint)
                .then().body(Matchers.equalTo(defaultFireplaceJson.toString()));

        given()
                .when().get(fireplaceEndpoint + "/" + defaultFireplaceJson.getString("label"))
                .then().body(Matchers.equalTo(defaultFireplaceJson.toString()));
    }

    // === PUT ===

    @Test
    void putRequestWithoutBody() {
        given()
                .when().put(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    void putRequestWithInvalidBody() throws JSONException {
        JSONObject invalidRequestBody = new JSONObject().put("json", "json");

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody.toString())
                .when().put(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void putRequestStatusCode200() {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given() 
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().put(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void simplePutRequest() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        JSONObject enabledFireplace = new JSONObject(defaultFireplaceJson.toString());
        enabledFireplace.put("enabled", true);

        given()
                .contentType(ContentType.JSON)
                .body(enabledFireplace.toString())
                .put(fireplaceEndpoint);

        given()
                .when().get(fireplaceEndpoint + "/" + defaultFireplaceJson.getString("label"))
                .then().body(Matchers.equalTo(enabledFireplace.toString()));
    }

    // === PATCH ===

    @Test
    void patchRequestWithoutBody() {
        given()
                .when().patch(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void patchRequestStatusCode200() {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().patch(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    // === DELETE ===

    @Test
    void deleteRequestWithoutQueryParam() {
        given()
                .when().delete(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    void deleteRequestStatusCode200() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .queryParam("label", defaultFireplaceJson.getString("label"))
                .when().delete(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    @Test
    void simpleDeleteRequest() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .param("label", defaultFireplaceJson.get("label"))
                .delete(fireplaceEndpoint);

        given()
                .param("label", defaultFireplaceJson.get("label"))
                .when().get(fireplaceEndpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }
}
