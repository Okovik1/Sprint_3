package createcourier;

import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierCreationTwoTheSameTest extends CourierFeature {

    CourierClient courierClient;
    Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("TestLogin1", "TestPassword1", "TestName1");
    }

    @DisplayName("Create two couriers with the same credentials")
    @Description("Create two couriers with the same credentials, try to verify an exception in that case")
    @Test
    public void courierCreationTwoTheSameTest() {
        ValidatableResponse firstCourierCreation = courierClient.createCourier(courier);
        ValidatableResponse secondSameCourierCreation = firstCourierCreation;

        int statusCode = secondSameCourierCreation.extract().statusCode();
        String bodyResponse = secondSameCourierCreation.extract().path("message");

        assertThat("Something went wrong, status != 409", statusCode, equalTo(SC_CONFLICT));
        assertThat("Текст ошибки не соответствует \"Этот логин уже используется. Попробуйте другой.\"", bodyResponse, is("Этот логин уже используется"));
    }
}
