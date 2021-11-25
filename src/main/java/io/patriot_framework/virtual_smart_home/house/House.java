/*
 * Copyright 2021 Patriot project
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package io.patriot_framework.virtual_smart_home.house;

import io.patriot_framework.virtual_smart_home.house.device.Device;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * House class is responsible for management of {@code Device} used in the Virtual Smart Home
 */
public final class House {

    private String houseName;
    private Map<String, Device> devices = new ConcurrentHashMap<>();

    public House(String houseName) {
        this.houseName = houseName;
    }

    /**
     * Method for addition of devices to house object
     *
     * @param label string representing device name
     * @param device actual instance of device
     */
    public void addDevice(String label, Device device) {
        devices.put(label, device);
    }

    /**
     * Method used to retrieve single device with certain label
     *
     * @param label label of desired device
     * @return instance of device with given label
     */
    public Device getDevice(String label) {
        return devices.get(label);
    }

    /**
     * Method used to replace device with certain label
     *
     * @param label label of device to be replaced
     * @param device instance of new device with given label
     */
    public void updateDevice(String label, Device device) {
        devices.put(label, device);
    }

    /**
     * Method used to remove device from house object
     *
     * @param label label of device to be removed
     */
    public void removeDevice(String label) {
        devices.remove(label);
    }

    /**
     * Method used to retrieve devices of certain type stored in house
     *
     * @param type type of desired devices
     * @return Map of devices of requested type with their labels
     */
    public Map<String, Device> getDevicesOfType(Class<? extends Device> type) {
        return devices.entrySet().stream()
                .filter(entry -> type.isInstance(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Getter for house name
     *
     * @return string containing house name
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * Setter for house name
     *
     * @param houseName string with house name
     */
    public void setHouseName(String houseName) { // TODO: Remove?
        this.houseName = houseName;
    }

    /**
     * Method used to retrieve all devices stored in house
     *
     * @return Map of devices and their labels
     */
    public Map<String, Device> getDevices() {
        return Collections.unmodifiableMap(devices);
    }

    /**
     * Method used to setup or reset all the devices in house
     *
     * @param devices map of devices and their labels
     */
    public void setDevices(Map<String, Device> devices) { // TODO: ?
        this.devices = devices;
    }
}
