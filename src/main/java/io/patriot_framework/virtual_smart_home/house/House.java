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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * House class is responsible for management of {@code Device} used in the Virtual Smart Home
 */
public final class House {

    public static final Logger LOGGER = LogManager.getLogger();
    private String houseName;
    private Map<String, Device> devices = new ConcurrentHashMap<>();

    public House(String houseName) {
        this.houseName = houseName;
        LOGGER.debug(String.format("Created new house with name \"%s\"",this.houseName));
    }

    /**
     * Method for addition of devices to house object
     *
     * @param label string representing device name
     * @param device actual instance of device
     */
    public void addDevice(String label, Device device) {
        devices.put(label, device);
        LOGGER.debug(String.format("Device %s with label %s added to house %s", device, label, this.houseName));
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
        final Device origDevice = devices.put(label, device);
        if(origDevice == null){
            LOGGER.warn(String.format("Device with label:%s does not exist in house:%s."
                    + " Adding new device:%s.", label, houseName, device));
        } else{
            LOGGER.debug(String.format("At house:%s device with label:%s updated. "
                    + "(Device:%s replaced by:%s", houseName, label, origDevice, device));
        }
    }

    /**
     * Method used to remove device from house object
     *
     * @param label label of device to be removed
     */
    public void removeDevice(String label) {
        final Device origDevice = devices.remove(label);
        if(origDevice == null){
            LOGGER.warn(String.format("Removing non-existing device with label:%s from house:%s)", label, houseName));
        } else{
            LOGGER.debug(String.format("At house:%s device:%s with label:%s removed.", houseName, origDevice, label));
        }
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
        LOGGER.debug(String.format("House with name:%s renamed to:%s", this.houseName, houseName));
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
        LOGGER.debug(String.format("At house:%s devices:%s set instead of devices:%s",
                houseName, devices, this.devices));
        this.devices = devices;
    }
}
