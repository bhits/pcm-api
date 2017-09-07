package gov.samhsa.c2s.pcm.service.consent;

import gov.samhsa.c2s.pcm.domain.consent.AttestedConsent;
import gov.samhsa.c2s.pcm.domain.consent.AttestedConsentRevocation;
import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.consent.ConsentRepository;
import gov.samhsa.c2s.pcm.domain.consent.ConsentTermsVersions;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.domain.patient.PatientRepository;
import gov.samhsa.c2s.pcm.domain.provider.IndividualProviderRepository;
import gov.samhsa.c2s.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.c2s.pcm.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.c2s.pcm.domain.reference.ClinicalDocumentSectionTypeCodeRepository;
import gov.samhsa.c2s.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.c2s.pcm.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.c2s.pcm.domain.reference.SensitivityPolicyCodeRepository;
import gov.samhsa.c2s.pcm.infrastructure.PhrService;
import gov.samhsa.c2s.pcm.infrastructure.dto.PatientDto;
import gov.samhsa.c2s.pcm.service.consentexport.ConsentExportService;
import gov.samhsa.c2s.pcm.service.dto.ConsentDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentListDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentPdfDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.c2s.pcm.service.patient.PatientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConsentServiceImplTest {

    final String PATIENT_EID = "PATIENT_EID";
    final String PATIENT_LocalID = "PATIENT_LocalId";
    final String NPI_1 = "NPI_1";
    final String NPI_2 = "NPI_2";
    final String MRN = "MRN";
    /**
     * The consent repository.
     */
    @Mock
    ConsentRepository consentRepository;
    /**
     * The individual provider repository.
     */
    @Mock
    IndividualProviderRepository individualProviderRepository;
    /**
     * The organizational provider repository.
     */
    @Mock
    OrganizationalProviderRepository organizationalProviderRepository;
    /**
     * The clinical document type code repository.
     */
    @Mock
    ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;
    /**
     * The clinical document section type code repository.
     */
    @Mock
    ClinicalDocumentSectionTypeCodeRepository clinicalDocumentSectionTypeCodeRepository;
    /**
     * The sensitivity policy code repository.
     */
    @Mock
    SensitivityPolicyCodeRepository sensitivityPolicyCodeRepository;
    /**
     * The purpose of use code repository.
     */
    @Mock
    PurposeOfUseCodeRepository purposeOfUseCodeRepository;
    /**
     * The consent check service.
     */
    @Mock
    ConsentCheckService consentCheckService;
    @Mock
    ConsentExportService consentExportService;
    @Mock
    Set<ConsentAssertion> consentAssertions;
    @Mock
    PolicyIdService policyIdService;

    @Mock
    PatientService patientService;

    @Mock
    PhrService phrService;

    @Mock
    ConsentTermsVersionsService consentTermsVersionsService;

    /**
     * The cst.
     */
    @InjectMocks
    ConsentServiceImpl cst;
    byte[] DOCUMENT_BYTES = "text" .getBytes();
    String DOCUMENT_FILE_NAME = "documentFileName";
    String DOCUMENT_NAME = "documentName";
    String SIGNED_DOCUMENT_URL = "signedDocumentUrl";
    String EMAIL = "consent2shar@gmail.com";

    /**
     * The patient repository.
     */
    @Mock
    private PatientRepository patientRepository;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        when(patientRepository.findOne(anyLong())).thenReturn(patient);
        when(patient.getEnterpriseIdentifier()).thenReturn(PATIENT_EID);
        when(patient.getMedicalRecordNumber()).thenReturn(PATIENT_LocalID);
        Set<OrganizationalProvider> ops = new HashSet<OrganizationalProvider>();
        OrganizationalProvider op1 = new OrganizationalProvider();
        OrganizationalProvider op2 = new OrganizationalProvider();
        op1.setNpi(NPI_1);
        op2.setNpi(NPI_2);
        ops.add(op1);
        ops.add(op2);
        when(patient.getOrganizationalProviders()).thenReturn(ops);
        when(patient.getMedicalRecordNumber()).thenReturn(MRN);

    }

    /**
     * Test saveConsent. Check if repository is called.
     */
    @Test
    public void testSaveConsent_Check_if_Repository_is_Called() {
        cst.saveConsent(mock(Consent.class));
        verify(consentRepository).save(any(Consent.class));
    }

    /**
     * Test updateConsent. Check if repository is called.
     */
    @Test
    public void testUpdateConsent_Check_if_Repository_is_Called() {
        cst.updateConsent(mock(Consent.class));
        verify(consentRepository).save(any(Consent.class));
    }

    /**
     * Test deleteConsent. Check if repository and findConsent is called.
     */
    @Test
    public void testDeleteConsent_Check_if_Repository_and_findConsent_is_called_when_Consent_is_Saved() {
        ConsentService cstspy = spy(cst);
        Consent consent = mock(Consent.class);

        when(cstspy.findConsent((long) 1))
                .thenReturn(consent);
        when(consent.getStatus())
                .thenReturn(ConsentStatus.CONSENT_SAVED);

        boolean isDeleteSuccess = cstspy.deleteConsent((long) 1);

        assertTrue(isDeleteSuccess);
        verify(consentRepository).delete(any(Consent.class));
    }

    /**
     * Test deleteConsent. Check if delete fails when consent is signed.
     */
    @Test
    public void testDeleteConsent_Check_if_Delete_Fails_when_Consent_is_Signed() {
        ConsentService cstspy = spy(cst);
        Consent consent = mock(Consent.class);

        when(cstspy.findConsent((long) 1)).thenReturn(consent);
        when(consent.getStatus())
                .thenReturn(ConsentStatus.CONSENT_SIGNED);

        boolean isDeleteSuccess = cstspy.deleteConsent((long) 1);

        assertFalse("Expected isDeleteSuccess to be false", isDeleteSuccess);
        verify(consentRepository, never()).delete(any(Consent.class));
    }

    @Test
    public void testDeleteConsent_Check_if_Delete_Fails_when_Consent_is_Revoked() {
        ConsentService cstspy = spy(cst);
        Consent consent = mock(Consent.class);

        when(cstspy.findConsent((long) 1)).thenReturn(consent);
        when(consent.getStatus())
                .thenReturn(ConsentStatus.REVOCATION_REVOKED);

        boolean isDeleteSuccess = cstspy.deleteConsent((long) 1);

        assertFalse("Expected isDeleteSuccess to be false", isDeleteSuccess);
        verify(consentRepository, never()).delete(any(Consent.class));
    }

    /**
     * Test countAllConsents. Check if repository is called.
     */
    @Test
    public void testCountAllConsents_Check_if_Repository_is_Called() {
        when(consentRepository.count()).thenReturn((long) 321);
        cst.countAllConsents();
        verify(consentRepository).count();
    }

    /**
     * Test findConsent. Check if repository is called.
     */
    @Test
    public void testFindConsent_Check_if_Repository_is_Called() {
        Consent consent = mock(Consent.class);
        when(consentRepository.findOne(anyLong())).thenReturn(consent);
        cst.findConsent((long) 321);
        verify(consentRepository).findOne(anyLong());
    }

    /**
     * Test findAllConsents. Check if repository is called.
     */
    @Test
    public void testFindAllConsents_Check_if_Repository_is_Called() {
        @SuppressWarnings("unchecked")
        List<Consent> consentList = mock(List.class);
        when(consentRepository.findAll()).thenReturn(consentList);
        cst.findAllConsents();
        verify(consentRepository).findAll();
    }

    /**
     * Test signConsent. Check if necessary domain bindings are set and
     * repository is called.
     */
    // FIXME (#15): Replace this test with a test for consent attestation
    /*@Test
    public void testSignConsent_Check_if_Necessary_Domain_Bindings_are_Set_and_Repository_is_Called() {
        Consent consent = mock(Consent.class);
        Patient patient = mock(Patient.class);
        when(consentRepository.findOne(anyLong())).thenReturn(consent);

        ConsentService spy = spy(cst);
        ConsentPdfDto consentPdfDto = mock(ConsentPdfDto.class);
        SignedPDFConsent signedPdfConsent = mock(SignedPDFConsent.class);
        when(spy.makeSignedPdfConsent()).thenReturn(signedPdfConsent);
        when(consent.getPatient()).thenReturn(patient);
        when(patient.getEmail()).thenReturn("patient@consent2share.com");
        spy.signConsent(consentPdfDto);

        // all the anystring() should be replaced after we've decided what to
        // put there
        verify(signedPdfConsent).setDocumentId(anyString());
        verify(signedPdfConsent).setDocumentNameBySender(anyString());
        verify(signedPdfConsent).setDocumentMessageBySender(anyString());
        verify(signedPdfConsent).setSignerEmail(anyString());
        verify(signedPdfConsent).setDocumentSignedStatus(anyString());
        verify(consent).setSignedPdfConsent(signedPdfConsent);
        verify(consentRepository).save(any(Consent.class));
    }*/

    // FIXME (#16): Replace this test with a test for consent revocation attestation
    /*@Test
    public void testSignConsentRevokation() {
        Consent consent = mock(Consent.class);
        Patient patient = mock(Patient.class);
        when(consent.getPatient()).thenReturn(patient);
        when(patient.getFirstName()).thenReturn("John");
        when(patient.getLastName()).thenReturn("Doe");

        when(consentRepository.findOne(anyLong())).thenReturn(consent);

        ConsentService spy = spy(cst);
        ConsentRevokationPdfDto consentRevokationPdfDto = mock(ConsentRevokationPdfDto.class);
        when(consentRevokationPdfDto.getRevokationType())
                .thenReturn("NO NEVER");
        SignedPDFConsentRevocation signedPDFConsentRevocation = mock(SignedPDFConsentRevocation.class);
        when(spy.makeSignedPDFConsentRevocation()).thenReturn(
                signedPDFConsentRevocation);
        spy.signConsentRevokation(consentRevokationPdfDto);

        verify(signedPDFConsentRevocation).setDocumentId(anyString());
        verify(signedPDFConsentRevocation).setDocumentNameBySender(anyString());
        verify(signedPDFConsentRevocation).setDocumentMessageBySender(
                anyString());
        verify(signedPDFConsentRevocation).setSignerEmail(anyString());
        verify(signedPDFConsentRevocation).setDocumentSignedStatus(anyString());
        verify(signedPDFConsentRevocation).setDocumentCreatedBy("Doe, John");
        verify(signedPDFConsentRevocation)
                .setDocumentSentOutForSignatureDateTime(any(Date.class));
        verify(consent).setSignedPdfConsentRevoke(signedPDFConsentRevocation);
        verify(consentRepository).save(any(Consent.class));
    }*/

    /**
     * Test findAllConsentsDtoByPatient .Verify consentListDtos add exact times
     * as the number of consentListdto in the list.
     */
    @Test
    public void testFindAllConsentsDtoByPatient_Verify_consentListDtos_add_exact_times_as_the_number_of_consentlistDto_in_the_List() {
        List<Consent> consents = new ArrayList<Consent>();
        List<Consent> spyConsents = spy(consents);
        for (int i = 0; i < 3; i++) {
            spyConsents.add(mock(Consent.class));
        }
        // for (Consent consent:spyConsents){
        // Set<ConsentIndividualProviderDisclosureIsMadeTo>
        // consentIndividualProviderDisclosureIsMadeToSet=
        // new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>();
        // ConsentIndividualProviderDisclosureIsMadeTo
        // consentIndividualProviderDisclosureIsMadeTo=mock(ConsentIndividualProviderDisclosureIsMadeTo.class);
        // when(consentIndividualProviderDisclosureIsMadeTo.getIndividualProvider().getFirstName()).thenReturn("John");
        // when(consentIndividualProviderDisclosureIsMadeTo.getIndividualProvider().getLastName()).thenReturn("Doe");
        // consentIndividualProviderDisclosureIsMadeToSet.add(consentIndividualProviderDisclosureIsMadeTo);
        // when(consent.getProvidersDisclosureIsMadeTo()).thenReturn(consentIndividualProviderDisclosureIsMadeToSet);
        //
        // Set<ConsentOrganizationalProviderDisclosureIsMadeTo>
        // consentOrganizationalProviderDisclosureIsMadeToSet=
        // new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>();
        // ConsentOrganizationalProviderDisclosureIsMadeTo
        // consentOrganizationalProviderDisclosureIsMadeTo=
        // mock(ConsentOrganizationalProviderDisclosureIsMadeTo.class);
        // when(consentOrganizationalProviderDisclosureIsMadeTo.getOrganizationalProvider().getOrgName()).thenReturn("abc company");
        // consentOrganizationalProviderDisclosureIsMadeToSet.add(consentOrganizationalProviderDisclosureIsMadeTo);
        // when(consent.getOrganizationalProvidersDisclosureIsMadeTo()).thenReturn(consentOrganizationalProviderDisclosureIsMadeToSet);
        //
        // }

        when(consentRepository.findByPatient(any(Patient.class))).thenReturn(
                spyConsents);
        ConsentService spy = spy(cst);
        @SuppressWarnings("unchecked")
        ArrayList<ConsentListDto> consentListDtos = mock(ArrayList.class);
        when(spy.makeConsentListDtos()).thenReturn(consentListDtos);
        spy.findAllConsentsDtoByPatient((long) 123);
        verify(consentListDtos, times(3)).add(any(ConsentListDto.class));
    }

    /**
     * Test findConsentPdfDto when consent is signed.
     */
    @Test
    public void testFindConsentPdfDto_when_Consent_is_Signed() {
        Consent consent = mock(Consent.class);
        AttestedConsent attestedConsent = mock(AttestedConsent.class);
        ConsentPdfDto consentPdfDto = mock(ConsentPdfDto.class);
        Patient patient = mock(Patient.class);
        byte[] attestedPdfConsentContent = new byte[]{1, 2, 3};
        byte[] unattestedPdfConsentContent = new byte[]{4, 5, 6};
        ConsentService cstSpy = spy(cst);

        when(patient.getFirstName())
                .thenReturn("John");
        when(patient.getLastName())
                .thenReturn("Doe");
        when(attestedConsent.getAttestedPdfConsent())
                .thenReturn(attestedPdfConsentContent);
        when(consent.getPatient())
                .thenReturn(patient);
        when(consent.getAttestedConsent())
                .thenReturn(attestedConsent);
        when(consent.getUnAttestedPdfConsent())
                .thenReturn(unattestedPdfConsentContent);
        when(consent.getName())
                .thenReturn("A regular consent");
        when(consent.getStatus())
                .thenReturn(ConsentStatus.CONSENT_SIGNED);
        when(consentRepository.findOne(anyLong()))
                .thenReturn(consent);
        when(cstSpy.makeConsentPdfDto())
                .thenReturn(consentPdfDto);

        cstSpy.findConsentPdfDto((long) 2);

        verify(consentPdfDto).setContent(attestedPdfConsentContent);
        verify(consentPdfDto, never()).setContent(unattestedPdfConsentContent);
        verify(consentPdfDto).setFilename(anyString());
        verify(consentPdfDto).setConsentName("A regular consent");
        verify(consentPdfDto).setId((long) 2);
    }

    /**
     * Test find consentPdfDto when consent is unsigned.
     */
    @Test
    public void testFindConsentPdfDto_when_Consent_is_Saved() {
        Consent consent = mock(Consent.class);
        AttestedConsent attestedConsent = mock(AttestedConsent.class);
        ConsentPdfDto consentPdfDto = mock(ConsentPdfDto.class);
        Patient patient = mock(Patient.class);
        byte[] unattestedPdfConsentContent = new byte[]{4, 5, 6};

        when(attestedConsent.getAttestedPdfConsent())
                .thenReturn(null);
        when(patient.getFirstName())
                .thenReturn("John");
        when(patient.getLastName())
                .thenReturn("Doe");
        when(consent.getPatient())
                .thenReturn(patient);
        when(consent.getAttestedConsent())
                .thenReturn(attestedConsent);
        when(consent.getStatus())
                .thenReturn(ConsentStatus.CONSENT_SAVED);
        when(consent.getUnAttestedPdfConsent())
                .thenReturn(unattestedPdfConsentContent);
        when(consent.getName())
                .thenReturn("A regular consent");
        when(consentRepository.findOne(anyLong()))
                .thenReturn(consent);
        ConsentService cstSpy = spy(cst);
        when(cstSpy.makeConsentPdfDto())
                .thenReturn(consentPdfDto);

        cstSpy.findConsentPdfDto((long) 2);

        verify(consentPdfDto, never()).setContent(null);
        verify(consentPdfDto).setContent(unattestedPdfConsentContent);
        verify(consentPdfDto).setFilename(anyString());
        verify(consentPdfDto).setConsentName("A regular consent");
        verify(consentPdfDto).setId((long) 2);
    }

    /**
     * Test isConsentBelongToThisUser when succeeds.
     */
    @Test
    public void testIsConsentBelongToThisUser_when_succeeds() {
        Consent consent = mock(Consent.class);
        Patient patient = mock(Patient.class);

        when(patientRepository.findOne(anyLong())).thenReturn(patient);
        when(consentRepository.findOne(anyLong())).thenReturn(consent);
        when(consent.getPatient()).thenReturn(patient);
        Boolean result = cst.isConsentBelongToThisUser((long) 1, (long) 2);
        assertEquals(true, result);
    }

    /**
     * Test isConsentBelongToThisUser when fails.
     */
    @Test
    public void testIsConsentBelongToThisUser_when_fails() {
        Consent consent = mock(Consent.class);
        Patient patient = mock(Patient.class);
        Patient patient2 = mock(Patient.class);
        when(patientRepository.findOne(anyLong())).thenReturn(patient);
        when(consentRepository.findOne(anyLong())).thenReturn(consent);
        when(consent.getPatient()).thenReturn(patient2);
        Boolean result = cst.isConsentBelongToThisUser((long) 1, (long) 2);
        assertEquals(false, result);
    }

    /**
     * Test save consent.
     *
     * @throws Exception
     */
    @Test
    public void testSaveConsent() throws Exception {
        // Arrange
        ConsentService cstSpy = spy(cst);
        Consent consent = mock(Consent.class);

        PatientDto patientDto = mock(PatientDto.class);
        when(phrService.getPatientProfile())
                .thenReturn(patientDto);

        ConsentTermsVersions consentTermsVersions = mock(ConsentTermsVersions.class);
        when(consentTermsVersions.getConsentTermsText())
                .thenReturn("TEST CONSENT TERMS TEXT");

        when(consentTermsVersionsService.getEnabledConsentTermsVersion())
                .thenReturn(consentTermsVersions);

        String xacmlMock = "xacmlMock";
        String policyIdMock = "policyIdMock";
        when(cstSpy.makeConsent()).thenReturn(consent);
        when(consentExportService.exportConsent2XACML(any(Consent.class)))
                .thenReturn(xacmlMock);
        when(
                consentExportService
                        .exportConsent2CDAR2ConsentDirective(any(Consent.class)))
                .thenReturn(xacmlMock);
        when(
                consentExportService
                        .exportConsent2XacmlPdfConsentFrom(any(Consent.class)))
                .thenReturn(xacmlMock);
        when(
                consentExportService
                        .exportConsent2XacmlPdfConsentTo(any(Consent.class)))
                .thenReturn(xacmlMock);
        ConsentDto consentDto = mock(ConsentDto.class);
        when(consentCheckService.getConflictConsent(consentDto)).thenReturn(
                null);
        when(policyIdService.generatePolicyId(consentDto, MRN)).thenReturn(
                policyIdMock);
        Set<String> organizationalProvidersDisclosureIsMadeTo = new HashSet<String>();
        organizationalProvidersDisclosureIsMadeTo.add(NPI_1);
        Set<String> organizationalProvidersPermittedToDisclose = new HashSet<String>();
        organizationalProvidersPermittedToDisclose.add(NPI_2);
        when(consentDto.getOrganizationalProvidersDisclosureIsMadeTo())
                .thenReturn(organizationalProvidersDisclosureIsMadeTo);
        when(consentDto.getOrganizationalProvidersPermittedToDisclose())
                .thenReturn(organizationalProvidersPermittedToDisclose);

        // Act
        cstSpy.saveConsent(consentDto, 0);

        // Assert
        verify(consentRepository).save(consent);
        verify(consentAssertions).forEach(any(Consumer.class));
    }

    /**
     * Test if makeConsentPdfDto return correct class.
     */
    @Test
    public void testMakeConsentPdfDto_return_correct_class() {
        Object object = cst.makeConsentPdfDto();
        String className = object.getClass().getName();
        assertEquals(ConsentPdfDto.class.getName(),
                className);
    }

    /**
     * Test if makeConsentListDtos return correct class.
     */
    @Test
    public void testMakeConsentListDtos_return_correct_class() {
        Object object = cst.makeConsentListDtos();
        String className = object.getClass().getName();
        assertEquals("java.util.ArrayList", className);
    }

    /**
     * Test find consent entries.
     */
    @Test
    public void testFindConsentEntries() {
        @SuppressWarnings("unchecked")
        Page<Consent> consentList = mock(Page.class);
        when(
                consentRepository
                        .findAll(any(org.springframework.data.domain.PageRequest.class)))
                .thenReturn(consentList);
        Object object = cst.findConsentEntries(0, 5);
        verify(consentRepository).findAll(
                any(org.springframework.data.domain.PageRequest.class));
        assertEquals("java.util.LinkedList", object.getClass().getName());
    }

    /**
     * Test if makeConsent return correct class.
     */
    @Test
    public void testMakeConsent_return_correct_class() {
        Object object = cst.makeConsent();
        String className = object.getClass().getName();
        assertEquals(Consent.class.getName(),
                className);
    }

    /**
     * Test make consent dto_return_correct_class.
     */
    @Test
    public void testMakeConsentDto_return_correct_class() {
        Object object = cst.makeConsentDto();
        assertEquals(ConsentDto.class.getName(), object
                .getClass().getName());
    }

    /**
     * Test are there duplicates_when_there_are_duplicates.
     */
    @Test
    public void testAreThereDuplicates_when_there_are_duplicates() {
        Set<String> set1 = new HashSet<String>();
        Set<String> set2 = new HashSet<String>();

        String a = "abc";
        String b = "efg";
        String c = "hij";
        String d = "klm";

        set1.add(a);
        set1.add(b);
        set1.add(c);
        set2.add(c);
        set2.add(d);

        assertEquals(true, cst.areThereDuplicatesInTwoSets(set1, set2));
    }

    /**
     * Test are there duplicates_when_there_are_no_duplicates.
     */
    @Test
    public void testAreThereDuplicates_when_there_are_no_duplicates() {
        Set<String> set1 = new HashSet<String>();
        Set<String> set2 = new HashSet<String>();

        String a = "abc";
        String b = "efg";
        String c = "hij";
        String d = "klm";

        set1.add(a);
        set1.add(b);
        set2.add(c);
        set2.add(d);

        assertEquals(false, cst.areThereDuplicatesInTwoSets(set1, set2));
    }

    @Test
    public void testFindConsentRevokationPdfDto() {
        ConsentService consentService = spy(cst);
        ConsentRevokationPdfDto consentRevokationPdfDto = mock(ConsentRevokationPdfDto.class);
        Consent consent = mock(Consent.class);
        AttestedConsentRevocation attestedConsentRevocation = mock(AttestedConsentRevocation.class);
        Patient patient = mock(Patient.class);

        when(consentService.makeConsentRevokationPdfDto())
                .thenReturn(consentRevokationPdfDto);
        when(consentRepository.findOne(anyLong()))
                .thenReturn(consent);
        when(consent.getAttestedConsentRevocation())
                .thenReturn(attestedConsentRevocation);
        when(consent.getPatient())
                .thenReturn(patient);
        when(patient.getFirstName())
                .thenReturn("John");
        when(patient.getLastName())
                .thenReturn("Doe");
        when(consent.getStatus())
                .thenReturn(ConsentStatus.REVOCATION_REVOKED);
        when(attestedConsentRevocation.getAttestedPdfConsentRevoke())
                .thenReturn(new byte[]{1, 2, 3});

        consentService.findConsentRevokationPdfDto((long) 1);

        verify(consentRevokationPdfDto).setFilename(anyString());
        verify(consentRevokationPdfDto).setConsentName(anyString());
        verify(consentRevokationPdfDto).setId(anyLong());
    }

    @Test
    public void testGetConsentStatusWhenConsentIsSigned() {
        Consent consent = mock(Consent.class);
        doReturn(ConsentStatus.CONSENT_SIGNED).when(consent).getStatus();
        doReturn(consent).when(consentRepository).findOne(anyLong());
        assertEquals(ConsentStatus.CONSENT_SIGNED, cst.getConsentStatus((long) 1));
    }

    @Test
    public void testGetConsentStatusWhenConsentIsSaved() {
        Consent consent = mock(Consent.class);
        doReturn(ConsentStatus.CONSENT_SAVED).when(consent).getStatus();
        doReturn(consent).when(consentRepository).findOne(anyLong());
        assertEquals(ConsentStatus.CONSENT_SAVED, cst.getConsentStatus((long) 1));
    }

    @Test
    public void testGetConsentStatusWhenConsentIsRevoked() {
        Consent consent = mock(Consent.class);
        doReturn(ConsentStatus.REVOCATION_REVOKED).when(consent).getStatus();
        doReturn(consent).when(consentRepository).findOne(anyLong());
        assertEquals(ConsentStatus.REVOCATION_REVOKED, cst.getConsentStatus((long) 1));
    }

    @Test
    public void testValidateConsentDate_when_null() {
        assertEquals(false, cst.validateConsentDate(null, null));
    }

    @Test
    public void testValidateConsentDate_when_startDate_is_previous() {
        assertEquals(false, cst.validateConsentDate(new Date(100, 06, 04),
                new Date(200, 06, 04)));
    }

    @Test
    public void testValidateConsentDate_when_startDate_is_previous_endDate() {
        assertEquals(false, cst.validateConsentDate(new Date(117, 06, 04),
                new Date(117, 06, 02)));
    }

    @Test
    public void testValidateConsentDate_true() {
        assertEquals(true, cst.validateConsentDate(new Date(117, 06, 04),
                new Date(117, 06, 19)));
    }

}
