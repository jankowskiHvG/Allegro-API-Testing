package helpers;


import java.util.Base64;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.Config;
import utils.TokenPocket;

public class AuthHelper {

	//Purpose: get a new accesss_token from API with use of authCode
	//then save new access token, refresh token and validity dates to JSON file
	public static String getNewAccessToken() {
		
		System.out.println("Requesting new access token using authCode...");
		
		String clientId = Config.get("clientId");
		String clientSecret = Config.get("clientSecret");
		String redirectUri = Config.get("redirectUri");
		String tokenUrl = Config.get("tokenUrl");
		String authCode = Config.get("authCode");
		
		//Build authorization header
		String credentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
		
		//Request sending
		Response response = RestAssured
				.given()
				.header("Authorization", "Basic " + credentials)
				.contentType("application/x-www-form-urlencoded")
				.formParam("grant_type", "authorization_code")
				.formParam("redirect_uri", redirectUri)
				.formParam("code",authCode)
				.when()
				.post(tokenUrl)
				.then()
				.extract().response();
		
		//Response errors handling
		if (response.statusCode() == 400) {
			throw new RuntimeException("To request new access token please refresh the authCode in .env file. \nCheck readme for guideline and link to generate code in web browser manually.");
		}
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error acquiring new access token: "+ response.statusLine());
		}
		
		//Reading specific data from JSON response
		String accessToken = response.jsonPath().getString("access_token");
		String refreshToken = response.jsonPath().getString("refresh_token");
		int expiresIn = response.jsonPath().getInt("expires_in");
		long accessTokenExpiry = System.currentTimeMillis() + expiresIn*1000L;
		long refreshTokenExpiry = System.currentTimeMillis() + 90 * 24 * 60 * 60 * 1000L; //+ 90days in ms
		
		
		
		//New TockenPocket object building and saving data to JSON file
		TokenPocket pocket = new TokenPocket();
		pocket.setAccessToken(accessToken);
		pocket.setRefreshToken(refreshToken);
		pocket.setAccessTokenExpiry(accessTokenExpiry);
		pocket.setRefreshTokenExpiry(refreshTokenExpiry);
		
		TokenPocket.save(pocket);
		
		System.out.println("New acceess token acquired and saved to JSON file");
		return accessToken;
		
			
	}

	

	public static String getAccessToken() {
		
		//Repository for HR - Mock mode
		
		if ("mock".equalsIgnoreCase(System.getProperty("env"))) {
			return "mock_access_token"; // Returns mock token without connecting to API  
		}
		
		//--------------------------------------------------------------------
		
		TokenPocket pocket = TokenPocket.load();

	
		// 1. Access Token  valid - return it
		
		if (pocket.isAccessTokenValid()) {
			//System.out.println("Returning valid access token from file.");
			return pocket.getAccessToken();
		}
	
		//3. If access token got outdated then use refresh token to generate new tokens.
		if (pocket.isRefreshTokenValid()) {
			System.out.println("Access token expired, but can be refreshed. Refreshing...");
			return refreshAccessToken(pocket);
		}
		//4. Refresh token outdated - generate new token with authCode with instructions message 
		System.out.println("Access token expired and can't be refreshed. Requesting new access token");
		return getNewAccessToken();
	}
	
	


	//refresh access token with refresh token - if refresh token is valid
	private static String refreshAccessToken(TokenPocket pocket) {
			System.out.println("Refreshing access token...");
			
			String clientId = Config.get("clientId");
			String clientSecret = Config.get("clientSecret");
			String tokenUrl = Config.get("tokenUrl");
			String refreshToken = pocket.getRefreshToken();
			
			String credentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
			
			Response response = RestAssured
					.given()
					.header("Authorization", "Basic " + credentials)
					.contentType("application/x-www-form-urlencoded")
					.formParam("grant_type", "refresh_token")
					.formParam("refresh_token", refreshToken)
					.when()
					.post(tokenUrl)
					.then()
					.extract().response();
			
			if (response.statusCode() != 200) {
				throw new RuntimeException ("Error refreshing token: " + response.statusCode() + response.statusLine());
			}
			
			//Reading specific data from JSON response
			String accessToken = response.jsonPath().getString("access_token");
			String newRefreshToken = response.jsonPath().getString("refresh_token");
			int expiresIn = response.jsonPath().getInt("expires_in");
			long accessTokenExpiry = System.currentTimeMillis() + expiresIn*1000L;
			long refreshTokenExpiry = System.currentTimeMillis() + 90* 24*60*60*1000L; //+ 90days in ms
			
			pocket.setAccessToken(accessToken);
			pocket.setRefreshToken(newRefreshToken);
			pocket.setAccessTokenExpiry(accessTokenExpiry);
			pocket.setRefreshTokenExpiry(refreshTokenExpiry);
			
			TokenPocket.save(pocket);
			
			System.out.println("Access token refreshed successfully and saved to JSON file.");
			return accessToken;
			
	}
}
