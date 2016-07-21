/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.mhc.pcm.service.consent;

import gov.samhsa.mhc.common.consentgen.ConsentBuilder;
import gov.samhsa.mhc.common.consentgen.ConsentGenException;
import gov.samhsa.mhc.common.document.accessor.DocumentAccessor;
import gov.samhsa.mhc.common.document.converter.DocumentXmlConverter;
import gov.samhsa.mhc.common.document.transformer.XmlTransformer;
import gov.samhsa.mhc.common.util.UniqueValueGeneratorException;
import gov.samhsa.mhc.pcm.domain.consent.*;
import gov.samhsa.mhc.pcm.domain.patient.Patient;
import gov.samhsa.mhc.pcm.domain.patient.PatientRepository;
import gov.samhsa.mhc.pcm.domain.provider.*;
import gov.samhsa.mhc.pcm.domain.reference.*;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategory;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.mhc.pcm.infrastructure.AbstractConsentRevokationPdfGenerator;
import gov.samhsa.mhc.pcm.infrastructure.PhrService;
import gov.samhsa.mhc.pcm.infrastructure.dto.PatientDto;
import gov.samhsa.mhc.pcm.service.consentexport.ConsentExportService;
import gov.samhsa.mhc.pcm.service.dto.*;
import gov.samhsa.mhc.pcm.service.exception.AttestedConsentException;
import gov.samhsa.mhc.pcm.service.exception.AttestedConsentRevocationException;
import gov.samhsa.mhc.pcm.service.exception.XacmlNotFoundException;
import gov.samhsa.mhc.pcm.service.patient.PatientService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * The Class ConsentServiceImpl.
 */
@Transactional
@Service
public class ConsentServiceImpl implements ConsentService {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The c2s domain id.
     */
    @Value("${mhc.pcm.config.pid.domain.id}")
    private String pidDomainId;

    /**
     * The c2s domain type.
     */
    @Value("${mhc.pcm.config.pid.domain.type}")
    private String pidDomainType;

    /**
     * The consent repository.
     */
    @Autowired
    private ConsentRepository consentRepository;

    /**
     * The consent pdf generator.
     */
    @Autowired
    private ConsentPdfGenerator consentPdfGenerator;

    /**
     * The patient repository.
     */
    @Autowired
    private PatientRepository patientRepository;

    /**
     * The individual provider repository.
     */
    @Autowired
    private IndividualProviderRepository individualProviderRepository;

    /**
     * The organizational provider repository.
     */
    @Autowired
    private OrganizationalProviderRepository organizationalProviderRepository;

    /**
     * The clinical document type code repository.
     */
    @Autowired
    private ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

    /**
     * The value set category repository.
     */
    @Autowired
    private ValueSetCategoryRepository valueSetCategoryRepository;

    /**
     * The purpose of use code repository.
     */
    @Autowired
    private PurposeOfUseCodeRepository purposeOfUseCodeRepository;

    /**
     * The consent revokation pdf generator.
     */
    @Autowired
    private AbstractConsentRevokationPdfGenerator consentRevokationPdfGenerator;

    /**
     * The consent export service.
     */
    @Autowired
    private ConsentExportService consentExportService;

    /**
     * The consent builder.
     */
    @Autowired
    private ConsentBuilder consentBuilder;

    /**
     * The consent factory.
     */
    @Autowired
    private ConsentFactory consentFactory;

    /**
     * The consent check service.
     */
    @Autowired
    private ConsentCheckService consentCheckService;

    /**
     * The consent assertions.
     */
    @Autowired
    private Set<ConsentAssertion> consentAssertions;

    /**
     * The policy id service.
     */
    @Autowired
    private PolicyIdService policyIdService;

    /**
     * The xml transformer.
     */
    @Autowired
    private XmlTransformer xmlTransformer;

    /**
     * The document xml converter.
     */
    @Autowired
    private DocumentXmlConverter documentXmlConverter;

    /**
     * The document accessor.
     */
    @Autowired
    private DocumentAccessor documentAccessor;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private PhrService phrService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ConsentTermsVersionsService consentTermsVersionsService;

    @Autowired
    private ConsentRevocationTermsVersionsService consentRevocationTermsVersionsService;

    /**
     * The model mapper.
     */
    @Autowired
    private ModelMapper modelMapper;
    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.consent.ConsentService#
     * addUnsignedConsentRevokationPdf(java.lang.Long, java.lang.String)
     */
    @Override
    public void addUnsignedConsentRevokationPdf(Long consentId,
                                                String revokationType) {
        final Consent consent = consentRepository.findOne(consentId);
        if (revokationType.equals("EMERGENCY ONLY")) {
            consent.setConsentRevokationType("EMERGENCY ONLY");
        } else if (revokationType.equals("NO NEVER")) {
            consent.setConsentRevokationType("NO NEVER");
        }

        Patient patient = consent.getPatient();

        consent.setUnAttestedPdfConsentRevoke(consentRevokationPdfGenerator.generateUnattestedConsentRevokationPdf(consent, patient));
        consentRepository.save(consent);
    }

    /**
     * Are there duplicates.
     *
     * @param set1 the set1
     * @param set2 the set2
     * @return true, if successful
     */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public boolean areThereDuplicatesInTwoSets(Set set1, Set set2) {
        final Set all = new HashSet();
        all.addAll(set1);
        all.addAll(set2);
        if (all.size() == set1.size() + set2.size()) {
            return false;
        }
        return true;
    }

    /**
     * Consent list to consent list dtos converter.
     *
     * @param consents the consents
     * @return the list
     */
    public List<ConsentListDto> consentListToConsentListDtosConverter(
            List<Consent> consents) {
        final List<ConsentListDto> consentListDtos = makeConsentListDtos();
        for (final Consent consent : consents) {
            final ConsentListDto consentListDto = new ConsentListDto();
            // Get fields
            final Set<String> isMadeToName = new HashSet<String>();
            for (final ConsentIndividualProviderDisclosureIsMadeTo item : consent
                    .getProvidersDisclosureIsMadeTo()) {
                final String name = item.getIndividualProvider().getLastName()
                        + ", " + item.getIndividualProvider().getFirstName();
                isMadeToName.add(name);
            }
            final Set<String> isMadeToOrgName = new HashSet<String>();
            for (final ConsentOrganizationalProviderDisclosureIsMadeTo item : consent
                    .getOrganizationalProvidersDisclosureIsMadeTo()) {
                isMadeToOrgName.add(item.getOrganizationalProvider()
                        .getOrgName());
            }

            final Set<String> toDiscloseName = new HashSet<String>();
            for (final ConsentIndividualProviderPermittedToDisclose item : consent
                    .getProvidersPermittedToDisclose()) {
                final String name = item.getIndividualProvider().getLastName()
                        + ", " + item.getIndividualProvider().getFirstName();
                toDiscloseName.add(name);
            }

            final Set<String> toDiscloseOrgName = new HashSet<String>();
            for (final ConsentOrganizationalProviderPermittedToDisclose item : consent
                    .getOrganizationalProvidersPermittedToDisclose()) {
                toDiscloseOrgName.add(item.getOrganizationalProvider()
                        .getOrgName());
            }

            final Set<String> consentDoNotShareClinicalDocumentTypeCode = new HashSet<String>();
            for (final ConsentDoNotShareClinicalDocumentTypeCode item : consent
                    .getDoNotShareClinicalDocumentTypeCodes()) {
                consentDoNotShareClinicalDocumentTypeCode.add(item
                        .getClinicalDocumentTypeCode().getDisplayName());
            }

            final Set<String> consentDoNotShareSensitivityPolicyCode = new HashSet<String>();
            for (final ConsentDoNotShareSensitivityPolicyCode item : consent
                    .getDoNotShareSensitivityPolicyCodes()) {
                consentDoNotShareSensitivityPolicyCode.add(item
                        .getValueSetCategory().getName());
            }

            final Set<String> consentShareForPurposeOfUseCode = new HashSet<String>();
            for (final ConsentShareForPurposeOfUseCode item : consent
                    .getShareForPurposeOfUseCodes()) {
                consentShareForPurposeOfUseCode.add(item.getPurposeOfUseCode().getDisplayName());
            }

            final Set<PurposeOfUseCode> shareForPurposeOfUse = new HashSet<PurposeOfUseCode>();
            for (final ConsentShareForPurposeOfUseCode item : consent
                    .getShareForPurposeOfUseCodes()) {

                String code = item.getPurposeOfUseCode().getCode();
                shareForPurposeOfUse.add(purposeOfUseCodeRepository.findByCode(code));
            }

            final Set<String> consentDoNotShareClinicalConceptCodes = new HashSet<String>();
            for (final ClinicalConceptCode item : consent
                    .getDoNotShareClinicalConceptCodes()) {
                consentDoNotShareClinicalConceptCodes
                        .add(item.getDisplayName());
            }

            consentListDto.setConsentStage(consent.getStatus());

            // Set fields
            isMadeToName.addAll(isMadeToOrgName);
            toDiscloseName.addAll(toDiscloseOrgName);
            consentListDto
                    .setDoNotShareClinicalConceptCodes(consentDoNotShareClinicalConceptCodes);
            consentListDto.setId(Long.toString(consent.getId()));
            consentListDto.setIsMadeToName(isMadeToName);
            consentListDto.setToDiscloseName(toDiscloseName);
            consentListDto
                    .setDoNotShareClinicalDocumentTypeCodes(consentDoNotShareClinicalDocumentTypeCode);
            consentListDto
                    .setShareForPurposeOfUseCodes(consentShareForPurposeOfUseCode);
            consentListDto
                    .setShareForPurposeOfUse(shareForPurposeOfUse);
            consentListDto
                    .setDoNotShareSensitivityPolicyCodes(consentDoNotShareSensitivityPolicyCode);

            consentListDto.setConsentStart(consent.getStartDate());
            consentListDto.setConsentEnd(consent.getEndDate());

            // Merge all Dtos
            consentListDtos.add(consentListDto);
        }
        return consentListDtos;
    }

