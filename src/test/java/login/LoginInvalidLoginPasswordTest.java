package login;

import courier1.Courier;
import courier1.CourierClient;
import courier1.CourierCredentials;
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

public class LoginInvalidLoginPasswordTest extends CourierFeature {

    CourierClient courierClient;
    Courier courier;
    String bodyResponse1;
    String bodyResponse2;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("TestLogin1", "TestPassword", "TestFirstName");
        courierClient.createCourier(courier);
    }

    @DisplayName("Login courier with invalid login and then with invalid password")
    @Description("Login courier with invalid login and then with invalid password, try to verify an exception in that case")
    @Test
    public void loginInvalidLoginPasswordTest() {
        ValidatableResponse responseIncorrectLogin = courierClient.loginCourier(new CourierCredentials("Test", courier.getPassword()));
        int statusCode1 = responseIncorrectLogin.extract().statusCode();
        bodyResponse1 = responseIncorrectLogin.extract().path("message");

        assertThat("Something went wrong, status != 400 for courier with incorrect login", statusCode1, equalTo(SC_NOT_FOUND));
        assertThat("Текст ошибки не соответствует \"Учетная запись не найдена\" for courier with incorrect login ", bodyResponse1, is("Учетная запись не найдена"));

        ValidatableResponse responseIncorrectPassword = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), "Test"));
        int statusCode2 = responseIncorrectPassword.extract().statusCode();
        bodyResponse2 = responseIncorrectPassword.extract().path("message");

        assertThat("Something went wrong, status != 404 for courier with incorrect password", statusCode2, equalTo(SC_NOT_FOUND));
        assertThat("Текст ошибки не соответствует \"Учетная запись не найдена\" for courier with incorrect password ", bodyResponse2, is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        ValidatableResponse loggedInCourier = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int id = loggedInCourier.extract().body().path("id");
        courierClient.deleteCourier(id);
    }
}
