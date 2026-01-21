package tests.product;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import api.ProductProposalApiClient;
import factory.ProductProposalFactory;
import io.restassured.response.Response;
import tests.BaseTest;

import static org.hamcrest.Matchers.*;
import java.util.function.Supplier;


@Tag("Product")
@Tag("Positive")
@Tag("Regression")
public class CreateProductProposalTest extends BaseTest {
	

	@ParameterizedTest(name = "{0}")
	@MethodSource("arguments.ValidIsbnArguments#validIsbn")
	void shouldCreateProductWithValidIsbnVariants(Supplier<String>isbnSupplier) {
	
		String validIsbn = isbnSupplier.get();
	
		var reqBuilder = ProductProposalFactory.valid()
				.withParamValue("245669", validIsbn);
	
		var req = reqBuilder.build();
	
		Response response = ProductProposalApiClient.create(req);
	
		response.then()
		.statusCode(201)
		.body("id", notNullValue())
		.body("publication.status", is("PROPOSED"));
	
	
}
	
}