    /**
     * Count all consents.
     *
     * @return the long
     */
    @Override
    public long countAllConsents() {
        return consentRepository.count();
    }

    /**
     * Delete consent.
     *
     * @param consentId the consent id
     * @return true, if successful
     */
    @Override
    public boolean deleteConsent(Long consentId) {
        Consent consent = null;

        try {
            consent = findConsent(consentId);
        } catch (final IllegalArgumentException e) {
            logger.warn("Attempted to call findConsent(consentId) with null or invalid consentId from deleteConsent(Long consentId) method in ConsentService.");
            logger.warn("The exception stack trace is: " + e);
            return false;
        }

        if (!consent.getStatus().equals(ConsentStatus.CONSENT_SAVED)) {
            return false;
        }

        try {
            consentRepository.delete(consent);
        } catch (final IllegalArgumentException e) {
            logger.warn("Attempted to call consentRepository.delete(consent) with null or invalid consent from deleteConsent(Long consentId) method in ConsentService.");
            logger.warn("The exception stack trace is: " + e);
            return false;
        }

        return true;
    }

    /**
     * Find all consents.
     *
     * @return the list
     */
    @Override
    @Transactional(readOnly = true)
    public List<Consent> findAllConsents() {
        return consentRepository.findAll();
    }

    /**
     * Find all consents dto by patient.
     *
     * @param patientId the patient id
     * @return the list
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsentListDto> findAllConsentsDtoByPatient(Long patientId) {
        final Patient patient = patientRepository.findOne(patientId);
        final List<Consent> consents = consentRepository.findByPatient(patient);
        List<ConsentListDto> consentListDtos = makeConsentListDtos();
        if (consents != null) {
            consentListDtos = consentListToConsentListDtosConverter(consents);
        }
        return consentListDtos;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> findAllConsentsDtoByPatientAndPage(Long patientId, String pageNumber) {
        final Patient patient = patientRepository.findOne(patientId);

        PageRequest page = new PageRequest(Integer.parseInt(pageNumber), 5,
                Direction.DESC, "startDate");


        final Page<Consent> pages = consentRepository.findByPatient(patient, page);
        List<ConsentListDto> consentListDtos = makeConsentListDtos();
        if (pages != null) {
            consentListDtos = consentListToConsentListDtosConverter(pages.getContent());
        }

        Map<String, Object> pageResultsMap = new HashMap<String, Object>();
        pageResultsMap.put("results", consentListDtos);
        pageResultsMap.put("totalItems", pages.getTotalElements());
        pageResultsMap.put("totalPages", pages.getTotalPages());
        pageResultsMap.put("itemsPerPage", pages.getSize());
        pageResultsMap.put("currentPage", pages.getNumber());

        return pageResultsMap;
    }

    @Override
    public ConsentAttestationDto getConsentAttestationDto( String userName,Long consentId) {

        Consent consent = null;
        ConsentAttestationDto consentAttestationDto = null;
        Optional<Consent> findConsentOptional = patientRepository.findByUsername(userName).getConsents().stream()
                .filter(c -> consentId.equals(c.getId()))
                .findAny();
        if (findConsentOptional.isPresent()) {
            consent = findConsentOptional.get();

            consentAttestationDto = new ConsentAttestationDto();
            consentAttestationDto.setConsentReferenceNumber(consent.getConsentReferenceId());

            final PatientDto patientProfile = phrService.getPatientProfile();

            if(consentAttestationDto != null){
                consentAttestationDto.setPatientDateOfBirth(patientProfile.getBirthDate());
                consentAttestationDto.setFirstName(patientProfile.getFirstName());
                consentAttestationDto.setLastName(patientProfile.getLastName());
            }

            final Set<OrganizationalProvider> consentOrganizationalProviderDisclosureIsMadeTo = new HashSet<OrganizationalProvider>();
            for (final ConsentOrganizationalProviderDisclosureIsMadeTo item : consent.getOrganizationalProvidersDisclosureIsMadeTo()) {
                consentOrganizationalProviderDisclosureIsMadeTo.add(item.getOrganizationalProvider());
            }

            final Set<IndividualProvider> consentIndividualProviderDisclosureIsMadeTo = new HashSet<IndividualProvider>();
            for (final ConsentIndividualProviderDisclosureIsMadeTo item : consent.getProvidersDisclosureIsMadeTo()) {
                consentIndividualProviderDisclosureIsMadeTo.add(item.getIndividualProvider());
            }


            final Set<OrganizationalProvider> consentOrganizationalProviderPermittedToDisclose = new HashSet<OrganizationalProvider>();
            for (final ConsentOrganizationalProviderPermittedToDisclose item : consent.getOrganizationalProvidersPermittedToDisclose()) {
                consentOrganizationalProviderPermittedToDisclose.add(item.getOrganizationalProvider());
            }

            final Set<IndividualProvider> consentIndividualProviderPermittedToDisclose = new HashSet<IndividualProvider>();
            for (final ConsentIndividualProviderPermittedToDisclose item : consent.getProvidersPermittedToDisclose()) {
                consentIndividualProviderPermittedToDisclose.add(item.getIndividualProvider());
            }

            final Set<String> shareSensitivityPolicyDisplayName = new HashSet<String>();
            List<ValueSetCategory> valueSetCategoryList = valueSetCategoryRepository
                    .findAll();
            //All possible VSC
            for (ValueSetCategory valueSetCategory : valueSetCategoryList) {
                String valueSetName = valueSetCategory.getName();
                shareSensitivityPolicyDisplayName.add(valueSetName);
            }
            for (final ConsentDoNotShareSensitivityPolicyCode item : consent.getDoNotShareSensitivityPolicyCodes()) {
                shareSensitivityPolicyDisplayName.remove(item.getValueSetCategory().getName());
            }

            // Set fields
            // isMadeToName.addAll(isMadeToOrgName);
            // toDiscloseName.addAll(toDiscloseOrgName);
            consentAttestationDto.setConsentId(String.valueOf(consent.getId()));
            consentAttestationDto.setUsername(consent.getPatient().getUsername());

            //Set start and end date
            consentAttestationDto.setConsentStart(consent.getStartDate());
            consentAttestationDto.setConsentEnd(consent.getEndDate());

            //Authorize to
            consentAttestationDto.setOrgProvidersPermittedToDisclosure(consentOrganizationalProviderPermittedToDisclose);
            consentAttestationDto.setIndProvidersPermittedToDisclosure(consentIndividualProviderPermittedToDisclose);

            //Disclose to
            consentAttestationDto.setOrgProvidersDisclosureIsMadeTo(consentOrganizationalProviderDisclosureIsMadeTo);
            consentAttestationDto.setIndProvidersDisclosureIsMadeTo(consentIndividualProviderDisclosureIsMadeTo);

            consentAttestationDto.setDoNotShareSensitivityPolicyDisplayNames(shareSensitivityPolicyDisplayName);

            final Set<PurposeOfUseCode> purposeOfUses= new HashSet<PurposeOfUseCode>();
            for (final ConsentShareForPurposeOfUseCode purposeOfUseCode : consent.getShareForPurposeOfUseCodes()) {
                purposeOfUses.add(purposeOfUseCode.getPurposeOfUseCode());
            }
            consentAttestationDto.setPurposeOfUseCodes(purposeOfUses);

            consentAttestationDto.setConsentTermsVersions(consentTermsVersionsService.getEnabledConsentTermsVersion());
        }

        return consentAttestationDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ConsentRevocationAttestationDto getConsentRevocationAttestationDto(String userName, Long consentId) {
        Consent consent = null;
        ConsentRevocationAttestationDto consentRevocationAttestationDto = null;

        Optional<Consent> findConsentOptional = patientRepository.findByUsername(userName).getConsents().stream()
                .filter(c -> consentId.equals(c.getId()))
                .findAny();

        if(findConsentOptional.isPresent()){
            consent = findConsentOptional.get();

            consentRevocationAttestationDto = new ConsentRevocationAttestationDto();
            consentRevocationAttestationDto.setConsentReferenceId(consent.getConsentReferenceId());

            final PatientDto patientProfile = phrService.getPatientProfile();

            if(patientProfile != null){
                consentRevocationAttestationDto.setAttesterLastName(patientProfile.getLastName());
                consentRevocationAttestationDto.setAttesterFirstName(patientProfile.getFirstName());
                consentRevocationAttestationDto.setAttesterMiddleName(null);
                consentRevocationAttestationDto.setPatientLastName(patientProfile.getLastName());
                consentRevocationAttestationDto.setPatientFirstName(patientProfile.getFirstName());
                consentRevocationAttestationDto.setAttesterEmail(patientProfile.getEmail());
                consentRevocationAttestationDto.setPatientDateOfBirth(patientProfile.getBirthDate());
            }

            consentRevocationAttestationDto.setConsentRevokeTermsAccepted(false);

            String currentConsentRevokeTermsText = consentRevocationTermsVersionsService.findByLatestEnabledVersion().getConsentRevokeTermsText();
            consentRevocationAttestationDto.setConsentRevokeTermsText(currentConsentRevokeTermsText);
        }

        return consentRevocationAttestationDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.consent.ConsentService#
     * findAllConsentsDtoByUserName(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsentListDto> findAllConsentsDtoByUserName(String userName) {
        final Patient patient = patientRepository.findByUsername(userName);
        final List<Consent> consents = consentRepository.findByPatient(patient);
        List<ConsentListDto> consentListDtos = makeConsentListDtos();
        if (consents != null) {
            consentListDtos = consentListToConsentListDtosConverter(consents);
        }
        return consentListDtos;
    }

    /**
     * Find consent.
     *
     * @param id the id
     * @return the consent
     */
    @Override
    @Transactional(readOnly = true)
    public Consent findConsent(Long id) {
        final Consent consent = consentRepository.findOne(id);
        return consent;
    }

