package io.patriot_framework.virtualsmarthomeplus.house.devices;

import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoorTest {

    private final Door door1 = new Door("door1");
    private final Door door2 = new Door("door2");

    @Test
    public void constructorTest() {

        assertDoesNotThrow(() -> {
            Door device2 = new Door("door1");
        });
        assertThrows(IllegalArgumentException.class, () -> new Door(""));
        assertThrows(IllegalArgumentException.class, () -> new Door(null));
    }

    @Test
    public void OpenCloseTest() {
        door1.setEnabled(true);
        Assertions.assertEquals(door1.getStatus(), Door.CLOSED);
        door1.open();
        Assertions.assertEquals(door1.getStatus(), Door.OPENED);
        door1.close();
        Assertions.assertEquals(door1.getStatus(), Door.CLOSED);
        door1.open();
        door1.open();
        Assertions.assertEquals(door1.getStatus(), Door.OPENED);

        door1.close();
        door1.close();
        Assertions.assertEquals(door1.getStatus(), Door.CLOSED);
    }

    @Test
    public void createWithSameAttributes() {
        door1.setEnabled(true);
        Door door3 = door1.createWithSameAttributes("rgb3");
        assertEquals(door3.isEnabled(), door1.isEnabled());
        assertTrue(door3.hasSameAttributes(door1));
    }

    @Test
    public void testHasSameAttributes() {
        DeviceMock device1 = new DeviceMock("door1");
        assertThrows(IllegalArgumentException.class, () -> door1.hasSameAttributes(device1));

        assertThrows(IllegalArgumentException.class, () -> door1.hasSameAttributes(null));

        assertTrue(door1.hasSameAttributes(door2));
        door1.open();
        assertFalse(door1.hasSameAttributes(door2));
        door1.close();
        assertTrue(door1.hasSameAttributes(door2));
    }
}
