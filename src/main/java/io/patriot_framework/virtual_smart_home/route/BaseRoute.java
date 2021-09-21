package io.patriot_framework.virtual_smart_home.route;

import io.patriot_framework.virtual_smart_home.AppConfig;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BaseRoute extends RouteBuilder {

    protected AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .host("localhost").port(8080)
                .bindingMode(RestBindingMode.auto);
    }
}
