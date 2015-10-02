package gov.samhsa.consent2share.web.config;

import gov.samhsa.consent2share.web.config.di.root.PropertyPlaceholderConfig;
import gov.samhsa.consent2share.web.config.di.root.RootConfig;
import gov.samhsa.consent2share.web.config.di.servlet.ServletConfig;

import javax.servlet.HttpConstraintElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * WebApplicationInitializer is used to replace web.xml in Servlet 3.0+
 * environments like Tomcat 7.0 and above.
 *
 * <p>
 * Only use this WebApplicationInitializer to register @Configuration classes
 *
 * <p>
 * One application could have multiple WebApplicationInitializer implementations
 * which can be used to partition configurations and as well as the old web.xml.
 *
 * <p>
 * One application could have one root web application context which associates
 * with the webapp and multiple servlet application contexts each of which
 * defines the beans for one Spring servlet's app context.
 *
 * <p>
 * The root web application context is loaded by
 * org.springframework.web.context.ContextLoaderListener. <br>
 * servlet application context is loaded by
 * org.springframework.web.servlet.FrameworkServlet which is the parent class of
 * org.springframework.web.servlet.DispatcherServlet.
 *
 * <p>
 * All Spring MVC controllers must go in the servlet application context. <br>
 * Beans in servlet application contexts can reference beans in root web
 * application context, but not vice versa.
 *
 * <p>
 * In most simple cases, the root application context is unnecessary. It
 * is generally used to contain beans that are shared between all servlets and
 * filters in a webapp. If you only have one servlet, then there's not really
 * much point, unless you have a specific use for it.
 *
 * <p>
 * See the detailed comments below to see how java code corresponds to elements in web.xml.
 * And some set up in AbstractAnnotationConfigDispatcherServletInitializer corresponds to <servlet>
 * and <servlet-mapping> elements in web.xml.
 *
 */

public class AppContextDispatcherServletInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String SERVLET_NAME = "consent2share";

	// If returns NOT null, then means same thing as the following elements in
	// web.xml:
	// <context-param>
	// <param-name>contextConfigLocation</param-name>
	// <param-value>classpath*:META-INF/spring/applicationContext*.xml</param-value>
	// </context-param>
	// <listener>
	// <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	// </listener>
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		logger.debug("getServletConfigClasses is called.");
		// Here we also load PropertyPlaceholderConfig which is defined in di.root package.
		// This is because PropertyPlaceholderConfigurer bean in root application context cannot be used by any servlet application context.
		// See this link http://asoftwareguy.com/2010/11/19/spring-propertyplaceholderconfigurer-within-web-and-application-context/ from reference
		// which quotes what Jürgen Höller said regarding PropertyPlaceholderConfigurer.
		return new Class<?>[] { ServletConfig.class, PropertyPlaceholderConfig.class };
	}

	// <url-pattern>/</url-pattern>
	@Override
	protected String[] getServletMappings() {
		// Map Servlet to "/"
		return new String[] { "/" };
	}

	// <servlet-name>consent2share</servlet-name>
	@Override
	protected String getServletName() {
		logger.debug("Servlet Name: " + SERVLET_NAME);
		return SERVLET_NAME;
	}

	@Override
	protected void customizeRegistration(
			ServletRegistration.Dynamic registration) {
		super.customizeRegistration(registration);

		registerSecurityConstraint(registration);
	}

	// <security-constraint> element in web.xml but at servelet level
	private void registerSecurityConstraint(
			ServletRegistration.Dynamic registration) {
		// HttpConstraintElement corresponds to user-data-constraint element in
		// deployment descriptor web.xml.
		// https is required.
		HttpConstraintElement httpConstraintElement = new HttpConstraintElement(
				TransportGuarantee.CONFIDENTIAL);

		// ServletSecurityElement corresponds to security-constraint element in
		// deployment descriptor web.xml.
		ServletSecurityElement servletSecurityElement = new ServletSecurityElement(
				httpConstraintElement);

		// Configured at one servlet level and one servelet configuration at one
		// time.
		// But in web.xml, security-constraint element can be configured at
		// application level
		// and web-resource-collection element is used to fine control the urls
		// which could be across multiple servlets
		registration.setServletSecurity(servletSecurityElement);
	}

	// The following two elements in web.xml cannot be specified in Java
	// <display-name>consent2share</display-name>
	// <description>consent2share application</description>

	// Same the following elements in web.xml
	// <context-param>
	// <param-name>spring.profiles.active</param-name>
	// <param-value>profileName</param-value>
	// </context-param>
	//@Override
	//protected WebApplicationContext createRootApplicationContext() {
	//	WebApplicationContext rootContext = super
	//			.createRootApplicationContext();
	//	if (rootContext != null
	//			&& rootContext instanceof ConfigurableApplicationContext) {
	//		((ConfigurableApplicationContext) rootContext).getEnvironment()
	//				.setActiveProfiles("profileName");
	//	}
	//	return rootContext;
	//}
}
