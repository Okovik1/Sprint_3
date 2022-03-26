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

public class CreateOrderWithDifferentColors extends OrderFeature {

    int bodyResponse;
    public String ORDER_PATH = "/api/v1/orders";


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @DisplayName("Create order with different colors")
    @Test
    public void createOrderWithDifferentColors() {
        File orderWithTwoColors = new File("src/main/resources/orderWithTwoColors.json");
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .and()
                .body(orderWithTwoColors)
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
        orderClient.cancelOrder(bodyResponse);
    }
}
