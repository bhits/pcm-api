package gov.samhsa.mhc.pcm.infrastructure;

import gov.samhsa.mhc.pcm.infrastructure.dto.ClinicalDocumentValidationRequest;
import gov.samhsa.mhc.pcm.infrastructure.dto.ClinicalDocumentValidationResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("dss")
public interface DssService {
    @RequestMapping(value = "/validateDocument", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ClinicalDocumentValidationResult validateClinicalDocument(@RequestBody ClinicalDocumentValidationRequest validationRequest);
}
