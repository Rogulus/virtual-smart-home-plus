package io.patriot_framework.virtual_smart_home.house.device;

/**
 * Main representation of the device used in {@code House}
 */
public abstract class Device {

    private final String label;

    public Device(String label) {
        this.label = label;
    }

    /**
     * Getter for device label
     *
     * @return string containing device label
     */
    public String getLabel() {
        return label;
    }
}
