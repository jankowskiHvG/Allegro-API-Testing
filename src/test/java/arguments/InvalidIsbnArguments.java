package arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import com.github.javafaker.Faker;
import java.util.function.Supplier;

public class InvalidIsbnArguments {

	private static final Faker faker = new Faker();
	
	public static final String ISBN_INCORRECT = "Incorrect value for ISBN parameter";
	public static final String ISBN_INVALID_CHARS = "Invalid characters in ISBN parameter value";
	public static final String ISBN_REQUIRED = "You cannot create a product without providing correct values for all the required parameters: [ISBN]";
	public static final String ISBN_NOT_NULL = "Either values, valuesIds, or rangeValue must be defined for parameter 245669";
	
	
	
	
	public static Stream<Arguments> invalidIsbn(){
		
		
		return Stream.of(
				Arguments.of("12 digits instead of 13", (Supplier<String>) () -> faker.code().isbn13().substring(0,12), ISBN_INCORRECT),
				Arguments.of("14 digits instead of 13", (Supplier<String>) () -> faker.code().isbn13() + faker.number().randomDigit(), ISBN_INCORRECT),
				Arguments.of("invalid checksum", (Supplier<String>) () -> isbnWithInvalidChecksum(faker.code().isbn13()), ISBN_INCORRECT),
				Arguments.of("null value", (Supplier<String>) () -> null,ISBN_NOT_NULL),
				Arguments.of("Empty string", (Supplier<String>) () -> "", ISBN_REQUIRED), 
				Arguments.of("Spaces only", (Supplier<String>) () -> " ".repeat(13),ISBN_REQUIRED),
				Arguments.of("Zero width space character inside ISBN", (Supplier<String>) () -> isbnZeroSpaceWidthInside(faker.code().isbn13()), ISBN_INVALID_CHARS),
				Arguments.of("Zero width space char instead of one digit", (Supplier<String>) () -> isbnReplaceDigitZeroSpaceWidth(faker.code().isbn13()), ISBN_INVALID_CHARS),
				Arguments.of("Dashes inside ISBN", (Supplier<String>) () -> withSeparators(faker.code().isbn13(), "-"), ISBN_INVALID_CHARS),
				Arguments.of("Spaces as separator", (Supplier<String>) () -> withSeparators(faker.code().isbn13(), " "), ISBN_INVALID_CHARS));
		}
	
	
	//helper for generating invalid Isbn with incorrect checksum(last digit)
	
	private static String isbnWithInvalidChecksum(String isbn) {
		char lastCharacter = isbn.charAt(isbn.length()-1);
		int lastDigit = Character.getNumericValue(lastCharacter);
		int incorrectLastDigit = Math.abs(lastDigit-1);
		return isbn.substring(0, (isbn.length()-1)) + incorrectLastDigit;
		
	}
	
	private static String isbnZeroSpaceWidthInside(String isbn) {
		return isbn.substring(0,6) + "\u200B" + isbn.substring(6);	
	}
	
	private static String isbnReplaceDigitZeroSpaceWidth(String isbn) {
		return isbn.substring(0,4) + "\u200B" + isbn.substring(7);
	}
	
	private static String withSeparators(String isbn, String separator) {
		return isbn.substring(0,3)
				+separator
				+isbn.substring(3,9)
				+separator
				+isbn.substring(9);
	}
	
	}

	