package gov.samhsa.c2s.pcm.service.consent;

import gov.samhsa.c2s.pcm.domain.consent.ConsentRevocationTermsVersions;
import gov.samhsa.c2s.pcm.service.dto.ConsentRevocationTermsVersionsDto;

public interface ConsentRevocationTermsVersionsService {

    ConsentRevocationTermsVersions findByLatestEnabledVersion();

    ConsentRevocationTermsVersionsDto findDtoByLatestEnabledVersion();
}
