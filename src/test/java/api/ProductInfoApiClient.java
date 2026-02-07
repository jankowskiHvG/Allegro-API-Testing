package api;


import io.restassured.RestAssured;
import utils.Config;
import io.restassured.response.Response;

public class ProductInfoApiClient {
	
	public static Response getProduct(String productId) {
	
		String token = helpers.AuthHelper.getAccessToken();
		String baseUrl = Config.get("baseUrl");
		
		return RestAssured
				.given()
				.header("Accept","application/vnd.allegro.public.v1+json")
				.header("Accept-Language", "en-US")
				.header("Authorization", "Bearer " + token)
				.when()
				.get(baseUrl + "/sale/products/" + productId)
				.then()
				.extract()
				.response();
	}
}