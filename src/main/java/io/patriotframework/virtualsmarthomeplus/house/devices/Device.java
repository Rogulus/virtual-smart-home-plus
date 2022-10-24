package io.patriotframework.virtualsmarthomeplus.house.devices;

import org.apache.commons.lang3.NotImplementedException;

//From this class will be derived Actuators and Sensors. From them will be then derived final devices.
public class Device {

    public Device(Device origDevice, String newLabel){
        throw new NotImplementedException("Not implemented yet.");
    }

    public String getLabel() {
        throw new NotImplementedException("Not implemented yet.");
    }
}
