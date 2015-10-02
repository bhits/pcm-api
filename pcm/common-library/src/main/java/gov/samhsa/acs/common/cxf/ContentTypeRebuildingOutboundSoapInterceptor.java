package gov.samhsa.acs.common.cxf;

import static java.util.stream.Collectors.joining;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

/**
 * The Class ContentTypeRebuildingOutboundSoapInterceptor.
 */
public class ContentTypeRebuildingOutboundSoapInterceptor extends
AbstractSoapInterceptor {

	/** The Constant CONTENT_TYPE. */
	private static final String CONTENT_TYPE = "Content-Type";

	/** The Constant SEMICOLON. */
	private static final String SEMICOLON = ";";

	/** The Constant EQUAL. */
	private static final String EQUAL = "=";

	/** The Constant SPACE. */
	private static final String SPACE = " ";

	/** The Constant EMPTY. */
	private static final String EMPTY = "";

	/** The Constant BACKSLASH. */
	private static final String BACKSLASH = "\\";

	/** The Constant DOUBLE_QUOTE. */
	private static final String DOUBLE_QUOTE = "\"";

	/** The Constant IDX_KEY. */
	private static final int IDX_KEY = 0;

	/** The Constant IDX_VALUE. */
	private static final int IDX_VALUE = 1;

	/** The Constant SEMICOLON_WITH_SPACE. */
	private static final String SEMICOLON_WITH_SPACE = SEMICOLON + SPACE;

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/**
	 * Instantiates a new content type rebuilding outbound soap interceptor.
	 */
	public ContentTypeRebuildingOutboundSoapInterceptor() {
		super(Phase.WRITE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message
	 * .Message)
	 */
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		logger.debug(() -> message.toString());
		logger.debug(() -> new StringBuilder().append(CONTENT_TYPE)
				.append(" before modification: ")
				.append(message.get(CONTENT_TYPE).toString()).toString());

		final String contentType = (String) message.get(CONTENT_TYPE);

		final List<Pair> contentTypeList = new ArrayList<>();

		try (Scanner scanner = new Scanner(contentType)) {
			scanner.useDelimiter(SEMICOLON);
			while (scanner.hasNext()) {
				final String next = scanner.next();
				final String[] split = next.split(EQUAL);
				contentTypeList.add(new Pair(clean(split, IDX_KEY),
						split.length > IDX_VALUE ? Optional.of(clean(split,
								IDX_VALUE)) : Optional.empty()));
			}
		}

		final String correctedContentType = contentTypeList
				.stream()
				.map(ContentTypeRebuildingOutboundSoapInterceptor::toCorrectedContentType)
				.collect(joining(SEMICOLON_WITH_SPACE));

		logger.debug(() -> new StringBuilder().append(CONTENT_TYPE)
				.append(" after modification: ").append(correctedContentType)
				.toString());
		message.put(CONTENT_TYPE, correctedContentType);
	}

	/**
	 * Clean.
	 *
	 * @param split
	 *            the split
	 * @param idx
	 *            the idx
	 * @return the string
	 */
	private static final String clean(String[] split, int idx) {
		return split[idx].replace(DOUBLE_QUOTE, EMPTY)
				.replace(BACKSLASH, EMPTY).replace(SPACE, EMPTY);
	}

	/**
	 * To corrected content type.
	 *
	 * @param entry
	 *            the entry
	 * @return the string
	 */
	private static final String toCorrectedContentType(Pair entry) {
		return new StringBuilder().append(entry.getKey())
				.append(wrap(entry.getValue().orElse(EMPTY))).toString();
	}

	/**
	 * Wrap.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	private static final String wrap(String value) {
		return EMPTY.equals(value) ? value : new StringBuilder().append(EQUAL)
				.append(DOUBLE_QUOTE).append(value).append(DOUBLE_QUOTE)
				.toString();
	}

	/**
	 * The Class Pair.
	 */
	private class Pair {

		/** The key. */
		private final String key;

		/** The value. */
		private final Optional<String> value;

		/**
		 * Instantiates a new pair.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 */
		public Pair(String key, Optional<String> value) {
			super();
			this.key = key;
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return new StringBuilder().append(this.key).append("=")
					.append(this.value.orElse("")).toString();
		}

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		private String getKey() {
			return key;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		private Optional<String> getValue() {
			return value;
		}
	}
}
