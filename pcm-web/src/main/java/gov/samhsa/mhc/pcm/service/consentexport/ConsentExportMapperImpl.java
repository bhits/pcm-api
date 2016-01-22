package gov.samhsa.mhc.pcm.service.consentexport;

import gov.samhsa.mhc.consentgen.*;
import gov.samhsa.mhc.pcm.domain.consent.*;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalConceptCode;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class ConsentExportMapperImpl.
 */
@Service
public class ConsentExportMapperImpl implements ConsentExportMapper {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The model mapper.
     */
    @Autowired
    private ModelMapper modelMapper;

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.consentexport.ConsentExportMapper#map
     * (gov.samhsa.consent2share.domain.consent.Consent)
     */
    @Override
    public ConsentDto map(Consent consent) {
        ConsentDto consentDto = new ConsentDto();

        consentDto.setConsentReferenceid(consent.getConsentReferenceId());

        PatientDto patientDto = modelMapper.map(consent.getPatient(),
                PatientDto.class);
        consentDto.setPatientDto(patientDto);

        if (consent.getLegalRepresentative() != null) {
            PatientDto legalRepresentativeDto = modelMapper.map(
                    consent.getLegalRepresentative(), PatientDto.class);
            consentDto.setLegalRepresentative(legalRepresentativeDto);
        }

        Set<IndividualProviderDto> indiprovidersPermittedToDisclose = new HashSet<IndividualProviderDto>();

        for (ConsentIndividualProviderPermittedToDisclose pptd : consent
                .getProvidersPermittedToDisclose()) {

            IndividualProviderDto iptd = modelMapper.map(
                    pptd.getIndividualProvider(), IndividualProviderDto.class);
            indiprovidersPermittedToDisclose.add(iptd);
        }
        consentDto
                .setProvidersPermittedToDisclose(indiprovidersPermittedToDisclose);

        Set<IndividualProviderDto> indiprovidersDisclosureIsMadeTo = new HashSet<IndividualProviderDto>();

        for (ConsentIndividualProviderDisclosureIsMadeTo pdmt : consent
                .getProvidersDisclosureIsMadeTo()) {

            IndividualProviderDto iptd2 = modelMapper.map(
                    pdmt.getIndividualProvider(), IndividualProviderDto.class);
            indiprovidersDisclosureIsMadeTo.add(iptd2);
        }
        consentDto
                .setProvidersDisclosureIsMadeTo(indiprovidersDisclosureIsMadeTo);

        Set<OrganizationalProviderDto> orgaprovidersPermittedToDisclose = new HashSet<OrganizationalProviderDto>();

        for (ConsentOrganizationalProviderPermittedToDisclose coptd : consent
                .getOrganizationalProvidersPermittedToDisclose()) {

            OrganizationalProviderDto optd = modelMapper.map(
                    coptd.getOrganizationalProvider(),
                    OrganizationalProviderDto.class);
            orgaprovidersPermittedToDisclose.add(optd);
        }
        consentDto
                .setOrganizationalProvidersPermittedToDisclose(orgaprovidersPermittedToDisclose);

        Set<OrganizationalProviderDto> orgaprovidersDisclosureIsMadeTo = new HashSet<OrganizationalProviderDto>();

        for (ConsentOrganizationalProviderDisclosureIsMadeTo opdmt : consent
                .getOrganizationalProvidersDisclosureIsMadeTo()) {

            OrganizationalProviderDto optd2 = modelMapper.map(
                    opdmt.getOrganizationalProvider(),
                    OrganizationalProviderDto.class);
            orgaprovidersDisclosureIsMadeTo.add(optd2);
        }
        consentDto
                .setOrganizationalProvidersDisclosureIsMadeTo(orgaprovidersDisclosureIsMadeTo);

        Set<TypeCodesDto> consentDoNotShareClinicalDocumentTypeCode = new HashSet<TypeCodesDto>();
        for (ConsentDoNotShareClinicalDocumentTypeCode item : consent
                .getDoNotShareClinicalDocumentTypeCodes()) {
            TypeCodesDto tcd = new TypeCodesDto();
            tcd.setDisplayName(item.getClinicalDocumentTypeCode()
                    .getDisplayName());
            tcd.setCode(item.getClinicalDocumentTypeCode().getCode());
            tcd.setCodeSystem(item.getClinicalDocumentTypeCode()
                    .getCodeSystem());
            tcd.setCodeSystemName(item.getClinicalDocumentTypeCode()
                    .getCodeSystemName());
            consentDoNotShareClinicalDocumentTypeCode.add(tcd);
        }
        consentDto
                .setDoNotShareClinicalDocumentTypeCodes(consentDoNotShareClinicalDocumentTypeCode);

        Set<TypeCodesDto> consentDoNotShareClinicalDocumentSectionTypeCode = new HashSet<TypeCodesDto>();
        for (ConsentDoNotShareClinicalDocumentSectionTypeCode item : consent
                .getDoNotShareClinicalDocumentSectionTypeCodes()) {
            TypeCodesDto tcd1 = new TypeCodesDto();
            tcd1.setDisplayName(item.getMedicalSection().getName());
            tcd1.setCode(item.getMedicalSection().getCode());
            tcd1.setCodeSystem(null);
            tcd1.setCodeSystemName(null);
            consentDoNotShareClinicalDocumentSectionTypeCode.add(tcd1);

        }
        consentDto
                .setDoNotShareClinicalDocumentSectionTypeCodes(consentDoNotShareClinicalDocumentSectionTypeCode);

        Set<TypeCodesDto> consentDoNotShareSensitivityPolicyCode = new HashSet<TypeCodesDto>();
        for (ConsentDoNotShareSensitivityPolicyCode item : consent
                .getDoNotShareSensitivityPolicyCodes()) {

            TypeCodesDto tcd2 = new TypeCodesDto();
            tcd2.setDisplayName(item.getValueSetCategory().getName());
            tcd2.setCode(item.getValueSetCategory().getCode());
            tcd2.setCodeSystem(null);
            tcd2.setCodeSystemName(null);
            consentDoNotShareSensitivityPolicyCode.add(tcd2);

        }
        consentDto
                .setDoNotShareSensitivityPolicyCodes(consentDoNotShareSensitivityPolicyCode);

        Set<TypeCodesDto> consentShareForPurposeOfUseCode = new HashSet<TypeCodesDto>();
        for (ConsentShareForPurposeOfUseCode item : consent
                .getShareForPurposeOfUseCodes()) {

            TypeCodesDto tcd3 = new TypeCodesDto();
            tcd3.setDisplayName(item.getPurposeOfUseCode().getDisplayName());
            tcd3.setCode(item.getPurposeOfUseCode().getCode());
            tcd3.setCodeSystem(item.getPurposeOfUseCode().getCodeSystem());
            tcd3.setCodeSystemName(item.getPurposeOfUseCode()
                    .getCodeSystemName());
            consentShareForPurposeOfUseCode.add(tcd3);

        }
        consentDto
                .setShareForPurposeOfUseCodes(consentShareForPurposeOfUseCode);

        Set<TypeCodesDto> consentDoNotShareClinicalConceptCodes = new HashSet<TypeCodesDto>();
        for (ClinicalConceptCode item : consent
                .getDoNotShareClinicalConceptCodes()) {
            TypeCodesDto tcd4 = new TypeCodesDto();
            tcd4.setDisplayName(item.getDisplayName());
            tcd4.setCode(item.getCode());
            tcd4.setCodeSystem(item.getCodeSystem());
            tcd4.setCodeSystemName(item.getCodeSystemName());
            consentDoNotShareClinicalConceptCodes.add(tcd4);
        }
        consentDto
                .setDoNotShareClinicalConceptCodes(consentDoNotShareClinicalConceptCodes);

        consentDto.setConsentStart(consent.getStartDate());
        consentDto.setConsentEnd(consent.getEndDate());
        consentDto.setSignedDate(consent.getSignedDate());
        consentDto.setVersion(consent.getVersion());
        consentDto.setRevocationDate(consent.getRevocationDate());

        return consentDto;
    }

    /**
     * Gets the model mapper.
     *
     * @return the model mapper
     */
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * Sets the model mapper.
     *
     * @param modelMapper the new model mapper
     */
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
