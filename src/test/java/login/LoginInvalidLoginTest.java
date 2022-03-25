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

public class LoginInvalidLoginTest extends CourierFeature {

    CourierClient courierClient;
    Courier courier;
    String bodyResponseForIncorrectLogin;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("TestLogin1", "TestPassword", "TestFirstName");
        courierClient.createCourier(courier);
    }

    @DisplayName("Login courier with invalid login")
    @Description("Login courier with invalid login, try to verify an exception in that case")
    @Test
    public void loginInvalidLoginTest() {
        ValidatableResponse responseIncorrectLogin = courierClient.loginCourier(new CourierCredentials("Test", courier.getPassword()));
        int statusCodeForIncorrectLogin = responseIncorrectLogin.extract().statusCode();
        bodyResponseForIncorrectLogin = responseIncorrectLogin.extract().path("message");

        assertThat("Something went wrong, status != 400 for courier with incorrect login", statusCodeForIncorrectLogin, equalTo(SC_NOT_FOUND));
        assertThat("Текст ошибки не соответствует \"Учетная запись не найдена\" for courier with incorrect login ", bodyResponseForIncorrectLogin, is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        ValidatableResponse loggedInCourier = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int id = loggedInCourier.extract().body().path("id");
        courierClient.deleteCourier(id);
    }
}
