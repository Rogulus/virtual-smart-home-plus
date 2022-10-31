package io.patriotframework.virtualsmarthomeplus.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public abstract class DeviceControllerTestBase {
    protected JSONObject minPostDevice;
    protected JSONObject fullDefaultDevice;
    protected JSONObject fullUpdatedDevice;
    protected String pathToDevices;

    protected String label;

    ObjectMapper jsonMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    @Autowired House house;

    public DeviceControllerTestBase() throws JSONException{
        pathToDevices = getPathToDevices();
        minPostDevice = getMinPostDevice();
        fullDefaultDevice = getDefaultDevice();
        fullUpdatedDevice = getFullUpdatedDevice();
        label = fullDefaultDevice.getString("label");
    }

    //todo comments and mentiion, that it should be same device with different representations
    protected abstract String getPathToDevices();
    protected abstract JSONObject getMinPostDevice() throws JSONException;
    protected abstract JSONObject getDefaultDevice() throws JSONException;
    protected abstract JSONObject getFullUpdatedDevice() throws JSONException;

    @Test
    public void validPost() throws JsonProcessingException {
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(200).body(equalTo(fullDefaultDevice));

        assertEquals(jsonMapper.writeValueAsString(house.getDevice(label)), fullDefaultDevice.toString());
    }

    @Test
    public void postAlreadyPresentDeviceReturns409() throws JSONException, JsonProcessingException {
        house.putDevice(new Device(label));

        given().body(fullUpdatedDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(409);

        assertEquals(jsonMapper.writeValueAsString(house.getDevice(label)), fullDefaultDevice.toString());
    }

    @Test
    public void postWithMissingMandatoryAttributeReturns400() {
        fullDefaultDevice.remove("label");
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(400);

        assertThrows(NoSuchElementException.class, () -> {
            house.getDevice(label);
        });
    }

    @Test
    public void postWithoutOptionalAttributesReturnsDefaultDevice() throws JsonProcessingException{
        given().body(minPostDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(200).body(equalTo(fullDefaultDevice));

        assertEquals(jsonMapper.writeValueAsString(house.getDevice(label)), fullDefaultDevice.toString());
    }

    @Test
    public void postWithSuperfluousAttributeReturns400() throws JSONException {
        fullDefaultDevice.put("superfluous", true);
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(400);

        assertThrows(NoSuchElementException.class, () -> {
            house.getDevice(label);
        });
    }

    @Test
    public void validDelete() {
        house.putDevice(new Device(label));

        given()
                .when().delete(pathToDevices + "/" + label)
                .then().statusCode(200).body(equalTo(fullDefaultDevice.toString()));

        assertThrows(NoSuchElementException.class, () -> {
            house.getDevice(label);
        });
    }

    @Test
    public void deleteDeviceWhichIsNotPresentReturns404() {
        given()
                .when().delete(pathToDevices + "/" + label)
                .then().statusCode(404);
    }

    @Test
    public void updateDefaultDevice() throws JSONException, JsonProcessingException {
        fullUpdatedDevice.getString("label");
        house.putDevice(new Device(label));

        given().body(fullUpdatedDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(200).body(equalTo(fullUpdatedDevice));

        assertEquals(jsonMapper.writeValueAsString(house.getDevice(label)), fullUpdatedDevice.toString());
    }

    @Test
    public void putWithMissingMandatoryAttributeReturns400() throws JsonProcessingException {
        fullDefaultDevice.remove("label");
        house.putDevice(new Device(label));

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertEquals(jsonMapper.writeValueAsString(house.getDevice(label)), fullDefaultDevice.toString());
    }

    @Test
    public void putWithSuperfluousAttributeReturns400() throws JSONException, JsonProcessingException {
        house.putDevice(new Device(label));
        fullDefaultDevice.put("Superfluous", true);

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertEquals(jsonMapper.writeValueAsString(house.getDevice(label)), fullDefaultDevice.toString());
    }

    @Test
    public void putAttributeWithWrongValueReturns400() throws JSONException, JsonProcessingException {
        house.putDevice(new Device(label));
        fullDefaultDevice.put("label", "newLabel");

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertEquals(jsonMapper.writeValueAsString(house.getDevice(label)), fullDefaultDevice.toString());
    }

    @Test
    public void getAllDevicesOfGivenType() throws JsonProcessingException {
        house.putDevice(new Device("label1"));
        house.putDevice(new Device("label2"));

        given()
                .when().get(pathToDevices)
                .then().statusCode(200).body(equalTo(jsonMapper.writeValueAsString(house.getDevices(
                        new HashSet<>(Arrays.asList("label1", "label2"))))));
    }

    @Test
    public void getEmptyListOfDevicesOfGivenType() throws JsonProcessingException {
        given()
                .when().get(pathToDevices)
                .then().statusCode(200).body(equalTo(jsonMapper.writeValueAsString(new HashSet<>())));
    }
}


//todo udelat test jinde, kde se bude mazat/updatovat/ziskavat zarizeni jehoz label je v dome, ale jiny typ
