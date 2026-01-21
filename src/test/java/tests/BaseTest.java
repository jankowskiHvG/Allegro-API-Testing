package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.qameta.allure.restassured.AllureRestAssured;

public class BaseTest {
	
	private static WireMockServer wireMockServer;
	
	@BeforeAll
	public static void setup() {
		
		//logging when test fails
		RestAssured.config = RestAssured.config().logConfig(
				LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
				);
		
		//Global Allure filter
		RestAssured.filters(new AllureRestAssured());
		
		
		//Automatic WireMock start for mock mode
		if ("mock".equalsIgnoreCase(System.getProperty("env"))) {
			if (wireMockServer == null) {
			//wiremock searches for for files in src/test/resources
			wireMockServer = new WireMockServer(wireMockConfig().port(8080));
			wireMockServer.start();
			System.out.println("---MOCK MODE: WireMock started on port 8080 ---");
			} 			
		} else {System.out.println("---LIVE MODE: Connecting to Allegro Sandbox ---");
		}	
	}
	@AfterAll
	public static void tearDown() {
		//cleaning up after tests
		if (wireMockServer != null && wireMockServer.isRunning()) {
			wireMockServer.stop();
			System.out.println("---WireMock stopped---");
		}
	}
}
