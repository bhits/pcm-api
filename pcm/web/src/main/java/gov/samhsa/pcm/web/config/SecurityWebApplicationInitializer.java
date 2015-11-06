package gov.samhsa.pcm.web.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Do NOT register any @Configuration class in this WebApplicationInitializer.
 *
 * <p>
 * Some set up in AbstractSecurityWebApplicationInitializer corresponds to
 * the following elements in web.xml:
 * <filter>
 *		<filter-name>springSecurityFilterChain</filter-name>
 *		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
 *	</filter>
 *
 * <filter-mapping>
 *		<filter-name>springSecurityFilterChain</filter-name>
 *		<url-pattern>/*</url-pattern>
 * </filter-mapping>
 *
 */
public class SecurityWebApplicationInitializer extends
		AbstractSecurityWebApplicationInitializer {

	// Return true means
	// ServletContext.addListener(org.springframework.security.web.session.HttpSessionEventPublisher.class)
	// and corresponds to listener element in deployment descriptor:
	// <listener> <listener-class>
	// org.springframework.security.web.session.HttpSessionEventPublisher
	// </listener-class> </listener>
	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}
}
