package createcourier;

import courier1.Courier;
import courier1.CourierClient;
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

public class CourierCreationInvalidSetOfCredentialsTest extends CourierFeature{

    CourierClient courierClient;
    Courier courier1;
    Courier courier2;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier1 = new Courier(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        courier2 = new Courier(RandomStringUtils.randomAlphabetic(10), null, RandomStringUtils.randomAlphabetic(10));
    }

    @DisplayName("Create courier with invalid set of credentials")
    @Description("Create new courier without login and then without password")
    @Test
    public void courierCreationInvalidSetOfCredentialsTest() {
        ValidatableResponse response1 = courierClient.createCourier(courier1);
        ValidatableResponse response2 = courierClient.createCourier(courier2);

        int statusCode1 = response1.extract().statusCode();
        String bodyResponse1 = response1.extract().path("message");
        int statusCode2 = response2.extract().statusCode();
        String bodyResponse2 = response2.extract().path("message");

        assertThat("Something went wrong, status != 400 for courier without login", statusCode1, equalTo(SC_BAD_REQUEST));
        assertThat("Текст ошибки не соответствует \"Недостаточно данных для создания учетной записи\" for courier without login ", bodyResponse1, is("Недостаточно данных для создания учетной записи"));

        assertThat("Something went wrong, status != 400 for courier without password", statusCode2, equalTo(SC_BAD_REQUEST));
        assertThat("Текст ошибки не соответствует \"Недостаточно данных для создания учетной записи\" for courier without password ", bodyResponse2, is("Недостаточно данных для создания учетной записи"));

    }
}
