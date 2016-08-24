package gov.samhsa.c2s.pcm.service.notification;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import gov.samhsa.c2s.pcm.domain.consent.AttestedConsent;
import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.domain.patient.PatientRepository;
import gov.samhsa.c2s.pcm.domain.provider.IndividualProvider;
import gov.samhsa.c2s.pcm.service.consent.ConsentStatus;
import gov.samhsa.c2s.pcm.service.notification.NotificationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    NotificationServiceImpl nst;

    @Before
    public void setUp() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);

    }

    @Test
    public void testnotificationStage_is_add_provider() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        String result = nst.notificationStage("username", null);
        assertEquals("notification_add_provider", result);
    }

    @Test
    public void testnotificationStage_is_add_one_provider_successed() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        IndividualProvider individualProvider = mock(IndividualProvider.class);
        Set<IndividualProvider> individualProviders = new HashSet<IndividualProvider>();
        individualProviders.add(individualProvider);
        when(patient.getIndividualProviders()).thenReturn(individualProviders);
        String result = nst.notificationStage("username", "add");
        assertEquals("notification_add_one_provider_successed", result);
    }

    @Test
    public void testnotificationStage_is_add_second_provider() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        IndividualProvider individualProvider = mock(IndividualProvider.class);
        Set<IndividualProvider> individualProviders = new HashSet<IndividualProvider>();
        individualProviders.add(individualProvider);
        when(patient.getIndividualProviders()).thenReturn(individualProviders);
        String result = nst.notificationStage("username", null);
        assertEquals("notification_add_second_provider", result);
    }

    @Test
    public void testnotificationStage_is_add_second_provider_successed() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        IndividualProvider individualProvider = mock(IndividualProvider.class);
        IndividualProvider individualProvider2 = mock(IndividualProvider.class);
        Set<IndividualProvider> individualProviders = new HashSet<IndividualProvider>();
        individualProviders.add(individualProvider);
        individualProviders.add(individualProvider2);
        when(patient.getIndividualProviders()).thenReturn(individualProviders);
        String result = nst.notificationStage("username", "add");
        assertEquals("notification_add_second_provider_successed", result);
    }

    @Test
    public void testnotificationStage_is_add_consent() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        IndividualProvider individualProvider = mock(IndividualProvider.class);
        IndividualProvider individualProvider2 = mock(IndividualProvider.class);
        Set<IndividualProvider> individualProviders = new HashSet<IndividualProvider>();
        individualProviders.add(individualProvider);
        individualProviders.add(individualProvider2);
        when(patient.getIndividualProviders()).thenReturn(individualProviders);
        String result = nst.notificationStage("username", null);
        assertEquals("notification_add_consent", result);
    }

    @Test
    public void testnotificationStage_is_add_consent_successed() {
        Patient patient = mock(Patient.class);
        IndividualProvider individualProvider = mock(IndividualProvider.class);
        IndividualProvider individualProvider2 = mock(IndividualProvider.class);
        Set<IndividualProvider> individualProviders = new HashSet<IndividualProvider>();
        Set<Consent> consents = new HashSet<Consent>();
        Consent consent = mock(Consent.class);

        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        when(patient.getIndividualProviders()).thenReturn(individualProviders);
        when(patient.getConsents()).thenReturn(consents);
        when(consent.getStatus())
                .thenReturn(ConsentStatus.CONSENT_SIGNED);

        consents.add(consent);
        individualProviders.add(individualProvider);
        individualProviders.add(individualProvider2);

        String result = nst.notificationStage("username", "add");

        assertEquals("notification_add_consent_successed", result);
    }

    @Test
    public void testcheckConsentSignedStatus_when_consent_size_0() {
        Set<Consent> consents = new HashSet<Consent>();
        boolean result = nst.checkConsentSignedStatus(consents);
        assertEquals(false, result);
    }

    @Test
    public void testcheckConsentSignedStatus_when_consent_is_saved() {
        Set<Consent> consents = new HashSet<Consent>();
        Consent consent = mock(Consent.class);
        consents.add(consent);

        when(consent.getStatus())
                .thenReturn(ConsentStatus.CONSENT_SAVED);

        boolean result = nst.checkConsentSignedStatus(consents);

        assertEquals(false, result);
    }

    @Test
    public void testcheckConsentSignedStatus_when_consent_is_signed() {
        Set<Consent> consents = new HashSet<Consent>();
        Consent consent = mock(Consent.class);
        consents.add(consent);

        when(consent.getStatus())
                .thenReturn(ConsentStatus.CONSENT_SIGNED);

        boolean result = nst.checkConsentSignedStatus(consents);

        assertEquals(true, result);
    }

    @Test
    public void testcheckConsentSignedStatus_when_consent_is_revoked() {
        Set<Consent> consents = new HashSet<Consent>();
        Consent consent = mock(Consent.class);
        AttestedConsent attestedConsent = mock(AttestedConsent.class);
        byte[] attestedConsentPDFContent = new byte[] { (byte) 0xba, (byte) 0x8a, };
        consents.add(consent);

        when(consent.getStatus())
                .thenReturn(ConsentStatus.CONSENT_SIGNED);
        when(consent.getAttestedConsent())
                .thenReturn(attestedConsent);
        when(attestedConsent.getAttestedPdfConsent())
                .thenReturn(attestedConsentPDFContent);

        boolean result = nst.checkConsentSignedStatus(consents);

        assertEquals(true, result);
    }

}
