package gov.samhsa.c2s.pcm.service.consent;

import gov.samhsa.c2s.pcm.domain.consent.ConsentRevocationTermsVersions;
import gov.samhsa.c2s.pcm.service.dto.ConsentRevocationTermsVersionsDto;
import gov.samhsa.c2s.pcm.service.exception.ConsentRevocationTermsVersionNotFoundException;
import gov.samhsa.c2s.pcm.domain.consent.ConsentRevocationTermsVersionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ConsentRevocationTermsVersionsServiceImpl implements ConsentRevocationTermsVersionsService {

    @Autowired
    private ConsentRevocationTermsVersionsRepository consentRevocationTermsVersionsRepository;


    /**
     * Search for the latest enabled version.
     *
     * @return ConsentRevocationTermsVersions
     */
    @Override
    public ConsentRevocationTermsVersions findByLatestEnabledVersion() {
        List<ConsentRevocationTermsVersions> consentRevocationTermsVersionsList = consentRevocationTermsVersionsRepository.findAllByVersionDisabledOrderByAddedDateTimeDesc(false);
        ConsentRevocationTermsVersions consentRevocationTermsVersions;

        try{
            consentRevocationTermsVersions = consentRevocationTermsVersionsList.get(0);
        }catch(IndexOutOfBoundsException e){
            consentRevocationTermsVersions = null;
        }

        return consentRevocationTermsVersions;
    }

    /**
     * Search for the latest enabled version and return
     * the DTO for that version.
     *
     * @return ConsentRevocationTermsVersionsDto
     */
    @Override
    public ConsentRevocationTermsVersionsDto findDtoByLatestEnabledVersion() {
        ConsentRevocationTermsVersionsDto consentRevocationTermsVersionsDto;

        ConsentRevocationTermsVersions consentRevocationTermsVersions = findByLatestEnabledVersion();

        if (consentRevocationTermsVersions == null){
            throw new ConsentRevocationTermsVersionNotFoundException("No active ConsentRevocationTermsVersions record found in database");
        }else {

            consentRevocationTermsVersionsDto = new ConsentRevocationTermsVersionsDto();

            consentRevocationTermsVersionsDto.setId(consentRevocationTermsVersions.getId());
            consentRevocationTermsVersionsDto.setAddedDateTime(consentRevocationTermsVersions.getAddedDateTime());
            consentRevocationTermsVersionsDto.setConsentRevokeTermsText(consentRevocationTermsVersions.getConsentRevokeTermsText());
            consentRevocationTermsVersionsDto.setVersionDisabled(consentRevocationTermsVersions.getVersionDisabled());
        }

        return consentRevocationTermsVersionsDto;
    }
}