    /**
     * Returns consentDto based on consent id.
     *
     * @param consentId the consent id
     * @return ConsentDto
     */
    @Override
    @Transactional(readOnly = true)
    public ConsentDto findConsentById(String username, Long consentId) {
        Consent consent = null;
        ConsentDto consentDto = null;
        Optional<Consent> findConsentOptional = patientRepository.findByUsername(username).getConsents().stream()
                .filter(c -> consentId.equals(c.getId()))
                .findAny();
        if (findConsentOptional.isPresent()) {
            consent = findConsentOptional.get();

            consentDto = new ConsentDto();
            // Get fields
            final Set<String> isMadeToName = new HashSet<String>();
            final Set<String> isMadeToNpi = new HashSet<String>();
            for (final ConsentIndividualProviderDisclosureIsMadeTo item : consent
                    .getProvidersDisclosureIsMadeTo()) {
                final String name = item.getIndividualProvider().getLastName()
                        + ", " + item.getIndividualProvider().getFirstName();
                isMadeToName.add(name);
                isMadeToNpi.add(item.getIndividualProvider().getNpi());
            }

            final Set<String> isMadeToOrgName = new HashSet<String>();
            final Set<String> isMadeToOrgNpi = new HashSet<String>();
            for (final ConsentOrganizationalProviderDisclosureIsMadeTo item : consent
                    .getOrganizationalProvidersDisclosureIsMadeTo()) {
                isMadeToOrgName.add(item.getOrganizationalProvider()
                        .getOrgName());
                isMadeToOrgNpi.add(item.getOrganizationalProvider().getNpi());
            }

            final Set<String> toDiscloseName = new HashSet<String>();
            final Set<String> toDiscloseNpi = new HashSet<String>();
            for (final ConsentIndividualProviderPermittedToDisclose item : consent
                    .getProvidersPermittedToDisclose()) {
                final String name = item.getIndividualProvider().getLastName()
                        + ", " + item.getIndividualProvider().getFirstName();
                toDiscloseName.add(name);
                toDiscloseNpi.add(item.getIndividualProvider().getNpi());
            }

            final Set<String> toDiscloseOrgName = new HashSet<String>();
            final Set<String> toDiscloseOrgNpi = new HashSet<String>();
            for (final ConsentOrganizationalProviderPermittedToDisclose item : consent
                    .getOrganizationalProvidersPermittedToDisclose()) {
                toDiscloseOrgName.add(item.getOrganizationalProvider()
                        .getOrgName());
                toDiscloseOrgNpi.add(item.getOrganizationalProvider().getNpi());
            }

            final Set<String> consentDoNotShareClinicalDocumentTypeCode = new HashSet<String>();
            for (final ConsentDoNotShareClinicalDocumentTypeCode item : consent
                    .getDoNotShareClinicalDocumentTypeCodes()) {
                consentDoNotShareClinicalDocumentTypeCode.add(item
                        .getClinicalDocumentTypeCode().getCode());
            }

            final Set<String> consentDoNotShareSensitivityPolicyCode = new HashSet<String>();
            for (final ConsentDoNotShareSensitivityPolicyCode item : consent
                    .getDoNotShareSensitivityPolicyCodes()) {
                consentDoNotShareSensitivityPolicyCode.add(item
                        .getValueSetCategory().getCode());
            }

            final Set<String> consentShareForPurposeOfUseCode = new HashSet<String>();
            for (final ConsentShareForPurposeOfUseCode item : consent
                    .getShareForPurposeOfUseCodes()) {
                consentShareForPurposeOfUseCode.add(item.getPurposeOfUseCode()
                        .getCode());
            }

            final Set<SpecificMedicalInfoDto> consentDoNotShareClinicalConceptCodes = new HashSet<SpecificMedicalInfoDto>();
            for (final ClinicalConceptCode item : consent
                    .getDoNotShareClinicalConceptCodes()) {
                final SpecificMedicalInfoDto specificMedicalInfoDto = new SpecificMedicalInfoDto();
                specificMedicalInfoDto.setCode(item.getCode());
                specificMedicalInfoDto.setCodeSystem(item.getCodeSystem());
                specificMedicalInfoDto.setDisplayName(item.getDisplayName());
                consentDoNotShareClinicalConceptCodes
                        .add(specificMedicalInfoDto);
            }

            // Set fields
            // isMadeToName.addAll(isMadeToOrgName);
            // toDiscloseName.addAll(toDiscloseOrgName);
            consentDto
                    .setDoNotShareClinicalConceptCodes(consentDoNotShareClinicalConceptCodes);
            consentDto.setId(String.valueOf(consent.getId()));
            consentDto.setUsername(consent.getPatient().getUsername());

            //
            // Converting timestamp to Date to resolve
            // rendering the values in IE
            //
            final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String today = "";
            try {
                today = formatter.format(consent.getStartDate());
                consentDto.setConsentStart(formatter.parse(today));
            } catch (final ParseException e) {
                consentDto.setConsentStart(consent.getStartDate());
                e.printStackTrace();
            }
            try {
                today = formatter.format(consent.getEndDate());
                consentDto.setConsentEnd(formatter.parse(today));
            } catch (final ParseException e) {
                consentDto.setConsentEnd(consent.getEndDate());
                e.printStackTrace();
            }

            // TODO: Cleanup: combine name and npi into one object
            // populate consent dto with selected options
            consentDto
                    .setOrganizationalProvidersDisclosureIsMadeTo(isMadeToOrgName);
            consentDto
                    .setOrganizationalProvidersDisclosureIsMadeToNpi(isMadeToOrgNpi);

            consentDto
                    .setOrganizationalProvidersPermittedToDisclose(toDiscloseOrgName);
            consentDto
                    .setOrganizationalProvidersPermittedToDiscloseNpi(toDiscloseOrgNpi);

            consentDto.setProvidersDisclosureIsMadeTo(isMadeToName);
            consentDto.setProvidersDisclosureIsMadeToNpi(isMadeToNpi);

            consentDto.setProvidersPermittedToDisclose(toDiscloseName);
            consentDto.setProvidersPermittedToDiscloseNpi(toDiscloseNpi);
            consentDto
                    .setDoNotShareClinicalDocumentTypeCodes(consentDoNotShareClinicalDocumentTypeCode);
            consentDto
                    .setShareForPurposeOfUseCodes(consentShareForPurposeOfUseCode);
            consentDto
                    .setDoNotShareSensitivityPolicyCodes(consentDoNotShareSensitivityPolicyCode);

            final HashMap<String, String> purposeOfUseMap = new HashMap<String, String>();
            for (final ConsentShareForPurposeOfUseCode purposeOfUseCode : consent
                    .getShareForPurposeOfUseCodes()) {
                purposeOfUseMap.put(purposeOfUseCode.getPurposeOfUseCode()
                        .getCode(), purposeOfUseCode.getPurposeOfUseCode()
                        .getDisplayName());
            }
            consentDto.setPurposeOfUseCodesAndValues(purposeOfUseMap);

        }
        return consentDto;
    }

