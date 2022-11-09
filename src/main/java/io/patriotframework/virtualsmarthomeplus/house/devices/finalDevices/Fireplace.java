package io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.apache.commons.lang3.NotImplementedException;

public class Fireplace extends Device{
    public Fireplace(String label){
        super(label);
    }



    public void fireUp() {
        super.enabled = true;
    }

    public void extinguish() {
        throw new NotImplementedException("Not implemented yet.");
    }

    @JsonIgnore
    public String getStatus() {
        return enabled ? "enabled" : "disabled";
    }

    public Fireplace createWithSameState(String newLabel) {
        throw new NotImplementedException("Not implemented yet.");
    }

    public boolean hasSameAttributes(Device device) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet.");
    }
}
