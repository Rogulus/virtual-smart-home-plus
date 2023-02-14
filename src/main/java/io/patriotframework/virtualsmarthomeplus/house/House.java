package io.patriotframework.virtualsmarthomeplus.house;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.apache.commons.lang3.NotImplementedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.HashSet;

/**
 * This class is responsible for management of {@code Device} used in the Virtual Smart Home.
 */
public class House {

    /**
     * Puts new device to the house object.
     *
     * @param device actual instance of device
     * @throws IllegalArgumentException if device is null
     * @throws KeyAlreadyExistsException if device with given label is already present in the house
     */
    public void addDevice(Device device) throws IllegalArgumentException, KeyAlreadyExistsException {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Returns a device occupied in the house.
     *
     * @param label label of the demanded device
     * @return instance of device with given label
     * @throws IllegalArgumentException if label is null
     */
    public Device getDevice(String label) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Updates device from the house.
     *
     * @param device device with updated values (device is specified by its label)
     * @throws IllegalArgumentException if device is null
     * @throws NoSuchElementException if no device with given label is present in the house
     */
    public void updateDevice(Device device) throws IllegalArgumentException, NoSuchElementException {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Removes given device from the house. (Removes device with the same label.)
     *
     * @param device label of the device to be removed
     * @throws IllegalArgumentException if label is null
     * @throws NoSuchElementException if house doesn't contain device with given label
     */
    public void removeDevice(Device device) throws IllegalArgumentException, NoSuchElementException{
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Provides devices of certain type which are stored in house.
     *
     * @param deviceOfType type of requested devices
     * @return set of devices of requested type, null if such device does not exist
     */
    public HashSet<Device> getDevicesOfType(Class<? extends Device> deviceOfType) {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Provides the name of the house.
     *
     * @return name of the house
     */
    public String getName() {
        throw new NotImplementedException("Not implemented yet.");
    }
}