    /**
     * Returns consentDto based on consent id.
     *
     * @param consentId the consent id
     * @return ConsentDto
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> findObligationsConsentById(String username, Long consentId) {
        Consent consent = null;
        final Set<String> obligationCodes = new HashSet<String>();
        Optional<Consent> findConsentOptional = patientRepository.findByUsername(username).getConsents().stream()
                .filter(c -> consentId.equals(c.getId()))
                .findAny();
        if (findConsentOptional.isPresent()) {
            consent = findConsentOptional.get();

            for (final ConsentDoNotShareClinicalDocumentTypeCode item : consent
                    .getDoNotShareClinicalDocumentTypeCodes()) {
                obligationCodes.add(item
                        .getClinicalDocumentTypeCode().getCode());
            }

            for (final ConsentDoNotShareSensitivityPolicyCode item : consent
                    .getDoNotShareSensitivityPolicyCodes()) {
                obligationCodes.add(item
                        .getValueSetCategory().getCode());
            }

            final Set<String> consentDoNotShareClinicalConceptCodes = new HashSet<String>();
            for (final ClinicalConceptCode item : consent
                    .getDoNotShareClinicalConceptCodes()) {
                final SpecificMedicalInfoDto specificMedicalInfoDto = new SpecificMedicalInfoDto();
                consentDoNotShareClinicalConceptCodes
                        .add(item.getCode());
            }

        }
        return new ArrayList<String>(obligationCodes);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.consent.ConsentService#findConsentContentDto
     * (java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public AbstractPdfDto findConsentContentDto(Long consentId) {
        final Consent consent = findConsent(consentId);
        AbstractPdfDto consentPdfDto;

        String consentStatus = consent.getStatus();
        switch(consentStatus){
            case ConsentStatus.CONSENT_SAVED:
            case ConsentStatus.CONSENT_SIGNED:
                consentPdfDto = findConsentPdfDto(consent.getId());
                break;
            case ConsentStatus.REVOCATION_REVOKED:
                consentPdfDto = findConsentRevokationPdfDto(consent.getId());
                break;
            default:
                throw new IllegalStateException("The status field of this consent object has an invalid value.");
        }

        return consentPdfDto;

    }

    /**
     * Find consent entries.
     *
     * @param firstResult the first result
     * @param maxResults  the max results
     * @return the list
     */
    @Override
    @Transactional(readOnly = true)
    public List<Consent> findConsentEntries(int firstResult, int maxResults) {
        return consentRepository.findAll(
                new PageRequest(firstResult
                        / maxResults, maxResults)).getContent();
    }

    /**
     * Find consent pdf dto.
     *
     * @param consentId the consent id
     * @return the consent pdf dto
     */
    @Override
    @Transactional(readOnly = true)
    public ConsentPdfDto findConsentPdfDto(Long consentId) {
        final Consent consent = consentRepository.findOne(consentId);
        final ConsentPdfDto consentPdfDto = makeConsentPdfDto();
        if (consent.getStatus().equals(ConsentStatus.CONSENT_SIGNED) || consent.getStatus().equals(ConsentStatus.REVOCATION_REVOKED)) {
                consentPdfDto.setContent(consent.getAttestedConsent().getAttestedPdfConsent());
        } else {
            consentPdfDto.setContent(consent.getUnAttestedPdfConsent());
        }

        consentPdfDto.setFilename(consent.getPatient().getFirstName() + "_"
                + consent.getPatient().getLastName() + "_Consent"
                + consent.getId() + ".pdf");
        consentPdfDto.setConsentName(consent.getName());
        consentPdfDto.setId(consentId);
        return consentPdfDto;
    }

    @Override
    public byte[] getAttestedConsentPdf(Long consentId) throws ConsentGenException{
        final Consent consent = consentRepository.findOne(consentId);
        PatientDto patientDto = phrService.getPatientProfile();
        final ConsentPdfDto consentPdfDto = makeConsentPdfDto();
        byte[] attestedConsentPdf = null;

        if (consent != null && patientDto != null && consent.getAttestedConsent() != null && consent.getAttestedConsent().getAttestedPdfConsent() != null) {
            attestedConsentPdf =  consent.getAttestedConsent().getAttestedPdfConsent();
        }else{
            logger.error("Error in getting attested consent pdf.");
            throw new AttestedConsentException("Error in getting attested consent pdf.");
        }
        return attestedConsentPdf;
    }

