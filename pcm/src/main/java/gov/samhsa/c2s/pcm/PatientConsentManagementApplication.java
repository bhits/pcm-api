package gov.samhsa.c2s.pcm;

import gov.samhsa.c2s.pcm.domain.PcmDomainBasePackageMarkerInterface;
import gov.samhsa.c2s.vss.VssBasePackageMarkerInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EntityScan(basePackageClasses = PcmDomainBasePackageMarkerInterface.class)
@EnableJpaRepositories(basePackageClasses = PcmDomainBasePackageMarkerInterface.class)
@EnableResourceServer
// TODO (#23)(BU): remove component scan when VSS is separated from PCM
@ComponentScan(basePackageClasses = {PatientConsentManagementApplication.class, VssBasePackageMarkerInterface.class})
@EnableDiscoveryClient
@EnableFeignClients
public class PatientConsentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientConsentManagementApplication.class, args);
    }
}
