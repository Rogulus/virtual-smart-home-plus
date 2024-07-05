package io.patriot_framework.virtualsmarthomeplus.house.devices;


public class DeviceMock extends Device {

    public DeviceMock(String label) {
        super(label);
    }

    public DeviceMock(Device origDevice, String newLabel) {
        super(origDevice, newLabel);
    }

    @Override
    public Device createWithSameAttributes(String newLabel) {
        return null;
    }

    @Override
    public boolean hasSameAttributes(Device device) throws IllegalArgumentException {
        return false;
    }
}
