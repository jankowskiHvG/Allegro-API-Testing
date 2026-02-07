package tests.auth;

import org.junit.jupiter.api.Test;

import helpers.AuthHelper;
import tests.BaseTest;

public class GetAccessToken extends BaseTest{

	@Test
	public void ShouldReturnValidAccessToken() {
		System.out.println("Token retriving test...");
		
		String token = AuthHelper.getAccessToken();
		
		if (token == null || token.isEmpty()) {
			throw new AssertionError("Access token has not been retrieved");
		}
		
		System.out.println("Access token succesfully retrived");
		System.out.println(token);
		
		
	}
}
