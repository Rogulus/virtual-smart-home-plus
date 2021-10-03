package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.catalina.connector.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DeviceRouteTest {

    private final String deviceEndpoint = "house/device";

    @Test
    void getRequestContentTypeJson() {
        given()
                .when().get(deviceEndpoint)
                .then().contentType(ContentType.JSON);
    }

    @Test
    void getRequestStatusCode200() {
        given()
                .when().get(deviceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    @Test
    void emptyGetRequest() {
        given()
                .when().get(deviceEndpoint)
                .then().body(Matchers.equalTo(new JSONObject().toString()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void simpleGetRequest() throws JSONException {
        JSONObject defaultFireplaceJson = new JSONObject()
                .put("label", "fireplace")
                .put("enabled", false);

        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(deviceEndpoint + "/fireplace")
                .then().statusCode(Response.SC_CREATED); // Successful POST request (201) but nothing on the endpoint.

        given()
                .when().get(deviceEndpoint)
                .then().body(Matchers.equalTo(new JSONObject().put(defaultFireplaceJson.getString("label"),
                    defaultFireplaceJson).toString()));
    }
}
