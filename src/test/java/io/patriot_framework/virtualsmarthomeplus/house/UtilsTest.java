package io.patriot_framework.virtualsmarthomeplus.house;

import io.patriot_framework.generator.device.Device;
import io.patriot_framework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriot_framework.virtualsmarthomeplus.utils.VirtualSmartHomePlusClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {
    @Test
    public void getReturnsDevice() {
        DeviceDTO device = VirtualSmartHomePlusClient.getDevice("127.0.0.1", 8080, "thermometer", "t1");
        System.out.println(device);
        assertTrue(true);
    }
}
