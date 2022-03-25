import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import order.OrderFeature;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetAllOrdersTest extends OrderFeature {
    ArrayList bodyResponse;
    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @DisplayName("Get all orders")
    @Test
    public void getAllOrdersTest() {
        ValidatableResponse response = orderClient.getOrders();
        int statusCode = response.extract().statusCode();
        bodyResponse = response.extract().path("orders.id");

        assertThat("Something went wrong, status != 200", statusCode, equalTo(SC_OK));
        assertThat("There are any orders detected", bodyResponse, is(not(0)));
    }
}
