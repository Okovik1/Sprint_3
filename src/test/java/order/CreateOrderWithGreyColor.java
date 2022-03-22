package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateOrderWithGreyColor extends OrderFeature{

    int bodyResponse;
    public String ORDER_PATH = "/api/v1/orders";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }

    @DisplayName("Create order with grey color")
    @Test
    public void createOrderWithGreyColor() {
        File json = new File("src/main/resources/orderWithGreyColor.json");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(ORDER_PATH)
                .then();

        int statusCode = response.extract().statusCode();
        bodyResponse = response.extract().path("track");

        assertThat("Something went wrong, status != 201", statusCode, equalTo(SC_CREATED));
        assertThat("Order cannot created", bodyResponse, is(not(0)));
    }

    @After
    public void tearDown() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse deleteOrder = orderClient.cancelOrder(bodyResponse);
    }
}
