package io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.apache.commons.lang3.NotImplementedException;


/**
 * Representation of fireplace device.
 */
public class Fireplace extends Device {

    private Boolean onFire = false;

    public static final String ON_FIRE = "on_fire";
    public static final String EXTINGUISHED = "extinguished";

    /**
     * Creates new fireplace with given label.
     *
     * @param label label creates identity of the fireplace and is compared in the equals method
     */
    public Fireplace(String label) {
        super(label);
    }

    /**
     * Fires up the fireplace.
     */
    public void fireUp() {
        onFire = true;
    }

    /**
     * Extinguishes the fireplace.
     */
    public void extinguish() {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Returns the info about the fireplace.
     *
     * @return {@link #ON_FIRE} if the fireplace is lit, {@link #EXTINGUISHED} otherwise
     */
    @JsonIgnore
    public String getStatus() {
        return onFire ? ON_FIRE : EXTINGUISHED;
    }

    @Override
    public Fireplace createWithSameAttributes(String newLabel) {
        throw new NotImplementedException("Not implemented yet.");
    }

    @Override
    public boolean hasSameAttributes(Device device) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet.");
    }
}
