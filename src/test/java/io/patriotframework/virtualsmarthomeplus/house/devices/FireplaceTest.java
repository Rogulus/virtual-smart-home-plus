package io.patriotframework.virtualsmarthomeplus.house.devices;

import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FireplaceTest {
    Fireplace fireplace1 = new Fireplace("fireplace1");
    Fireplace fireplace2 = new Fireplace("fireplace2");

    @Test
    public void getStatus() {
        fireplace1.setEnabled(false);
        assertEquals(Fireplace.DISABLED, fireplace1.getStatus());
        assertEquals(Fireplace.DISABLED, fireplace1.getStatus());
        assertFalse(fireplace1.getEnabled());
        fireplace1.setEnabled(true);
        assertEquals(Fireplace.ENABLED, fireplace1.getStatus());
        assertEquals(Fireplace.ENABLED, fireplace1.getStatus());
        assertTrue(fireplace1.getEnabled());
    }

    @Test
    public void fireUp() {
        assertEquals(Fireplace.DISABLED, fireplace1.getStatus());
        fireplace1.fireUp();
        assertEquals(Fireplace.ENABLED, fireplace1.getStatus());
        fireplace1.fireUp();
        assertEquals(Fireplace.ENABLED, fireplace1.getStatus());
        fireplace1.fireUp();
        fireplace1.fireUp();
        assertEquals(Fireplace.ENABLED, fireplace1.getStatus());
    }

    @Test
    public void extinguish() {
        fireplace1.setEnabled(false);
        fireplace1.extinguish();
        assertEquals(Fireplace.DISABLED, fireplace1.getStatus());
        fireplace1.extinguish();
        assertEquals(Fireplace.DISABLED, fireplace1.getStatus());
        fireplace1.extinguish();
        fireplace1.extinguish();
        assertEquals(Fireplace.DISABLED, fireplace1.getStatus());
    }

    @Test
    public void testHasSameAttributes(){
        assertTrue(fireplace1.hasSameAttributes(fireplace2));
        fireplace1.setEnabled(true);
        assertFalse(fireplace1.hasSameAttributes(fireplace2));
        fireplace2.setEnabled(true);
        assertTrue(fireplace1.hasSameAttributes(fireplace2));
    }

    @Test
    public void testCreateWithSameAttributes() {
        fireplace1.setEnabled(true);
        assertFalse(fireplace1.hasSameAttributes(fireplace2));
        fireplace2 = fireplace1.createWithSameAttributes("fireplace2");
        assertTrue(fireplace1.hasSameAttributes(fireplace2));
    }
}
