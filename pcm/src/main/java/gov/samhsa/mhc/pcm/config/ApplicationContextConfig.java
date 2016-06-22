package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.pcm.infrastructure.ClasspathSqlScriptProvider;
import gov.samhsa.mhc.pcm.infrastructure.SqlScriptProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jiahao.Li on 6/19/2016.
 */

@Configuration
public class ApplicationContextConfig {

    @Bean
    public SqlScriptProvider sqlScriptProvider() {
        return new ClasspathSqlScriptProvider();
    }
}
