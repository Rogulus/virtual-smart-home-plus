package io.patriot_framework.virtualsmarthomeplus.DTOs;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;


/**
 * Base class for all devices
 */
@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "deviceType")
public class DeviceDTO {
    /**
     * Each device has unique label. Label represents id of the device.
     */
    @NotNull
    @NotEmpty(message = "label cannot be empty")
    private String label;
    /**
     * True if device is enabled
     */
    private Boolean enabled;

    public DeviceDTO(String label) {
        this.label = label;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final DeviceDTO deviceDTO)) return false;
        return Objects.equals(getLabel(), deviceDTO.getLabel())
                && Objects.equals(getEnabled(), deviceDTO.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), this.getClass().getName(), getEnabled());
    }
}
