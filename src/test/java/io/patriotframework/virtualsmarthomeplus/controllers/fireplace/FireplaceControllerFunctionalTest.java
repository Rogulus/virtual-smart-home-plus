package io.patriotframework.virtualsmarthomeplus.controllers.fireplace;

import io.patriotframework.virtualsmarthomeplus.controllers.DeviceControllerTestBase;

import org.json.JSONException;
import org.json.JSONObject;

public class FireplaceControllerFunctionalTest extends DeviceControllerTestBase {

    public FireplaceControllerFunctionalTest() throws JSONException {
        super();
    }

    @Override
    protected String getPathToDevices() {
        return "/api/v0.1/house/device";
    }


    @Override
    protected JSONObject getMinPostDevice() throws JSONException {
        return new JSONObject()
                .put("label", "label1");
    }

    @Override
    protected JSONObject getDefaultDevice() throws JSONException {
        return new JSONObject()
                .put("label", "label1")
                .put("enabled", false);
    }

    @Override
    protected JSONObject getFullUpdatedDevice() throws JSONException {
        return new JSONObject()
                .put("label", "label1")
                .put("enabled", true);
    }
}

