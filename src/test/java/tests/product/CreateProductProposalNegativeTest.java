package tests.product;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import api.ProductInfoApiClient;
import api.ProductProposalApiClient;
import factory.ProductProposalFactory;
import io.restassured.response.Response;
import tests.BaseTest;

import static org.hamcrest.Matchers.*;

import java.time.Duration;
import java.util.function.Supplier;



@Tag("Product")
@Tag("Negative")
@Tag("Product-Proposals")
public class CreateProductProposalNegativeTest  extends BaseTest {
	

	@Test
	@Tag("Slow")
	@Tag("Business-Logic")
	@DisplayName("409: should return Product Duplicate error for duplicate ISBN")
	public void ShouldRejestDuplicateProduct() throws InterruptedException {
		
		var req = ProductProposalFactory.valid().build();

		//1st create request attempt - should be successful
		Response firstResponse = ProductProposalApiClient.create(req);
		
		firstResponse.then()
			.statusCode(201)
			.body("id", notNullValue())
			.body("publication.status", is("PROPOSED"));
		
		String createdProductId = firstResponse.jsonPath().getString("id");
		
		//Time needed to index new product
		Awaitility.await()
			.atMost(Duration.ofSeconds(40))
			.pollInterval(Duration.ofSeconds(20))
			.until(() -> ProductInfoApiClient.getProduct(createdProductId).getStatusCode() == 200);
		
		//2nd request - creating duplicate attempt
		Response duplicateResponse = ProductProposalApiClient.create(req);
		
		
		duplicateResponse.then()
			.statusCode(409)
			.body("errors[0].code", is("ProductDuplicate"))
			.body("errors[0].userMessage", containsString("Product already exists"))
			.body("errors[0].metadata.existingProductId", is(createdProductId));
	}
	
	@ParameterizedTest(name = "Invalid ISBN - {0}")
	@MethodSource("arguments.InvalidIsbnArguments#invalidIsbn")
	@Tag("Validation")
	@DisplayName("422: Should reject invalid ISBN field")
	public void ShouldRejectInvalidEAN(Supplier<String> invalidIsbn, String expectedMessage) {
		
		String isbn = invalidIsbn.get();
		
		var reqBuilder = ProductProposalFactory.valid()
				.withParamValue("245669", isbn);
		
		var req = reqBuilder.build();
		
		//Sending a request with invalid isbn
		
		Response response = ProductProposalApiClient.create(req);
		
		response.then()
			.statusCode(422)
			.body("errors[0].userMessage", containsString(expectedMessage));
			
			
		
				
		
		
				
	}
	
	

}
