package gov.samhsa.bhits.common;

/**
 * The Class ParamsBuilder.
 */
public class ParamsBuilder {

	/**
	 * New params.
	 *
	 * @return the params
	 */
	public static Params newParams() {
		return new Params();
	}

	/**
	 * Instantiates and returns a Params with the given key-value. For the key,
	 * key.toString() is invoked before saving.
	 *
	 * @param <T>
	 *            the generic type
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the params
	 */
	public static <T> Params withParam(T key, String value) {
		return new Params(key.toString(), value);
	}
}
