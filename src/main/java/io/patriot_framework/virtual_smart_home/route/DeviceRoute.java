package io.patriot_framework.virtual_smart_home.route;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Device endpoint which allows HTTP GET request only and returns information
 * about all devices.
 */
@Component
public class DeviceRoute extends HouseRoute {

    @Override
    public void configure() throws Exception {
        rest(getRoute())
                .get()
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .route()
                    .process(exchange -> exchange.getMessage().setBody(house.getDevices()))
                    .endRest();
    }

    @Override
    protected String getRoute() {
        return super.getRoute() + "device/";
    }
}
