package io.patriot_framework.virtualsmarthomeplus.house.devices;

import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FireplaceTest {
    private final Fireplace fireplace1 = new Fireplace("fireplace1");
    private final Fireplace fireplace2 = new Fireplace("fireplace2");

    @Test
    public void constructorTest() {

        Fireplace device1 = new Fireplace("fireplacex");
        assertDoesNotThrow(() -> {
            Fireplace device2 = new Fireplace("fireplacexy");
        });
        assertThrows(IllegalArgumentException.class, () -> new Fireplace(""));
        assertThrows(IllegalArgumentException.class, () -> new Fireplace(null));
    }

    @Test
    public void fireUpExtinguishTest() {
        fireplace1.setEnabled(true);
        Assertions.assertEquals(fireplace1.getStatus(), Fireplace.EXTINGUISHED);
        fireplace1.fireUp();
        Assertions.assertEquals(fireplace1.getStatus(), Fireplace.ON_FIRE);
        fireplace1.extinguish();
        Assertions.assertEquals(fireplace1.getStatus(), Fireplace.EXTINGUISHED);
        fireplace1.fireUp();
        fireplace1.fireUp();
        Assertions.assertEquals(fireplace1.getStatus(), Fireplace.ON_FIRE);

        fireplace1.extinguish();
        fireplace1.extinguish();
        Assertions.assertEquals(fireplace1.getStatus(), Fireplace.EXTINGUISHED);
    }

    @Test
    public void createWithSameAttributes() {
        fireplace1.setEnabled(true);
        Fireplace fireplace3 = fireplace1.createWithSameAttributes("rgb3");
        Assertions.assertEquals(fireplace3.isEnabled(), fireplace1.isEnabled());
        assertTrue(fireplace3.hasSameAttributes(fireplace1));
    }

    @Test
    public void testHasSameAttributes() {
        DeviceMock device1 = new DeviceMock("fireplace1");
        assertThrows(IllegalArgumentException.class, () -> fireplace1.hasSameAttributes(device1));

        assertThrows(IllegalArgumentException.class, () -> fireplace1.hasSameAttributes(null));

        assertTrue(fireplace1.hasSameAttributes(fireplace2));
        fireplace1.fireUp();
        assertFalse(fireplace1.hasSameAttributes(fireplace2));
        fireplace1.extinguish();
        assertTrue(fireplace1.hasSameAttributes(fireplace2));
    }
}
