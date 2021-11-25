/*
 * Copyright 2021 Patriot project
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.apache.catalina.connector.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
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
                .then().statusCode(Response.SC_CREATED);

        given()
                .when().get(deviceEndpoint)
                .then().body(Matchers.equalTo(new JSONObject().put(defaultFireplaceJson.getString("label"),
                    defaultFireplaceJson).toString()));
    }
}
