package gov.samhsa.mhc.pcm.infrastructure;

import gov.samhsa.mhc.pcm.config.OAuth2FeignClientConfig;
import gov.samhsa.mhc.pcm.infrastructure.dto.PatientDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "phr", configuration = OAuth2FeignClientConfig.class)
public interface PhrService {

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    PatientDto getPatientProfile();
}
