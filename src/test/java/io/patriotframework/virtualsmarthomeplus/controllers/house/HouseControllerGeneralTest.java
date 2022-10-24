package io.patriotframework.virtualsmarthomeplus.controllers.house;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class HouseControllerGeneralTest {
    private final String housePath = "/api/v0.1/house";
    private final String houseName = "house1";
    private final JSONObject defaultHouse;
    private final JSONObject updatedHouse;


    public HouseControllerGeneralTest() throws JSONException {
        RestAssured.baseURI = housePath;
        defaultHouse = new JSONObject()
                .put("houseName", houseName)
                .put("devices", new List[] {});

        updatedHouse = new JSONObject()
                .put("houseName", "newName")
                .put("devices", new List[] {});
    }
//
//
//
//    //  universal
//    @Test
//    public void contentTypeJson() {
//        get().then()
//                .contentType(JSON);
//    }
//
//    @Test void noContentType() {
//        given().noContentType()
//                .get().then()
//                .statusCode(200)
//                .body(equalTo(defaultHouse));
//    }
//
//    @Test
//    public void acceptHeaderJson() {
//        given().accept("application/json")
//                .get().then()
//                .statusCode(200)
//                .body(equalTo(defaultHouse));
//    }
//
//    @Test
//    public void basicGetHouseWithQueryParams() {
//        given().queryParam("param", "param")
//                .get().then()
//                .statusCode(200)
//                .body(equalTo(defaultHouse));
//    }
//
//    @Test
//    public void putHouseNameWithoutBody() {
//        put().then()
//                .statusCode(400);
//    }
//
//    @Test
//    public void putHouseNameWithQueryParam() {
//        given().queryParam("param", "param").body(updatedHouse)
//                .put().then()
//                .statusCode(200)
//                .body(equalTo(updatedHouse));
//    }
//
//    @Test
//    public void putHouseNameWithUnexpectedAttribute() throws JSONException {
//        JSONObject jo = new JSONObject(defaultHouse.toString());
//        jo.put("unexpected", "unexpected");
//
//        given().body(jo)
//                .put().then()
//                .statusCode(400);
//    }
//
//    //  POST
//    @Test
//    public void postReturns405() {
//        post().then()
//                .statusCode(405);
//    }
//
//    //  DELETE
//    @Test
//    public void deleteReturns405() {
//        delete().then()
//                .statusCode(405);
//    }
//
//    //  OPTIONS
//    @Test
//    public void optionsReturns405() {
//        options().then()
//                .statusCode(405);
//    }
//
//    //  PATCH
//    @Test
//    public void patchReturns405() {
//        patch().then()
//                .statusCode(405);
//    }
//
//    //  HEAD
//    @Test
//    public void headReturns405() {
//        head().then()
//                .statusCode(405);
//    }

}
