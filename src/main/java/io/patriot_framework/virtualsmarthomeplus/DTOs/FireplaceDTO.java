package io.patriot_framework.virtualsmarthomeplus.DTOs;

import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * DTO for Fireplace device
 */
@Getter
@Setter
@NoArgsConstructor
public class FireplaceDTO extends DeviceDTO {

    /**
     * in response to request this attribute has value
     * {@link Fireplace#ON_FIRE} or
     * {@link Fireplace#EXTINGUISHED}.
     */
    private String status;


    public FireplaceDTO(String label) {
        super(label);
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final FireplaceDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getClass().getName(), getStatus());
    }

    public void setOnFire () {
        status = "on_fire";
    }
    public void extinguish() {
        status = "extinguished";
    }
}
