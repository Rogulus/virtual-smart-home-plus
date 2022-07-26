/*
 * Copyright 2021 Patriot project
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package io.patriot_framework.virtual_smart_home.route;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.patriot_framework.virtual_smart_home.house.device.Device;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestParamType;
import static org.apache.camel.model.rest.RestParamType.path;
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
                    .description(String.format("Returns basic info about %s with given label", endpoint))
                    .outType(deviceType)
                    .param().name("label").type(path).description(String.format("The label of the %s to get", endpoint))
                        .endParam()
                    .responseMessage().code(200).message(String.format("Basic info about %s returned", endpoint))
                        .endResponseMessage()
                    .responseMessage().code(404).message(
                        String.format("%s with given label isn't present in the house",
                        endpoint.substring(0,1).toUpperCase() + endpoint.substring(1))).endResponseMessage()
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:read" + endpoint)

                .get()
                    .description(String.format("Returns array of all %ss",endpoint))
                    .outType(deviceType)    //TODO for java version >= 12 .outType(deviceType.arrayType())
                    .responseMessage().code(200).message(String.format("Basic info about all %ss returned", endpoint))
                        .endResponseMessage()
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:read" + endpoint + "s")

                .post()
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .description(String.format("Add new %s to the house", endpoint))
                    .type(deviceType)
                    .param().name("body").description("Object, that needs to be added to the house."
                            + " Label must be unique identifier among the devices in the house.").endParam()
                    .outType(deviceType)
                    .responseMessage().code(201).message(String.format("%s added", endpoint))
                        .endResponseMessage()
                    .responseMessage().code(400).message("Invalid body").endResponseMessage()
                    .responseMessage().code(409).message(
                        String.format("Device %s with given label already exists ", endpoint))
                        .endResponseMessage()
                    .to("direct:create" + endpoint)

                .put()
                    .description(String.format("Update an existing %s", endpoint))
                    .type(deviceType)
                    .param().name("body").description("Object, that needs to be updated in the house."
                            + " Label is identifier of the updated device.").endParam()
                    .outType(deviceType)
                    .responseMessage().code(200).message(String.format("%s updated",
                        endpoint.substring(0,1).toUpperCase()+endpoint.substring(1))).endResponseMessage()
                    .responseMessage().code(400).message("Invalid body").endResponseMessage()
                    .responseMessage().code(404).message(String.format("%s with given label isn't present"
                        + " in the house", endpoint.substring(0,1).toUpperCase() + endpoint.substring(1)))
                        .endResponseMessage()
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:update" + endpoint)

                .patch()
                    .description(String.format("Update an existing %s (same as put)",endpoint))
                    .type(deviceType)
                    .param().name("body").description("Object, that needs to be updated in the house."
                            + " Label is identifier of the updated device.").endParam()
                    .outType(deviceType)
                    .responseMessage().code(200).message(String.format("%s updated",
                        endpoint.substring(0,1).toUpperCase()+endpoint.substring(1))).endResponseMessage()
                    .responseMessage().code(400).message("Invalid body").endResponseMessage()
                    .responseMessage().code(404).message(String.format("%s with given label isn't present in the house",
                        endpoint.substring(0,1).toUpperCase()+endpoint.substring(1))).endResponseMessage()
                    .consumes(MediaType.APPLICATION_JSON_VALUE)
                    .to("direct:update" + endpoint)

                .delete()
                    .description(String.format("Deletes a %s with given label", endpoint))
                    .param().name("label").type(RestParamType.header).required(Boolean.TRUE)
                        .description("Label of the device").endParam()
                    .responseMessage().code(200).message(String.format("%s deleted",
                        endpoint.substring(0,1).toUpperCase() + endpoint.substring(1))).endResponseMessage()
                    .responseMessage().code(400).message("Header label is missing").endResponseMessage()
                    .responseMessage().code(404).message(String.format("%s with given label isn't present in the house",
                            endpoint.substring(0,1).toUpperCase() + endpoint.substring(1))).endResponseMessage()
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
                    final String label = exchange.getMessage().getHeader("label").toString();
                    final Device retrievedDevice = house.getDevicesOfType(deviceType).get(label);
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
                            final Device deviceToAdd = exchange.getMessage().getBody(deviceType);
                            final Device checkForConflict = house
                                    .getDevicesOfType(deviceType)
                                    .get(deviceToAdd.getLabel());

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
                            final Device deviceToUpdate = exchange.getMessage().getBody(deviceType);
                            final Device checkIfExists = house
                                    .getDevicesOfType(deviceType)
                                    .get(deviceToUpdate.getLabel());

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
                            final String label = exchange.getMessage().getHeader("label").toString();
                            final Device deviceToDelete = house.getDevicesOfType(deviceType).get(label);

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
