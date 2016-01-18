package gov.samhsa.bhits.pcm.web;

import gov.samhsa.bhits.pcm.domain.PcmDomainMarkerInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = PcmDomainMarkerInterface.class)
@EnableJpaRepositories(basePackageClasses = PcmDomainMarkerInterface.class)
public class PatientConsentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientConsentManagementApplication.class, args);
    }
}
