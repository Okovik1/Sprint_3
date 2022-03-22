package login;

import courier1.CourierClient;
import courier1.CourierCredentials;
import createcourier.CourierFeature;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginViaNonExistentUserTest extends CourierFeature {

    CourierClient courierClient;
    String bodyResponse;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @DisplayName("Login courier via non-existent user")
    @Description("Login courier via non-existent user, try to verify an exception in that case")
    @Test
    public void loginViaNonExistentUserTest(){

        ValidatableResponse response1 = courierClient.loginCourier(new CourierCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10)));
        int statusCode = response1.extract().statusCode();
        bodyResponse = response1.extract().path("message");

        assertThat("Something went wrong, status != 404 for non-existent courier", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Текст ошибки не соответствует \"Учетная запись не найдена\" for non-existent courier", bodyResponse, is("Учетная запись не найдена"));
    }

}
