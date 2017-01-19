package gov.samhsa.c2s.pcm.service.provider;


import gov.samhsa.c2s.pcm.domain.reference.EntityType;
import gov.samhsa.c2s.pcm.infrastructure.dto.ProviderDto;
import gov.samhsa.c2s.pcm.service.dto.AbstractProviderDto;
import org.springframework.stereotype.Service;

/**
 * The Class MapProviderResultToProviderDtoConverter.
 */
@Service
public class MapProviderResultToProviderDtoConverter {

    /**
     * Sets the provider dto.
     *
     * @param abstractProviderDto the abstractProviderDto dto
     * @param providerDto         the providerDto
     * @return the abstract provider dto
     */
    public AbstractProviderDto setProviderDto(AbstractProviderDto abstractProviderDto, ProviderDto providerDto) {

        //TODO: Need to handle constraint violations for whether allow Providers to have NULL properties
        abstractProviderDto.setNpi(providerDto.getNpi());
        abstractProviderDto.setEntityType(EntityType.valueOf(providerDto.getEntityType().getDisplayName()));
        abstractProviderDto.setFirstLineMailingAddress(providerDto.getFirstLineBusinessMailingAddress() == null ? "" : providerDto.getFirstLineBusinessMailingAddress());
        abstractProviderDto.setSecondLineMailingAddress(providerDto.getSecondLineBusinessMailingAddress() == null ? "" : providerDto.getSecondLineBusinessMailingAddress());
        abstractProviderDto.setMailingAddressCityName(providerDto.getBusinessMailingAddressCityName() == null ? "" : providerDto.getBusinessMailingAddressCityName());
        abstractProviderDto.setMailingAddressStateName(providerDto.getBusinessMailingAddressStateName() == null ? "" : providerDto.getBusinessMailingAddressStateName());
        abstractProviderDto.setMailingAddressPostalCode(providerDto.getBusinessMailingAddressPostalCode() == null ? "" : providerDto.getBusinessMailingAddressPostalCode());
        abstractProviderDto.setMailingAddressCountryCode(providerDto.getBusinessMailingAddressCountryCode() == null ? "" : providerDto.getBusinessMailingAddressCountryCode());
        abstractProviderDto.setMailingAddressTelephoneNumber(providerDto.getBusinessMailingAddressTelephoneNumber() == null ? "" : providerDto.getBusinessMailingAddressTelephoneNumber());
        abstractProviderDto.setMailingAddressFaxNumber(providerDto.getBusinessMailingAddressFaxNumber() == null ? "" : providerDto.getBusinessMailingAddressFaxNumber());
        abstractProviderDto.setFirstLinePracticeLocationAddress(providerDto.getFirstLineBusinessPracticeLocationAddress() == null ? "" : providerDto.getFirstLineBusinessPracticeLocationAddress());
        abstractProviderDto.setSecondLinePracticeLocationAddress(providerDto.getSecondLineBusinessPracticeLocationAddress() == null ? "" : providerDto.getSecondLineBusinessPracticeLocationAddress());
        abstractProviderDto.setPracticeLocationAddressCityName(providerDto.getBusinessPracticeLocationAddressCityName() == null ? "" : providerDto.getBusinessPracticeLocationAddressCityName());
        abstractProviderDto.setPracticeLocationAddressStateName(providerDto.getBusinessPracticeLocationAddressStateName() == null ? "" : providerDto.getBusinessPracticeLocationAddressStateName());
        abstractProviderDto.setPracticeLocationAddressPostalCode(providerDto.getBusinessPracticeLocationAddressPostalCode() == null ? "" : providerDto.getBusinessPracticeLocationAddressPostalCode());
        abstractProviderDto.setPracticeLocationAddressCountryCode(providerDto.getBusinessPracticeLocationAddressCountryCode() == null ? "" : providerDto.getBusinessPracticeLocationAddressCountryCode());
        abstractProviderDto.setPracticeLocationAddressTelephoneNumber(providerDto.getBusinessPracticeLocationAddressTelephoneNumber() == null ? "" : providerDto.getBusinessPracticeLocationAddressTelephoneNumber());
        abstractProviderDto.setPracticeLocationAddressFaxNumber(providerDto.getBusinessPracticeLocationAddressFaxNumber() == null ? "" : providerDto.getBusinessPracticeLocationAddressFaxNumber());
        abstractProviderDto.setEnumerationDate(providerDto.getEnumerationDate() == null ? "" : providerDto.getEnumerationDate());
        abstractProviderDto.setLastUpdateDate(providerDto.getLastUpdateDate() == null ? "" : providerDto.getLastUpdateDate());

        return abstractProviderDto;
    }
}