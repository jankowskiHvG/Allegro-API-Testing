package tests.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import helpers.AuthHelper;
import tests.BaseTest;

public class GetNewAccessToken  extends BaseTest {
	
	@Test
	//Verifying that Allegro Sandbox API correctly generates new access tokens
	public void shouldGetAccessToken() {
		String token = AuthHelper.getNewAccessToken();
		
		//token from AuthHelper is not null nor an empty string
		assertNotNull(token, "Access token should not be null");
		assertFalse(token.isEmpty(), "Access token should not be empty");	
		
		System.out.println("Token acquired");
	}

}
