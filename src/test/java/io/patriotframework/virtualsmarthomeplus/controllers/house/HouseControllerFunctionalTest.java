package io.patriotframework.virtualsmarthomeplus.controllers.house;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest
public class HouseControllerFunctionalTest {
    private final String housePath = "/api/v0.1/house";
    private final String houseName = "house1";
    private final JSONObject defaultHouse;
    private final JSONObject updatedHouse;

    public HouseControllerFunctionalTest() throws JSONException {
        RestAssured.baseURI = housePath;
        defaultHouse = new JSONObject()
                .put("houseName", houseName)
                .put("devices", new List[] {});

        updatedHouse = new JSONObject()
                .put("houseName", "newName")
                .put("devices", new List[] {});
    }
//
//    //  GET
//    @Test
//    public void getDefaultHouse() {
//        get().then()
//                .statusCode(200)
//                .body(equalTo(defaultHouse));
//    }
//
//    //  PUT
//    @Test
//    public void putHouseName() {
//        given().body(updatedHouse)
//                .put().then()
//                    .statusCode(200)
//                    .body(equalTo(updatedHouse));
//    }
//
//    @Test
//    public void putHouseNameWithoutOptionalAttribute() {
//        given().body(new JSONObject())
//                .put().then()
//                .statusCode(200)
//                .body(equalTo(defaultHouse));
//    }

//    in case of some mandatory attribute
//    @Test
//    public void putHouseNameWithoutMandatoryAttributeReturns400() {
//        JSONObject jo = new JSONObject();
//        jo.put("mandatory", "mandatory");
//
//        given().body(jo)
//                .put()
//                .then().statusCode(400);
//    }








    //todo testy na json
    // returns JSON hlavicka?

    //todo body house - attributy + devices

    //put without body
    //put with empty body

    //todo slocit statuscode a body u stejnych pripadu do jednoho
    //todo getWith body - ignore body
    //todo put without body - bad request asi. rekl bych pokud je neco navic, tak neva, ale pokud neco chybi, tak uz spis error nez agile, asi hlavne zalezi jestli put musi mit vsechny atributy pokud ne, tak je to specialni pripad a 200
    //rozhodnuti - body musi byt full
    //rozhodnuti - pri spatnem pozadavku, body jakekoli
    //atribut navic vadi, volitelne atributy nemusi byt, pak se nastavi defaultni hodnota
    //agilni je super, ale taky zabere vic prace, v takovem pripade na to prdim

    //  sloucit body a statusy

}
