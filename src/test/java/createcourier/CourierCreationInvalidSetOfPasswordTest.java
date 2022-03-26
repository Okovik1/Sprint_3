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

public class CourierCreationInvalidSetOfPasswordTest extends CourierFeature {

    CourierClient courierClient;
    Courier courierWithoutPassword;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courierWithoutPassword = new Courier(RandomStringUtils.randomAlphabetic(10), null, RandomStringUtils.randomAlphabetic(10));
    }

    @DisplayName("Create courier without password")
    @Description("Create new courier  without password and try to verify exception message")
    @Test
    public void courierCreationInvalidSetOfCredentialsTest() {
        ValidatableResponse responseWithoutPassword = courierClient.createCourier(courierWithoutPassword);

        int statusCodeWithoutPassword = responseWithoutPassword.extract().statusCode();
        String bodyResponseWithoutPassword = responseWithoutPassword.extract().path("message");

        assertThat("Something went wrong, status != 400 for courier without password", statusCodeWithoutPassword, equalTo(SC_BAD_REQUEST));
        assertThat("Текст ошибки не соответствует \"Недостаточно данных для создания учетной записи\" for courier without password ", bodyResponseWithoutPassword, is("Недостаточно данных для создания учетной записи"));
    }
}