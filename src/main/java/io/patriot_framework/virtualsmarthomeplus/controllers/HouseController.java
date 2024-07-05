package io.patriot_framework.virtualsmarthomeplus.controllers;

import io.patriot_framework.virtualsmarthomeplus.APIRoutes;
import io.patriot_framework.virtualsmarthomeplus.APIVersions;
import io.patriot_framework.virtualsmarthomeplus.DTOs.HouseDTO;
import io.patriot_framework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriot_framework.virtualsmarthomeplus.house.House;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


/**
 * Handles the GET requests on House route: {@link APIRoutes#HOUSE_ROUTE}
 */
@RestController
public class HouseController {

    private final House house;
    private final DTOMapper houseMapper;

    HouseController(House house, DTOMapper houseMapper) {
        this.house = house;
        this.houseMapper = houseMapper;
    }


    /**
     * Serving method for get requests on the house.
     *
     * @param apiVersion used api version
     * @return house with it`s devices
     * @throws ResponseStatusException 404 if invalid API version is demanded
     */
    @GetMapping(APIRoutes.HOUSE_ROUTE)
    public HouseDTO getHouse(@PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return houseMapper.map(house);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    @GetMapping(APIRoutes.HOUSE_ROUTE + APIRoutes.STATUS_ROUTE)
    public String getStatus() {
        return "OK";
    }
}
