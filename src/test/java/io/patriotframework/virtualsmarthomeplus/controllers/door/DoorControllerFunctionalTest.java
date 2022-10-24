package io.patriotframework.virtualsmarthomeplus.controllers.door;

import io.patriotframework.virtualsmarthomeplus.controllers.DeviceTestFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class DoorControllerFunctionalTest {
    String doorURN = "/api/v0.1/house/device/door";

    JSONObject minPostFireplace = new JSONObject()
            .put("label", "label1");

    JSONObject fullDefaultFireplace = new JSONObject()
            .put("label", "label1")
            .put("enabled", false);

    JSONObject fullUpdatedFireplace = new JSONObject()
            .put("label", "label1")
            .put("enabled", true);

    public DoorControllerFunctionalTest() throws JSONException {
    }

//    @TestFactory
//    Iterable<DynamicTest>commonTests(){
//        DeviceTestFactory dtf = new DeviceTestFactory();
//        return dtf.getTests(doorURN);
//    }
}
