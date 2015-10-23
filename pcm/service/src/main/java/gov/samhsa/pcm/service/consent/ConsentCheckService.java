package gov.samhsa.pcm.service.consent;

import gov.samhsa.pcm.service.dto.ConsentDto;
import gov.samhsa.pcm.service.dto.ConsentValidationDto;

import org.springframework.security.access.annotation.Secured;

@Secured ({"ROLE_USER", "ROLE_ADMIN"})
public interface ConsentCheckService {

	public ConsentValidationDto getConflictConsent(ConsentDto consentDto);

}
