package io.patriot_framework.virtual_smart_home.route;

import io.patriot_framework.virtual_smart_home.house.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HouseRoute extends BaseRoute {

    @Autowired House house;

    @Override
    public void configure() throws Exception {
        rest(getRoute())
                .get()
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .route()
                    .process(exchange -> exchange.getMessage().setBody(house))
                    .endRest();
    }

    protected String getRoute() {
        return "house/";
    }
}