    @Override
    public byte[] getAttestedConsentRevokedPdf(Long consentId) throws ConsentGenException{
        final Consent consent = consentRepository.findOne(consentId);
        PatientDto patientDto = phrService.getPatientProfile();
        final ConsentPdfDto consentPdfDto = makeConsentPdfDto();
        byte[] attestedConsentRevocationPdf = null;

        if (consent != null && patientDto != null && consent.getAttestedConsent() != null && consent.getAttestedConsent().getAttestedPdfConsent() != null
                && consent.getStatus().equals(ConsentStatus.REVOCATION_REVOKED)) {
            attestedConsentRevocationPdf =  consent.getAttestedConsentRevocation().getAttestedPdfConsentRevoke();
        }else{
            logger.error("Error in getting attested consent revocation pdf");
            throw new AttestedConsentRevocationException ("Error in getting attested consent revocation pdf");
        }
        return attestedConsentRevocationPdf;
    }


    @Override
    public void attestConsent(AttestationDto attestationDto) {
        Long consentId = attestationDto.getConsentId();
        String attesterIdAddress = attestationDto.getAttesterIpAddress();

        final Consent consent = consentRepository.findOne(consentId);
        //Updating the patient data with data from phr api
        PatientDto patientDto = phrService.getPatientProfile();

        if (consent != null && consent.getAttestedConsent() == null && patientDto!= null && consentId != null && attesterIdAddress != null) {
            patientService.updatePatientFromPHR(patientDto);
            Patient patient = patientRepository.findByUsername(patientDto.getEmail());

            // TODO: Refactor this code block so that the attester fields are populated with the data fro the currently logged in user from UAA instead of the patient from PHR/PCM
            AttestedConsent attestedConsent =  new AttestedConsent();
            ConsentTermsVersions consentTerms = consentTermsVersionsService.getEnabledConsentTermsVersion();
            attestedConsent.setConsentTermsVersions(consentTerms);
            attestedConsent.setAttesterEmail(patient.getEmail());
            attestedConsent.setAttesterLastName(patient.getLastName());
            // Middle name not available in Patient entity to be updated
            attestedConsent.setAttesterMiddleName("");
            attestedConsent.setAttesterFirstName(patient.getFirstName());
            attestedConsent.setAttesterIpAddress(attesterIdAddress);

            Date attestedOn = new Date();
            attestedConsent.setAttestedDateTime(attestedOn);

            attestedConsent.setConsentTermsAccepted(true);
            attestedConsent.setAttesterByUser(patient.getEmail());
            attestedConsent.setConsentReferenceId(consent.getConsentReferenceId());

            attestedConsent.setPatientGuid(consent.getPatient().getMedicalRecordNumber());

            String term = consentTerms.getConsentTermsText();
            attestedConsent.setAttestedPdfConsent(consentPdfGenerator.generate42CfrPart2Pdf(consent,patient, true, attestedOn, term));

            consent.setAttestedConsent(attestedConsent);
            consent.setSignedDate(new Date());
            consent.setStatus(ConsentStatus.CONSENT_SIGNED);
            consentRepository.save(consent);
        }else {
            logger.error("Error in creating attested consent");
            throw new AttestedConsentRevocationException("Error in creating attested consent");
        }
    }

