package io.patriot_framework.virtualsmarthomeplus.DTOS;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriot_framework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriot_framework.virtualsmarthomeplus.DTOs.ThermometerDTO;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {

    @Test
    void serializeThermometer() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ThermometerDTO thermometerDTO = new ThermometerDTO();
        thermometerDTO.setUnit("C");
        thermometerDTO.setLabel("t1");
//        thermometerDTO.setDeviceType("io.patriot_framework.virtualsmarthomeplus.DTOs.ThermometerDTO"); todo
        String jsonThermometer = objectMapper.writeValueAsString(thermometerDTO);
        DeviceDTO deserializedThermometerDTO = objectMapper.readValue(jsonThermometer, DeviceDTO.class);
        assertEquals(thermometerDTO, deserializedThermometerDTO);
    }
}
