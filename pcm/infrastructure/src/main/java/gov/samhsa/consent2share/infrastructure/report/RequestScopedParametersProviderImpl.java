package gov.samhsa.consent2share.infrastructure.report;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * This class implements {@link ApplicationContextAware}, gets and returns and
 * instance of {@link RequestScopedParameters} type from the
 * {@link ApplicationContext}.
 */
public class RequestScopedParametersProviderImpl implements
ApplicationContextAware, RequestScopedParametersProvider {

	/** The application context. */
	private ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.infrastructure.report.
	 * RequestScopedParametersProvider#getRequestScopedParameters()
	 */
	@Override
	public RequestScopedParameters getRequestScopedParameters() {
		final RequestScopedParameters bean = this.applicationContext
				.getBean(RequestScopedParameters.class);
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
