/*
 * 
 */
package gov.samhsa.pcm.service.consentexport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.samhsa.consent.ConsentDto;
import gov.samhsa.consent.IndividualProviderDto;
import gov.samhsa.consent.OrganizationalProviderDto;
import gov.samhsa.pcm.domain.consent.Consent;
import gov.samhsa.pcm.domain.consent.ConsentDoNotShareClinicalDocumentSectionTypeCode;
import gov.samhsa.pcm.domain.consent.ConsentDoNotShareClinicalDocumentTypeCode;
import gov.samhsa.pcm.domain.consent.ConsentDoNotShareSensitivityPolicyCode;
import gov.samhsa.pcm.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.pcm.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.pcm.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.pcm.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.pcm.domain.consent.ConsentRepository;
import gov.samhsa.pcm.domain.consent.ConsentShareForPurposeOfUseCode;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.provider.IndividualProvider;
import gov.samhsa.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.pcm.domain.reference.ClinicalConceptCode;
import gov.samhsa.pcm.domain.reference.ClinicalDocumentTypeCode;
import gov.samhsa.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.pcm.domain.valueset.MedicalSection;
import gov.samhsa.pcm.domain.valueset.ValueSetCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.samhsa.pcm.service.consentexport.ConsentExportMapper;
import gov.samhsa.pcm.service.consentexport.ConsentExportMapperImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class ConsentExportMapperImplTest {

	@Mock
	ConsentRepository consentRepository;

	@Mock
	ConsentExportMapper consentExportMapper;

	ModelMapper modelMapper;

	@InjectMocks
	ConsentExportMapperImpl sut;

	@Test
	public void testMap_getModalMapper() {
		// Arrange
		modelMapper = new ModelMapper();
		sut.setModelMapper(modelMapper);

		// Act
		ModelMapper modelMapperRet = sut.getModelMapper();

		// Assert
		assertEquals(modelMapper, modelMapperRet);

	}

	@Test
	public void testMap_when_no_LegalRep() {
		// Arrange
		Consent consent = new Consent();
		modelMapper = new ModelMapper();
		sut.setModelMapper(modelMapper);

		Patient patient = new Patient();
		patient.setFirstName("test");
		consent.setPatient(patient);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertEquals(consent.getPatient().getFirstName(), consentDto
				.getPatientDto().getFirstName());
		assertNull(consentDto.getLegalRepresentative());

	}

	@Test
	public void testMap_when_mapping_providersDisclosureIsMadeTo() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// set providers
		Set<ConsentIndividualProviderDisclosureIsMadeTo> providersDisclosureIsMadeTo = new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>();
		List<IndividualProvider> indProviders = setDataIndividualProviders();
		for (IndividualProvider individualProvider : indProviders) {
			ConsentIndividualProviderDisclosureIsMadeTo consentIndividualProviderPermittedToDisclose = new ConsentIndividualProviderDisclosureIsMadeTo(
					individualProvider);

			providersDisclosureIsMadeTo
					.add(consentIndividualProviderPermittedToDisclose);

		}
		consent.setProvidersDisclosureIsMadeTo(providersDisclosureIsMadeTo);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertEquals(consent.getProvidersDisclosureIsMadeTo().getClass(),
				consentDto.getProvidersDisclosureIsMadeTo().getClass());
		assertEquals(consent.getProvidersDisclosureIsMadeTo().size(),
				consentDto.getProvidersDisclosureIsMadeTo().size());

		for (ConsentIndividualProviderDisclosureIsMadeTo pdmt : consent
				.getProvidersDisclosureIsMadeTo()) {

			String consentNpi = pdmt.getIndividualProvider().getNpi();

			for (IndividualProviderDto indiprovidersDisclosureIsMadeTo : consentDto
					.getProvidersDisclosureIsMadeTo()) {
				String consentDtoNpi = indiprovidersDisclosureIsMadeTo.getNpi();
				if (!consentNpi.equalsIgnoreCase(consentDtoNpi)) {
					continue;
				}
				assertEquals(pdmt.getIndividualProvider().getFirstName(),
						indiprovidersDisclosureIsMadeTo.getFirstName());
				assertEquals(pdmt.getIndividualProvider().getLastName(),
						indiprovidersDisclosureIsMadeTo.getLastName());
				assertEquals(pdmt.getIndividualProvider().getMiddleName(),
						indiprovidersDisclosureIsMadeTo.getMiddleName());
				assertEquals(pdmt.getIndividualProvider().getNamePrefix(),
						indiprovidersDisclosureIsMadeTo.getNamePrefix());
				assertEquals(pdmt.getIndividualProvider().getNameSuffix(),
						indiprovidersDisclosureIsMadeTo.getNameSuffix());
				assertEquals(pdmt.getIndividualProvider()
						.getPracticeLocationAddressCityName(),
						indiprovidersDisclosureIsMadeTo
								.getPracticeLocationAddressCityName());
				assertEquals(pdmt.getIndividualProvider()
						.getPracticeLocationAddressCountryCode(),
						indiprovidersDisclosureIsMadeTo
								.getPracticeLocationAddressCountryCode());
				assertEquals(pdmt.getIndividualProvider()
						.getPracticeLocationAddressPostalCode(),
						indiprovidersDisclosureIsMadeTo
								.getPracticeLocationAddressPostalCode());
				assertEquals(pdmt.getIndividualProvider()
						.getPracticeLocationAddressStateName(),
						indiprovidersDisclosureIsMadeTo
								.getPracticeLocationAddressStateName());

			}
		}

	}

	@Test
	public void testMap_when_mapping_ProvidersPermittedToDisclose() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// set providers
		Set<ConsentIndividualProviderPermittedToDisclose> ProvidersPermittedToDisclose = new HashSet<ConsentIndividualProviderPermittedToDisclose>();
		List<IndividualProvider> indProviders = setDataIndividualProviders();

		for (IndividualProvider individualProvider : indProviders) {

			ConsentIndividualProviderPermittedToDisclose consentIndividualProviderPermittedToDisclose = new ConsentIndividualProviderPermittedToDisclose(
					individualProvider);

			ProvidersPermittedToDisclose
					.add(consentIndividualProviderPermittedToDisclose);

		}

		consent.setProvidersPermittedToDisclose(ProvidersPermittedToDisclose);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertEquals(consent.getProvidersPermittedToDisclose().getClass(),
				consentDto.getProvidersPermittedToDisclose().getClass());
		assertEquals(consent.getProvidersPermittedToDisclose().size(),
				consentDto.getProvidersPermittedToDisclose().size());

		for (ConsentIndividualProviderPermittedToDisclose pdmt : consent
				.getProvidersPermittedToDisclose()) {

			String consentNpi = pdmt.getIndividualProvider().getNpi();

			for (IndividualProviderDto indiProvidersPermittedToDisclose : consentDto
					.getProvidersPermittedToDisclose()) {
				String consentDtoNpi = indiProvidersPermittedToDisclose
						.getNpi();
				if (!consentNpi.equalsIgnoreCase(consentDtoNpi)) {
					continue;
				}
				assertEquals(pdmt.getIndividualProvider().getFirstName(),
						indiProvidersPermittedToDisclose.getFirstName());
				assertEquals(pdmt.getIndividualProvider().getLastName(),
						indiProvidersPermittedToDisclose.getLastName());
				assertEquals(pdmt.getIndividualProvider().getMiddleName(),
						indiProvidersPermittedToDisclose.getMiddleName());
				assertEquals(pdmt.getIndividualProvider().getNamePrefix(),
						indiProvidersPermittedToDisclose.getNamePrefix());
				assertEquals(pdmt.getIndividualProvider().getNameSuffix(),
						indiProvidersPermittedToDisclose.getNameSuffix());
				assertEquals(pdmt.getIndividualProvider()
						.getPracticeLocationAddressCityName(),
						indiProvidersPermittedToDisclose
								.getPracticeLocationAddressCityName());
				assertEquals(pdmt.getIndividualProvider()
						.getPracticeLocationAddressCountryCode(),
						indiProvidersPermittedToDisclose
								.getPracticeLocationAddressCountryCode());
				assertEquals(pdmt.getIndividualProvider()
						.getPracticeLocationAddressPostalCode(),
						indiProvidersPermittedToDisclose
								.getPracticeLocationAddressPostalCode());
				assertEquals(pdmt.getIndividualProvider()
						.getPracticeLocationAddressStateName(),
						indiProvidersPermittedToDisclose
								.getPracticeLocationAddressStateName());

			}
		}

	}

	@Test
	public void testMap_when_mapping_OrganizationalProvidersPermittedToDisclose() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// set providers

		Set<ConsentOrganizationalProviderPermittedToDisclose> organizationalProvidersPermittedToDisclose = new HashSet<ConsentOrganizationalProviderPermittedToDisclose>();
		List<OrganizationalProvider> orgProviders = setDataOrgProviders();
		for (OrganizationalProvider organizationalProvider : orgProviders) {

			ConsentOrganizationalProviderPermittedToDisclose consentOrganizationalProviderPermittedToDisclose = new ConsentOrganizationalProviderPermittedToDisclose(
					organizationalProvider);

			organizationalProvidersPermittedToDisclose
					.add(consentOrganizationalProviderPermittedToDisclose);

		}
		consent.setOrganizationalProvidersPermittedToDisclose(organizationalProvidersPermittedToDisclose);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertEquals(consent.getProvidersPermittedToDisclose().getClass(),
				consentDto.getProvidersPermittedToDisclose().getClass());
		assertEquals(consent.getProvidersPermittedToDisclose().size(),
				consentDto.getProvidersPermittedToDisclose().size());

		for (ConsentOrganizationalProviderPermittedToDisclose pdmt : consent
				.getOrganizationalProvidersPermittedToDisclose()) {

			String consentNpi = pdmt.getOrganizationalProvider().getNpi();

			for (OrganizationalProviderDto organizationalPermittedToDisclose : consentDto
					.getOrganizationalProvidersPermittedToDisclose()) {
				String consentDtoNpi = organizationalPermittedToDisclose
						.getNpi();
				if (!consentNpi.equalsIgnoreCase(consentDtoNpi)) {
					continue;
				}
				assertEquals(pdmt.getOrganizationalProvider().getOrgName(),
						organizationalPermittedToDisclose.getOrgName());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressCityName(),
						organizationalPermittedToDisclose
								.getPracticeLocationAddressCityName());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressStateName(),
						organizationalPermittedToDisclose
								.getPracticeLocationAddressStateName());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressCountryCode(),
						organizationalPermittedToDisclose
								.getPracticeLocationAddressCountryCode());
				assertEquals(pdmt.getOrganizationalProvider()
						.getSecondLinePracticeLocationAddress(),
						organizationalPermittedToDisclose
								.getSecondLinePracticeLocationAddress());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressPostalCode(),
						organizationalPermittedToDisclose
								.getPracticeLocationAddressPostalCode());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressStateName(),
						organizationalPermittedToDisclose
								.getPracticeLocationAddressStateName());

			}
		}

	}

	@Test
	public void testMap_when_mapping_OrganizationalProvidersDisclosureIsMadeTo() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// set providers
		Set<ConsentOrganizationalProviderDisclosureIsMadeTo> OrganizationalProvidersDisclosureIsMadeTo = new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>();
		List<OrganizationalProvider> orgProviders = setDataOrgProviders();
		for (OrganizationalProvider organizationalProvider : orgProviders) {

			ConsentOrganizationalProviderDisclosureIsMadeTo consentOrganizationalProviderDisclosureIsMadeTo = new ConsentOrganizationalProviderDisclosureIsMadeTo(
					organizationalProvider);

			OrganizationalProvidersDisclosureIsMadeTo
					.add(consentOrganizationalProviderDisclosureIsMadeTo);

		}
		consent.setOrganizationalProvidersDisclosureIsMadeTo(OrganizationalProvidersDisclosureIsMadeTo);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertEquals(consent.getProvidersDisclosureIsMadeTo().getClass(),
				consentDto.getProvidersDisclosureIsMadeTo().getClass());
		assertEquals(consent.getProvidersDisclosureIsMadeTo().size(),
				consentDto.getProvidersDisclosureIsMadeTo().size());

		for (ConsentOrganizationalProviderDisclosureIsMadeTo pdmt : consent
				.getOrganizationalProvidersDisclosureIsMadeTo()) {

			String consentNpi = pdmt.getOrganizationalProvider().getNpi();

			for (OrganizationalProviderDto organizationalProvidersDisclosureIsMadeTo : consentDto
					.getOrganizationalProvidersDisclosureIsMadeTo()) {
				String consentDtoNpi = organizationalProvidersDisclosureIsMadeTo
						.getNpi();
				if (!consentNpi.equalsIgnoreCase(consentDtoNpi)) {
					continue;
				}
				assertEquals(pdmt.getOrganizationalProvider().getOrgName(),
						organizationalProvidersDisclosureIsMadeTo.getOrgName());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressCityName(),
						organizationalProvidersDisclosureIsMadeTo
								.getPracticeLocationAddressCityName());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressStateName(),
						organizationalProvidersDisclosureIsMadeTo
								.getPracticeLocationAddressStateName());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressCountryCode(),
						organizationalProvidersDisclosureIsMadeTo
								.getPracticeLocationAddressCountryCode());
				assertEquals(pdmt.getOrganizationalProvider()
						.getSecondLinePracticeLocationAddress(),
						organizationalProvidersDisclosureIsMadeTo
								.getSecondLinePracticeLocationAddress());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressPostalCode(),
						organizationalProvidersDisclosureIsMadeTo
								.getPracticeLocationAddressPostalCode());
				assertEquals(pdmt.getOrganizationalProvider()
						.getPracticeLocationAddressStateName(),
						organizationalProvidersDisclosureIsMadeTo
								.getPracticeLocationAddressStateName());

			}
		}

	}

	@Test
	public void testMap_with_ClinicalConceptCode() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// Set Do Not Shares
		Set<ClinicalConceptCode> doNotShareClinicalConceptCodes = new HashSet<ClinicalConceptCode>();

		ClinicalConceptCode clinicalConceptCode = new ClinicalConceptCode();
		clinicalConceptCode.setCode("6");
		clinicalConceptCode.setCodeSystemName("Code System Name");
		clinicalConceptCode.setDisplayName("Code Display Name");
		doNotShareClinicalConceptCodes.add(clinicalConceptCode);

		consent.setDoNotShareClinicalConceptCodes(doNotShareClinicalConceptCodes);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertEquals(consent.getDoNotShareClinicalConceptCodes().size(),
				consentDto.getDoNotShareClinicalConceptCodes().size());

	}

	@Test
	public void testMap_DoNotShareClinicalDocumentTypeCodes() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// Set Do Not Shares
		Set<ConsentDoNotShareClinicalDocumentTypeCode> doNotShareClinicalDocumentTypeCodes = new HashSet<ConsentDoNotShareClinicalDocumentTypeCode>();

		ClinicalDocumentTypeCode clinicalDocumentTypeCode = new ClinicalDocumentTypeCode();
		clinicalDocumentTypeCode.setCode("code");
		clinicalDocumentTypeCode.setDisplayName("display name");
		clinicalDocumentTypeCode.setCodeSystem("code system");
		clinicalDocumentTypeCode.setCodeSystemName("code system name");

		ConsentDoNotShareClinicalDocumentTypeCode consentDoNotShareClinicalDocumentTypeCode = new ConsentDoNotShareClinicalDocumentTypeCode(
				clinicalDocumentTypeCode);
		doNotShareClinicalDocumentTypeCodes
				.add(consentDoNotShareClinicalDocumentTypeCode);

		consent.setDoNotShareClinicalDocumentTypeCodes(doNotShareClinicalDocumentTypeCodes);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertNotNull("typecodesdto is empty",
				consentDto.getDoNotShareClinicalConceptCodes());
		assertEquals(consent.getDoNotShareClinicalDocumentTypeCodes().size(),
				consentDto.getDoNotShareClinicalDocumentTypeCodes().size());

	}

	@Test
	public void testMap_DoNotShareClinicalDocumentSectionTypeCode() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// Set Do Not Shares

		Set<ConsentDoNotShareClinicalDocumentSectionTypeCode> doNotShareClinicalDocumentSectionTypeCodes = new HashSet<ConsentDoNotShareClinicalDocumentSectionTypeCode>();

		MedicalSection medicalSection = new MedicalSection();
		medicalSection.setCode("code");
		medicalSection.setName("display name");

		ConsentDoNotShareClinicalDocumentSectionTypeCode consentDoNotShareClinicalDocumentSectionTypeCode = new ConsentDoNotShareClinicalDocumentSectionTypeCode(
				medicalSection);
		doNotShareClinicalDocumentSectionTypeCodes
				.add(consentDoNotShareClinicalDocumentSectionTypeCode);

		consent.setDoNotShareClinicalDocumentSectionTypeCodes(doNotShareClinicalDocumentSectionTypeCodes);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertNotNull("typecodesdto is empty",
				consentDto.getDoNotShareClinicalDocumentSectionTypeCodes());
		assertEquals(consent.getDoNotShareClinicalDocumentSectionTypeCodes()
				.size(), consentDto
				.getDoNotShareClinicalDocumentSectionTypeCodes().size());
	}

	@Test
	public void testMap_DoNotShareSensitivityPolicyCode() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// Set Do Not Shares

		Set<ConsentDoNotShareSensitivityPolicyCode> doNotShareSensitivityPolicyCodes = new HashSet<ConsentDoNotShareSensitivityPolicyCode>();
		ValueSetCategory valueSetCategory = new ValueSetCategory();
		valueSetCategory.setCode("code");
		valueSetCategory.setName("display name");

		ConsentDoNotShareSensitivityPolicyCode consentDoNotShareSensitivityPolicyCode = new ConsentDoNotShareSensitivityPolicyCode(
				valueSetCategory);
		doNotShareSensitivityPolicyCodes
				.add(consentDoNotShareSensitivityPolicyCode);

		consent.setDoNotShareSensitivityPolicyCodes(doNotShareSensitivityPolicyCodes);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertNotNull("typecodesdto is empty",
				consentDto.getDoNotShareSensitivityPolicyCodes());
		assertEquals(consent.getDoNotShareSensitivityPolicyCodes().size(),
				consentDto.getDoNotShareSensitivityPolicyCodes().size());
	}

	@Test
	public void testMap_ShareForPurposeOfUseCodes() {
		// Arrange
		Consent consent = new Consent();
		setDataBasicConsent(consent);

		// Set Do Not Shares

		Set<ConsentShareForPurposeOfUseCode> shareForPurposeOfUseCodes = new HashSet<ConsentShareForPurposeOfUseCode>();
		PurposeOfUseCode purposeOfUseCode = new PurposeOfUseCode();
		purposeOfUseCode.setCode("code");
		purposeOfUseCode.setDisplayName("display name");
		purposeOfUseCode.setCodeSystem("code system");
		purposeOfUseCode.setCodeSystemName("code system name");

		ConsentShareForPurposeOfUseCode consentShareForPurposeOfUseCode = new ConsentShareForPurposeOfUseCode(
				purposeOfUseCode);
		shareForPurposeOfUseCodes.add(consentShareForPurposeOfUseCode);
		consent.setShareForPurposeOfUseCodes(shareForPurposeOfUseCodes);

		// Act
		ConsentDto consentDto = sut.map(consent);

		// Assert
		assertNotNull("typecodesdto is empty",
				consentDto.getShareForPurposeOfUseCodes());
		assertEquals(consent.getShareForPurposeOfUseCodes().size(), consentDto
				.getShareForPurposeOfUseCodes().size());
	}

	private void setDataBasicConsent(Consent consent) {

		modelMapper = new ModelMapper();
		sut.setModelMapper(modelMapper);

		Patient patient = new Patient();
		patient.setFirstName("test");
		consent.setPatient(patient);

		Patient legalRep = new Patient();
		legalRep.setUsername("legal");
		consent.setLegalRepresentative(legalRep);

	}

	private List<IndividualProvider> setDataIndividualProviders() {

		List<IndividualProvider> indProviders = new ArrayList<IndividualProvider>();

		// first one
		IndividualProvider individualProvider = new IndividualProvider();
		individualProvider.setEnumerationDate("10/08/2009");
		individualProvider.setFirstLinePracticeLocationAddress("107 S 5TH ST");
		individualProvider.setFirstName("TERESA");
		individualProvider.setLastName("LUQUIN");
		individualProvider.setMiddleName("INEZ");
		individualProvider.setNamePrefix("");
		individualProvider.setNameSuffix("");
		individualProvider.setNpi("1568797520");
		individualProvider.setPracticeLocationAddressCityName("EL CENTRO");
		individualProvider.setPracticeLocationAddressCountryCode("US");
		individualProvider.setPracticeLocationAddressPostalCode("922433024");
		individualProvider.setPracticeLocationAddressStateName("CA");
		individualProvider
				.setPracticeLocationAddressTelephoneNumber("7603536151");
		individualProvider.setSecondLinePracticeLocationAddress("STE. 210");

		indProviders.add(individualProvider);

		// second one
		individualProvider = new IndividualProvider();

		individualProvider.setEnumerationDate("10/08/2009");
		individualProvider
				.setFirstLinePracticeLocationAddress("600 N WOLFE ST");
		individualProvider.setFirstName("MONICA");
		individualProvider.setLastName("VAN DONGEN");
		individualProvider.setMiddleName("LYNNE");
		individualProvider.setNamePrefix("MS.");
		individualProvider.setNameSuffix("");
		individualProvider.setNpi("1083949036");
		individualProvider.setPracticeLocationAddressCityName("BALTIMORE");
		individualProvider.setPracticeLocationAddressCountryCode("US");
		individualProvider.setPracticeLocationAddressPostalCode("212870005");
		individualProvider.setPracticeLocationAddressStateName("MD");
		individualProvider
				.setPracticeLocationAddressTelephoneNumber("4106141937");
		individualProvider.setSecondLinePracticeLocationAddress("BLALOCK 412");

		indProviders.add(individualProvider);

		return indProviders;

	}

	private List<OrganizationalProvider> setDataOrgProviders() {

		List<OrganizationalProvider> orgProviders = new ArrayList<OrganizationalProvider>();
		// first one
		OrganizationalProvider organizationalProvider = new OrganizationalProvider();
		organizationalProvider.setNpi("1174858088");
		organizationalProvider.setEnumerationDate("10/09/2009");
		organizationalProvider
				.setFirstLinePracticeLocationAddress("107 S 5TH ST");
		organizationalProvider.setOrgName("NEVAEH LLC");
		organizationalProvider.setAuthorizedOfficialFirstName("DEE ANNA");
		organizationalProvider.setAuthorizedOfficialLastName("SMALLWOOD");
		organizationalProvider.setAuthorizedOfficialNamePrefix("MS.");
		organizationalProvider
				.setAuthorizedOfficialTelephoneNumber("6024134412");
		organizationalProvider
				.setAuthorizedOfficialTitle("CHIEF ADMINISTION OFFICER");
		organizationalProvider.setPracticeLocationAddressCityName("EL CENTRO");
		organizationalProvider.setPracticeLocationAddressCountryCode("US");
		organizationalProvider
				.setPracticeLocationAddressPostalCode("922433024");
		organizationalProvider.setPracticeLocationAddressStateName("CA");
		organizationalProvider
				.setPracticeLocationAddressTelephoneNumber("7603536151");
		organizationalProvider.setSecondLinePracticeLocationAddress("STE. 210");

		orgProviders.add(organizationalProvider);

		// second one
		organizationalProvider = new OrganizationalProvider();

		organizationalProvider.setEnumerationDate("10/08/2009");
		organizationalProvider
				.setFirstLinePracticeLocationAddress("600 N WOLFE ST");
		organizationalProvider.setOrgName("NEVAEH LLC 2");
		organizationalProvider.setAuthorizedOfficialFirstName("DEE ANNA 2");
		organizationalProvider.setAuthorizedOfficialLastName("SMALLWOOD 2");
		organizationalProvider.setAuthorizedOfficialNamePrefix("MS. 2");
		organizationalProvider
				.setAuthorizedOfficialTelephoneNumber("6024134413");
		organizationalProvider
				.setAuthorizedOfficialTitle("CHIEF ADMINISTION OFFICER 2");
		organizationalProvider.setPracticeLocationAddressCityName("EL CENTRO");
		organizationalProvider.setNpi("1083949036");
		organizationalProvider.setPracticeLocationAddressCityName("BALTIMORE");
		organizationalProvider.setPracticeLocationAddressCountryCode("US");
		organizationalProvider
				.setPracticeLocationAddressPostalCode("212870005");
		organizationalProvider.setPracticeLocationAddressStateName("MD");
		organizationalProvider
				.setPracticeLocationAddressTelephoneNumber("4106141937");
		organizationalProvider
				.setSecondLinePracticeLocationAddress("BLALOCK 412");

		return orgProviders;

	}

}
