package gov.samhsa.bhits.pcm;

import gov.samhsa.bhits.pcm.domain.PcmDomainBasePackageMarkerInterface;
import gov.samhsa.bhits.vss.VssBasePackageMarkerInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EntityScan(basePackageClasses = PcmDomainBasePackageMarkerInterface.class)
@EnableJpaRepositories(basePackageClasses = PcmDomainBasePackageMarkerInterface.class)
@EnableResourceServer
// TODO (BU): remove component scan when VSS is separated from PCM
@ComponentScan(basePackageClasses = {PatientConsentManagementApplication.class, VssBasePackageMarkerInterface.class})
public class PatientConsentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientConsentManagementApplication.class, args);
    }
}
