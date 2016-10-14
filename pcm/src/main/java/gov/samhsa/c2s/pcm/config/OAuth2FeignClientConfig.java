package gov.samhsa.c2s.pcm.config;

import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

// TODO (#22): remove this configuration when PHR API is refactored to a service that can be called by this API without OAuth2 token
@Configuration
public class OAuth2FeignClientConfig {

    @Bean
    public OAuth2FeignRequestInterceptor oAuth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resource);
    }
}
