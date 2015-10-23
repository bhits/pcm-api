package gov.samhsa.pcm.service.notification;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.domain.consent.Consent;
import gov.samhsa.pcm.domain.consent.SignedPDFConsent;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.provider.IndividualProvider;

import java.util.HashSet;
import java.util.Set;

import gov.samhsa.pcm.service.notification.NotificationServiceImpl;
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
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		IndividualProvider individualProvider = mock(IndividualProvider.class);
		IndividualProvider individualProvider2 = mock(IndividualProvider.class);
		Set<IndividualProvider> individualProviders = new HashSet<IndividualProvider>();
		Set<Consent> consents = new HashSet<Consent>();
		Consent consent = mock(Consent.class);
		consents.add(consent);
		individualProviders.add(individualProvider);
		individualProviders.add(individualProvider2);
		when(patient.getIndividualProviders()).thenReturn(individualProviders);
		when(patient.getConsents()).thenReturn(consents);
		String result = nst.notificationStage("username", "add");
		assertEquals("notification_add_consent_successed", result);
	}

	@Test
	public void testcheckConsentReviewStatus_when_consent_size_0() {
		Set<Consent> consents = new HashSet<Consent>();
		boolean result = nst.checkConsentReviewStatus(consents);
		assertEquals(false, result);
	}

	@Test
	public void testcheckConsentReviewStatus_when_consent_has_not_reviewed() {
		Set<Consent> consents = new HashSet<Consent>();
		Consent consent = mock(Consent.class);
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(null);
		boolean result = nst.checkConsentReviewStatus(consents);
		assertEquals(false, result);
	}

	@Test
	public void testcheckConsentReviewStatus_when_consent_has_reviewed() {
		Set<Consent> consents = new HashSet<Consent>();
		Consent consent = mock(Consent.class);
		SignedPDFConsent signedPdfConsent = mock(SignedPDFConsent.class);
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(signedPdfConsent);
		boolean result = nst.checkConsentReviewStatus(consents);
		assertEquals(true, result);
	}

	@Test
	public void testcheckConsentSignedStatus_when_consent_size_0() {
		Set<Consent> consents = new HashSet<Consent>();
		boolean result = nst.checkConsentSignedStatus(consents);
		assertEquals(false, result);
	}

	@Test
	public void testcheckConsentSignedStatus_when_consent_has_not_reviewed() {
		Set<Consent> consents = new HashSet<Consent>();
		Consent consent = mock(Consent.class);
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(null);
		boolean result = nst.checkConsentSignedStatus(consents);
		assertEquals(false, result);
	}

	@Test
	public void testcheckConsentSignedStatus_when_consent_has_reviewed_not_signed() {
		Set<Consent> consents = new HashSet<Consent>();
		Consent consent = mock(Consent.class);
		SignedPDFConsent signedPdfConsent = mock(SignedPDFConsent.class);
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(signedPdfConsent);
		when(consent.getSignedPdfConsent().getSignedPdfConsentContent())
				.thenReturn(null);
		boolean result = nst.checkConsentSignedStatus(consents);
		assertEquals(false, result);
	}

	@Test
	public void testcheckConsentSignedStatus_when_consent_has_reviewed_signed() {
		Set<Consent> consents = new HashSet<Consent>();
		Consent consent = mock(Consent.class);
		SignedPDFConsent signedPdfConsent = mock(SignedPDFConsent.class);
		byte[] signedPdfConsentContent = new byte[] { (byte) 0xba, (byte) 0x8a, };
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(signedPdfConsent);
		when(consent.getSignedPdfConsent().getSignedPdfConsentContent())
				.thenReturn(signedPdfConsentContent);
		boolean result = nst.checkConsentSignedStatus(consents);
		assertEquals(true, result);
	}

}
