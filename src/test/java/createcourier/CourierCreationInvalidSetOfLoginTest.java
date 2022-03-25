package createcourier;

import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierCreationInvalidSetOfLoginTest extends CourierFeature {

    CourierClient courierClient;
    Courier courierWithoutLogin;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courierWithoutLogin = new Courier(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
    }

    @DisplayName("Create courier without login")
    @Description("Create new courier without login and try to verify exception message")
    @Test
    public void courierCreationInvalidSetOfLoginTest() {
        ValidatableResponse responseWithoutLogin = courierClient.createCourier(courierWithoutLogin);

        int statusCodeWithoutLogin = responseWithoutLogin.extract().statusCode();
        String bodyResponseWithoutLogin = responseWithoutLogin.extract().path("message");

        assertThat("Something went wrong, status != 400 for courier without login", statusCodeWithoutLogin, equalTo(SC_BAD_REQUEST));
        assertThat("Текст ошибки не соответствует \"Недостаточно данных для создания учетной записи\" for courier without login ", bodyResponseWithoutLogin, is("Недостаточно данных для создания учетной записи"));
    }
}

