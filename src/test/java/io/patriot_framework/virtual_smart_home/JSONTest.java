package io.patriot_framework.virtual_smart_home;

import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.number.OrderingComparison.lessThan;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JSONTest {

    @Test
    public void getResponseTime() {
        given()
                .when().get("http://localhost:8080/house/device/fireplace")
                .then().statusCode(200).time(lessThan(1000L));
    }
}
