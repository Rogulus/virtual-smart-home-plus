package io.patriotframework.virtualsmarthomeplus.controllers.fireplace;

import io.patriotframework.virtualsmarthomeplus.controllers.DeviceTestFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class FireplaceControllerFunctionalTest extends DeviceTestFactory {
    String fireplaceURN = "/api/v0.1/house/device/fireplace";

    JSONObject minPostFireplace = new JSONObject()
            .put("label", "label1");

    JSONObject fullDefaultFireplace = new JSONObject()
            .put("label", "label1")
            .put("enabled", false);

    JSONObject fullUpdatedFireplace = new JSONObject()
            .put("label", "label1")
            .put("enabled", true);

    public FireplaceControllerFunctionalTest() throws JSONException {
    }


    @TestFactory
    Iterable<DynamicTest> commonTests() throws JSONException{
    return getTests(fireplaceURN, minPostFireplace, fullDefaultFireplace, fullUpdatedFireplace);
    }
}

