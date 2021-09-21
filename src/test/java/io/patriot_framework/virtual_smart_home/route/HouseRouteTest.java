package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.catalina.connector.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HouseRouteTest {

    private final String houseEndpoint = "house";

    @Test
    public void contentTypeJSON() {
        given()
                .when().get(houseEndpoint)
                .then().assertThat().contentType(ContentType.JSON);
    }

    @Test
    public void getRequestStatusCode200() {
        given()
                .when().get(houseEndpoint)
                .then().assertThat().statusCode(Response.SC_OK);
    }

    @Test
    public void emptyGetRequest() throws JSONException {
        JSONObject expected = new JSONObject();
        expected.put("houseName", "house").put("devices", new JSONObject());

        given()
                .when().get(houseEndpoint)
                .then().assertThat().body(Matchers.equalTo(expected.toString()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void simpleGetRequest() throws JSONException {
        JSONObject defaultFireplaceJson = new JSONObject()
                .put("label", "fireplace")
                .put("enabled", false);

        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(houseEndpoint)
                .then().statusCode(Response.SC_CREATED); // TODO: 404

        given()
                .when().get(houseEndpoint)
                .then().body(Matchers.equalTo("OK"));
    }
}
