package gov.samhsa.c2s.pcm.service.provider;


import gov.samhsa.c2s.pcm.domain.reference.EntityType;
import gov.samhsa.c2s.pcm.infrastructure.dto.Provider;
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
     * @param providerDto the provider dto
     * @param provider    the provider
     * @return the abstract provider dto
     */
    public AbstractProviderDto setProviderDto(AbstractProviderDto providerDto, Provider provider) {

        //TODO: Need to handle constraint violations for whether allow Providers to have NULL properties
        providerDto.setNpi(provider.getNpi());
        providerDto.setEntityType(EntityType.valueOf(provider.getEntityType().getDisplayName()));
        providerDto.setFirstLineMailingAddress(provider.getFirstLineBusinessMailingAddress() == null ? "" : provider.getFirstLineBusinessMailingAddress());
        providerDto.setSecondLineMailingAddress(provider.getSecondLineBusinessMailingAddress() == null ? "" : provider.getSecondLineBusinessMailingAddress());
        providerDto.setMailingAddressCityName(provider.getBusinessMailingAddressCityName() == null ? "" : provider.getBusinessMailingAddressCityName());
        providerDto.setMailingAddressStateName(provider.getBusinessMailingAddressStateName() == null ? "" : provider.getBusinessMailingAddressStateName());
        providerDto.setMailingAddressPostalCode(provider.getBusinessMailingAddressPostalCode() == null ? "" : provider.getBusinessMailingAddressPostalCode());
        providerDto.setMailingAddressCountryCode(provider.getBusinessMailingAddressCountryCode() == null ? "" : provider.getBusinessMailingAddressCountryCode());
        providerDto.setMailingAddressTelephoneNumber(provider.getBusinessMailingAddressTelephoneNumber() == null ? "" : provider.getBusinessMailingAddressTelephoneNumber());
        providerDto.setMailingAddressFaxNumber(provider.getBusinessMailingAddressFaxNumber() == null ? "" : provider.getBusinessMailingAddressFaxNumber());
        providerDto.setFirstLinePracticeLocationAddress(provider.getFirstLineBusinessPracticeLocationAddress() == null ? "" : provider.getFirstLineBusinessPracticeLocationAddress());
        providerDto.setSecondLinePracticeLocationAddress(provider.getSecondLineBusinessPracticeLocationAddress() == null ? "" : provider.getSecondLineBusinessPracticeLocationAddress());
        providerDto.setPracticeLocationAddressCityName(provider.getBusinessPracticeLocationAddressCityName() == null ? "" : provider.getBusinessPracticeLocationAddressCityName());
        providerDto.setPracticeLocationAddressStateName(provider.getBusinessPracticeLocationAddressStateName() == null ? "" : provider.getBusinessPracticeLocationAddressStateName());
        providerDto.setPracticeLocationAddressPostalCode(provider.getBusinessPracticeLocationAddressPostalCode() == null ? "" : provider.getBusinessPracticeLocationAddressPostalCode());
        providerDto.setPracticeLocationAddressCountryCode(provider.getBusinessPracticeLocationAddressCountryCode() == null ? "" : provider.getBusinessPracticeLocationAddressCountryCode());
        providerDto.setPracticeLocationAddressTelephoneNumber(provider.getBusinessPracticeLocationAddressTelephoneNumber() == null ? "" : provider.getBusinessPracticeLocationAddressTelephoneNumber());
        providerDto.setPracticeLocationAddressFaxNumber(provider.getBusinessPracticeLocationAddressFaxNumber() == null ? "" : provider.getBusinessPracticeLocationAddressFaxNumber());
        providerDto.setEnumerationDate(provider.getEnumerationDate() == null ? "" : provider.getEnumerationDate());
        providerDto.setLastUpdateDate(provider.getLastUpdateDate() == null ? "" : provider.getLastUpdateDate());

        //TODO: Remove
        providerDto.setProviderTaxonomyCode("");
        providerDto.setProviderTaxonomyDescription("");
        return providerDto;
    }
}