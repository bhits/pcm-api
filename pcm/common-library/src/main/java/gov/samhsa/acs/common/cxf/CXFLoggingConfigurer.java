package gov.samhsa.acs.common.cxf;

import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.xml.ws.Endpoint;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.spring.EndpointDefinitionParser.SpringEndpointImpl;
import org.apache.cxf.message.Message;

/**
 * The Class CXFLoggingConfigurer.
 */
public class CXFLoggingConfigurer {

	/** The Constant LOGGER. */
	private final static AcsLogger LOGGER = AcsLoggerFactory
			.getLogger(CXFLoggingConfigurer.class);

	/** The Constant ADD. */
	private static final String ADD = "Adding";

	/** The Constant REMOVE. */
	private static final String REMOVE = "Removing";

	/** The enable logging interceptors. */
	private boolean enableLoggingInterceptors;

	/** The endpoint. */
	private final Optional<SpringEndpointImpl> endpoint;

	/** The client. */
	private final Optional<Client> client;

	/**
	 * Instantiates a new CXF logging configurer.
	 *
	 * @param client
	 *            the client
	 */
	public CXFLoggingConfigurer(Client client) {
		this.endpoint = Optional.empty();
		this.client = Optional.of(client);
	}

	/**
	 * Instantiates a new CXF logging configurer.
	 *
	 * @param endpoint
	 *            the endpoint
	 */
	public CXFLoggingConfigurer(Endpoint endpoint) {
		if (!(endpoint instanceof SpringEndpointImpl)) {
			final String error = "Endpoint is not an instance of org.apache.cxf.jaxws.spring.EndpointDefinitionParser.SpringEndpointImpl";
			LOGGER.error(error);
			throw new CXFLoggingConfigurerException(error);
		}
		this.endpoint = Optional.of((SpringEndpointImpl) endpoint);
		this.client = Optional.empty();
		this.enableLoggingInterceptors = false;
	}

	/**
	 * Checks if is enable logging interceptors.
	 *
	 * @return true, if is enable logging interceptors
	 */
	public boolean isEnableLoggingInterceptors() {
		return this.enableLoggingInterceptors;
	}

	/**
	 * Sets the enable logging interceptors.
	 *
	 * @param enableLoggingInterceptors
	 *            the new enable logging interceptors
	 */
	public synchronized void setEnableLoggingInterceptors(
			boolean enableLoggingInterceptors) {
		this.endpoint.ifPresent(ep -> configureInterceptors(
				ep::getInInterceptors, ep::getOutInterceptors,
				serviceNameWithInvokingInstance(ep, this),
				enableLoggingInterceptors));
		this.client.ifPresent(client -> configureInterceptors(
				client::getInInterceptors, client::getOutInterceptors,
				serviceNameWithInvokingInstance(client, this),
				enableLoggingInterceptors));
		this.enableLoggingInterceptors = enableLoggingInterceptors;
	}

	/**
	 * Configure interceptors.
	 *
	 * @param client
	 *            the client
	 * @param enableLoggingInterceptors
	 *            the enable logging interceptors
	 */
	public static final void configureInterceptors(Client client,
			boolean enableLoggingInterceptors) {
		configureInterceptors(client::getInInterceptors,
				client::getOutInterceptors, client.getEndpoint().getService()
				.getName()::toString, enableLoggingInterceptors);
	}

	/**
	 * Configure interceptors.
	 *
	 * @param client
	 *            the client
	 * @param serviceName
	 *            the service name
	 * @param enableLoggingInterceptors
	 *            the enable logging interceptors
	 */
	public static final void configureInterceptors(Client client,
			Supplier<String> serviceName, boolean enableLoggingInterceptors) {
		configureInterceptors(client::getInInterceptors,
				client::getOutInterceptors, serviceName,
				enableLoggingInterceptors);
	}

	/**
	 * Configure interceptors.
	 *
	 * @param inInterceptors
	 *            the in interceptors
	 * @param outInterceptors
	 *            the out interceptors
	 * @param serviceName
	 *            the service name
	 * @param enableLoggingInterceptors
	 *            the enable logging interceptors
	 */
	public static final void configureInterceptors(
			Supplier<List<Interceptor<? extends Message>>> inInterceptors,
			Supplier<List<Interceptor<? extends Message>>> outInterceptors,
			Supplier<String> serviceName, boolean enableLoggingInterceptors) {
		if (enableLoggingInterceptors) {
			LOGGER.info(() -> "Enabling CXF logging interceptors at "
					+ serviceName.get());
			if (inInterceptors
					.get()
					.stream()
					.noneMatch(
							interceptor -> interceptor instanceof LoggingInInterceptor)) {
				final LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
				byLoggingDebug(ADD).accept(loggingInInterceptor);
				inInterceptors.get().add(loggingInInterceptor);
			}
			if (outInterceptors
					.get()
					.stream()
					.noneMatch(
							interceptor -> interceptor instanceof LoggingOutInterceptor)) {
				final LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
				byLoggingDebug(ADD).accept(loggingOutInterceptor);
				outInterceptors.get().add(loggingOutInterceptor);
			}
		} else {
			LOGGER.info(() -> "Disabling CXF logging interceptors at "
					+ serviceName.get());
			inInterceptors.get().stream()
			.filter(by(LoggingInInterceptor.class))
			.peek(byLoggingDebug(REMOVE))
			.forEach(inInterceptors.get()::remove);
			outInterceptors.get().stream()
			.filter(by(LoggingOutInterceptor.class))
			.peek(byLoggingDebug(REMOVE))
			.forEach(outInterceptors.get()::remove);
		}
	}

	/**
	 * Service name with invoking instance.
	 *
	 * @param client
	 *            the client
	 * @param invokingInstance
	 *            the invoking instance
	 * @return the supplier
	 */
	public static final Supplier<String> serviceNameWithInvokingInstance(
			Client client, Object invokingInstance) {
		return serviceNameWithInvokingInstance(client.getEndpoint()
				.getService().getName()::toString, invokingInstance);
	}

	/**
	 * Service name with invoking instance.
	 *
	 * @param endpoint
	 *            the endpoint
	 * @param invokingInstance
	 *            the invoking instance
	 * @return the supplier
	 */
	public static final Supplier<String> serviceNameWithInvokingInstance(
			SpringEndpointImpl endpoint, Object invokingInstance) {
		return serviceNameWithInvokingInstance(
				endpoint.getServiceName()::toString, invokingInstance);
	}

	/**
	 * By.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the predicate<? super interceptor<? extends message>>
	 */
	private static final <T> Predicate<? super Interceptor<? extends Message>> by(
			Class<T> clazz) {
		return interceptor -> interceptor.getClass() == clazz;
	}

	/**
	 * By logging debug.
	 *
	 * @param logMsgPrefix
	 *            the log msg prefix
	 * @return the consumer<? super interceptor<? extends message>>
	 */
	private static final Consumer<? super Interceptor<? extends Message>> byLoggingDebug(
			String logMsgPrefix) {
		return interceptor -> LOGGER.debug(() -> new StringBuilder()
		.append(logMsgPrefix).append(" - ")
		.append(interceptor.getClass().toString()).toString());
	}

	/**
	 * Service name with invoking instance.
	 *
	 * @param serviceName
	 *            the service name
	 * @param invokingInstance
	 *            the invoking instance
	 * @return the supplier
	 */
	private static final Supplier<String> serviceNameWithInvokingInstance(
			Supplier<String> serviceName, Object invokingInstance) {
		return () -> new StringBuilder().append(serviceName.get())
				.append("; invoking instance: ")
				.append(invokingInstance.toString()).toString();
	}
}
