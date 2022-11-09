package io.patriotframework.virtualsmarthomeplus.house.devices;

import org.apache.commons.lang3.NotImplementedException;

//From this class will be derived Actuators and Sensors. From them will be then derived final devices.
public abstract class Device implements Comparable<Device>{
    private String label;
    protected Boolean enabled = false;

    public Device(String label){
       this.label = label;
    }

    public Device(Device origDevice, String newLabel){
        throw new NotImplementedException("Not implemented yet.");
    }

    public String getLabel() {
        return label;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public int compareTo(Device compareTo) {
        throw new NotImplementedException("Not implemented yet.");
    }

    /**
     * Method responsible for creation new devices with the same values of attributes except label
     *
     * @param newLabel label of the new device
     */
    public abstract Device createWithSameState(String newLabel);

    /**
     * Method responsible for check whether two Devices has the same values of attributes
     *
     * @param device device to which this object will be compared
     * @throws IllegalArgumentException if parameter device is null
     */
    public abstract boolean hasSameAttributes(Device device) throws IllegalArgumentException;
}
