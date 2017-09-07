package gov.samhsa.c2s.pcm.service.pdf;


import com.google.common.collect.ImmutableMap;
import gov.samhsa.c2s.pcm.config.PdfProperties;
import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.infrastructure.exception.InvalidContentException;
import gov.samhsa.c2s.pcm.infrastructure.exception.PdfGenerationException;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.Column;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxService;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxStyle;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.TableAttribute;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.util.PdfBoxHandler;
import gov.samhsa.c2s.pcm.service.consent.ConsentStatus;
import gov.samhsa.c2s.pcm.service.exception.PdfConfigMissingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ConsentPdfGeneratorImpl implements ConsentPdfGenerator {
    private static final String CONSENT_PDF = "consent-pdf";
    private static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";
    private static final String SPACE_PATTERN = " ";

    private final PdfBoxService pdfBoxService;
    private final PdfProperties pdfProperties;

    @Autowired
    public ConsentPdfGeneratorImpl(PdfBoxService pdfBoxService, PdfProperties pdfProperties) {
        this.pdfBoxService = pdfBoxService;
        this.pdfProperties = pdfProperties;
    }

    @Override
    public void addConsentTitle(String pdfType, float startYCoordinate, PDPage page, PDPageContentStream contentStream) throws IOException {
        String consentTitle = pdfProperties.getPdfConfigs().stream()
                .filter(pdfConfig -> pdfConfig.type.equalsIgnoreCase(pdfType))
                .map(PdfProperties.PdfConfig::getTitle)
                .findAny()
                .orElseThrow(PdfConfigMissingException::new);

        float titleFontSize = 20f;
        PDFont titleFont = PDType1Font.TIMES_BOLD;
        Color titleColor = Color.BLACK;
        pdfBoxService.addCenteredTextAtOffset(consentTitle, titleFont, titleFontSize, titleColor, startYCoordinate, page, contentStream);
    }

    @Override
    public void addConsentReferenceNumberAndPatientInfo(Consent consent, Patient patient, float startYCoordinate, PDFont defaultFont, PDPageContentStream contentStream) throws IOException {
        String consentReferenceNumber = consent.getConsentReferenceId();
        String patientFullName = patient.getFirstName().concat(SPACE_PATTERN + patient.getLastName());
        String patientBirthDate = PdfBoxHandler.formatDate(patient.getBirthDay(), DATE_FORMAT_PATTERN);

        final Color textColor = Color.BLACK;
        final float fontSize = PdfBoxStyle.TEXT_SMALL_SIZE;
        final PDFont contentFont = PDType1Font.TIMES_BOLD;

        // Add Consent Reference Number
        final String crnLabel = "Consent Reference Number: ";
        pdfBoxService.addTextAtOffset(crnLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, startYCoordinate, contentStream);
        final float crnXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(crnLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(consentReferenceNumber, contentFont, fontSize, textColor, crnXCoordinate, startYCoordinate, contentStream);

        // Add patient name
        final float nameYCoordinate = startYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        final String nameLabel = "Patient Name: ";
        pdfBoxService.addTextAtOffset(nameLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, nameYCoordinate, contentStream);
        final float nameXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(nameLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(patientFullName, contentFont, fontSize, textColor, nameXCoordinate, nameYCoordinate, contentStream);

        // Add patient DOB
        final String dobLabel = "Patient DOB: ";
        final float dobLabelXCoordinate = 300f;
        pdfBoxService.addTextAtOffset(dobLabel, defaultFont, fontSize, textColor, dobLabelXCoordinate, nameYCoordinate, contentStream);
        final float dobXCoordinate = dobLabelXCoordinate + PdfBoxHandler.targetedStringWidth(dobLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(patientBirthDate, contentFont, fontSize, textColor, dobXCoordinate, nameYCoordinate, contentStream);
    }

    @Override
    public void addConsentSigningDetails(Patient patient, Date signedOnDateTime, float startYCoordinate, PDFont defaultFont, PDPageContentStream contentStream) throws IOException {
        String patientName = patient.getFirstName().concat(SPACE_PATTERN + patient.getLastName());
        String email = patient.getEmail();

        final String signedByLabel = "Signed by: ";
        final String emailLabel = "Email: ";
        final String signedOnLabel = "Signed on: ";

        final PDFont contentFont = PDType1Font.TIMES_BOLD;
        final Color textColor = Color.BLACK;
        final float fontSize = PdfBoxStyle.TEXT_SMALL_SIZE;

        // Add Signed by
        pdfBoxService.addTextAtOffset(signedByLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, startYCoordinate, contentStream);
        final float crnXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(signedByLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(patientName, contentFont, fontSize, textColor, crnXCoordinate, startYCoordinate, contentStream);

        // Add Email
        final float emailYCoordinate = startYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        pdfBoxService.addTextAtOffset(emailLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, emailYCoordinate, contentStream);
        final float nameXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(emailLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(email, contentFont, fontSize, textColor, nameXCoordinate, emailYCoordinate, contentStream);

        // Add Signed on
        final float signedOnYCoordinate = emailYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        pdfBoxService.addTextAtOffset(signedOnLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, signedOnYCoordinate, contentStream);
        final float dobXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(signedOnLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(PdfBoxHandler.formatDate(signedOnDateTime, DATE_FORMAT_PATTERN), contentFont, fontSize, textColor, dobXCoordinate, signedOnYCoordinate, contentStream);
    }

    @Override
    public byte[] generateConsentPdf(Consent consent, Patient patient, Date attestedOn, String consentTerms) throws IOException {
        Assert.notNull(consent, "Consent is required.");

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        // Create a new empty document
        PDDocument document = new PDDocument();

        // Create a new blank page with configured page size and add it to the document
        PDPage page = pdfBoxService.generatePage(CONSENT_PDF, document);
        log.debug("Configured page size is: " + pdfBoxService.getConfiguredPdfFont(CONSENT_PDF));

        // Set configured font
        PDFont defaultFont = pdfBoxService.getConfiguredPdfFont(CONSENT_PDF);
        log.debug("Configured font is: " + defaultFont);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            // Configure each drawing section yCoordinate in order to centralized adjust layout
            final float titleSectionStartYCoordinate = page.getMediaBox().getHeight() - PdfBoxStyle.TOP_BOTTOM_MARGINS_OF_LETTER;
            final float consentReferenceNumberSectionStartYCoordinate = 690f;
            final float consentTermsSectionStartYCoordinate = 270f;
            final float consentEffectiveDateSectionStartYCoordinate = 120f;
            final float consentSigningSectionStartYCoordinate = 75f;

            // Title
            addConsentTitle(CONSENT_PDF, titleSectionStartYCoordinate, page, contentStream);

            // Consent Reference Number and Patient information
            addConsentReferenceNumberAndPatientInfo(consent, patient, consentReferenceNumberSectionStartYCoordinate, defaultFont, contentStream);

            // Consent terms section
            addConsentTerms(consentTerms, patient, consentTermsSectionStartYCoordinate, defaultFont, page, contentStream);

            // Consent effective and expiration date
            addEffectiveAndExpirationDate(consent, consentEffectiveDateSectionStartYCoordinate, contentStream);

            // Consent signing details
            if (consent.getStatus().equals(ConsentStatus.CONSENT_SIGNED)) {
                addConsentSigningDetails(patient, attestedOn, consentSigningSectionStartYCoordinate, defaultFont, contentStream);
            }

            // Make sure that the content stream is closed
            contentStream.close();

            // Save the document to an output stream
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

    private void addConsentTerms(String consentTerms, Patient patient, float startYCoordinate, PDFont font, PDPage page, PDPageContentStream contentStream) throws IOException {
        float cardXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER;
        final float paragraphYCoordinate = startYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        final String title = "CONSENT TERMS";

        drawSectionHeader(title, cardXCoordinate, startYCoordinate, page, contentStream);

        final String userNameKey = "ATTESTER_FULL_NAME";
        String termsWithAttestedName = StrSubstitutor.replace(consentTerms,
                ImmutableMap.of(userNameKey, patient.getFirstName().concat(SPACE_PATTERN + patient.getLastName())));

        try {
            pdfBoxService.addWrappedParagraphByLineBreaks(termsWithAttestedName, font, PdfBoxStyle.TEXT_SMALL_SIZE, Color.BLACK, paragraphYCoordinate, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, page, contentStream);
        } catch (Exception e) {
            log.error("Invalid character for cast specification", e);
            throw new InvalidContentException(e);
        }
    }

    private void drawSectionHeader(String title, float cardXCoordinate, float cardYCoordinate, PDPage page, PDPageContentStream contentStream) throws IOException {
        // Set background color
        Color color = new Color(73, 89, 105);
        float colorBoxWidth = page.getMediaBox().getWidth() - 2 * PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER;
        float colorBoxHeight = PdfBoxStyle.DEFAULT_TABLE_ROW_HEIGHT;
        PDFont titleFont = PDType1Font.TIMES_BOLD;
        float titleFontSize = PdfBoxStyle.TEXT_MEDIUM_SIZE;
        Color titleColor = Color.WHITE;

        pdfBoxService.addColorBox(color, cardXCoordinate, cardYCoordinate, colorBoxWidth, colorBoxHeight, page, contentStream);

        float titleYCoordinate = cardYCoordinate + (colorBoxHeight / 2)
                - ((titleFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * titleFontSize) / 4);

        pdfBoxService.addTextAtOffset(title, titleFont, titleFontSize, titleColor, cardXCoordinate + 4f, titleYCoordinate, contentStream);
    }

    private void addEffectiveAndExpirationDate(Consent consent, float startYCoordinate, PDPageContentStream contentStream) throws IOException {
        final float columnWidth = 180f;
        final float rowHeight = PdfBoxStyle.DEFAULT_TABLE_ROW_HEIGHT;
        final float cellMargin = 1f;

        // Prepare table content
        String col1 = "Effective Date: ".concat(PdfBoxHandler.formatDate(consent.getStartDate(), DATE_FORMAT_PATTERN));
        String col2 = "Expiration Date: ".concat(PdfBoxHandler.formatDate(consent.getEndDate(), DATE_FORMAT_PATTERN));
        java.util.List<String> firstRowContent = Arrays.asList(col1, col2);

        List<List<String>> tableContent = Collections.singletonList(firstRowContent);

        // Config each column width
        Column column1 = new Column(columnWidth);
        Column column2 = new Column(columnWidth);

        // Config Table attribute
        TableAttribute tableAttribute = TableAttribute.builder()
                .xCoordinate(PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER)
                .yCoordinate(startYCoordinate)
                .rowHeight(rowHeight)
                .cellMargin(cellMargin)
                .contentFont(PDType1Font.TIMES_BOLD)
                .contentFontSize(PdfBoxStyle.TEXT_SMALL_SIZE)
                .borderColor(Color.WHITE)
                .columns(Arrays.asList(column1, column2))
                .build();

        pdfBoxService.addTableContent(contentStream, tableAttribute, tableContent);
    }

}
