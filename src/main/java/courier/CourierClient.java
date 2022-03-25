package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    public static final String COURIER_PATH = "/api/v1/courier";

    @Step("Create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Log in with credentials {credentials.login}")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then();
    }

    @Step("Delete courier by id")
    public ValidatableResponse deleteCourier(int courierId) {
        return given()
                .spec(getBaseSpec())
                .body(courierId)
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then();
    }

}
