package api;
import dto.ProductProposalRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.Config;
import io.qameta.allure.restassured.AllureRestAssured;

public class ProductProposalApiClient {
	
	public static Response create(ProductProposalRequest req, String testCaseName) {
		
		String baseUrl = Config.get("baseUrl");
		String token = helpers.AuthHelper.getAccessToken();
		
		var request=  RestAssured
				.given()
				.filter(new AllureRestAssured())
				.header("Content-Type", "application/vnd.allegro.public.v1+json")
				.header("Accept","application/vnd.allegro.public.v1+json")
				.header("Accept-Language", "en-US")
				.header("Authorization", "Bearer " + token)
				.body(req);
		if (testCaseName != null) {
			request.header("Mock-Trigger", testCaseName);
		}
		
		return request.when()
				.post(baseUrl + "/sale/product-proposals")
				.then()
				.extract()
				.response();
		
}
}