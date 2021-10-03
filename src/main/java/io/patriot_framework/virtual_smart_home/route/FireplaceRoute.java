package io.patriot_framework.virtual_smart_home.route;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.patriot_framework.virtual_smart_home.house.device.Device;
import io.patriot_framework.virtual_smart_home.house.device.Fireplace;
import org.apache.camel.Exchange;
import org.apache.catalina.connector.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class FireplaceRoute extends DeviceRoute {

    @Override
    public void configure() throws Exception {
        rest(getRoute())
                .get("{label}")
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:readFireplace")

                .get()
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:readFireplaces")

                .post()
                    .type(Fireplace.class)
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:createFireplace")

                .put()
                    .type(Fireplace.class)
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:updateFireplace")

                .patch()
                    .type(Fireplace.class)
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:updateFireplace")

                .delete()
                    .to("direct:deleteFireplace");

        onException(UnrecognizedPropertyException.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST))
                .handled(true);

        onException(JsonParseException.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST))
                .handled(true);

        handleGet();
        handlePost();
        handlePut();
        handleDelete();
    }

    private void handleGet() {
        from("direct:readFireplace")
                .routeId("read-fireplace-route")
                .process(exchange -> {
                    String label = exchange.getMessage().getHeader("label").toString();
                    Device fireplace = house.getDevicesOfType(Fireplace.class).get(label);
                    exchange.getMessage().setBody(fireplace);

                    if (fireplace == null) {
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND); // 404
                    }
                })
                .endRest();

        from("direct:readFireplaces")
                .routeId("read-fireplaces-route")
                .process(exchange -> exchange.getMessage().setBody(house.getDevicesOfType(Fireplace.class).values()))
                .endRest();
    }

    private void handlePost() {
        from("direct:createFireplace")
                .routeId("create-fireplace-route")
                .choice()
                    .when(body().isNotNull())
                        .process(exchange -> {
                            Fireplace fireplace = exchange.getMessage().getBody(Fireplace.class);
                            Device currentFireplace = house.getDevicesOfType(Fireplace.class).get(fireplace.getLabel());

                            if (currentFireplace != null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_CONFLICT);
                                // 409
                                return;
                            }
                            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_CREATED); // 201
                            house.addDevice(fireplace.getLabel(), fireplace);
                            exchange.getMessage().setHeader("label", fireplace.getLabel());
                        })
                        .setBody(body()) // Respond with request body.
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 409"))
                                .log("Created fireplace \"${header.label}\"")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    private void handlePut() {
        from("direct:updateFireplace")
                .routeId("update-fireplace-route")
                .choice()
                    .when(body().isNotNull())
                        .process(exchange -> {
                            Fireplace fireplace = exchange.getMessage().getBody(Fireplace.class);
                            Device currentFireplace = house.getDevicesOfType(Fireplace.class).get(fireplace.getLabel());

                            if (currentFireplace == null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND);
                                // 404
                                return;
                            }
                            house.updateDevice(fireplace.getLabel(), fireplace);
                            exchange.getMessage().setHeader("label", fireplace.getLabel());
                        })
                        .setBody(body()) // Respond with request body.
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 404"))
                                .log("Updated fireplace \"${header.label}\"")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    private void handleDelete() {
        from("direct:deleteFireplace")
                .routeId("delete-fireplace-route")
                .choice()
                    .when(header("label").isNotNull())
                        .process(exchange -> {
                            String label = exchange.getMessage().getHeader("label").toString();
                            Device fireplace = house.getDevicesOfType(Fireplace.class).get(label);

                            if (fireplace == null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND);
                                // 404
                                return;
                            }
                            house.removeDevice(exchange.getMessage().getHeader("label").toString());
                        })
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 404"))
                                .log("Removed fireplace \"${header.label}\"")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    @Override
    protected String getRoute() {
        return super.getRoute() + "fireplace/";
    }
}
