package gov.samhsa.c2s.pcm.service.pdf;

import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.infrastructure.exception.InvalidContentException;
import gov.samhsa.c2s.pcm.infrastructure.exception.PdfGenerationException;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxService;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class ConsentRevocationPdfGeneratorImpl implements ConsentRevocationPdfGenerator {
    private static final String CONSENT_REVOCATION_PDF = "consent-revocation-pdf";

    private final PdfBoxService pdfBoxService;
    private final ConsentPdfGenerator consentPdfGenerator;

    @Autowired
    public ConsentRevocationPdfGeneratorImpl(PdfBoxService pdfBoxService, ConsentPdfGenerator consentPdfGenerator) {
        this.pdfBoxService = pdfBoxService;
        this.consentPdfGenerator = consentPdfGenerator;
    }

    @Override
    public byte[] generateConsentRevocationPdf(Consent consent, Patient patient, Date revokedOnDateTime, String consentRevocationTerm) throws IOException {
        Assert.notNull(consent, "Consent is required.");

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        // Create a new empty document
        PDDocument document = new PDDocument();

        // Create a new blank page with configured page size and add it to the document
        PDPage page = pdfBoxService.generatePage(CONSENT_REVOCATION_PDF, document);
        log.debug("Configured page size is: " + pdfBoxService.getConfiguredPdfFont(CONSENT_REVOCATION_PDF));

        // Set configured font
        PDFont defaultFont = pdfBoxService.getConfiguredPdfFont(CONSENT_REVOCATION_PDF);
        log.debug("Configured font is: " + defaultFont);
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            // Configure each drawing section yCoordinate in order to centralized adjust layout
            final float titleSectionStartYCoordinate = PdfBoxStyle.TOP_BOTTOM_MARGINS_OF_LETTER;
            final float consentReferenceNumberSectionStartYCoordinate = 670f;
            final float consentRevocationTermsSectionStartYCoordinate = 570f;
            final float consentRevocationSigningSectionStartYCoordinate = 230f;

            // Title
            final String titleMessageKey = "REVOCATION.PDF.TITLE";
            consentPdfGenerator.addConsentTitle(titleMessageKey, titleSectionStartYCoordinate, page, contentStream);

            // Consent Reference Number and Patient information
            consentPdfGenerator.addConsentReferenceNumberAndPatientInfo(consent, patient, consentReferenceNumberSectionStartYCoordinate, defaultFont, contentStream);

            // Consent revocation terms
            addConsentRevocationTerms(consentRevocationTerm, consentRevocationTermsSectionStartYCoordinate, defaultFont, page, contentStream);

            // Revocation signing details
            consentPdfGenerator.addConsentSigningDetails(patient, revokedOnDateTime, consentRevocationSigningSectionStartYCoordinate, defaultFont, contentStream);

            // Make sure that the content stream is closed
            contentStream.close();

            //Save the document to an output stream
            document.save(pdfOutputStream);

            return pdfOutputStream.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new PdfGenerationException(e);
        } finally {
            pdfOutputStream.close();
            // finally make sure that the document is properly closed
            document.close();
        }
    }

    private void addConsentRevocationTerms(String consentRevocationTerm, float startYCoordinate, PDFont defaultFont, PDPage page, PDPageContentStream contentStream) {
        try {
            pdfBoxService.addWrappedParagraphByLineBreaks(consentRevocationTerm, defaultFont, PdfBoxStyle.TEXT_SMALL_SIZE, Color.BLACK, startYCoordinate, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, page, contentStream);
        } catch (Exception e) {
            log.error("Invalid character for cast specification", e);
            throw new InvalidContentException(e);
        }
    }
}
