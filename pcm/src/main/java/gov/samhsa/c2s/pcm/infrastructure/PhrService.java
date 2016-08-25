package gov.samhsa.c2s.pcm.infrastructure;

import gov.samhsa.c2s.pcm.config.OAuth2FeignClientConfig;
import gov.samhsa.c2s.pcm.infrastructure.dto.PatientDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "phr", configuration = OAuth2FeignClientConfig.class)
public interface PhrService {

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    PatientDto getPatientProfile();
}
