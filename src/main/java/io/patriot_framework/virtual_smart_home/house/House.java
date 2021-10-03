package io.patriot_framework.virtual_smart_home.house;

import io.patriot_framework.virtual_smart_home.house.device.Device;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class House {

    private String houseName;
    private Map<String, Device> devices = new ConcurrentHashMap<>();

    public House(String houseName) {
        this.houseName = houseName;
    }

    public void addDevice(String label, Device device) {
        devices.put(label, device);
    }

    public Device getDevice(String label) {
        return devices.get(label);
    }

    public void updateDevice(String label, Device device) {
        devices.put(label, device);
    }

    public void removeDevice(String label) {
        devices.remove(label);
    }

    public Map<String, Device> getDevicesOfType(Class<? extends Device> type) {
        return devices.entrySet().stream()
                .filter(entry -> type.isInstance(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) { // TODO: Remove?
        this.houseName = houseName;
    }

    public Map<String, Device> getDevices() {
        return Collections.unmodifiableMap(devices);
    }

    public void setDevices(Map<String, Device> devices) { // TODO: ?
        this.devices = devices;
    }
}
