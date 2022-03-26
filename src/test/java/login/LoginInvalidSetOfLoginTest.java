package login;

import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
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

public class LoginInvalidSetOfLoginTest extends CourierFeature {

    CourierClient courierClient;
    Courier courier;
    String bodyResponseWithoutLogin;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        courierClient.createCourier(courier);
    }

    @DisplayName("Login courier with invalid set of login")
    @Description("Login courier with invalid set of login and try to verify exception message")
    @Test
    public void loginInvalidSetOfLoginTest() {
        ValidatableResponse responseWithoutLogin = courierClient.loginCourier(new CourierCredentials(null, courier.getPassword()));
        int statusCodeWithoutLogin = responseWithoutLogin.extract().statusCode();
        bodyResponseWithoutLogin = responseWithoutLogin.extract().path("message");

        assertThat("Something went wrong, status != 400 for courier without login", statusCodeWithoutLogin, equalTo(SC_BAD_REQUEST));
        assertThat("Текст ошибки не соответствует \"Недостаточно данных для входа\" for courier without login ", bodyResponseWithoutLogin, is("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        ValidatableResponse loggedInCourier = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int id = loggedInCourier.extract().body().path("id");
        courierClient.deleteCourier(id);
    }
}
