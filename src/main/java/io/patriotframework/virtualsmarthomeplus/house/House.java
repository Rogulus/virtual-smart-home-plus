package io.patriotframework.virtualsmarthomeplus.house;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Set;

public class House {

    public void putDevice(Device devices) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void putDevices(Set<Device> devices) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public Device getDevice(String label) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public Set<Device> getDevices(Set<String> labels) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void updateDevice(Device device) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void updateDevices(Set<Device> devices) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void removeDevice(Device device) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void removeDevices(Set<Device> devices) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public Device getDeviceOfType(Class<? extends Device> deviceOfType, String label) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public Set<Device> getDevicesOfType(Class<? extends Device> deviceOfType) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public void getName() {
        throw new NotImplementedException("Not implemented yet.");
    }
}
