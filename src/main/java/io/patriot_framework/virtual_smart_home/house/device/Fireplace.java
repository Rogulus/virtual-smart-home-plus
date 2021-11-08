package io.patriot_framework.virtual_smart_home.house.device;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of fireplace device
 */
public final class Fireplace extends Actuator {

    public Fireplace(@JsonProperty("label") String label) {
        super(label);
    }
}
