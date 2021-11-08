package io.patriot_framework.virtual_smart_home.route;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.patriot_framework.virtual_smart_home.house.device.Device;
import org.apache.camel.Exchange;
import org.apache.catalina.connector.Response;
import org.springframework.http.MediaType;

/**
 * REST API base class for device routes.
 */
public abstract class AbstractDeviceRoute extends HouseRoute {

    private String endpoint;
    private Class<? extends Device> deviceType;

    public void setupRoute(String endpoint, Class<? extends Device> deviceType) {
        this.endpoint = endpoint;
        this.deviceType = deviceType;
    }

    /**
     * REST API configuration.
     * Any issue related to JSON parsing returns status code 400 (Bad Request).
     */
    @Override
    public void configure() {
        rest(getRoute())
                .get("{label}")
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:read" + endpoint)

                .get()
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:read" + endpoint + "s")

                .post()
                    .type(deviceType)
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:create" + endpoint)

                .put()
                    .type(deviceType)
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:update" + endpoint)

                .patch()
                    .type(deviceType)
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:update" + endpoint)

                .delete()
                    .to("direct:delete" + endpoint);

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

    /**
     * HTTP GET request handling.
     *
     * Successful request returns JSON for a specific device or JSON array
     * of all devices of certain type. Otherwise, a 404 status code (Not Found)
     * is returned.
     */
    private void handleGet() {
        from("direct:read" + endpoint)
                .routeId("read-" + endpoint + "-route")
                .process(exchange -> {
                    String label = exchange.getMessage().getHeader("label").toString();
                    Device retrievedDevice = house.getDevicesOfType(deviceType).get(label);
                    exchange.getMessage().setBody(retrievedDevice);

                    if (retrievedDevice == null) {
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND); // 404
                    }
                })
                .endRest();

        from("direct:read" + endpoint + "s")
                .routeId("read-" + endpoint + "s-route")
                .process(exchange -> exchange.getMessage().setBody(house.getDevicesOfType(deviceType).values()))
                .endRest();
    }

    /**
     * HTTP POST request handling.
     *
     * Successful request creates an object of a certain type and returns
     * status code 201 (Created). If there is a conflict, status code 409
     * (Conflict) is returned. Otherwise, the request is considered invalid
     * and status code 400 (Bad Request) is returned.
     */
    private void handlePost() {
        from("direct:create" + endpoint)
                .routeId("create-" + endpoint + "-route")
                .choice()
                    .when(body().isNotNull())
                        .process(exchange -> {
                            Device deviceToAdd = exchange.getMessage().getBody(deviceType);
                            Device checkForConflict = house.getDevicesOfType(deviceType).get(deviceToAdd.getLabel());

                            if (checkForConflict != null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_CONFLICT);
                                // 409
                                return;
                            }
                            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_CREATED); // 201
                            house.addDevice(deviceToAdd.getLabel(), deviceToAdd);
                            exchange.getMessage().setHeader("label", deviceToAdd.getLabel());
                        })
                        .setBody(body()) // Respond with request body.
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 409"))
                                .log("Created route path for \"${header.label}\" device")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    /**
     * HTTP PUT/PATCH request handling.
     *
     * Successful request updates an object of a certain type. If an object is
     * not found, status code 404 (Not Found) is returned. Otherwise,
     * the request is considered invalid and status code 400 (Bad Request) is
     * returned.
     */
    private void handlePut() {
        from("direct:update" + endpoint)
                .routeId("update-" + endpoint + "-route")
                .choice()
                    .when(body().isNotNull())
                        .process(exchange -> {
                            Device deviceToUpdate = exchange.getMessage().getBody(deviceType);
                            Device checkIfExists = house.getDevicesOfType(deviceType).get(deviceToUpdate.getLabel());

                            if (checkIfExists == null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND);
                                // 404
                                return;
                            }
                            house.updateDevice(deviceToUpdate.getLabel(), deviceToUpdate);
                            exchange.getMessage().setHeader("label", deviceToUpdate.getLabel());
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

    /**
     * HTTP DELETE request handling.
     *
     * Successful request deletes an object of a certain type. If an object is
     * not found, status code 404 (Not Found) is returned. Otherwise,
     * the request is considered invalid and status code 400 (Bad Request) is
     * returned.
     */
    private void handleDelete() {
        from("direct:delete" + endpoint)
                .routeId("delete-" + endpoint + "-route")
                .choice()
                    .when(header("label").isNotNull())
                        .process(exchange -> {
                            String label = exchange.getMessage().getHeader("label").toString();
                            Device deviceToDelete = house.getDevicesOfType(deviceType).get(label);

                            if (deviceToDelete == null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND);
                                // 404
                                return;
                            }
                            house.removeDevice(exchange.getMessage().getHeader("label").toString());
                        })
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 404"))
                                .log("Removed device \"${header.label}\"")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    @Override
    protected String getRoute() {
        return super.getRoute() + "device/" + endpoint;
    }
}