    @Override
    public void attestConsentRevocation(AttestationDto attestationDto) {
        Long consentId = attestationDto.getConsentId();
        String attesterIdAddress = attestationDto.getAttesterIpAddress();

        final Consent consent = consentRepository.findOne(consentId);
        //Updating the patient data with data from phr api
        PatientDto patientDto = phrService.getPatientProfile();

        if (consent != null && consent.getAttestedConsentRevocation()== null && patientDto!= null && consentId != null && attesterIdAddress != null) {
            patientService.updatePatientFromPHR(patientDto);
            Patient patient = patientRepository.findByUsername(patientDto.getEmail());

            // TODO: Refactor this code block so that the attester fields are populated with the data fro the currently logged in user from UAA instead of the patient from PHR/PCM
            AttestedConsentRevocation attestedConsentRevocation =  new AttestedConsentRevocation();
            attestedConsentRevocation.setConsentRevocationTermsVersions(consentRevocationTermsVersionsService.findByLatestEnabledVersion());
            attestedConsentRevocation.setAttesterEmail(patient.getEmail());
            attestedConsentRevocation.setAttesterLastName(patient.getLastName());
            // Middle name not available in Patient entity to be updated
            attestedConsentRevocation.setAttesterMiddleName("");
            attestedConsentRevocation.setAttesterFirstName(patient.getFirstName());
            attestedConsentRevocation.setAttesterIpAddress(attesterIdAddress);

            Date revokedOn = new Date();
            attestedConsentRevocation.setAttestedDateTime(revokedOn);

            attestedConsentRevocation.setConsentRevokeTermsAccepted(true);
            attestedConsentRevocation.setAttesterByUser(patient.getEmail());
            attestedConsentRevocation.setConsentReferenceId(consent.getConsentReferenceId());

            attestedConsentRevocation.setPatientGuid(consent.getPatient().getMedicalRecordNumber());

            attestedConsentRevocation.setAttestedPdfConsentRevoke(consentRevokationPdfGenerator.generateConsentRevokationPdf(consent, patient, revokedOn));

            consent.setAttestedConsentRevocation(attestedConsentRevocation);
            consent.setRevocationDate(new Date());
            consent.setStatus(ConsentStatus.REVOCATION_REVOKED);
            consentRepository.save(consent);
        }else {
            logger.error("Error in creating attested consent revocation.");
            throw new AttestedConsentRevocationException("Error in creating attested consent revocation.");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.consent.ConsentService#
     * findConsentRevokationPdfDto(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public ConsentRevokationPdfDto findConsentRevokationPdfDto(Long consentId) {
        final Consent consent = consentRepository.findOne(consentId);
        final ConsentRevokationPdfDto consentRevokationPdfDto = makeConsentRevokationPdfDto();
        final String consentStatus = consent.getStatus();

        if (consentStatus.equals(ConsentStatus.REVOCATION_REVOKED)) {
            consentRevokationPdfDto.setContent(consent.getAttestedConsentRevocation().getAttestedPdfConsentRevoke());
        } else {
            throw new IllegalStateException("The status field of this consent object has an invalid value.");
        }

        consentRevokationPdfDto.setFilename(consent.getPatient().getFirstName()
                + "_" + consent.getPatient().getLastName()
                + "_ConsentRevokation" + consent.getId() + ".pdf");
        consentRevokationPdfDto.setConsentName(consent.getName());
        consentRevokationPdfDto.setId(consent.getId());
        return consentRevokationPdfDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.consent.ConsentService#getConsentSignedStage
     * (java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public String getConsentStatus(Long consentId) {
        String signStatus = "NA";
        final Consent consent = consentRepository.findOne(consentId);
        if (consent!= null) {
            signStatus = consent.getStatus();
        }
        return signStatus;
    }

    /**
     * Gets the ids.
     *
     * @param c32List the c32 list
     * @return the ids
     */
    public List<String> getIds(NodeList c32List) {
        final List<String> listOfIdsInC32 = new ArrayList<String>();

        for (int i = 0; i < c32List.getLength(); i++) {

            final NodeList childNodes = c32List.item(i).getChildNodes();

            for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
                final Node childNode = childNodes.item(childIndex);

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                    final NodeList grandChildNodeList = childNode
                            .getChildNodes();

                    for (int grandChildIndex = 0; grandChildIndex < grandChildNodeList
                            .getLength(); grandChildIndex++) {
                        final Node grandChildNode = grandChildNodeList
                                .item(grandChildIndex);

                        if (grandChildNode.getNodeName().equalsIgnoreCase("ID")) {
                            listOfIdsInC32.add(grandChildNode.getAttributes()
                                    .getNamedItem("root").getNodeValue());
                        }

                    }
                }
            }
        }
        return listOfIdsInC32;
    }

    /**
     * Gets the ids to tag.
     *
     * @param originalC32Ids  the original c32 ids
     * @param segmentedC32Ids the segmented c32 ids
     * @return the ids to tag
     */
    public List<String> getIdsToTag(List<String> originalC32Ids,
                                    List<String> segmentedC32Ids) {
        final List<String> idsToTag = new ArrayList<String>();

        for (int i = 0; i < originalC32Ids.size(); i++) {
            if (!segmentedC32Ids.contains(originalC32Ids.get(i))) {
                idsToTag.add(originalC32Ids.get(i));
            }
        }

        return idsToTag;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.consent.ConsentService#getXacmlCcd(java
     * .lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public byte[] getXacmlCcd(Long consentId) {
        return consentRepository.findOne(consentId).getXacmlCcd();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.consent.ConsentService#getConsentDirective(java
     * .lang.Long)
     */
    public byte[] getConsentDirective(Long consentId) {
        return consentRepository.findOne(consentId).getExportedCDAR2Consent();
    }

    /**
     * Checks if is consent belong to this user.
     *
     * @param consentId the consent id
     * @param patientId the patient id
     * @return true, if is consent belong to this user
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isConsentBelongToThisUser(Long consentId, Long patientId) {
        if (consentRepository.findOne(consentId) != null) {
            final Consent consent1 = consentRepository.findOne(consentId);
            final Patient patient = patientRepository.findOne(patientId);

            return consent1.getPatient().equals(patient);
        } else {
            return false;
        }
    }

    /**
     * Make consent.
     *
     * @return the consent
     */
    @Override
    public Consent makeConsent() {
        return new Consent();
    }

    /**
     * Make consent dto.
     *
     * @return the consent dto
     */
    @Override
    public ConsentDto makeConsentDto() {
        return new ConsentDto();
    }

    /**
     * Make consent list dtos.
     *
     * @return the array list
     */
    @Override
    public ArrayList<ConsentListDto> makeConsentListDtos() {
        return new ArrayList<ConsentListDto>();
    }

    /**
     * Make consent pdf dto.
     *
     * @return the consent pdf dto
     */
    @Override
    public ConsentPdfDto makeConsentPdfDto() {
        return new ConsentPdfDto();
    }

    /**
     * Make consent revokation pdf dto.
     *
     * @return the consent revokation pdf dto
     */
    @Override
    public ConsentRevokationPdfDto makeConsentRevokationPdfDto() {
        return new ConsentRevokationPdfDto();
    }

    /**
     * Save consent.
     *
     * @param consent the consent
     */
    @Override
    public void saveConsent(Consent consent) {
        consent.setCreatedDateTime(new Date());
        consentRepository.save(consent);
    }

    /**
     * Save consent.
     *
     * @param consentDto the consent dto
     * @param patientId  the patient id
     * @return the object
     * @throws ConsentGenException the consent gen exception
     */
    @Override
    public Object saveConsent(ConsentDto consentDto, long patientId)
            throws ConsentGenException {
        // Assert consentDto
        consentAssertions.forEach(assertion -> assertion
                .assertConsentDto(consentDto));

        // check for duplicate policy
        consentDto.setPatientId(patientId);
        final ConsentValidationDto consentValidationDto = consentCheckService
                .getConflictConsent(consentDto);
        if (null != consentValidationDto) {
            logger.debug("duplicate policy found");
            return consentValidationDto;
        }
        Consent consent = makeConsent();
        Patient patient;
        if (patientId != 0) {
            patient = patientRepository.findOne(patientId);
        } else {
            //Updating the patient data with data from phr api
            patientService.updatePatientFromPHR(phrService.getPatientProfile());
            patient = patientRepository.findByUsername(consentDto.getUsername());
        }
        final Map<String, AbstractProvider> providerMap = new HashMap<String, AbstractProvider>();
        for (final IndividualProvider o : patient.getIndividualProviders()) {
            providerMap.put(o.getNpi(), o);
        }
        for (final OrganizationalProvider o : patient
                .getOrganizationalProviders()) {
            providerMap.put(o.getNpi(), o);
        }
        if (consentDto.getId() != null
                && Long.parseLong(consentDto.getId()) > 0) {
            consent = consentRepository.findOne(Long.parseLong(consentDto
                    .getId()));
        }

        // Build the updated policy id and set it
        final String policyId = generatePolicyId(consentDto,
                patient.getMedicalRecordNumber());

        consent.setConsentReferenceId(policyId);

        // Set Providers
        if (consentDto.getProvidersDisclosureIsMadeToNpi() != null) {
            final Set<ConsentIndividualProviderDisclosureIsMadeTo> providersDisclosureIsMadeTo = new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>();
            for (final String item : consentDto
                    .getProvidersDisclosureIsMadeToNpi()) {
                final IndividualProvider individualProvider = (IndividualProvider) providerMap
                        .get(item);
                final ConsentIndividualProviderDisclosureIsMadeTo consentIndividualProviderPermittedToDisclose = new ConsentIndividualProviderDisclosureIsMadeTo(
                        individualProvider);
                providersDisclosureIsMadeTo
                        .add(consentIndividualProviderPermittedToDisclose);
            }
            consent.setProvidersDisclosureIsMadeTo(providersDisclosureIsMadeTo);
        } else {
            consent.setProvidersDisclosureIsMadeTo(new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>());
        }

        if (consentDto.getProvidersPermittedToDiscloseNpi() != null) {
            final Set<ConsentIndividualProviderPermittedToDisclose> providersPermittedToDisclose = new HashSet<ConsentIndividualProviderPermittedToDisclose>();
            for (final String item : consentDto
                    .getProvidersPermittedToDiscloseNpi()) {
                final IndividualProvider individualProvider = (IndividualProvider) providerMap
                        .get(item);
                final ConsentIndividualProviderPermittedToDisclose consentIndividualProviderPermittedToDisclose = new ConsentIndividualProviderPermittedToDisclose(
                        individualProvider);
                providersPermittedToDisclose
                        .add(consentIndividualProviderPermittedToDisclose);
            }
            consent.setProvidersPermittedToDisclose(providersPermittedToDisclose);
        } else {
            consent.setProvidersPermittedToDisclose(new HashSet<ConsentIndividualProviderPermittedToDisclose>());
        }

        if (consentDto.getOrganizationalProvidersDisclosureIsMadeToNpi() != null) {
            final Set<ConsentOrganizationalProviderDisclosureIsMadeTo> organizationalProvidersDisclosureIsMadeTo = new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>();
            for (final String item : consentDto
                    .getOrganizationalProvidersDisclosureIsMadeToNpi()) {
                final OrganizationalProvider organizationalProvider = (OrganizationalProvider) providerMap
                        .get(item);
                final ConsentOrganizationalProviderDisclosureIsMadeTo consentOrganizationalProviderPermittedToDisclose = new ConsentOrganizationalProviderDisclosureIsMadeTo(
                        organizationalProvider);
                organizationalProvidersDisclosureIsMadeTo
                        .add(consentOrganizationalProviderPermittedToDisclose);
            }
            consent.setOrganizationalProvidersDisclosureIsMadeTo(organizationalProvidersDisclosureIsMadeTo);
        } else {
            consent.setOrganizationalProvidersDisclosureIsMadeTo(new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>());
        }

        if (consentDto.getOrganizationalProvidersPermittedToDiscloseNpi() != null) {
            final Set<ConsentOrganizationalProviderPermittedToDisclose> organizationalProvidersPermittedToDisclose = new HashSet<ConsentOrganizationalProviderPermittedToDisclose>();
            for (final String item : consentDto
                    .getOrganizationalProvidersPermittedToDiscloseNpi()) {
                final OrganizationalProvider organizationalProvider = (OrganizationalProvider) providerMap
                        .get(item);
                final ConsentOrganizationalProviderPermittedToDisclose consentOrganizationalProviderPermittedToDisclose = new ConsentOrganizationalProviderPermittedToDisclose(
                        organizationalProvider);
                organizationalProvidersPermittedToDisclose
                        .add(consentOrganizationalProviderPermittedToDisclose);
            }
            consent.setOrganizationalProvidersPermittedToDisclose(organizationalProvidersPermittedToDisclose);
        } else {
            consent.setOrganizationalProvidersPermittedToDisclose(new HashSet<ConsentOrganizationalProviderPermittedToDisclose>());
        }

        // Set Do Not Shares
        if (consentDto.getDoNotShareClinicalDocumentTypeCodes() != null) {
            final Set<ConsentDoNotShareClinicalDocumentTypeCode> doNotShareClinicalDocumentTypeCodes = new HashSet<ConsentDoNotShareClinicalDocumentTypeCode>();
            for (final String item : consentDto
                    .getDoNotShareClinicalDocumentTypeCodes()) {
                final ClinicalDocumentTypeCode clinicalDocumentTypeCode = clinicalDocumentTypeCodeRepository
                        .findByCode(item);
                final ConsentDoNotShareClinicalDocumentTypeCode consentDoNotShareClinicalDocumentTypeCode = new ConsentDoNotShareClinicalDocumentTypeCode(
                        clinicalDocumentTypeCode);
                doNotShareClinicalDocumentTypeCodes
                        .add(consentDoNotShareClinicalDocumentTypeCode);
            }
            consent.setDoNotShareClinicalDocumentTypeCodes(doNotShareClinicalDocumentTypeCodes);
        } else {
            consent.setDoNotShareClinicalDocumentTypeCodes(new HashSet<ConsentDoNotShareClinicalDocumentTypeCode>());
        }

        if (consentDto.getDoNotShareSensitivityPolicyCodes() != null) {
            final Set<ConsentDoNotShareSensitivityPolicyCode> doNotShareSensitivityPolicyCodes = new HashSet<ConsentDoNotShareSensitivityPolicyCode>();
            for (final String item : consentDto
                    .getDoNotShareSensitivityPolicyCodes()) {
                final ValueSetCategory valueSetCategory = valueSetCategoryRepository
                        .findByCode(item);
                final ConsentDoNotShareSensitivityPolicyCode consentDoNotShareSensitivityPolicyCode = new ConsentDoNotShareSensitivityPolicyCode(
                        valueSetCategory);
                doNotShareSensitivityPolicyCodes
                        .add(consentDoNotShareSensitivityPolicyCode);
            }
            consent.setDoNotShareSensitivityPolicyCodes(doNotShareSensitivityPolicyCodes);
        } else {
            consent.setDoNotShareSensitivityPolicyCodes(new HashSet<ConsentDoNotShareSensitivityPolicyCode>());
        }

        if (consentDto.getShareForPurposeOfUseCodes() != null) {
            final Set<ConsentShareForPurposeOfUseCode> shareForPurposeOfUseCodes = new HashSet<ConsentShareForPurposeOfUseCode>();
            for (final String item : consentDto.getShareForPurposeOfUseCodes()) {
                final PurposeOfUseCode purposeOfUseCode = purposeOfUseCodeRepository
                        .findByCode(item);
                final ConsentShareForPurposeOfUseCode consentShareForPurposeOfUseCode = new ConsentShareForPurposeOfUseCode(
                        purposeOfUseCode);
                shareForPurposeOfUseCodes.add(consentShareForPurposeOfUseCode);
            }
            consent.setShareForPurposeOfUseCodes(shareForPurposeOfUseCodes);
        } else {
            consent.setShareForPurposeOfUseCodes(new HashSet<ConsentShareForPurposeOfUseCode>());
        }

        if (consentDto.getDoNotShareClinicalConceptCodes() != null) {
            final Set<ClinicalConceptCode> doNotShareClinicalConceptCodes = new HashSet<ClinicalConceptCode>();
            for (final SpecificMedicalInfoDto item : consentDto
                    .getDoNotShareClinicalConceptCodes()) {
                final ClinicalConceptCode clinicalConceptCode = new ClinicalConceptCode();
                clinicalConceptCode.setCode(item.getCode());
                clinicalConceptCode.setCodeSystem(item.getCodeSystem());
                clinicalConceptCode.setCodeSystemName(item.getCodeSystem());
                clinicalConceptCode.setDisplayName(item.getDisplayName());
                doNotShareClinicalConceptCodes.add(clinicalConceptCode);
            }
            consent.setDoNotShareClinicalConceptCodes(doNotShareClinicalConceptCodes);
        } else {
            consent.setDoNotShareClinicalConceptCodes(new HashSet<ClinicalConceptCode>());
        }

        // Set Dates
        consent.setStartDate(consentDto.getConsentStart());
        consent.setEndDate(consentDto.getConsentEnd());

        consent.setCreatedDateTime(new Date());

        consent.setStatus(ConsentStatus.CONSENT_SAVED);

        consent.setPatient(patient);
        consent.setName("Consent");
        consent.setDescription("This is a consent made by "
                + patient.getFirstName() + " " + patient.getLastName());
        String terms = consentTermsVersionsService.getEnabledConsentTermsVersion().getConsentTermsText();

        consent.setUnAttestedPdfConsent(consentPdfGenerator.generate42CfrPart2Pdf(consent,patient, false, null, terms));

        try {


            consent.setXacmlCcd(consentExportService.exportConsent2XACML(
                    consent).getBytes());

            // set xacml for consentFrom provider to give access
            // to consent pdf
            consent.setXacmlPdfConsentFrom(consentExportService
                    .exportConsent2XacmlPdfConsentFrom(consent).getBytes());

            consent.setXacmlPdfConsentTo(consentExportService
                    .exportConsent2XacmlPdfConsentTo(consent).getBytes());

            consent.setExportedCDAR2Consent(consentExportService.exportConsent2CDAR2ConsentDirective(consent).getBytes());

        } catch (final ConsentGenException e) {
            logger.error("Error in saving consent in xacml format", e);
            throw new ConsentGenException(e.getMessage());
        }

        if (consent.getId() != null) {
            consentRepository.save(consent);
        } else {
            consentFactory.createNewConsent(consent);
        }

        return consentDto;
    }

    /**
     * Tag c32 document.
     *
     * @param taggedC32List the tagged c32 list
     * @param taggedC32Ids  the tagged c32 ids
     */
    public void tagC32Document(NodeList taggedC32List, List<String> taggedC32Ids) {

        for (int i = 0; i < taggedC32List.getLength(); i++) {
            final Element elementToAddAttribute = (Element) taggedC32List
                    .item(i);

            final NodeList childNodes = taggedC32List.item(i).getChildNodes();

            for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
                final Node childNode = childNodes.item(childIndex);

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    final NodeList grandChildNodeList = childNode
                            .getChildNodes();

                    for (int grandChildIndex = 0; grandChildIndex < grandChildNodeList
                            .getLength(); grandChildIndex++) {
                        final Node grandChildNode = grandChildNodeList
                                .item(grandChildIndex);

                        if (grandChildNode.getNodeName().equalsIgnoreCase("ID")) {

                            // tag c32 by adding redact attribute to ids that
                            // were segmented
                            if (taggedC32Ids.contains(grandChildNode
                                    .getAttributes().getNamedItem("root")
                                    .getNodeValue())) {
                                logger.info("Match!");
                                elementToAddAttribute.setAttribute("redact",
                                        "redact");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Tag smart c32 document.
     *
     * @param taggedC32List the tagged c32 list
     * @param taggedC32Ids  the tagged c32 ids
     */
    public void tagSmartC32Document(NodeList taggedC32List,
                                    List<String> taggedC32Ids) {

        for (int i = 0; i < taggedC32List.getLength(); i++) {
            final Element elementToAddAttribute = (Element) taggedC32List
                    .item(i);

            final NodeList childNodes = taggedC32List.item(i).getChildNodes();

            if (!isRedactableSection(taggedC32List.item(i))) {
                continue;
            }

            for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
                final Node childNode = childNodes.item(childIndex);

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                    final NodeList grandChildNodeList = childNode
                            .getChildNodes();

                    for (int grandChildIndex = 0; grandChildIndex < grandChildNodeList
                            .getLength(); grandChildIndex++) {
                        final Node grandChildNode = grandChildNodeList
                                .item(grandChildIndex);

                        if (grandChildNode.getNodeName().equalsIgnoreCase(
                                "entryRelationship")) {
                            final Node observationNode = grandChildNode
                                    .getChildNodes().item(1);
                            final NodeList observationChildNodes = observationNode
                                    .getChildNodes();

                            for (int observationIndex = 0; observationIndex < observationChildNodes
                                    .getLength(); observationIndex++) {

                                final Node observationChildNode = observationChildNodes
                                        .item(observationIndex);

                                if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                                    if (observationChildNode.getNodeName()
                                            .equalsIgnoreCase("text")) {

                                        final Node referenceNode = observationChildNode
                                                .getChildNodes().item(1);
                                        final Node referenceValue = referenceNode
                                                .getAttributes().getNamedItem(
                                                        "value");
                                        logger.info("Reference value is: "
                                                + referenceValue.getNodeValue());

                                        if (taggedC32Ids
                                                .contains(referenceValue
                                                        .getNodeValue())) {
                                            logger.info("Match!");
                                            elementToAddAttribute.setAttribute(
                                                    "redact", "redact");
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Update consent.
     *
     * @param consent the consent
     * @return the consent
     */
    @Override
    public Consent updateConsent(Consent consent) {
        consent.setCreatedDateTime(new Date());
        return consentRepository.save(consent);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.consent.ConsentService#validateConsentDate
     * (java.util.Date, java.util.Date)
     */
    @Override
    public boolean validateConsentDate(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            final Instant startInstant = startDate.toInstant();
            final Instant endInstant = endDate.toInstant();
            final Instant currentInstant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            if (startInstant.compareTo(endInstant) <= 0
                    && startInstant.compareTo(currentInstant) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns xacmlFile based on consent id.
     *
     * @param consentId the consent id
     * @return xacmlFile
     */
    @Transactional(readOnly = true)
    private String findConsentXACMLById(Long consentId) {
        String xacmlFile = "";

        if (consentRepository.findOne(consentId) != null) {
            final Consent consent = consentRepository.findOne(consentId);
            final byte[] xacmlByte = consent.getXacmlCcd();
            try {
                xacmlFile = new String(xacmlByte, "UTF-8");
            } catch (final UnsupportedEncodingException e) {
                logger.error("Error while converting xacml byte[] to string "
                        + e.getMessage());
                e.printStackTrace();
            }

        }
        return xacmlFile;
    }

    @Override
    public XacmlDto findXACMLForCCDByConsentId(Long consentId) {
        Assert.notNull(consentId, "Consent ID is required to find XACML");
        byte[] xacmlForCCD = Optional.ofNullable(consentRepository.findOne(consentId))
                .map(Consent::getXacmlCcd)
                .orElseThrow(() -> new XacmlNotFoundException("XACML for CCD not found with consent id: " + consentId));
        return new XacmlDto(xacmlForCCD);
    }

    /**
     * Generate policy id.
     *
     * @param consentDto the consent dto
     * @param mrn        the mrn
     * @return the string
     */
    private String generatePolicyId(ConsentDto consentDto, String mrn) {
        String policyId = null;
        final StringBuilder errorBuilder = new StringBuilder();
        try {
            policyId = policyIdService.generatePolicyId(consentDto, mrn);
        } catch (final UniqueValueGeneratorException e) {
            errorBuilder.append(". ");
            errorBuilder.append(e.getMessage());
            logger.error(e.getMessage(), e);
        } finally {
            Assert.hasText(
                    policyId,
                    errorBuilder.insert(0,
                            "PolicyId cannot be generated by PCM!").toString());
        }
        return policyId;
    }

    /**
     * Gets the ids for smart.
     *
     * @param c32List the c32 list
     * @return the ids for smart
     */
    private List<String> getIdsForSmart(NodeList c32List) {
        final List<String> listOfIdsInC32 = new ArrayList<String>();

        for (int i = 0; i < c32List.getLength(); i++) {

            final NodeList childNodes = c32List.item(i).getChildNodes();

            if (!isRedactableSection(c32List.item(i))) {
                continue;
            }

            for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
                final Node childNode = childNodes.item(childIndex);

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                    final NodeList grandChildNodeList = childNode
                            .getChildNodes();

                    for (int grandChildIndex = 0; grandChildIndex < grandChildNodeList
                            .getLength(); grandChildIndex++) {
                        final Node grandChildNode = grandChildNodeList
                                .item(grandChildIndex);

                        if (grandChildNode.getNodeName().equalsIgnoreCase(
                                "entryRelationship")) {
                            final Node observationNode = grandChildNode
                                    .getChildNodes().item(1);
                            final NodeList observationChildNodes = observationNode
                                    .getChildNodes();

                            for (int observationIndex = 0; observationIndex < observationChildNodes
                                    .getLength(); observationIndex++) {

                                final Node observationChildNode = observationChildNodes
                                        .item(observationIndex);

                                if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                                    if (observationChildNode.getNodeName()
                                            .equalsIgnoreCase("text")) {

                                        final Node referenceNode = observationChildNode
                                                .getChildNodes().item(1);
                                        final Node referenceValue = referenceNode
                                                .getAttributes().getNamedItem(
                                                        "value");
                                        logger.info("Reference value is: "
                                                + referenceValue.getNodeValue());

                                        listOfIdsInC32.add(referenceValue
                                                .getNodeValue());
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return listOfIdsInC32;
    }

    /**
     * Checks if is redactable section.
     *
     * @param entryNode the entry node
     * @return true, if is redactable section
     */
    private boolean isRedactableSection(Node entryNode) {
        boolean isRedactable = false;
        while (entryNode.getPreviousSibling() != null) {
            final Node previousSibling = entryNode.getPreviousSibling();
            if (previousSibling.getNodeType() == Node.ELEMENT_NODE) {
                if (previousSibling.getNodeName().equalsIgnoreCase("title")) {
                    final NodeList c = previousSibling.getChildNodes();
                    isRedactable = validationSection(previousSibling
                            .getChildNodes().item(0).getNodeValue());
                }
            }
            entryNode = previousSibling;
        }
        return isRedactable;
    }

    /**
     * Checks if is smart c32.
     *
     * @param originalC32 the original c32
     * @return true, if is smart c32
     */
    private boolean isSmartC32(String originalC32) {
        if (originalC32.contains("content")) {
            return true;
        }
        return false;
    }

    /**
     * Validation section.
     *
     * @param nodeValue the node value
     * @return true, if successful
     */
    private boolean validationSection(String nodeValue) {
        boolean redacted = false;
        if (nodeValue.toUpperCase().matches(
                "^.*?(PROBLEMS|MEDICATIONS|RESULTS|ALLERGIES).*$")) {
            redacted = true;
        }

        logger.info("Node value: " + nodeValue + "   Is redactable: "
                + redacted);
        return redacted;
    }
}
