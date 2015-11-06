package gov.samhsa.pcm.infrastructure.report;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a bean to store request scoped parameters to add additional
 * parameters to the report model/parameters. This bean <b>MUST</b> be defined
 * in <b>"request"</b> scope in Spring DI configurations.<br>
 * <br>
 */
public class RequestScopedParameters {

	/** The parameters. */
	private Map<String, Object> parameters;

	/**
	 * Instantiates a new request scoped parameters instance with no parameters.
	 */
	public RequestScopedParameters() {
		parameters = newMap();
	}

	/**
	 * Instantiates a new request scoped parameters instance with the parameters
	 * provided in the <i>parameters</i> argument.
	 *
	 * @param parameters
	 *            the parameters
	 */
	public RequestScopedParameters(Map<String, Object> parameters) {
		parameters = newMap(parameters);
	}

	/**
	 * Adds the parameter and returns {@code this} current instance.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the request scoped parameters
	 */
	public RequestScopedParameters add(String key, Object value) {
		parameters = newMap(parameters);
		parameters.put(key, value);
		return this;
	}

	/**
	 * Gets the parameters set to {@code this} instance as a new and
	 * <b>unmodifiable</b> {@link Map}.
	 *
	 * @return the parameters
	 * @see Collections#unmodifiableMap
	 */
	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(newMap(parameters));
	}

	/**
	 * New map.
	 *
	 * @return the map
	 */
	private static final Map<String, Object> newMap() {
		return new HashMap<>();
	}

	/**
	 * New map.
	 *
	 * @param map
	 *            the map
	 * @return the map
	 */
	private static final Map<String, Object> newMap(Map<String, Object> map) {
		return new HashMap<>(map);
	}
}
