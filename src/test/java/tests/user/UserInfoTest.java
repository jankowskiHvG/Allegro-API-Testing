package tests.user;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import helpers.AuthHelper;
import io.restassured.RestAssured;
import tests.BaseTest;
import utils.Config;

import static org.hamcrest.Matchers.notNullValue;
import java.io.File;
import io.restassured.module.jsv.JsonSchemaValidator;

@Tag("User")
@Tag("Regression")
public class UserInfoTest extends BaseTest {
	
	private static final String INVALID_TOKEN = "inv41id.t0k3N";
	String token = AuthHelper.getAccessToken();
	String baseUrl = Config.get("baseUrl");
	
	@DisplayName("GET /me returns 200 and response matches JSON schema")
	@Test
	public void shouldReturnUserInfo() {
		
		
		RestAssured
			.given()
			.header("Authorization", "Bearer " + token)
			.header("Accept", "application/vnd.allegro.public.v1+json")
			.when()
			.get(baseUrl + "/me")
			.then()
			.statusCode(200)
			.body("id", notNullValue())
			.body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/schemas/user_schema.json")))
			.extract().response();
			
		System.out.println("Response matches JSON schema according to contract");
	}
		
		@DisplayName("GET /me Should return 401 when no Authorization with token provided")
		@Test
		public void noTokenProvided() {
			RestAssured
				.given()
				.header("Accept", "application/vnd.allegro.public.v1+json")
				.log().all()
				.when()				
				.get(baseUrl + "/me")
				.then()
				.statusCode(401)
				.extract().response();
		}
		
		@DisplayName("GET /me Should return 401 when invalid token is provided")
		@Test
		public void invalidTokenProvided() {
			
			RestAssured
				.given()
				.header("Authorization", "Bearer " + INVALID_TOKEN)
				.header("Accept", "application/vnd.allegro.public.v1+json")
				.log().all()
				.when()
				.get(baseUrl + "/me")
				.then()
				.statusCode(401)
				.extract().response();
			
		}
		
	

}
