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
import io.patriot_framework.virtual_smart_home.house.device.Fireplace;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HouseTest {

    House house = new House("house");
    Map<String, Device> devices = new ConcurrentHashMap<>();
    Fireplace fireplace = new Fireplace("fireplace");
    Fireplace fireplace2 = new Fireplace("fireplace2");

    @Test
    public void addDevice() {
        house.addDevice("fireplace", fireplace);
        assertThat(house.getDevice("fireplace"), equalTo(fireplace));
    }

    @Test
    public void removeDevice() {
        devices.put("fireplace", fireplace);
        house.setDevices(devices);
        house.removeDevice("fireplace");
        assertThat(house.getDevice("fireplace"), equalTo(null));
    }

    @Test
    public void setDevices() {
        devices.put("fireplace", fireplace);
        devices.put("fireplace2", fireplace2);
        house.setDevices(devices);
        assertThat(house.getDevice("fireplace"), equalTo(fireplace));
        assertThat(house.getDevice("fireplace2"), equalTo(fireplace2));
    }

    @Test
    public void getDevicesOfType() {
        devices.put("fireplace", fireplace);
        devices.put("fireplace2", fireplace2);
        house.setDevices(devices);
        Map<String, Device> expected = new HashMap<>(devices);
        assertThat(house.getDevicesOfType(Fireplace.class), equalTo(expected));
    }
}
