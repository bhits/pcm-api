package gov.samhsa.pcm.web.config.di.servlet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Application context aggregation class for a specific servlet application context
 *
 */
@Configuration
@ComponentScan(basePackageClasses = {ServletConfig.class})
public class ServletConfig {

}
