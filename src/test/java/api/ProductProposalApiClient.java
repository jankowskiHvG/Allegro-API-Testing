package api;
import dto.ProductProposalRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.restassured.AllureRestAssured;

public class ProductProposalApiClient {
	
	public static Response create(ProductProposalRequest req) {
	
		String token = helpers.AuthHelper.getAccessToken();
		
		return RestAssured
				.given()
				.filter(new AllureRestAssured())
				.header("Content-Type", "application/vnd.allegro.public.v1+json")
				.header("Accept","application/vnd.allegro.public.v1+json")
				.header("Accept-Language", "en-US")
				.header("Authorization", "Bearer " + token)
				.body(req)
				.when()
				.post("https://api.allegro.pl.allegrosandbox.pl/sale/product-proposals")
				.then()
				.extract()
				.response();
		
}
}