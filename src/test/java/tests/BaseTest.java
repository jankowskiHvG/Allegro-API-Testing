package tests;

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
		
		//only for mock setup purpose:
//		RestAssured.filters(new io.restassured.filter.log.RequestLoggingFilter(), 
//                new io.restassured.filter.log.ResponseLoggingFilter(),
//                new AllureRestAssured());
//		
//		
		//Global Allure filter
		RestAssured.filters(new AllureRestAssured());
		
		
		//Automatic WireMock start for mock mode
		if ("mock".equalsIgnoreCase(System.getProperty("env"))) {
			if (wireMockServer == null) {
			//wiremock searches for for files in src/test/resources
			wireMockServer = new WireMockServer(wireMockConfig().port(8080));
			wireMockServer.start();
			System.out.println("---MOCK MODE: WireMock started on port 8080 ---");
			
			//Register a shutdown hook  to ensure Wiremock stops after all test classes are finished
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				if(wireMockServer != null && wireMockServer.isRunning()) {
					wireMockServer.stop();
					System.out.println("---WireMock stopped---");
				}
			}));
			} 			
		} else {System.out.println("---LIVE MODE: Connecting to Allegro Sandbox ---");
		}	
	}
}
