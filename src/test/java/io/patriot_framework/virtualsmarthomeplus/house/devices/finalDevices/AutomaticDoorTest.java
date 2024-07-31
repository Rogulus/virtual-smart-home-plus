package io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutomaticDoorTest {

    @Test
    public void test() throws InterruptedException {
        System.out.println("JSME V TESSTU");
        var a = new AutomaticDoor("automaticDoor");
        Thread.sleep(100000);
    }

}