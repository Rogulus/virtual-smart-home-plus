package io.patriot_framework.virtualsmarthomeplus.utils;

import io.patriot_framework.generator.utils.SerializationException;

import io.patriot_framework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriot_framework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

public class VirtualSmartHomePlusHttpClient {
    @Getter
    @Setter
    private String ip;

    @Getter
    @Setter
    private int port;

    public VirtualSmartHomePlusHttpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void putDevice(String deviceType, DeviceDTO device) { // todo melo by to brat Device DTO
//        device.setDeviceType(device.getClass().getName());
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String uri = "http://" + ip + ":" + port + "/api/v0.1/house/device/" + deviceType + "/" + device.getLabel();
            // Create the PUT request
            HttpPut httpPut = new HttpPut(uri);
            // Convert the device object to JSON string
            String json = mapper.writeValueAsString(device);
            // Set the JSON string as the entity of the PUT request
            httpPut.setEntity(new StringEntity(json, "UTF-8"));
            httpPut.setHeader("Content-Type", "application/json");

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                // Handle the response if needed
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Response status: " + statusCode);

                // Optionally handle the response body, headers, etc.
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("Response body: " + responseBody);
                Thermometer t;

            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    public DeviceDTO getDevice(String deviceType, String label) {
        String responseBody = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String uri = "http://" + ip + ":" + port + "/api/v0.1/house/device/" + deviceType + "/" + label;
            // Create the PUT request
            HttpGet httpget = new HttpGet(uri);
            // Set the JSON string as the entity of the PUT request
            httpget.setHeader("Accept", "application/json");

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(httpget)) {
                // Handle the response if needed
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Response status: " + statusCode);

                // Optionally handle the response body, headers, etc.
                responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("Response body: " + responseBody);
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(responseBody, DeviceDTO.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
