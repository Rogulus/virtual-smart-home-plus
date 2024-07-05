package io.patriot_framework.virtualsmarthomeplus.DTOs;

import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * DTO for Door device
 */
@Getter
@Setter
public class DoorDTO extends DeviceDTO {

    /**
     * In response to request this attribute has value
     * {@link Door#CLOSED} or
     * {@link Door#OPENED}.
     */
    private String status;

    public DoorDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final DoorDTO doorDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDeviceType(), doorDTO.getDeviceType())
                && Objects.equals(getStatus(), doorDTO.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDeviceType(), getStatus());
    }
}
