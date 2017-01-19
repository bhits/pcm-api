package gov.samhsa.c2s.pcm.infrastructure;

import gov.samhsa.c2s.pcm.config.OAuth2FeignClientConfig;
import gov.samhsa.c2s.pcm.infrastructure.dto.ProviderDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "pls", configuration = OAuth2FeignClientConfig.class)
public interface PlsService {

    @RequestMapping(value = "/providers/{npi}", method = RequestMethod.GET)
    ProviderDto getProvider(@PathVariable("npi") String npi);
}
