package gov.samhsa.consent2share.service.consent;

import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentValidationDto;

import org.springframework.security.access.annotation.Secured;

@Secured ({"ROLE_USER", "ROLE_ADMIN"})
public interface ConsentCheckService {

	public ConsentValidationDto getConflictConsent(ConsentDto consentDto);

}
