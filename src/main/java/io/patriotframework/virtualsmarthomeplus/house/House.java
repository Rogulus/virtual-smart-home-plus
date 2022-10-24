package io.patriotframework.virtualsmarthomeplus.house;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.apache.commons.lang3.NotImplementedException;


import java.util.List;

public class House {

    public void putDevices(List<Device> devices) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public List<Device> getDevices(List<String> labels) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void updateDevices(List<Device> devices) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void removeDevices(List<Device> devices) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public List<Device> getDevicesOfType(Class<? extends Device> deviceOfType) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void getName() {
        throw new NotImplementedException("Not implemented yet.");
    }
}
