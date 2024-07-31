package io.patriot_framework.virtualsmarthomeplus.factory;

import io.patriot_framework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriot_framework.virtualsmarthomeplus.house.devices.Device;
import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.*;

/**
 * Class for creation of Devices
 */
public class DeviceFactory {
    public DeviceFactory() {
    }

    /**
     * Creates an instance of device using DeviceDTO.
     *
     * @param device DTO of device to be created
     * @return devices of certain type
     */
    public Device createDevice(DeviceDTO device) throws IllegalArgumentException {
        final String deviceType = device.getClass().getSimpleName();
        return switch (deviceType) {
            case "RGBLightDTO" -> new RGBLight(device.getLabel());
            case "ThermometerDTO" -> new Thermometer(device.getLabel());
            case "FireplaceDTO" -> new Fireplace(device.getLabel());
            case "DoorDTO" -> new Door(device.getLabel());
            case "AutomaticDoorDTO" -> new AutomaticDoor(device.getLabel());
            default -> throw new IllegalArgumentException(
                    "Device factory does not have rule for creating such device type");
        };
    }
}
