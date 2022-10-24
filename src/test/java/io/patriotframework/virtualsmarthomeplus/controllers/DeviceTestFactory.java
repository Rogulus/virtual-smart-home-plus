package io.patriotframework.virtualsmarthomeplus.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeviceTestFactory {

    public Iterable<DynamicTest> getTests(String pathToDevices, JSONObject minPostDevice, JSONObject fullDefaultDevice,
                                          JSONObject fullUpdatedDevice) throws JSONException {

        String deviceURN = pathToDevices + fullDefaultDevice.get("label");
            return Arrays.asList(
                    DynamicTest.dynamicTest("Post twice",
                            () -> {
                                given().body(minPostDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                        .statusCode(200);

                                given().body(minPostDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                        .statusCode(409);
                            }
                    ),

                    DynamicTest.dynamicTest("Delete twice",
                            () -> {
                                given().body(minPostDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                        .statusCode(200);

                                given()
                                        .when().delete(deviceURN)
                                        .then()
                                        .statusCode(200)
                                        .body(equalTo(fullUpdatedDevice.toString()));

                                given()
                                        .when().delete(deviceURN)
                                        .then()
                                        .statusCode(404);
                            }
                    ),

                    DynamicTest.dynamicTest("Post with missing attribute",
                            () -> {
                                minPostDevice.remove("label");
                                given().body(minPostDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                        .statusCode(400);
                            }
                    ),

                    DynamicTest.dynamicTest("Post with superfluous attribute",
                            () -> {
                                minPostDevice.put("superfluous", true);
                                given().body(minPostDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                        .statusCode(400);
                            }
                    ),

                    DynamicTest.dynamicTest("Put with missing attribute",
                            () -> {
                                given().body(fullDefaultDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                        .statusCode(200);

                                fullDefaultDevice.remove("label");
                                given().body(fullDefaultDevice.toString())
                                        .when().put(deviceURN)
                                        .then()
                                        .statusCode(400);
                            }
                    ),

                    DynamicTest.dynamicTest("Put with superfluous attribute",
                            () -> {
                                given().body(fullDefaultDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                        .statusCode(200);

                                fullDefaultDevice.put("superfluous", true);
                                given().body(fullDefaultDevice.toString())
                                        .when().put(deviceURN)
                                        .then()
                                        .statusCode(400);
                            }
                    ),

                    DynamicTest.dynamicTest("put with mismatching label",
                            () -> {
                                given().body(fullDefaultDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                        .statusCode(200);

                                fullDefaultDevice.put("label", "newLabel");
                                given().body(fullDefaultDevice.toString())
                                        .when().put(deviceURN)
                                        .then()
                                        .statusCode(400);
                            }
                    ),

                    DynamicTest.dynamicTest("Post and Get MinDevice",
                            () -> {
                                given().body(minPostDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                            .statusCode(200)
                                            .body(equalTo(fullDefaultDevice.toString()));

                                given()
                                        .when().get(deviceURN + "/label1")
                                        .then()
                                            .statusCode(200)
                                            .body(equalTo(fullDefaultDevice.toString()));
                            }
                    ),

                    DynamicTest.dynamicTest("postAndGetMaxDevice",
                        () -> {
                            given().body(fullDefaultDevice.toString())
                                    .when()
                                        .post(pathToDevices)
                                    .then()
                                        .statusCode(200)
                                        .body(equalTo(fullDefaultDevice.toString()));

                            given()
                                    .when()
                                        .get(deviceURN)
                                    .then()
                                        .statusCode(200)
                                        .body(equalTo(fullDefaultDevice.toString()));
                        }
                    ),

                    DynamicTest.dynamicTest("fullLifeCycle",
                            () -> {
                                given()
                                        .when().get(deviceURN)
                                        .then()
                                        .statusCode(404);

                                given().body(fullDefaultDevice.toString())
                                        .when().post(pathToDevices)
                                        .then()
                                            .statusCode(201)
                                            .body(equalTo(fullDefaultDevice.toString()));

                                given()
                                        .when().get(deviceURN)
                                        .then()
                                            .statusCode(200)
                                            .body(equalTo(fullDefaultDevice.toString()));

                                given().body(fullUpdatedDevice.toString())
                                        .when().put(deviceURN)
                                        .then()
                                            .statusCode(200)
                                            .body(equalTo(fullUpdatedDevice.toString()));

                                given()
                                        .when().get(deviceURN)
                                        .then()
                                            .statusCode(200)
                                            .body(equalTo(fullUpdatedDevice.toString()));

                                given()
                                        .when().delete(deviceURN)
                                        .then()
                                        .statusCode(200)
                                        .body(equalTo(fullUpdatedDevice.toString()));

                                given()
                                        .when().get(deviceURN)
                                        .then()
                                        .statusCode(404);
                            }
                    ),

                    DynamicTest.dynamicTest("get all devices of given type",
                            () -> {
                                given().body(fullDefaultDevice.toString())
                                        .post(pathToDevices)
                                        .then()
                                        .statusCode(200);

                                fullDefaultDevice.put("label", "label2");

                                given().body(fullDefaultDevice.toString())
                                        .post(pathToDevices)
                                        .then()
                                        .statusCode(200);

                                //todo
                            }
                    )


            );

// odeslany a prijaty objekt by mel mit stejne atributy
//label v ceste a v objektu se lisi - chyba
//

//todo bez labelu by to melo vratit list dostupnych zarizeni daneho typu
        //todo osetrit vstupy
        //todo popsat

    }
}
