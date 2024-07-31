package io.patriot_framework.virtualsmarthomeplus.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * DTO for thermometer device
 */
@Getter
@Setter
@NoArgsConstructor
public class ThermometerDTO extends DeviceDTO {

    /**
     * specifies temperature measurement unit
     */
    @NotEmpty
    private String unit;
    /**
     * value of actual temperature
     */

    public Float temperature;

    public ThermometerDTO(String label) {
        super(label);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final ThermometerDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getUnit(), that.getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getClass().getName(), getUnit(), getTemperature());
    }
}
