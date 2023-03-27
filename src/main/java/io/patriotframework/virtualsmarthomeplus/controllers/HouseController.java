package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.APIVersions;
import io.patriotframework.virtualsmarthomeplus.house.House;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    HouseController(House house) {
        this.house = house;
    }


    /**
     * Serving method for get requests on the house.
     * @param apiVersion used api version
     * @throws ResponseStatusException 404 if invalid API version is demanded
     * @return house with it`s devices
     */
    @GetMapping(APIRoutes.HOUSE_ROUTE)
    public House getHouse(@PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return house;
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }
}
