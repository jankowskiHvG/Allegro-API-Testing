package tests.product;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import tests.BaseTest;
import helpers.AuthHelper;

@Tag("Smoke")
public class SearchProductTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Smoke: Verify if product search returns results")
    public void searchShouldReturnResults() {
        String token = AuthHelper.getAccessToken();

        given()
            .header("Authorization", "Bearer " + token)
            .header("Accept", "application/vnd.allegro.public.v1+json")
            .queryParam("phrase", "iphone")
        .when()
            .get("https://api.allegro.pl.allegrosandbox.pl/sale/products")
        .then()
            .statusCode(200)
            .body("products", not(empty()));
    }
}