package io.patriotframework.virtualsmarthomeplus.house.devices;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Objects;

/**
 * From this class will be derived Actuators and Sensors. From them will be then derived final devices.
 */

public abstract class Device implements Comparable<Device> {
    private final String label;
    private Boolean enabled = false;

    /**
     * Creates new device with given label.
     *
     * @param label label creates identity of the device and is compared in the equals method
     */
    public Device(String label) {
        this.label = label;
    }

    /**
     * Creates new device with the same values of the attributes as given device except label.
     * Label of the new device is given by parameter.
     *
     * @param origDevice new device copies values of given device
     * @param newLabel   label creates identity of the device and is compared in the equals method
     */
    public Device(Device origDevice, String newLabel) {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Label creates identity of the device and is compared in the {@link #equals(Object) equals} method.
     *
     * @return label of the device
     */
    public String getLabel() {
        return label;
    }

    /**
     * Indicates whether the device will react to commands.
     *
     * @return if true, device will react to commands
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Configures whether the device will react to commands.
     *
     * @param enabled if true, device will react to commands
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Method responsible for creation new devices with the same values of attributes except the label
     *
     * @param newLabel label of the new device
     */
    public abstract Device createWithSameAttributes(String newLabel);

    /**
     * Method responsible for check whether two Devices has the same values of attributes
     *
     * @param device device to which this object will be compared
     * @throws IllegalArgumentException if parameter device is null
     */
    public abstract boolean hasSameAttributes(Device device) throws IllegalArgumentException;

    @Override
    public int compareTo(Device compareTo) {
        throw new NotImplementedException("Not implemented yet.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return getLabel().equals(device.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel());
    }
}
