package io.patriotframework.virtualsmarthomeplus.controllers.house;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.apache.commons.lang3.NotImplementedException;

public class TestDevice extends Device {

     public TestDevice(String label) {
         super(label);
     }

    public Device createWithSameState(String newLabel) {
        return new TestDevice("newLabel");
    }

    public boolean hasSameAttributes(Device device) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet.");
    }
}
