package gov.samhsa.pcm.web.config.di.servlet;

import gov.samhsa.pcm.web.controller.ControllerBasePackageMarker;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;

/**
 * Using bean defined in root application context as View resolver
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {ControllerBasePackageMarker.class})
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/*@Override
	public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
				.allowedOrigins("*")
				.allowCredentials(false).maxAge(3600);
	}*/

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("views/uncaughtException.html");
		registry.addViewController("views/resourceNotFound.html");
		registry.addViewController("views/underConstruction.html");
		registry.addViewController("views/dataAccessFailure.html");
		registry.addViewController("views/error404.html");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ThemeChangeInterceptor());
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		return localeResolver;
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		simpleMappingExceptionResolver
		.setDefaultErrorView("views/uncaughtException");
		simpleMappingExceptionResolver.setWarnLogCategory("InternalError");

		Properties prop = new Properties();
		prop.put(".DataAccessException", "views/dataAccessFailure");
		prop.put(".NoSuchRequestHandlingMethodException",
				"views/resourceNotFound");
		prop.put(".TypeMismatchException", "views/resourceNotFound");
		prop.put(".MissingServletRequestParameterException",
				"views/resourceNotFound");

		simpleMappingExceptionResolver.setExceptionMappings(prop);
		return simpleMappingExceptionResolver;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	// url?lang=cn request will change the locale to Chinese
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean
	public BeanNameViewResolver beanNameViewResolver() {
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
		return beanNameViewResolver;
	}
}
