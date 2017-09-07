package gov.samhsa.c2s.pcm.service.pdf;

import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.Date;

/**
 * The Interface ConsentPdfGenerator.
 */
public interface ConsentPdfGenerator {

    void addConsentTitle(String pdfType, float startYCoordinate, PDPage page, PDPageContentStream contentStream) throws IOException;

    /**
     * Generate consent pdf.
     *
     * @param consent  the consent
     * @param patient  the patient information
     * @param isSigned determine whether to add the signing information
     * @return the byte[]
     */
    byte[] generateConsentPdf(Consent consent, Patient patient, boolean isSigned, Date attestedOn, String terms) throws IOException;
}
