package gov.samhsa.c2s.pcm.service.pdf;

import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.patient.Patient;

import java.io.IOException;
import java.util.Date;

public interface ConsentRevocationPdfGenerator {

    byte[] generateConsentRevocationPdf(Consent consent, Patient patient, Date revokedOnDateTime, String consentRevocationTerm) throws IOException;
}
