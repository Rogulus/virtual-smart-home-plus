package io.patriot_framework.virtual_smart_home.house.device;

/**
 * Simple Device type with ability to manipulate state (enabled/disabled) of {@code Device}
 */
public class Actuator extends Device {

    private boolean enabled = false;

    public Actuator(String label) {
        super(label);
    }

    /**
     * Returns state of actuator
     *
     * @return true if actuator is running, false if actuator is disabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets state of the actuator
     *
     * @param enabled boolean parameter determining if actuator is running or if it's disabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
