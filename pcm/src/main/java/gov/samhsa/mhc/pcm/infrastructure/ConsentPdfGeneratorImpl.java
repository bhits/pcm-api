/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.mhc.pcm.infrastructure;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gov.samhsa.mhc.pcm.domain.consent.*;
import gov.samhsa.mhc.pcm.domain.provider.AbstractProvider;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalConceptCode;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.mhc.pcm.domain.reference.StateCode;
import gov.samhsa.mhc.pcm.domain.valueobject.Address;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategory;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * The Class ConsentPdfGeneratorImpl.
 */
@Service
public class ConsentPdfGeneratorImpl implements ConsentPdfGenerator {

    /** The value set category repository. */
    @Autowired
    private ValueSetCategoryRepository valueSetCategoryRepository;

    /** The clinical document type code repository. */
    @Autowired
    private ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.domain.consent.ConsentPdfGenerator#
     * generate42CfrPart2Pdf(gov.samhsa.consent2share.domain.consent.Consent)
     */
    @Override
    public byte[] generate42CfrPart2Pdf(Consent consent) {
        Assert.notNull(consent, "Consent is required.");

        ArrayList<String> ary_parConsentAgreementTextStrings = new ArrayList<>();
        ary_parConsentAgreementTextStrings.add("I understand that my records are protected under the federal regulations governing Confidentiality of Alcohol and Drug Abuse Patient Records, 42 CFR Part 2, and cannot be disclosed without my written consent unless otherwise provided for in the regulations, and may not be redisclosed without my written permission or as otherwise permitted by 42 CFR part 2. I also understand that I may revoke this consent at any time except to the extent that action has been taken in reliance on it, and that in any event this consent expires automatically as follows:");

        Font fontbold = FontFactory.getFont("Helvetica", 12, Font.BOLD);
        Font fontheader = FontFactory.getFont("Helvetica", 19, Font.BOLD);
        Font fontnotification = FontFactory.getFont("Helvetica", 16, Font.ITALIC);

        String strConsentIdValue = String.valueOf(consent.getConsentReferenceId());
        String strConsentDescriptionValue = consent.getDescription();
        Date dateConsentEndDate = consent.getEndDate();
        Date dateConsentStartDate = consent.getStartDate();

        String strPatientNameValue = consent.getPatient().getFirstName() + " " + consent.getPatient().getLastName() + "\n";
        Date datePatientDOB = consent.getPatient().getBirthDay();
        Address addrsPatientAddress = consent.getPatient().getAddress();

        Document document = new Document();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, pdfOutputStream);

            document.open();

            //New code

            // Title
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            document.add(createParagraphWithContent("Consent to Share My Health Information", titleFont));

            // Blank line
            document.add( Chunk.NEWLINE );

            // Consent Created Date
            document.add(createParagraphWithContent("Created On: " + "05/26/2016" , null));

            //consent Reference Number
            document.add(createConsentReferenceNumberTable(consent));

            document.add(new Paragraph(" "));

            //Patient Name and date of birth
            document.add(createPatientNameAndDOBTable(consent));

            document.add(new Paragraph(" "));

            //Authorization to disclose section
            document.add(createSectionTitle("AUTHORIZATION TO DISCLOSE"));
            //Authorizes
            document.add(new Paragraph("Authorizes: "));

            document.add(createProviderTable(null));
            //To disclose to
            document.add(new Paragraph("To disclose to: "));

            document.add(createProviderTable(null));

            //Health information to be disclosed section
            document.add(createSectionTitle("HEALTH INFORMATION TO BE DISCLOSED"));

            document.add(new Paragraph(" "));

            //Consent terms section
            document.add(createSectionTitle("CONSENT TERMS"));

            // Consent term
            document.add(createConsentTerms());

            document.add(new Paragraph(" "));

            // Consent effective and expiration date
            document.add(createStartAndEndDateTable(consent));


