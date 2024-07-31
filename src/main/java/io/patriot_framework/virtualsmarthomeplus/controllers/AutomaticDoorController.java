package io.patriot_framework.virtualsmarthomeplus.controllers;

import io.patriot_framework.virtualsmarthomeplus.APIRoutes;
import io.patriot_framework.virtualsmarthomeplus.APIVersions;
import io.patriot_framework.virtualsmarthomeplus.DTOs.AutomaticDoorDTO;
import io.patriot_framework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriot_framework.virtualsmarthomeplus.house.House;
import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.AutomaticDoor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Handles the POST, GET, PUT and DELETE requests on Door route: {@link APIRoutes#DOOR_ROUTE}
 */
@RestController
public class AutomaticDoorController extends FinalDeviceHandling {
    private static final String AUTOMATIC_DOOR_ID_ROUTE = APIRoutes.AUTOMATIC_DOOR_ROUTE + "/{label}";

    AutomaticDoorController(House house) {
        super(house);
    }

    /**
     * Returns the door
     *
     * @param label      label specified in route
     * @param apiVersion api version specified in route
     * @return door if present in the house
     */
    @GetMapping(AUTOMATIC_DOOR_ID_ROUTE)
    public ResponseEntity<DeviceDTO> getDoor(@NotNull @PathVariable String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handleGet(label, AutomaticDoor.class), HttpStatus.OK);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Creates the door
     *
     * @param device     new door
     * @param apiVersion api version specified in route
     * @return door added to the house
     */
    @PostMapping(APIRoutes.AUTOMATIC_DOOR_ROUTE)
    public ResponseEntity<DeviceDTO> postDoor(
            @Valid @RequestBody AutomaticDoorDTO device,
            @NotNull @PathVariable String apiVersion
    ) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handlePost(device), HttpStatus.OK);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Updates or creates the door
     *
     * @param device     updated door
     * @param apiVersion api version specified in route
     * @param label label of the new device
     * @return door updated or added to the house
     */
    @PutMapping(AUTOMATIC_DOOR_ID_ROUTE)
    public ResponseEntity<DeviceDTO> putDoor(
            @Valid @RequestBody AutomaticDoorDTO device,
            @NotNull @PathVariable String label,
            @PathVariable String apiVersion
    ) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handlePut(label, device), HttpStatus.OK);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Deletes the door
     *
     * @param label      label of the door to be deleted
     * @param apiVersion api version specified in route
     * @return "OK" if door exists in the house and was deleted
     */
    @DeleteMapping(AUTOMATIC_DOOR_ID_ROUTE)
    public ResponseEntity<HttpStatus> deleteDoor(@NotNull @PathVariable String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            handleDelete(label, AutomaticDoor.class);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }
}
