package gov.samhsa.bhits.pcm.web;

import gov.samhsa.bhits.pcm.domain.PcmDomainMarkerInterface;
import gov.samhsa.bhits.vss.VssBasePackageMarkerInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EntityScan(basePackageClasses = PcmDomainMarkerInterface.class)
@EnableJpaRepositories(basePackageClasses = PcmDomainMarkerInterface.class)
@EnableResourceServer
@ComponentScan(basePackageClasses = VssBasePackageMarkerInterface.class)
public class PatientConsentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientConsentManagementApplication.class, args);
    }
}
