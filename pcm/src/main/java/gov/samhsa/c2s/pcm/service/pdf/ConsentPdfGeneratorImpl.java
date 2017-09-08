package gov.samhsa.c2s.pcm.service.pdf;


import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.consent.ConsentDoNotShareSensitivityPolicyCode;
import gov.samhsa.c2s.pcm.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.c2s.pcm.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.c2s.pcm.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.c2s.pcm.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.c2s.pcm.domain.consent.ConsentShareForPurposeOfUseCode;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.domain.provider.AbstractProvider;
import gov.samhsa.c2s.pcm.domain.provider.IndividualProvider;
import gov.samhsa.c2s.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.c2s.pcm.domain.valueset.ValueSetCategory;
import gov.samhsa.c2s.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.c2s.pcm.infrastructure.exception.InvalidContentException;
import gov.samhsa.c2s.pcm.infrastructure.exception.PdfGenerationException;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.Column;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxService;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxStyle;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.TableAttribute;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.TextAlignment;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.util.PdfBoxHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConsentPdfGeneratorImpl implements ConsentPdfGenerator {
    private static final String CONSENT_PDF = "consent-pdf";
    private static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";
    private static final String SPACE_PATTERN = " ";

    private final PdfBoxService pdfBoxService;
    private final MessageSource messageSource;
    private final ValueSetCategoryRepository valueSetCategoryRepository;

    @Autowired
    public ConsentPdfGeneratorImpl(PdfBoxService pdfBoxService, MessageSource messageSource, ValueSetCategoryRepository valueSetCategoryRepository) {
        this.pdfBoxService = pdfBoxService;
        this.messageSource = messageSource;
        this.valueSetCategoryRepository = valueSetCategoryRepository;
    }

    @Override
    public void addConsentTitle(String titleMessageKey, float startYCoordinate, PDPage page, PDPageContentStream contentStream) throws IOException {
        String consentTitle = getI18nMessage(titleMessageKey);

        float titleFontSize = 20f;
        PDFont titleFont = PDType1Font.TIMES_BOLD;
        Color titleColor = Color.BLACK;
        float width = page.getMediaBox().getWidth() - 2 * PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER;
        pdfBoxService.addWrappedParagraph(consentTitle, titleFont, titleFontSize, titleColor, TextAlignment.LEFT, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, startYCoordinate, width, page, contentStream);
    }

    @Override
    public void addConsentReferenceNumberAndPatientInfo(Consent consent, Patient patient, float startYCoordinate, PDFont defaultFont, PDPageContentStream contentStream) throws IOException {
        String consentCreatedOn = PdfBoxHandler.formatDate(consent.getCreatedDateTime(), DATE_FORMAT_PATTERN);
        String consentReferenceNumber = consent.getConsentReferenceId();
        String patientFullName = patient.getFirstName().concat(SPACE_PATTERN + patient.getLastName());
        String patientBirthDate = PdfBoxHandler.formatDate(patient.getBirthDay(), DATE_FORMAT_PATTERN);

        final Color textColor = Color.BLACK;
        final float fontSize = PdfBoxStyle.TEXT_SMALL_SIZE;
        final PDFont contentFont = PDType1Font.TIMES_BOLD;

        // Add Consent Created On
        final String createdOnLabel = getI18nMessage("REVOCATION.PDF.DATE");
        pdfBoxService.addTextAtOffset(createdOnLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, startYCoordinate, contentStream);
        final float createdOnXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(createdOnLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(consentCreatedOn, contentFont, fontSize, textColor, createdOnXCoordinate, startYCoordinate, contentStream);

        // Add Consent Reference Number
        final float crnLabelYCoordinate = startYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        final String crnLabel = getI18nMessage("CONSENT.REFERENCE.NUMBER");
        pdfBoxService.addTextAtOffset(crnLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, crnLabelYCoordinate, contentStream);
        final float crnYCoordinate = crnLabelYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        pdfBoxService.addTextAtOffset(consentReferenceNumber, contentFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, crnYCoordinate, contentStream);

        // Add patient name
        final float nameYCoordinate = crnYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        final String nameLabel = getI18nMessage("PATIENT.NAME");
        pdfBoxService.addTextAtOffset(nameLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, nameYCoordinate, contentStream);
        final float nameXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(nameLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(patientFullName, contentFont, fontSize, textColor, nameXCoordinate, nameYCoordinate, contentStream);

        // Add patient DOB
        final String dobLabel = getI18nMessage("PATIENT.DOB");
        final float dobLabelXCoordinate = 310f;
        pdfBoxService.addTextAtOffset(dobLabel, defaultFont, fontSize, textColor, dobLabelXCoordinate, nameYCoordinate, contentStream);
        final float dobXCoordinate = dobLabelXCoordinate + PdfBoxHandler.targetedStringWidth(dobLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(patientBirthDate, contentFont, fontSize, textColor, dobXCoordinate, nameYCoordinate, contentStream);
    }

    @Override
    public void addConsentSigningDetails(Patient patient, Date signedOnDateTime, float startYCoordinate, PDFont defaultFont, PDPageContentStream contentStream) throws IOException {
        String patientName = patient.getFirstName().concat(SPACE_PATTERN + patient.getLastName());
        String email = patient.getEmail();

        final String signedByLabel = getI18nMessage("SIGNED.BY");
        final String emailLabel = getI18nMessage("EMAIL");
        final String signedOnLabel = getI18nMessage("SIGNED.ON");

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
    public byte[] generateConsentPdf(Consent consent, Patient patient, boolean isSigned, Date attestedOn, String consentTerms) throws IOException {
        Assert.notNull(consent, "Consent is required.");

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        // Create a new empty document
        PDDocument document = new PDDocument();

        // Create a new blank page with configured page size and add it to the document
        PDPage page = pdfBoxService.generatePage(CONSENT_PDF, document);
        log.debug("Configured page size is: " + pdfBoxService.getConfiguredPdfPageSize(CONSENT_PDF));

        // Set configured font
        PDFont defaultFont = pdfBoxService.getConfiguredPdfFont(CONSENT_PDF);
        log.debug("Configured font is: " + defaultFont);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            // Configure each drawing section yCoordinate in order to centralized adjust layout
            final float titleSectionStartYCoordinate = PdfBoxStyle.TOP_BOTTOM_MARGINS_OF_LETTER;
            final float consentReferenceNumberSectionStartYCoordinate = 690f;
            final float authorizationSectionStartYCoordinate = 590f;
            final float healthInformationSectionStartYCoordinate = 405f;
            final float consentTermsSectionStartYCoordinate = 220f;
            final float consentEffectiveDateSectionStartYCoordinate = 105f;
            final float consentSigningSectionStartYCoordinate = 70f;

            // Title
            final String titleMessageKey = "CONSENT.PDF.TITLE";
            addConsentTitle(titleMessageKey, titleSectionStartYCoordinate, page, contentStream);

            // Consent Reference Number and Patient information
            addConsentReferenceNumberAndPatientInfo(consent, patient, consentReferenceNumberSectionStartYCoordinate, defaultFont, contentStream);

            // Authorization to disclose section
            addAuthorizationToDisclose(consent, authorizationSectionStartYCoordinate, defaultFont, page, contentStream);

            // Health information to be disclosed section
            addHealthInformationToBeDisclose(consent, healthInformationSectionStartYCoordinate, defaultFont, page, contentStream);

            // Consent terms section
            addConsentTerms(consentTerms, patient, consentTermsSectionStartYCoordinate, defaultFont, page, contentStream);

            // Consent effective and expiration date
            addEffectiveAndExpirationDate(consent, consentEffectiveDateSectionStartYCoordinate, contentStream);

            // Consent signing details
            if (isSigned) {
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

    private void addAuthorizationToDisclose(Consent consent, float startYCoordinate, PDFont font, PDPage page, PDPageContentStream contentStream) throws IOException {
        final float cardXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER;
        final float colAWidth = 180f;
        final float colBWidth = 90f;
        final float colCWidth = 200f;
        final float colDWidth = 90f;
        String title = getI18nMessage("CONSENT.PDF.SECTION1.TITLE");
        drawSectionHeader(title, cardXCoordinate, startYCoordinate, page, contentStream);

        List<Column> tableColumns = Arrays.asList(new Column(colAWidth), new Column(colBWidth), new Column(colCWidth), new Column(colDWidth));

        // Provider permitted to disclose
        float providerPermittedStartYCoordinate = startYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        addProviderPermittedToDisclose(consent, tableColumns, providerPermittedStartYCoordinate, font, page, contentStream);

        // Provider disclosure is made to
        float providerDisclosureIsMadeToStartYCoordinate = 485f;
        addProviderDisclosureIsMadeTo(consent, tableColumns, providerDisclosureIsMadeToStartYCoordinate, font, page, contentStream);
    }

    private void addProviderPermittedToDisclose(Consent consent, List<Column> tableColumns, float startYCoordinate, PDFont font, PDPage page, PDPageContentStream contentStream) throws IOException {
        String label = getI18nMessage("CONSENT.PDF.SECTION1.CONTENT1");
        addAuthorizationTableHeader(label, startYCoordinate, tableColumns, font, contentStream);

        // From providers details
        final float fromProviderDetailsYCoordinate = 257f;
        Set<OrganizationalProvider> fromOrganizations = consent.getOrganizationalProvidersPermittedToDisclose().stream()
                .map(ConsentOrganizationalProviderPermittedToDisclose::getOrganizationalProvider)
                .collect(Collectors.toSet());

        Set<IndividualProvider> fromPractitioners = consent.getProvidersPermittedToDisclose().stream()
                .map(ConsentIndividualProviderPermittedToDisclose::getIndividualProvider)
                .collect(Collectors.toSet());

        addConsentProvidersDetails(fromOrganizations, fromPractitioners, tableColumns, fromProviderDetailsYCoordinate, page, contentStream);
    }

    private void addProviderDisclosureIsMadeTo(Consent consent, List<Column> tableColumns, float startYCoordinate, PDFont font, PDPage page, PDPageContentStream contentStream) throws IOException {
        String label = getI18nMessage("CONSENT.PDF.SECTION1.CONTENT2");
        addAuthorizationTableHeader(label, startYCoordinate, tableColumns, font, contentStream);

        // To providers details
        final float toProviderDetailsYCoordinate = 342f;
        Set<OrganizationalProvider> fromOrganizations = consent.getOrganizationalProvidersDisclosureIsMadeTo().stream()
                .map(ConsentOrganizationalProviderDisclosureIsMadeTo::getOrganizationalProvider)
                .collect(Collectors.toSet());

        Set<IndividualProvider> fromPractitioners = consent.getProvidersDisclosureIsMadeTo().stream()
                .map(ConsentIndividualProviderDisclosureIsMadeTo::getIndividualProvider)
                .collect(Collectors.toSet());

        addConsentProvidersDetails(fromOrganizations, fromPractitioners, tableColumns, toProviderDetailsYCoordinate, page, contentStream);
    }

    private void addConsentProvidersDetails(Set<OrganizationalProvider> organizations, Set<IndividualProvider> practitioners, List<Column> columns, float startYCoordinate, PDPage page, PDPageContentStream contentStream) throws IOException {
        float providerNameColWidth = columns.get(0).getCellWidth();
        float providerNPIColWidth = columns.get(1).getCellWidth();
        float providerAddressColWidth = columns.get(2).getCellWidth();
        float providerPhoneColWidth = columns.get(3).getCellWidth();

        for (OrganizationalProvider organization : organizations) {
            // Provider Name
            drawProviderDetails(organization.getOrgName(), PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER,
                    startYCoordinate, providerNameColWidth, page, contentStream);
            // Provider NPI Number
            drawProviderDetails(organization.getNpi(), PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + providerNameColWidth,
                    startYCoordinate, providerNPIColWidth, page, contentStream);
            // Provider Address
            drawProviderDetails(composeAddress(organization), PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + providerNameColWidth + providerNPIColWidth,
                    startYCoordinate, providerAddressColWidth, page, contentStream);
            // Provider Phone
            drawProviderDetails(organization.getPracticeLocationAddressTelephoneNumber(), PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + providerNameColWidth + providerNPIColWidth + providerAddressColWidth,
                    startYCoordinate, providerPhoneColWidth, page, contentStream);
        }

        for (IndividualProvider practitioner : practitioners) {
            // Provider Name
            drawProviderDetails(getFullName(practitioner.getFirstName(), practitioner.getMiddleName(), practitioner.getLastName()),
                    PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, startYCoordinate, providerNameColWidth, page, contentStream);
            // Provider NPI Number
            drawProviderDetails(practitioner.getNpi(), PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + providerNameColWidth,
                    startYCoordinate, providerNPIColWidth, page, contentStream);
            // Provider Address
            drawProviderDetails(composeAddress(practitioner), PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + providerNameColWidth + providerNPIColWidth,
                    startYCoordinate, providerAddressColWidth, page, contentStream);
            // Provider Phone
            drawProviderDetails(practitioner.getPracticeLocationAddressTelephoneNumber(), PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + providerNameColWidth + providerNPIColWidth + providerAddressColWidth,
                    startYCoordinate, providerPhoneColWidth, page, contentStream);
        }
    }

    private void drawProviderDetails(String providerInfo, float startXCoordinate, float startYCoordinate, float width, PDPage page, PDPageContentStream contentStream) throws IOException {
        final PDFont font = PDType1Font.TIMES_BOLD;
        final float fontSize = PdfBoxStyle.TEXT_SMALL_SIZE;
        final Color textColor = Color.BLACK;
        pdfBoxService.addWrappedParagraph(providerInfo, font, fontSize, textColor, TextAlignment.LEFT, startXCoordinate, startYCoordinate, width, page, contentStream);
    }

    private void addAuthorizationTableHeader(String label, float labelYCoordinate, List<Column> columnsWidth, PDFont font, PDPageContentStream contentStream) throws IOException {
        final float xCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER;
        final float headerYCoordinate = labelYCoordinate - PdfBoxStyle.SMALL_LINE_SPACE;
        pdfBoxService.addTextAtOffset(label, PDType1Font.TIMES_BOLD, PdfBoxStyle.TEXT_MEDIUM_SIZE, Color.BLACK, xCoordinate, labelYCoordinate, contentStream);

        final float rowHeight = 15f;
        final float cellMargin = 1f;
        // Provider table header
        String a1 = getI18nMessage("CONSENT.PDF.PROVIDER.NAME");
        String b1 = getI18nMessage("CONSENT.PDF.NPI");
        String c1 = getI18nMessage("CONSENT.PDF.ADDRESS");
        String d1 = getI18nMessage("CONSENT.PDF.PHONE");
        List<String> tableHeader = Arrays.asList(a1, b1, c1, d1);
        List<List<String>> header = Collections.singletonList(tableHeader);

        TableAttribute tableAttribute = TableAttribute.builder()
                .xCoordinate(PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER)
                .yCoordinate(headerYCoordinate)
                .rowHeight(rowHeight)
                .cellMargin(cellMargin)
                .contentFont(font)
                .contentFontSize(PdfBoxStyle.TEXT_SMALL_SIZE)
                .borderColor(Color.WHITE)
                .columns(columnsWidth)
                .build();

        pdfBoxService.addTableContent(contentStream, tableAttribute, header);
    }

    private void addHealthInformationToBeDisclose(Consent consent, float startYCoordinate, PDFont font, PDPage page, PDPageContentStream contentStream) throws IOException {
        float cardXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER;
        float labelYCoordinate = startYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;

        String title = getI18nMessage("CONSENT.PDF.SECTION2.TITLE");
        drawSectionHeader(title, cardXCoordinate, startYCoordinate, page, contentStream);

        // Medical Information
        addMedicalInformation(consent, labelYCoordinate, font, contentStream);

        // Purposes of use
        addPurposeOfUse(consent, labelYCoordinate, font, contentStream);
    }

    private void addMedicalInformation(Consent consent, float labelYCoordinate, PDFont font, PDPageContentStream contentStream) throws IOException {
        final float xCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER;
        final float listWidth = 286f;
        final String itemMarkerSymbol = "-";
        float subLabelYCoordinate = labelYCoordinate - 15f;
        float listYCoordinate = labelYCoordinate - 20f;
        String label = getI18nMessage("CONSENT.PDF.CATEGORY.TITLE");
        String subLabel = getI18nMessage("SENSITIVE.CATEGORY");

        pdfBoxService.addTextAtOffset(label, PDType1Font.TIMES_BOLD, PdfBoxStyle.TEXT_SMALL_SIZE, Color.BLACK, xCoordinate, labelYCoordinate, contentStream);
        pdfBoxService.addTextAtOffset(subLabel, font, PdfBoxStyle.TEXT_SMALL_SIZE, Color.BLACK, xCoordinate, subLabelYCoordinate, contentStream);

        List<String> sensitivityCategories = getMedicalInformation(consent);

        pdfBoxService.addUnorderedListContent(sensitivityCategories, itemMarkerSymbol, xCoordinate, listYCoordinate, listWidth, font, PdfBoxStyle.TEXT_SMALL_SIZE, contentStream);
    }

    private List<String> getMedicalInformation(Consent consent) {
        Set<String> medicalInformationListToShare = new HashSet<String>();

        List<ValueSetCategory> valueSetCategoryList = valueSetCategoryRepository
                .findAll();
        //All possible VSC
        for (ValueSetCategory valueSetCategory : valueSetCategoryList) {
            String valueSetName = valueSetCategory.getName();
            if (LocaleContextHolder.getLocale().getLanguage().equalsIgnoreCase("en")) {
                medicalInformationListToShare.add(valueSetName);
            } else {
                medicalInformationListToShare.add(getI18nMessage(valueSetCategory.getCode() + ".NAME"));
            }
        }

        for (final ConsentDoNotShareSensitivityPolicyCode item : consent.getDoNotShareSensitivityPolicyCodes()) {
            String vscName = item.getValueSetCategory().getName();
            if (LocaleContextHolder.getLocale().getLanguage().equalsIgnoreCase("en")) {
                medicalInformationListToShare.remove(vscName);
            } else {
                medicalInformationListToShare.remove(getI18nMessage(item.getValueSetCategory().getCode() + ".NAME"));
            }
        }
        return new ArrayList<>(medicalInformationListToShare);
    }

    private void addPurposeOfUse(Consent consent, float labelYCoordinate, PDFont font, PDPageContentStream contentStream) throws IOException {
        final float xCoordinate = 326f;
        final float listWidth = 280f;
        final String itemMarkerSymbol = "-";
        float listYCoordinate = labelYCoordinate - 5f;
        String label = getI18nMessage("CONSENT.PDF.PURPOSE.TITLE");

        pdfBoxService.addTextAtOffset(label, PDType1Font.TIMES_BOLD, PdfBoxStyle.TEXT_SMALL_SIZE, Color.BLACK, xCoordinate, labelYCoordinate, contentStream);

        List<String> purposes = getPurposeOfUse(consent);

        pdfBoxService.addUnorderedListContent(purposes, itemMarkerSymbol, xCoordinate, listYCoordinate, listWidth, font, PdfBoxStyle.TEXT_SMALL_SIZE, contentStream);
    }

    private List<String> getPurposeOfUse(Consent consent) {
        ArrayList<String> purposesOfUseList = new ArrayList<>();
        for (final ConsentShareForPurposeOfUseCode purposeOfUseCode : consent.getShareForPurposeOfUseCodes()) {
            if (LocaleContextHolder.getLocale().getLanguage().equalsIgnoreCase("en")) {
                purposesOfUseList.add(purposeOfUseCode.getPurposeOfUseCode().getDisplayName());
            } else {
                purposesOfUseList.add(getI18nMessage(purposeOfUseCode.getPurposeOfUseCode().getCode() + ".NAME"));
            }
        }
        return purposesOfUseList;
    }

    private void addConsentTerms(String consentTerms, Patient patient, float startYCoordinate, PDFont font, PDPage page, PDPageContentStream contentStream) throws IOException {
        float cardXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER;
        final float paragraphYCoordinate = startYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        final String title = getI18nMessage("CONSENT.PDF.SECTION3.TITLE");

        drawSectionHeader(title, cardXCoordinate, startYCoordinate, page, contentStream);

        final String userNameKey = "ATTESTER_FULL_NAME";
        String termsWithAttestedName = consentTerms.replace(userNameKey, patient.getFirstName().concat(SPACE_PATTERN + patient.getLastName()));

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
        String col1 = getI18nMessage("CONSENT.EFFECTIVE.DATE").concat(PdfBoxHandler.formatDate(consent.getStartDate(), DATE_FORMAT_PATTERN));
        String col2 = getI18nMessage("CONSENT.EXPIRATION.DATE").concat(PdfBoxHandler.formatDate(consent.getEndDate(), DATE_FORMAT_PATTERN));
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

    private String composeAddress(AbstractProvider provider) {
        return provider.getFirstLinePracticeLocationAddress()
                .concat(filterNullAddressValue(provider.getSecondLinePracticeLocationAddress()))
                .concat(filterNullAddressValue(provider.getPracticeLocationAddressCityName()))
                .concat(filterNullAddressValue(provider.getPracticeLocationAddressStateName()))
                .concat(filterNullAddressValue(provider.getPracticeLocationAddressPostalCode()))
                .concat(filterNullAddressValue(provider.getPracticeLocationAddressCountryCode()));
    }

    private static String filterNullAddressValue(String value) {
        final String commaPattern = ", ";
        if (value == null || value.isEmpty()) {
            return "";
        } else {
            return commaPattern.concat(value);
        }
    }

    private String getFullName(String firstName, String middleName, String lastName) {
        return firstName
                .concat(getMiddleName(middleName))
                .concat(SPACE_PATTERN + lastName);
    }

    private String getMiddleName(String middleName) {
        if (middleName == null) {
            return "";
        } else {
            return SPACE_PATTERN.concat(middleName);
        }
    }

    private String getI18nMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }
}
