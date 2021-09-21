package io.patriot_framework.virtual_smart_home.route;

import io.patriot_framework.virtual_smart_home.house.House;
import org.springframework.stereotype.Component;

@Component
public class HouseRoute extends BaseRoute {

    protected House house = context.getBean(House.class);

    @Override
    public void configure() throws Exception {
        rest(getRoute())
                .get()
                    .produces("application/json")
                    .route()
                    .process(exchange -> exchange.getMessage().setBody(house))
                    .endRest();
    }

    protected String getRoute() {
        return "house/";
    }
}
