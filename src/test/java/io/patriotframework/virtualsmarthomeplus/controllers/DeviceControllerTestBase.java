package io.patriotframework.virtualsmarthomeplus.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.patriotframework.virtualsmarthomeplus.controllers.house.TestDevice;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public abstract class DeviceControllerTestBase {
    protected Device minPostDevice;
    protected Device fullDefaultDevice;
    protected Device fullUpdatedDevice;
    protected String pathToDevices;
    protected String label;
    protected Class<?extends Device[]> aClass = Fireplace[].class;

    protected ObjectNode minPostDeviceJson;
    protected ObjectNode fullDefaultDeviceJson;
    protected ObjectNode fullUpdatedDeviceJson;
    ObjectMapper jsonMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    @Autowired House house;

    public DeviceControllerTestBase() throws JSONException, JsonProcessingException {
        pathToDevices = getPathToDevices();

        fullDefaultDevice = getDefaultDevice();
        fullUpdatedDevice = getFullUpdatedDevice();

        minPostDeviceJson = (ObjectNode) jsonMapper.readTree(getMinPostDeviceJson());
        fullDefaultDeviceJson = jsonMapper.valueToTree(fullDefaultDevice);
        fullUpdatedDeviceJson = jsonMapper.valueToTree(fullUpdatedDevice);

        label = fullDefaultDevice.getLabel();
    }


    //todo comments and mentiion, that it should be same device with different representations
    protected abstract String getPathToDevices();
    protected abstract String getMinPostDeviceJson() throws JSONException;
    protected abstract Device getDefaultDevice();
    protected abstract Device getFullUpdatedDevice();

    @Test
    public void validPost() throws JsonProcessingException {
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(200).body(equalTo(fullDefaultDevice));

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void postAlreadyPresentDeviceReturns409() {
        house.putDevice(fullDefaultDevice);

        given().body(fullUpdatedDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(409);

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void postWithMissingMandatoryAttributeReturns400() {
        fullDefaultDeviceJson.remove("label");
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(400);

        assertThrows(NoSuchElementException.class, () -> {
            house.getDevice(label);
        });
    }

    @Test
    public void postWithoutOptionalAttributesReturnsDefaultDevice() {
        given().body(minPostDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(200).body(equalTo(fullDefaultDevice));

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void postWithSuperfluousAttributeReturns400() {
        fullDefaultDeviceJson.put("superfluous", true);
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(400);

        assertThrows(NoSuchElementException.class, () -> {
            house.getDevice(label);
        });
    }

    @Test
    public void validDelete() {
        house.putDevice(fullDefaultDevice);

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
    public void updateDefaultDevice() {
        fullUpdatedDeviceJson.get("label");
        house.putDevice(fullDefaultDevice);

        given().body(fullUpdatedDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(200).body(equalTo(fullUpdatedDevice));

        assertTrue(fullUpdatedDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void putWithMissingMandatoryAttributeReturns400() {
        fullDefaultDeviceJson.remove("label");
        house.putDevice(fullDefaultDevice);

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void putWithSuperfluousAttributeReturns400() {
        house.putDevice(fullDefaultDevice);
        fullDefaultDeviceJson.put("Superfluous", true);

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void putAttributeWithWrongValueReturns400() {
        house.putDevice(fullDefaultDevice);
        fullDefaultDeviceJson.put("label", "newLabel");

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void getAllDevicesOfGivenType() {
        Device secondFullDefaultDevice = fullDefaultDevice.createWithSameState("label2");
        house.putDevice(fullDefaultDevice);
        house.putDevice(secondFullDefaultDevice);
        house.putDevice(new TestDevice("label3"));
        TreeSet<Device> tsGiven = new TreeSet<>();
        TreeSet<Device> tsExpected = new TreeSet<>();
        tsExpected.add(fullDefaultDevice);
        tsExpected.add(secondFullDefaultDevice);

        Device[] responseBody = given()
                .when().get(pathToDevices)
                .then().statusCode(200).extract().as(aClass);

        Collections.addAll(tsGiven, responseBody);
        assertEquals(tsExpected, tsGiven);
    }

    @Test
    public void getEmptyListOfDevicesOfGivenType() throws JsonProcessingException {
        given()
                .when().get(pathToDevices)
                .then().statusCode(200).body(equalTo(jsonMapper.writeValueAsString(new HashSet<>())));
    }
}
