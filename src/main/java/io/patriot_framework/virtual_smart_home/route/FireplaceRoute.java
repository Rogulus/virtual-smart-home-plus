package io.patriot_framework.virtual_smart_home.route;

import io.patriot_framework.virtual_smart_home.house.device.Fireplace;
import org.springframework.stereotype.Component;

@Component
public class FireplaceRoute extends AbstractDeviceRoute {

    public FireplaceRoute() {
        super.setupRoute("fireplace", Fireplace.class);
    }
}
