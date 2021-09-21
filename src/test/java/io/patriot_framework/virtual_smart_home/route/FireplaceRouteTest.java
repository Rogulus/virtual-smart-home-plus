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

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FireplaceRouteTest {

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
    public void getRequestContentTypeJson() {
        given()
                .when().get(fireplaceEndpoint)
                .then().contentType(ContentType.JSON);
    }

    @Test
    public void getRequestNotFound() {
        given()
                .when().get(fireplaceEndpoint + "/notFound")
                .then().statusCode(Response.SC_NOT_FOUND); // 404
    }

    @Test
    public void getRequestNonExistentEndpoint() {
        given()
                .when().get(fireplaceEndpoint + "/nonExistent")
                .then().body(Matchers.equalTo(""));
    }

    @Test
    public void getRequestWithInvalidParam() {
        given()
                .param("param", "param")
                .when().get(fireplaceEndpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    @Test
    public void getRequestStatusCode200() {
        given()
                .when().get(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    @Test
    public void emptyGetRequest() throws JSONException {
        given()
                .param("label", defaultFireplaceJson.get("label"))
                .when().get(fireplaceEndpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getUniqueFireplace() throws JSONException {
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
    public void postRequestWithoutBody() {
        given()
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void postRequestWithIncorrectJsonBody() throws JSONException {
        JSONObject invalidRequestBody = new JSONObject().put("json", "json");

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody.toString())
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void postRequestWithNonJsonBody() {
        given()
                .contentType(ContentType.TEXT)
                .body("body")
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void postRequestConflict() {
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
    public void postRequestStatusCode201() {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_CREATED);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void simplePostRequest() throws JSONException {
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
    public void putRequestWithoutBody() {
        given()
                .when().put(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void putRequestWithInvalidBody() throws JSONException {
        JSONObject invalidRequestBody = new JSONObject().put("json", "json");

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody.toString())
                .when().put(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void putRequestStatusCode200() {
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
    public void simplePutRequest() throws JSONException {
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
    public void patchRequestWithoutBody() {
        given()
                .when().patch(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void patchRequestStatusCode200() {
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
    public void deleteRequestWithoutQueryParam() {
        given()
                .when().delete(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void deleteRequestStatusCode200() throws JSONException {
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
    public void simpleDeleteRequest() throws JSONException {
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
