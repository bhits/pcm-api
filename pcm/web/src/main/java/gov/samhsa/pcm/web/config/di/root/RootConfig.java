package gov.samhsa.pcm.web.config.di.root;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application context aggregation class for root application context
 * @author tao.lin
 *
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {RootConfig.class})
public class RootConfig {

}
