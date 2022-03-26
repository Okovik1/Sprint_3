package login;

import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import createcourier.CourierFeature;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginInvalidPasswordTest extends CourierFeature {

    CourierClient courierClient;
    Courier courier;
    String bodyResponseForIncorrectPassword;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("TestLogin1", "TestPassword", "TestFirstName");
        courierClient.createCourier(courier);
    }

    @DisplayName("Login courier with invalid password")
    @Description("Login courier with invalid password, try to verify an exception in that case")
    @Test
    public void loginInvalidPasswordTest() {

        ValidatableResponse responseIncorrectPassword = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), "Test"));
        int statusCodeForIncorrectPassword = responseIncorrectPassword.extract().statusCode();
        bodyResponseForIncorrectPassword = responseIncorrectPassword.extract().path("message");

        assertThat("Something went wrong, status != 404 for courier with incorrect password", statusCodeForIncorrectPassword, equalTo(SC_NOT_FOUND));
        assertThat("Текст ошибки не соответствует \"Учетная запись не найдена\" for courier with incorrect password ", bodyResponseForIncorrectPassword, is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        ValidatableResponse loggedInCourier = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int id = loggedInCourier.extract().body().path("id");
        courierClient.deleteCourier(id);
    }
}