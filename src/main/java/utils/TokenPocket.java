package utils;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class TokenPocket {
	
	//storage of token, refresh token and their validity terms
	//json file tokenPocket.json read i in between running tests
	//objectmapper of Jackson to parse
	
	private String accessToken;
	private String refreshToken;
	private long accessTokenExpiry;
	private long refreshTokenExpiry;
	
	private static final String TOKEN_FILE_PATH = "tokens/tokenPocket.json";
	private static final ObjectMapper mapper = new ObjectMapper();
	
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public long getAccessTokenExpiry() {
		return accessTokenExpiry;
	}
	public void setAccessTokenExpiry(long accessTokenExpiry) {
		this.accessTokenExpiry = accessTokenExpiry;
	}
	public long getRefreshTokenExpiry() {
		return refreshTokenExpiry;
	}
	public void setRefreshTokenExpiry(long refreshTokenExpiry) {
		this.refreshTokenExpiry = refreshTokenExpiry;
	}
	
	
	//Saving tokens and token validity date to JSON file
	public static void save(TokenPocket pocket) {
		try
		{
			mapper.writeValue(new File(TOKEN_FILE_PATH), pocket);
			System.out.println("Access token saved to file");
		} catch (IOException e) {
			throw new RuntimeException ("Error saving token in tokenPocket.json", e);
		}
		
		
		
	}
	
	//checking if tokenPocket exists and if yes - loading it
	public static TokenPocket load() {
		try {
			File file = new File(TOKEN_FILE_PATH);
			if (!file.exists()) {
				System.out.println("tokenPocket.json file not found");
				return new TokenPocket();
			}
			
			TokenPocket pocket = mapper.readValue(file, TokenPocket.class);
			//System.out.println("Token data loaded from tokenPocket.json file");
			return pocket;
		} catch (IOException e) {
			throw new RuntimeException("Error reading tokenPocket.json", e);
		}
	}
	
	@JsonIgnore
	public boolean isAccessTokenValid() {
		return System.currentTimeMillis() < accessTokenExpiry;
	}
	
	@JsonIgnore
	public boolean isRefreshTokenValid() {
		return System.currentTimeMillis() < refreshTokenExpiry;
	}
	
	
	

}
