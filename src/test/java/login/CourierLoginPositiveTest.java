package login;

import courier1.Courier;
import courier1.CourierClient;
import courier1.CourierCredentials;
import createcourier.CourierFeature;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierLoginPositiveTest extends CourierFeature {

    CourierClient courierClient;
    Courier courier;
    int bodyResponse;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        courierClient.createCourier(courier);
    }

    @DisplayName("Login courier")
    @Description("Verify positive courier login")
    @Test
    public void courierLoginPositiveTest(){
        ValidatableResponse response = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int statusCode = response.extract().statusCode();
        bodyResponse = response.extract().path("id");

        assertThat("Courier cannot login", statusCode, equalTo(SC_OK));
        assertThat("Courier id is incorrect", bodyResponse, is(not(0)));
    }

    @After
    public void tearDown(){
        courierClient.deleteCourier(bodyResponse);
    }
}
