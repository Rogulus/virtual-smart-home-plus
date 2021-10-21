package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class FireplaceRouteTest extends DeviceRouteBase {

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
        super.getRequestContentTypeJson(fireplaceEndpoint);
    }

    @Test
    void getRequestNotFound() {
        super.getRequestNotFound(fireplaceEndpoint);
    }

    @Test
    void getRequestNonExistentEndpoint() {
        super.getRequestNonExistentEndpoint(fireplaceEndpoint);
    }

    @Test
    void getRequestWithInvalidParam() {
        super.getRequestWithInvalidParam(fireplaceEndpoint);
    }

    @Test
    void getRequestStatusCode200() {
        super.getRequestStatusCode200(fireplaceEndpoint);
    }

    @Test
    void emptyGetRequest() throws JSONException {
        super.emptyGetRequest(fireplaceEndpoint, defaultFireplaceJson);
    }

    @Test
    void getUniqueFireplace() throws JSONException {
        super.getUniqueDevice(fireplaceEndpoint, defaultFireplaceJson);
    }

    // === POST ===
    @Test
    void postRequestWithoutBody() {
        super.postRequestWithoutBody(fireplaceEndpoint);
    }

    @Test
    void postRequestWithInvalidJsonBody() throws JSONException {
        JSONObject invalidRequestBody = new JSONObject().put("json", "json");

        super.postRequestWithInvalidJsonBody(fireplaceEndpoint, invalidRequestBody);
    }

    @Test
    void postRequestWithNonJsonBody() {
        super.postRequestWithNonJsonBody(fireplaceEndpoint);
    }

    @Test
    void postRequestConflict() {
        super.postRequestConflict(fireplaceEndpoint, defaultFireplaceJson);
    }

    @Test
    void postRequestStatusCode201() {
        super.postRequestStatusCode201(fireplaceEndpoint, defaultFireplaceJson);
    }

    @Test
    void simplePostRequest() throws JSONException {
        super.simplePostRequest(fireplaceEndpoint, defaultFireplaceJson);
    }

    // === PUT ===
    @Test
    void putRequestWithoutBody() {
        super.putRequestWithoutBody(fireplaceEndpoint);
    }

    @Test
    void putRequestWithInvalidJsonBody() throws JSONException {
        JSONObject invalidRequestBody = new JSONObject().put("json", "json");

        super.putRequestWithInvalidJsonBody(fireplaceEndpoint, invalidRequestBody);
    }

    @Test
    void putRequestStatusCode200() {
        super.putRequestStatusCode200(fireplaceEndpoint, defaultFireplaceJson);
    }

    @Test
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
        super.patchRequestWithoutBody(fireplaceEndpoint);
    }

    @Test
    void patchRequestStatusCode200() {
        super.patchRequestStatusCode200(fireplaceEndpoint, defaultFireplaceJson);
    }

    // === DELETE ===
    @Test
    void deleteRequestWithoutQueryParam() {
        super.deleteRequestWithoutQueryParam(fireplaceEndpoint);
    }

    @Test
    void deleteRequestStatusCode200() throws JSONException {
        super.deleteRequestStatusCode200(fireplaceEndpoint, defaultFireplaceJson);
    }

    @Test
    void simpleDeleteRequest() throws JSONException {
        super.simpleDeleteRequest(fireplaceEndpoint, defaultFireplaceJson);
    }
}
