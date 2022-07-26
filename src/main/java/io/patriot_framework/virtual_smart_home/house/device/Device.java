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

package io.patriot_framework.virtual_smart_home.house.device;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * Main representation of the device used in {@code House}
 */
public abstract class Device {

    public static final Logger LOGGER = LogManager.getLogger();
    @ApiModelProperty(accessMode = AccessMode.READ_WRITE)
    private final String label;

    public Device(String label) {
        this.label = label;
        LOGGER.debug(String.format("Created new device with label \"%s\"", label));
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
