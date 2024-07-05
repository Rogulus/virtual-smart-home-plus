package io.patriot_framework.virtualsmarthomeplus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriot_framework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriot_framework.virtualsmarthomeplus.DTOs.DoorDTO;
import io.patriot_framework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriot_framework.virtualsmarthomeplus.VirtualSmartHomePlusApplication;
import io.patriot_framework.virtualsmarthomeplus.house.House;
import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ContextConfiguration(classes = VirtualSmartHomePlusApplication.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DoorApiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);


    private final DTOMapper dtoMapper = new DTOMapper(new ModelMapper());
    @Autowired
    public House house = new House();
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @BeforeAll
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        Door door = new Door("door1");
        door.setEnabled(true);
        house.addDevice(door);
    }

    @Test
    public void shouldFetchDevice() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DoorDTO DoorDTO = (DoorDTO) dtoMapper.map(house.getDevice("door1"));
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/device/door/door1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        DoorDTO responseDTO = objectMapper.readValue(responseBody, DoorDTO.class);

        assertEquals(DoorDTO, responseDTO);


    }

    @Test
    public void shouldPostDoor() throws Exception {
        DoorDTO door = new DoorDTO();
        door.setLabel("door2");

        door.setEnabled(false);
        door.setStatus(Door.OPENED);

        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/api/v0.1/house/device/door/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(door)))
                .andExpect(status().isOk());
        DeviceDTO deviceDTO = dtoMapper.map(house.getDevice("door2"));
        assertEquals(door, deviceDTO);

        door.setLabel("newDoor2");

        door.setEnabled(false);
        door.setStatus(Door.OPENED);

        this.mockMvc.perform(post("/api/v0.1/house/device/door/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(door)))
                .andExpect(status().isOk());
        deviceDTO = dtoMapper.map(house.getDevice("newDoor2"));
        assertEquals(door, deviceDTO);

    }

    @Test
    public void shouldUpdateDoor() throws Exception {
        DoorDTO door = new DoorDTO();
        door.setLabel("door1");
        door.setStatus(Door.OPENED);

        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/door/door1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(door))
                )
                .andExpect(status().isOk());
        DoorDTO DoorDTO2 = (DoorDTO) dtoMapper.map(house.getDevice("door1"));
        Assertions.assertEquals(DoorDTO2.getStatus(), Door.OPENED);


        door = new DoorDTO();
        door.setLabel("d2");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/door/door1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(door))
                )
                .andExpect(status().is(400));
    }

    @Test
    public void shouldDeleteMapping() throws Exception {
        Door door = new Door("1");
        house.addDevice(door);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v0.1/house/device/door/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(204))
                .andReturn();
    }

    @Test
    public void deviceAlreadyExists() throws Exception {
        DoorDTO door = new DoorDTO();
        door.setLabel("door1");
        door.setEnabled(false);

        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/api/v0.1/house/device/door/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(door)))
                .andExpect(status().is(409));
    }

    @Test
    public void wrongApiVersion() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/house/device/door/thermometer1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void noSuchElementException() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/device/door/door8")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void wrongLabel() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        DoorDTO door = new DoorDTO();
        door.setLabel(null);
        door.setEnabled(false);

        this.mockMvc.perform(post("/api/v0.1/house/device/door/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(door)))
                .andExpect(status().is(400));
    }
}

