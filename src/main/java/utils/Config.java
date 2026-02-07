package utils;
import io.github.cdimascio.dotenv.Dotenv;


public class Config {
	
	//Loading values from .env file in main folder. 
	//ignoreIfMissing lets running mocked tests in no .env file had been prepared
	private static final Dotenv dotenv = Dotenv.configure()
			.ignoreIfMissing()
			.load();
	
	public static String get(String key) {
		//mock mode
		if ("mock".equalsIgnoreCase(System.getProperty("env"))) {
			return getMockValue(key);
		}
		
		//LIVE mode
		//Converts camelCase config keys to SCREAMING_SNAKE_CASE env variables
		String envKey = "ALLEGRO_" + key.replaceAll("([A-Z])", "_$1").toUpperCase();
		String value = System.getenv(envKey);
		
		if (value == null) {
			value = dotenv.get(envKey);
		}
		
		if (value == null) {
			throw new RuntimeException("Missing configuration for: " + envKey + ". Check if .env file exists or use -Denv=mock for demo mode.");
		}
		return value;
	}
	

	private static String getMockValue(String key) {
		switch (key) {
		case "baseUrl":
		case "tokenUrl":
			return "http://localhost:8080";
			default:
				return "mock_value";
		}
	}
}
