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

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginInvalidSetOfCredentialsTest extends CourierFeature {

    CourierClient courierClient;
    Courier courier;
    String bodyResponse1;
    String bodyResponse2;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        courierClient.createCourier(courier);
    }

    @DisplayName("Login courier with invalid set of credentials")
    @Description("Login courier with invalid set of credentials")
    @Test
    public void loginInvalidSetOfCredentialsTest(){
        ValidatableResponse response1 = courierClient.loginCourier(new CourierCredentials(null, courier.getPassword()));
        int statusCode1 = response1.extract().statusCode();
        bodyResponse1 = response1.extract().path("message");

        assertThat("Something went wrong, status != 400 for courier without login", statusCode1, equalTo(SC_BAD_REQUEST));
        assertThat("Текст ошибки не соответствует \"Недостаточно данных для входа\" for courier without login ", bodyResponse1, is("Недостаточно данных для входа"));

        ValidatableResponse response2 = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), null));
        int statusCode2 = response2.extract().statusCode();
        bodyResponse2 = response2.extract().path("message");

        assertThat("Something went wrong, status != 400 for courier without password", statusCode2, equalTo(SC_BAD_REQUEST));
        assertThat("Текст ошибки не соответствует \"Недостаточно данных для входа\" for courier without password ", bodyResponse2, is("Недостаточно данных для входа"));
    }

    @After
    public void tearDown(){
        ValidatableResponse loggedInCourier= courierClient.loginCourier(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int id = loggedInCourier.extract().body().path("id");
        courierClient.deleteCourier(id);
    }
}
