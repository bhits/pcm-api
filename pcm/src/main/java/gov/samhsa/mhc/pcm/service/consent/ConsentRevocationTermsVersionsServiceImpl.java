package gov.samhsa.mhc.pcm.service.consent;

import gov.samhsa.mhc.pcm.domain.consent.ConsentRevocationTermsVersions;
import gov.samhsa.mhc.pcm.domain.consent.ConsentRevocationTermsVersionsRepository;
import gov.samhsa.mhc.pcm.service.dto.ConsentRevocationTermsVersionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ConsentRevocationTermsVersionsServiceImpl implements ConsentRevocationTermsVersionsService {

    @Autowired
    private ConsentRevocationTermsVersionsRepository consentRevocationTermsVersionsRepository;

    @Override
    public ConsentRevocationTermsVersions findByLatestEnabledVersion() {
        return consentRevocationTermsVersionsRepository.findOneByVersionDisabled(false);
    }

    @Override
    public ConsentRevocationTermsVersionsDto findDtoByLatestEnabledVersion() {
        ConsentRevocationTermsVersions consentRevocationTermsVersions = findByLatestEnabledVersion();

        ConsentRevocationTermsVersionsDto consentRevocationTermsVersionsDto = new ConsentRevocationTermsVersionsDto();

        consentRevocationTermsVersionsDto.setId(consentRevocationTermsVersions.getId());
        consentRevocationTermsVersionsDto.setAddedDateTime(consentRevocationTermsVersions.getAddedDateTime());
        consentRevocationTermsVersionsDto.setConsentRevokeTermsText(consentRevocationTermsVersions.getConsentRevokeTermsText());
        consentRevocationTermsVersionsDto.setVersionDisabled(consentRevocationTermsVersions.getVersionDisabled());

        return consentRevocationTermsVersionsDto;
    }
}