            document.close();

        } catch (Throwable e) {
            // TODO log exception
            e.printStackTrace();
            throw new ConsentPdfGenerationException("Exception when trying to generate pdf", e);
        }

        byte[] pdfBytes = pdfOutputStream.toByteArray();

        return pdfBytes;
    }



    //Create document title
    private Paragraph createParagraphWithContent(String title, Font font){
        Paragraph titleParagraph = null;

        if(font != null){
            titleParagraph = new Paragraph(title, font);
        }else{
            titleParagraph = new Paragraph(title);
        }

        return titleParagraph;
    }

    // Create borderless table
    private PdfPTable  createBorderlessTable(int column){
        PdfPTable table = new PdfPTable(column);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(0);
        return table;
    }

    private PdfPCell createBorderlessCell(String content, Font font){
        PdfPCell cell = null;

        if(font != null){
            cell = new PdfPCell(new Paragraph(content, font));
        }else {
            cell = new PdfPCell(new Paragraph(content));
        }
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingLeft(0);

        return cell;
    }

    private PdfPTable createConsentReferenceNumberTable(Consent consent){
        PdfPTable  consentReferenceNumberTable  = createBorderlessTable(1);

        if(consent != null){
            consentReferenceNumberTable.addCell(createBorderlessCell("Consent Reference Number:", null));
            Font consentRefNumberFont = new Font(Font.FontFamily.TIMES_ROMAN, 11);
            consentReferenceNumberTable.addCell(createBorderlessCell(consent.getConsentReferenceId(),consentRefNumberFont));
        }

        return consentReferenceNumberTable;
    }

    private Chunk createChunkWithFont(String text, Font textFont){
        Chunk labelChunk = null;
        if(text != null && textFont != null ){
            labelChunk = new Chunk(text, textFont);
        }else if(text != null){
            labelChunk = new Chunk(text);
        }
        return labelChunk;
    }

    private Paragraph createCellContent(String label, Font labelFont, String value, Font valueFont){
        Paragraph content = new Paragraph();
        content.add(createChunkWithFont(label, labelFont));
        content.add(createChunkWithFont(value, valueFont));
        return content;
    }

    private PdfPTable createPatientNameAndDOBTable(Consent consent) {
        PdfPTable consentReferenceNumberTable = createBorderlessTable(2);

        if (consent != null) {
            Font patientInfoFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
            PdfPCell patientNameCell = new PdfPCell(createCellContent("Patient Name: ", null, "Tomson Ngassa", patientInfoFont));
            patientNameCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell patientDOBCell = new PdfPCell(createCellContent("Patient DOB: ", null, "05/27/2015", patientInfoFont));
            patientDOBCell.setBorder(Rectangle.NO_BORDER);

            consentReferenceNumberTable.addCell(patientNameCell);
            consentReferenceNumberTable.addCell(patientDOBCell);
        }

        return consentReferenceNumberTable;
    }

    private String formatDate(Date aDate){
        String dateFormat  = "MM/dd/yyyy";
        return new SimpleDateFormat(dateFormat).format(aDate);
    }

    private PdfPTable createStartAndEndDateTable(Consent consent) {
        PdfPTable consentStartAndEndDateTable = createBorderlessTable(3);

        if (consent != null) {
            Font patientDateFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
            PdfPCell EffectiveDateCell = new PdfPCell(createCellContent("Effective Date: ", patientDateFont, formatDate(consent.getStartDate()), patientDateFont));
            EffectiveDateCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell expirationDateCell = new PdfPCell(createCellContent("Expiration Date: ", patientDateFont,formatDate(consent.getEndDate()), patientDateFont));
            expirationDateCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell emptyCell = new PdfPCell();
            emptyCell.setBorder(Rectangle.NO_BORDER);

            consentStartAndEndDateTable.addCell(EffectiveDateCell);
            consentStartAndEndDateTable.addCell(expirationDateCell);
            consentStartAndEndDateTable.addCell(emptyCell);
        }
        return consentStartAndEndDateTable;
    }

    private PdfPTable createSectionTitle(String title){
        PdfPTable sectionTitle = createBorderlessTable(1);

        Font cellFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
        cellFont.setColor(BaseColor.WHITE);

        PdfPCell cell = createBorderlessCell(title,cellFont);
        cell.setBackgroundColor(new BaseColor(73,89,105));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(5);
        sectionTitle.addCell(cell);

        return sectionTitle;
    }

    private Paragraph createConsentTerms(){
       String term =  "I, Bob Lastname, understand that my records are protected under the federal regulations governing Confidentiality of " +
                "Alcohol and Drug Abuse Patient Records, 42 CFR part 2, and cannot be disclosed without my written permission or " +
                "as otherwise permitted by 42 CFR part 2. I also understand that I may revoke this consent at any time except to the " +
                "extent that action has been taken in reliance on it, and that any event this consent expires automatically as follows:";

        return createParagraphWithContent(term, null);
    }

    private PdfPTable createProviderPropertyValueTable(String propertyName, String propertyValue){
        PdfPTable providerTable = createBorderlessTable(1);

        Font propertNameFont = new Font(Font.FontFamily.TIMES_ROMAN,10);
        providerTable.addCell(createBorderlessCell(propertyName, propertNameFont));
        Font valueFont = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
        providerTable.addCell(createBorderlessCell(propertyValue, valueFont));

        return providerTable;
    }
    private PdfPTable createProviderTable(AbstractProvider provider){
        PdfPTable providerTable = createBorderlessTable(4);
        providerTable.addCell(createProviderPropertyValueTable("Provider Name", "KOYOMJI DENTAL"));
        providerTable.addCell(createProviderPropertyValueTable("NPI Number", "111111111"));
        providerTable.addCell(createProviderPropertyValueTable("Address", "8601 Falls Run Road Unit F, Ellicott City, MD 21043"));
        providerTable.addCell(createProviderPropertyValueTable("Phone", "301-654-1111"));
//        if(provider != null){
//            providerTable.addCell(createProviderPropertyValueTable("Provider"));
//        }

        return providerTable;
    }

    //// Old code


    /**
     * Gets the sensitivity paragraph.
     *
     * @param consent
     *            the consent
     * @return the sensitivity paragraph
     */
    private Paragraph getSensitivityParagraph(Consent consent) {
        Paragraph paragraph = null;
        Set<ConsentDoNotShareSensitivityPolicyCode> consentDoNotShareSensitivityPolicyCodes = consent
                .getDoNotShareSensitivityPolicyCodes();

        Set<ValueSetCategory> valueSetCategorys = new HashSet<ValueSetCategory>(
                valueSetCategoryRepository.findAll());

        if (consentDoNotShareSensitivityPolicyCodes.size() < valueSetCategorys
                .size()) {
            paragraph = new Paragraph();
            paragraph.add(new Chunk("Sensitivity Categories:"));

            List<String> nameList = new ArrayList<String>();

            for (ConsentDoNotShareSensitivityPolicyCode consentSensitivityPolicyCode : consentDoNotShareSensitivityPolicyCodes) {
                valueSetCategorys.remove(consentSensitivityPolicyCode
                        .getValueSetCategory());

            }
            for (ValueSetCategory valueSetCategory : valueSetCategorys)
                nameList.add(valueSetCategory.getName());

            Collections.sort(nameList);
            com.itextpdf.text.List list = new com.itextpdf.text.List(false, 20);
            for (String displayName : nameList) {
                list.add(new ListItem(displayName));
            }

            paragraph.add(list);
            paragraph.add("\n");
        }

        return paragraph;
    }

    /**
     * Gets the clinical document type paragraph.
     *
     * @param consent
     *            the consent
     * @return the clinical document type paragraph
     */
    private Paragraph getClinicalDocumentTypeParagraph(Consent consent) {
        Paragraph paragraph = null;
        // Set<ConsentDoNotShareClinicalDocumentTypeCode>
        // consentDoNotShareClinicalDocumentTypeCodes = consent
        // .getDoNotShareClinicalDocumentTypeCodes();
        // @SuppressWarnings("unchecked")
        // Set<ClinicalDocumentTypeCode>
        // clinicalDocumentTypeCodes=(Set<ClinicalDocumentTypeCode>)
        // clinicalDocumentTypeCodeRepository.findAll();
        //
        // if (consentDoNotShareClinicalDocumentTypeCodes.size() <
        // clinicalDocumentTypeCodes.size()) {
        // paragraph = new Paragraph();
        // paragraph.add(new Chunk("Clinical Document Types:"));
        //
        // List<String> nameList = new ArrayList<String>();
        // for (ConsentDoNotShareClinicalDocumentTypeCode
        // consentClinicalDocumentTypeCode :
        // consentDoNotShareClinicalDocumentTypeCodes) {
        // clinicalDocumentTypeCodes.remove(consentClinicalDocumentTypeCode
        // .getClinicalDocumentTypeCode());
        // }
        //
        // for (ClinicalDocumentTypeCode clinicalDocumentTypeCode :
        // clinicalDocumentTypeCodes)
        // nameList.add(clinicalDocumentTypeCode.getDisplayName());
        //
        // Collections.sort(nameList);
        // com.itextpdf.text.List list = new com.itextpdf.text.List(false, 20);
        // for (String displayName : nameList) {
        // list.add(new ListItem(displayName));
        // }

        // paragraph.add(list);
        // }

        return paragraph;
    }


    /**
     * Gets the clinical concept codes paragraph.
     *
     * @param consent
     *            the consent
     * @return the clinical concept codes paragraph
     */
    private Paragraph getClinicalConceptCodesParagraph(Consent consent) {
        Paragraph paragraph = null;
        Set<ClinicalConceptCode> doNotShareClinicalConceptCodes = consent
                .getDoNotShareClinicalConceptCodes();
        if (doNotShareClinicalConceptCodes.size() > 0) {
            paragraph = new Paragraph();
            paragraph.add(new Chunk("Specific Medical Information:"));

            List<String> nameList = new ArrayList<String>();
            for (ClinicalConceptCode clinicalConceptCode : doNotShareClinicalConceptCodes) {
                nameList.add(clinicalConceptCode.getDisplayName());
            }
            Collections.sort(nameList);
            com.itextpdf.text.List list = new com.itextpdf.text.List(false, 20);
            for (String displayName : nameList) {
                list.add(new ListItem(displayName));
            }

            paragraph.add(list);
        }
        return paragraph;
    }

    /**
     * Gets the purpose of use paragraph.
     *
     * @param consent
     *            the consent
     * @return the purpose of use paragraph
     */
    private Paragraph getPurposeOfUseParagraph(Consent consent) {
        Paragraph paragraph = null;
        Set<ConsentShareForPurposeOfUseCode> shareForPurposeOfUseCodeCodes = consent
                .getShareForPurposeOfUseCodes();
        if (shareForPurposeOfUseCodeCodes.size() > 0) {
            paragraph = new Paragraph();
            List<String> nameList = new ArrayList<String>();
            for (ConsentShareForPurposeOfUseCode consentPurposeOfUseCode : shareForPurposeOfUseCodeCodes) {
                PurposeOfUseCode code = consentPurposeOfUseCode
                        .getPurposeOfUseCode();
                nameList.add(code.getDisplayName());
            }
            Collections.sort(nameList);
            com.itextpdf.text.List list = new com.itextpdf.text.List(false, 20);
            for (String displayName : nameList) {
                list.add(new ListItem(displayName));
            }

            paragraph.add(list);
        }
        return paragraph;
    }

    /**
     * Creates the consent made from table.
     *
     * @param consent
     *            the consent
     * @return the pdf p table
     */
    private PdfPTable createConsentMadeFromTable(Consent consent) {
        // a table with three columns
        Font fontbold = FontFactory.getFont("Helvetica", 12, Font.BOLD);
        PdfPTable table = new PdfPTable(new float[]{0.2f, 0.2f, 0.4f, 0.2f});
        // table.setWidths(new int[]{1, 1, 2, 1});
        table.setWidthPercentage(100f);
        table.setHeaderRows(1);
        // table.addCell("Person/Organization Name");
        table.addCell(new PdfPCell(new Phrase("Provider Name", fontbold)));
        table.addCell(new PdfPCell(new Phrase("Phone", fontbold)));
        table.addCell(new PdfPCell(new Phrase("Address", fontbold)));
        table.addCell(new PdfPCell(new Phrase("NPI Number", fontbold)));

        for (ConsentIndividualProviderPermittedToDisclose item : consent
                .getProvidersPermittedToDisclose()) {
            table.addCell(item.getIndividualProvider().getFirstName() + " "
                    + item.getIndividualProvider().getLastName());
            table.addCell(item.getIndividualProvider()
                    .getPracticeLocationAddressTelephoneNumber());
            table.addCell(item.getIndividualProvider()
                    .getFirstLinePracticeLocationAddress()
                    + ((item.getIndividualProvider()
                    .getSecondLinePracticeLocationAddress()
                    .replaceAll("\\W", "") == "") ? "" : "\n"
                    + item.getIndividualProvider()
                    .getSecondLinePracticeLocationAddress())
                    + "\n"
                    + item.getIndividualProvider()
                    .getPracticeLocationAddressCityName()
                    + ", "
                    + item.getIndividualProvider()
                    .getPracticeLocationAddressStateName()
                    + ", "
                    + item.getIndividualProvider()
                    .getPracticeLocationAddressPostalCode());
            table.addCell(item.getIndividualProvider().getNpi());
        }

        for (ConsentOrganizationalProviderPermittedToDisclose item : consent
                .getOrganizationalProvidersPermittedToDisclose()) {
            table.addCell(item.getOrganizationalProvider().getOrgName());
            table.addCell(item.getOrganizationalProvider()
                    .getPracticeLocationAddressTelephoneNumber());
            table.addCell(item.getOrganizationalProvider()
                    .getFirstLinePracticeLocationAddress()
                    + ((item.getOrganizationalProvider()
                    .getSecondLinePracticeLocationAddress()
                    .replaceAll("\\W", "") == "") ? "" : "\n"
                    + item.getOrganizationalProvider()
                    .getSecondLinePracticeLocationAddress())
                    + "\n"
                    + item.getOrganizationalProvider()
                    .getPracticeLocationAddressCityName()
                    + ", "
                    + item.getOrganizationalProvider()
                    .getPracticeLocationAddressStateName()
                    + ", "
                    + item.getOrganizationalProvider()
                    .getPracticeLocationAddressPostalCode());
            table.addCell(item.getOrganizationalProvider().getNpi());
        }
        return table;
    }

    /**
     * Creates the consent made to table.
     *
     * @param consent
     *            the consent
     * @return the pdf p table
     */
    private PdfPTable createConsentMadeToTable(Consent consent) {
        Font fontbold = FontFactory.getFont("Helvetica", 12, Font.BOLD);
        PdfPTable table = new PdfPTable(new float[]{0.2f, 0.2f, 0.4f, 0.2f});
        // table.setWidths(new int[]{1, 1, 2, 1});
        table.setWidthPercentage(100f);
        table.setHeaderRows(1);
        // table.addCell("Person/Organization Name");
        table.addCell(new PdfPCell(new Phrase("Provider Name", fontbold)));
        table.addCell(new PdfPCell(new Phrase("Phone", fontbold)));
        table.addCell(new PdfPCell(new Phrase("Address", fontbold)));
        table.addCell(new PdfPCell(new Phrase("NPI Number", fontbold)));
        for (ConsentIndividualProviderDisclosureIsMadeTo item : consent
                .getProvidersDisclosureIsMadeTo()) {
            table.addCell(item.getIndividualProvider().getFirstName() + " "
                    + item.getIndividualProvider().getLastName());
            table.addCell(item.getIndividualProvider()
                    .getPracticeLocationAddressTelephoneNumber());
            table.addCell(item.getIndividualProvider()
                    .getFirstLinePracticeLocationAddress()
                    + ((item.getIndividualProvider()
                    .getSecondLinePracticeLocationAddress()
                    .replaceAll("\\W", "") == "") ? "" : "\n"
                    + item.getIndividualProvider()
                    .getSecondLinePracticeLocationAddress())
                    + "\n"
                    + item.getIndividualProvider()
                    .getPracticeLocationAddressCityName()
                    + ", "
                    + item.getIndividualProvider()
                    .getPracticeLocationAddressStateName()
                    + ", "
                    + item.getIndividualProvider()
                    .getPracticeLocationAddressPostalCode());
            table.addCell(item.getIndividualProvider().getNpi());
        }

        for (ConsentOrganizationalProviderDisclosureIsMadeTo item : consent
                .getOrganizationalProvidersDisclosureIsMadeTo()) {
            table.addCell(item.getOrganizationalProvider().getOrgName());
            table.addCell(item.getOrganizationalProvider()
                    .getPracticeLocationAddressTelephoneNumber());
            table.addCell(item.getOrganizationalProvider()
                    .getFirstLinePracticeLocationAddress()
                    + ((item.getOrganizationalProvider()
                    .getSecondLinePracticeLocationAddress()
                    .replaceAll("\\W", "") == "") ? "" : "\n"
                    + item.getOrganizationalProvider()
                    .getSecondLinePracticeLocationAddress())
                    + "\n"
                    + item.getOrganizationalProvider()
                    .getPracticeLocationAddressCityName()
                    + ", "
                    + item.getOrganizationalProvider()
                    .getPracticeLocationAddressStateName()
                    + ", "
                    + item.getOrganizationalProvider()
                    .getPracticeLocationAddressPostalCode());
            table.addCell(item.getOrganizationalProvider().getNpi());
        }
        return table;
    }
}
