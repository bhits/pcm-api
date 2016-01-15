package gov.samhsa.bhits.common;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Params.
 */
public class Params {

	/** The map. */
	private Map<String, String> map;

	/**
	 * Instantiates a new params.
	 */
	public Params() {
		super();
		this.map = null;
	}

	/**
	 * Instantiates a new params. key.toString() is invoked before saving.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public Params(String key, String value) {
		super();
		this.map = new HashMap<String, String>();
		map.put(key, value);
	}

	/**
	 * Adds another param. key.toString() is invoked before saving.
	 *
	 * @param <T>
	 *            the generic type
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the params
	 */
	public <T> Params and(T key, String value) {
		this.map.put(key.toString(), value);
		return this;
	}

	/**
	 * To map.
	 *
	 * @return the map
	 */
	public Map<String, String> toMap() {
		return this.map;
	}

	/**
	 * Gets the value of a given key. key.toString() is invoked before getting
	 * the value.
	 *
	 * @param <T>
	 *            the generic type
	 * @param key
	 *            the key
	 * @return the string
	 */
	public <T> String get(T key) {
		return this.map.get(key.toString());
	}
}
