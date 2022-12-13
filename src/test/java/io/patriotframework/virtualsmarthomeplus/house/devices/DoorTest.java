package io.patriotframework.virtualsmarthomeplus.house.devices;

import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoorTest {
    Door door1 = new Door("door1");
    Door door2 = new Door("door2");

    @Test
    public void getStatus() {
        door1.setEnabled(false);
        assertEquals(Door.DISABLED, door1.getStatus());
        assertEquals(Door.DISABLED, door1.getStatus());
        assertFalse(door1.getEnabled());
        door1.setEnabled(true);
        assertEquals(Door.ENABLED, door1.getStatus());
        assertEquals(Door.ENABLED, door1.getStatus());
        assertTrue(door1.getEnabled());
    }

    @Test
    public void fireUp() {
        assertEquals(Door.DISABLED, door1.getStatus());
        door1.open();
        assertEquals(Door.ENABLED, door1.getStatus());
        door1.open();
        assertEquals(Door.ENABLED, door1.getStatus());
        door1.open();
        door1.open();
        assertEquals(Door.ENABLED, door1.getStatus());
    }

    @Test
    public void extinguish() {
        door1.setEnabled(false);
        door1.close();
        assertEquals(Door.DISABLED, door1.getStatus());
        door1.close();
        assertEquals(Door.DISABLED, door1.getStatus());
        door1.close();
        door1.close();
        assertEquals(Door.DISABLED, door1.getStatus());
    }

    @Test
    public void testHasSameAttributes(){
        assertTrue(door1.hasSameAttributes(door2));
        door1.setEnabled(true);
        assertFalse(door1.hasSameAttributes(door2));
        door2.setEnabled(true);
        assertTrue(door1.hasSameAttributes(door2));
    }

    @Test
    public void testCreateWithSameAttributes() {
        door1.setEnabled(true);
        assertFalse(door1.hasSameAttributes(door2));
        door2 = door1.createWithSameAttributes("door2");
        assertTrue(door1.hasSameAttributes(door2));
    }
}
