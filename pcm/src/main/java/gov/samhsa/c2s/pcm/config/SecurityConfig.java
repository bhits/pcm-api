package gov.samhsa.c2s.pcm.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static gov.samhsa.c2s.common.oauth2.OAuth2ScopeUtils.hasScope;

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
                        .antMatchers(HttpMethod.GET, "/management/**").access(hasScope("pcm.management"))
                        .antMatchers(HttpMethod.POST, "/management/**").access(hasScope("pcm.management"))
                        // FIXME (#27): Change following method to protect new attest consent endpoint
                        .antMatchers(HttpMethod.GET, "/patients/consents/signConsent/**").access(hasScope("pcm.consent_sign"))
                        // FIXME (#28): Change following method to protect new attest consent revocation endpoint
                        .antMatchers(HttpMethod.GET, "/patients/consents/revokeConsent/**").access(hasScope("pcm.consent_revoke"))
                        .antMatchers(HttpMethod.GET, "/patients/providers/**").access(hasScope("pcm.provider_read"))
                        .antMatchers(HttpMethod.POST, "/patients/providers/**").access(hasScope("pcm.provider_create"))
                        .antMatchers(HttpMethod.DELETE, "/patients/providers/**").access(hasScope("pcm.provider_delete"))
                        .antMatchers(HttpMethod.GET, "/patients/consents/**").access(hasScope("pcm.consent_read"))
                        .antMatchers(HttpMethod.POST, "/patients/consents/**").access(hasScope("pcm.consent_create"))
                        .antMatchers(HttpMethod.PUT, "/patients/consents/**").access(hasScope("pcm.consent_update"))
                        .antMatchers(HttpMethod.DELETE, "/patients/consents/**").access(hasScope("pcm.consent_delete"))
                        .antMatchers(HttpMethod.GET, "/patients/activities/**").access(hasScope("pcm.activity_read"))
                        .antMatchers(HttpMethod.GET, "/patients/clinicaldocuments/**").access(hasScope("pcm.clinicalDocument_read"))
                        .antMatchers(HttpMethod.POST, "/patients/clinicaldocuments/**").access(hasScope("pcm.clinicalDocument_create"))
                        .antMatchers(HttpMethod.DELETE, "/patients/clinicaldocuments/**").access(hasScope("pcm.clinicalDocument_delete"))
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/patients/purposeOfUse", "/patients/medicalSection", "/patients/sensitivityPolicy").authenticated()
                        // TODO (#29)(BU): remove this permission after VSS is separated
                        .antMatchers(HttpMethod.GET, "/lookupService/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/lookupService/**").permitAll()
                        .anyRequest().denyAll();
            }
        };
    }

    // Uses SHA-256 with multiple iterations and a random salt value.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
}
