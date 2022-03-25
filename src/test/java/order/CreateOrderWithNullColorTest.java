package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class CreateOrderWithNullColorTest extends OrderFeature {

    int bodyResponse;
    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @DisplayName("Create order with null color")
    @Test
    public void createOrderWithNullColorTest() {
        Order order = Order.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .lastName(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphabetic(10))
                .metroStation(2)
                .phone(RandomStringUtils.randomAlphabetic(10))
                .rentTime(4)
                .deliveryDate(RandomStringUtils.randomAlphabetic(10))
                .comment(RandomStringUtils.randomAlphabetic(10))
                .color(null).build();

        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response.extract().statusCode();
        bodyResponse = response.extract().path("track");

        assertThat("Something went wrong, status != 201", statusCode, equalTo(SC_CREATED));
        assertThat("Order cannot created", bodyResponse, is(not(0)));
    }

    @After
    public void tearDown() {
        orderClient.cancelOrder(bodyResponse);
    }
}
