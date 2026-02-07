package factory;
import java.util.List;
import com.github.javafaker.Faker;
import dto.ProductProposalRequest;


public class ProductProposalFactory {
	
	private static final Faker faker = new Faker();
	
	
	
	public static ProductProposalRequest.ProductProposalRequestBuilder valid() {
		
		
		String isbn = faker.code().isbn13();
		String randomNameSufix = faker.number().digits(4);		
		
		return dto.ProductProposalRequest.builder()
				.name("Test Title of the Product" + randomNameSufix)
				.category(ProductProposalRequest.Category.builder()
						.id("79157")
						.build())
				.language("en-US")
				.parameters(List.of(
						ProductProposalRequest.Parameter.builder()
						.id("223545")
						.values(List.of("Test Title"))
						.build(),
						ProductProposalRequest.Parameter.builder()
						.id("223489")
						.values(List.of(
								"Author Name1",
								"Author Name Two",
								"Author Name Three"))
						.build(),
						ProductProposalRequest.Parameter.builder()
							.id("2868")
							.valuesIds(List.of("2868_1"))
							.build(),
						ProductProposalRequest.Parameter.builder()
							.id("223541")
							.valuesIds(List.of("223541_563273"))
							.build(),
						ProductProposalRequest.Parameter.builder()
							.id("249857")
							.valuesIds(List.of("249857_1769440"))
							.build(),
						ProductProposalRequest.Parameter.builder()
							.id("249493")
							.valuesIds(List.of("249493_1645911"))
							.build(),
						ProductProposalRequest.Parameter.builder()
							.id("75")
							.valuesIds(List.of("75_2"))
							.build(),
						ProductProposalRequest.Parameter.builder()
							.id("74")
							.values(List.of("2020"))
							.build(),
						ProductProposalRequest.Parameter.builder()
							.id("24648")
							.valuesIds(List.of("24648_1", "24648_2"))
							.build(),
						ProductProposalRequest.Parameter.builder()
							.id("223333")
							.values(List.of("20.5"))
							.build(),
						ProductProposalRequest.Parameter.builder()
							.id("245669")
							.name("ISBN")
							.valuesLabels(List.of(isbn))
							.values(List.of(isbn))
							.options(ProductProposalRequest.Parameter.Options.builder()
								.identifiesProduct(true)
								.isGTIN(true)
								.build())
						.build()
				))
						
						
				.images(List.of(
						ProductProposalRequest.Image.builder()
						.url("https://a.allegroimg.allegrosandbox.pl/original/11e24f/32cd712c41ada5fe3f3c41836c5b")
						.build()
						))
				.description(
						ProductProposalRequest.Description.builder()
							.sections(List.of(
									ProductProposalRequest.Section.builder()
									.items(List.of(
											ProductProposalRequest.Item.builder()
												.type("TEXT")
												.content("<p>Test description</p>")
												.build()
												))
									.build()
									))
							.build()
				);
	}
}