package gov.samhsa.mhc.pcm.service.consent;

import gov.samhsa.mhc.pcm.domain.consent.ConsentRevocationTermsVersions;
import gov.samhsa.mhc.pcm.service.dto.ConsentRevocationTermsVersionsDto;

public interface ConsentRevocationTermsVersionsService {

    ConsentRevocationTermsVersions findByLatestEnabledVersion();

    ConsentRevocationTermsVersionsDto findDtoByLatestEnabledVersion();
}
