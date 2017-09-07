package gov.samhsa.c2s.pcm.service.pdf;

import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.patient.Patient;

import java.util.Date;

/**
 * The Interface ConsentPdfGenerator.
 */
public interface ConsentPdfGenerator {
    /**
     * Generate consent pdf.
     *
     * @param consent  the consent
     * @param patient  the patient information
     * @param isSigned determine whether to add the signing information
     * @return the byte[]
     */
    byte[] generateConsentPdf(Consent consent, Patient patient, boolean isSigned, Date attestedOn, String terms);
}
