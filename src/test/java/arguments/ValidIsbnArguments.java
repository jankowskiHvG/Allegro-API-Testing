package arguments;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.provider.Arguments;

import com.github.javafaker.Faker;

public class ValidIsbnArguments {
	
	private static final Faker faker = new Faker();
	
	public static Stream<Arguments> validIsbn() {
		return Stream.of(
			//Valid ISBN number
			Arguments.of(Named.of("validIsbn",(Supplier<String>) () -> faker.code().isbn13())),
			//Set of valid ISBN numbers with additional spaces
			Arguments.of(Named.of("space + validIsbn + space",(Supplier<String>) () -> " " + faker.code().isbn13() + " ")),
			Arguments.of(Named.of("validIsbn + space",(Supplier<String>) () -> faker.code().isbn13() + " ")),
			Arguments.of(Named.of("space + validIsbn",(Supplier<String>) () -> " " + faker.code().isbn13()))	
		);
	}	
}
