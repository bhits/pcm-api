package gov.samhsa.c2s.pcm.config;

import gov.samhsa.c2s.pcm.infrastructure.ClasspathSqlScriptProvider;
import gov.samhsa.c2s.pcm.infrastructure.SqlScriptProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextConfig {

    @Bean
    public SqlScriptProvider sqlScriptProvider() {
        return new ClasspathSqlScriptProvider();
    }
}
