package gov.samhsa.mhc.pcm.service.consent;

import gov.samhsa.mhc.pcm.domain.consent.ConsentRevocationTermsVersions;
import gov.samhsa.mhc.pcm.service.dto.ConsentRevocationTermsVersionsDto;

import javax.persistence.OrderBy;

public interface ConsentRevocationTermsVersionsService {

    @OrderBy("added_date_time DESC")
    ConsentRevocationTermsVersions findByLatestEnabledVersion();

    @OrderBy("added_date_time DESC")
    ConsentRevocationTermsVersionsDto findDtoByLatestEnabledVersion();
}
