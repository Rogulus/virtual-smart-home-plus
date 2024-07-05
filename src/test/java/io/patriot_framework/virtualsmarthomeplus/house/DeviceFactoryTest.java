package io.patriot_framework.virtualsmarthomeplus.house;

import io.patriot_framework.virtualsmarthomeplus.DTOS.MockDTO;
import io.patriot_framework.virtualsmarthomeplus.DTOs.DoorDTO;
import io.patriot_framework.virtualsmarthomeplus.DTOs.FireplaceDTO;
import io.patriot_framework.virtualsmarthomeplus.DTOs.RGBLightDTO;
import io.patriot_framework.virtualsmarthomeplus.DTOs.ThermometerDTO;
import io.patriot_framework.virtualsmarthomeplus.factory.DeviceFactory;
import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import io.patriot_framework.virtualsmarthomeplus.DTOs.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeviceFactoryTest {

    private final DeviceFactory deviceFactory;

    public DeviceFactoryTest() {
        this.deviceFactory = new DeviceFactory();
    }

    @Test
    public void createRGBDevice() {
        RGBLightDTO rgbLightDTO = new RGBLightDTO();
        rgbLightDTO.setLabel("rgb1");
        Assertions.assertEquals(deviceFactory.createDevice(rgbLightDTO).getClass(), RGBLight.class);
    }

    @Test
    public void createFireplaceDevice() {
        FireplaceDTO fireplaceDTO = new FireplaceDTO();
        fireplaceDTO.setLabel("fire1");
        Assertions.assertEquals(deviceFactory.createDevice(fireplaceDTO).getClass(), Fireplace.class);
    }

    @Test
    public void createDoorDevice() {
        DoorDTO doorDTO = new DoorDTO();
        doorDTO.setLabel("door1");
        Assertions.assertEquals(deviceFactory.createDevice(doorDTO).getClass(), Door.class);
    }

    @Test
    public void createThermometerDevice() {
        ThermometerDTO thermometerDTO = new ThermometerDTO();
        thermometerDTO.setLabel("1");
        Assertions.assertEquals(deviceFactory.createDevice(thermometerDTO).getClass(), Thermometer.class);
    }

    @Test
    public void createUnknownDevice() {
        MockDTO mockDTO = new MockDTO();
        mockDTO.setLabel("mock");
        assertThrows(IllegalArgumentException.class, () -> deviceFactory.createDevice(mockDTO));
    }

    @Test
    public void labelNullInDeviceDTO() {
        RGBLightDTO mockDTO = new RGBLightDTO();

        assertThrows(IllegalArgumentException.class, () -> deviceFactory.createDevice(mockDTO));
    }
}
