package createcourier;

import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class CourierCreationPositiveTest extends CourierFeature {

    CourierClient courierClient;
    Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
    }

    @DisplayName("Create new courier")
    @Description("Create new courier with valid set of credentials")
    @Test
    public void courierCreationValidCredentialsPositiveTest() {
        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        Boolean bodyResponse = response.extract().path("ok");

        assertThat("Something went wrong, status != 201", statusCode, equalTo(SC_CREATED));
        assertThat("Boolean expression is not true", bodyResponse, is(true));
    }

    @After
    public void tearDown() {
        ValidatableResponse loggedInCourier = courierClient.loginCourier(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int id = loggedInCourier.extract().body().path("id");
        courierClient.deleteCourier(id);
    }

}
