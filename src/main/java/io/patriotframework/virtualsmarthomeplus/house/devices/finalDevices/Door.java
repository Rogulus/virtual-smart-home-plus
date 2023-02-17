package io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.apache.commons.lang3.NotImplementedException;


/**
 * Representation of door device.
 */
public class Door extends Device {

    private boolean opened = false;

    public static final String OPENED = "opened";
    public static final String CLOSED = "closed";

    /**
     * Creates new door with given label.
     *
     * @param label label creates identity of the door and is compared in the equals method
     */
    public Door(String label) {
        super(label);
    }

    /**
     * Opens the door.
     */
    public void open() {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Closes the door.
     */
    public void close() {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Returns info about the door.
     *
     * @return {@link #OPENED} if the door is opened, {@link #CLOSED} otherwise
     */
    public String getStatus() {
        return opened ? OPENED : CLOSED;
    }

    @Override
    public Door createWithSameAttributes(String newLabel) {
        throw new NotImplementedException("Not implemented yet.");
    }

    @Override
    public boolean hasSameAttributes(Device device) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet.");
    }
}
