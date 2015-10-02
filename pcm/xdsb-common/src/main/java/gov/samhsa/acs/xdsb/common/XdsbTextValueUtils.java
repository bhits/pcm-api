package gov.samhsa.acs.xdsb.common;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.util.Assert;

/**
 * The Class XdsbTextValueUtils.
 */
public final class XdsbTextValueUtils {

	/** The Constant OPEN_PARENTHESIS. */
	private static final String OPEN_PARENTHESIS = "(";

	/** The Constant CLOSE_PARENTHESIS. */
	private static final String CLOSE_PARENTHESIS = ")";

	/** The Constant DELIMITER. */
	private static final String DELIMITER = ",";

	/** The Constant SINGLE_QUOTE. */
	private static final String SINGLE_QUOTE = "'";

	/** The Constant EMPTY_STRING. */
	private static final String EMPTY_STRING = "";

	/**
	 * Instantiates a new xdsb text value utils.
	 */
	private XdsbTextValueUtils() {
	}

	/**
	 * Collect as string.
	 *
	 * @param args
	 *            the args
	 * @return the string
	 */
	public static final String collectAsString(List<String> args) {
		return collectAsString(args.stream());
	}

	/**
	 * Collect as string.
	 *
	 * @param args
	 *            the args
	 * @return the string
	 */
	public static final String collectAsString(Stream<String> args) {
		final String values = args
				.map(XdsbTextValueUtils::wrapWithSingleQuotes).collect(
						joining(DELIMITER));
		return new StringBuilder().append(OPEN_PARENTHESIS).append(values)
				.append(CLOSE_PARENTHESIS).toString();
	}

	/**
	 * Collect as string.
	 *
	 * @param args
	 *            the args
	 * @return the string
	 */
	public static final String collectAsString(String... args) {
		return collectAsString(Arrays.stream(args));
	}

	/**
	 * Wrap with single quotes.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static final String wrapWithSingleQuotes(String value) {
		Assert.hasText(value);
		if (!value.startsWith(SINGLE_QUOTE) || !value.endsWith(SINGLE_QUOTE)) {
			value = value.replace(SINGLE_QUOTE, EMPTY_STRING);
			value = new StringBuilder().append(SINGLE_QUOTE).append(value)
					.append(SINGLE_QUOTE).toString();
		}
		return value;
	}
}
