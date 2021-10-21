package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.apache.catalina.connector.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class DeviceRouteBase {

    // === GET ===
    void getRequestContentTypeJson(String endpoint) {
        given()
                .when().get(endpoint)
                .then().contentType(ContentType.JSON);
    }

    void getRequestNotFound(String endpoint) {
        given()
                .when().get(endpoint + "/notFound")
                .then().statusCode(Response.SC_NOT_FOUND); // 404
    }

    void getRequestNonExistentEndpoint(String endpoint) {
        given()
                .when().get(endpoint + "/nonExistent")
                .then().body(Matchers.equalTo(""));
    }

    void getRequestWithInvalidParam(String endpoint) {
        given()
                .param("param", "param")
                .when().get(endpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    void getRequestStatusCode200(String endpoint) {
        given()
                .when().get(endpoint)
                .then().statusCode(Response.SC_OK);
    }

    void emptyGetRequest(String endpoint, JSONObject body) throws JSONException {
        given()
                .param("label", body.get("label"))
                .when().get(endpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    void getUniqueDevice(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .when().get(endpoint + "/" + body.getString("label"))
                .then().body(Matchers.equalTo(body.toString()));
    }

    // === POST ===
    void postRequestWithoutBody(String endpoint) {
        given()
                .when().post(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void postRequestWithInvalidJsonBody(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void postRequestWithNonJsonBody(String endpoint) {
        given()
                .contentType(ContentType.TEXT)
                .body("body")
                .when().post(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void postRequestConflict(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().statusCode(Response.SC_CONFLICT); // 409
    }

    void postRequestStatusCode201(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().statusCode(Response.SC_CREATED);
    }

    void simplePostRequest(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().body(Matchers.equalTo(body.toString()));

        given()
                .when().get(endpoint + "/" + body.getString("label"))
                .then().body(Matchers.equalTo(body.toString()));
    }

    // === PUT ===
    void putRequestWithoutBody(String endpoint) {
        given()
                .when().put(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void putRequestWithInvalidJsonBody(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().put(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void putRequestStatusCode200(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().put(endpoint)
                .then().statusCode(Response.SC_OK);
    }

    // === PATCH ===
    void patchRequestWithoutBody(String endpoint) {
        given()
                .when().patch(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void patchRequestStatusCode200(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().patch(endpoint)
                .then().statusCode(Response.SC_OK);
    }

    // === DELETE ===
    void deleteRequestWithoutQueryParam(String endpoint) {
        given()
                .when().delete(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void deleteRequestStatusCode200(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .queryParam("label", body.getString("label"))
                .when().delete(endpoint)
                .then().statusCode(Response.SC_OK);
    }

    void simpleDeleteRequest(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .param("label", body.get("label"))
                .delete(endpoint);

        given()
                .param("label", body.get("label"))
                .when().get(endpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }
}
