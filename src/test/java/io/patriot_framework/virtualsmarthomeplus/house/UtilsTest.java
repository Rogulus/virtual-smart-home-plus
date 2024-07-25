package io.patriot_framework.virtualsmarthomeplus.house;

import io.patriot_framework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriot_framework.virtualsmarthomeplus.utils.VirtualSmartHomePlusHttpClient;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {

    public void getReturnsDevice() {
        VirtualSmartHomePlusHttpClient client = new VirtualSmartHomePlusHttpClient("127.0.0.1", 8080);
        DeviceDTO device = client.getDevice( "thermometer", "t1");
        System.out.println(device);
        assertTrue(true);
    }
}
