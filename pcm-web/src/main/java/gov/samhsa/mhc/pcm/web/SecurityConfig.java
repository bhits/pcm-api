package gov.samhsa.mhc.pcm.web;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class SecurityConfig {

    private static final String RESOURCE_ID = "pcm";

    @Bean
    public ResourceServerConfigurer resourceServer(SecurityProperties securityProperties) {
        return new ResourceServerConfigurerAdapter() {
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {
                resources.resourceId(RESOURCE_ID);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                if (securityProperties.isRequireSsl()) {
                    http.requiresChannel().anyRequest().requiresSecure();
                }
                http.authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/patients/providers/**").access("#oauth2.hasScope('pcm.readProvider')")
                        .antMatchers(HttpMethod.POST, "/patients/providers/**").access("#oauth2.hasScope('pcm.writeProvider')")
                        .antMatchers(HttpMethod.PUT, "/patients/providers/**").access("#oauth2.hasScope('pcm.writeProvider')")
                        .antMatchers(HttpMethod.DELETE, "/patients/providers/**").access("#oauth2.hasScope('pcm.deleteProvider')")
                        .antMatchers(HttpMethod.GET, "/patients/consents/**").access("#oauth2.hasScope('pcm.readConsent')")
                        .antMatchers(HttpMethod.POST, "/patients/consents/**").access("#oauth2.hasScope('pcm.writeConsent')")
                        .antMatchers(HttpMethod.PUT, "/patients/consents/**").access("#oauth2.hasScope('pcm.writeConsent')")
                        .antMatchers(HttpMethod.DELETE, "/patients/consents/**").access("#oauth2.hasScope('pcm.deleteConsent')");

            }
        };
    }
}
