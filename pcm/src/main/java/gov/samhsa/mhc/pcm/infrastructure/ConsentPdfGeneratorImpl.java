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
import com.itextpdf.text.List;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gov.samhsa.mhc.pcm.domain.consent.*;
import gov.samhsa.mhc.pcm.domain.patient.Patient;
import gov.samhsa.mhc.pcm.domain.provider.AbstractProvider;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProvider;
import gov.samhsa.mhc.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalConceptCode;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.mhc.pcm.domain.reference.StateCode;
import gov.samhsa.mhc.pcm.domain.valueobject.Address;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategory;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.mhc.pcm.infrastructure.dto.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;


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
    public byte[] generate42CfrPart2Pdf(Consent consent, Patient patientProfile) {
        Assert.notNull(consent, "Consent is required.");

        Document document = new Document();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, pdfOutputStream);

            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            document.add(createParagraphWithContent("Consent to Share My Health Information", titleFont));

            // Blank line
            document.add( Chunk.NEWLINE );

            // Consent Created Date
            document.add(createParagraphWithContent("Created On: " + formatDate(consent.getCreatedDateTime()) , null));

            //consent Reference Number
            document.add(createConsentReferenceNumberTable(consent));

            document.add(new Paragraph(" "));

            //Patient Name and date of birth
            document.add(createPatientNameAndDOBTable(patientProfile));

            document.add(new Paragraph(" "));

            //Authorization to disclose section
            document.add(createSectionTitle("AUTHORIZATION TO DISCLOSE"));
            //Authorizes
            document.add(new Paragraph("Authorizes: "));

            document.add(createProviderPermittedToDiscloseTable(consent));
            //To disclose to
            document.add(new Paragraph("To disclose to: "));

            document.add(createProviderDisclosureIsMadeToTable(consent));

            document.add(new Paragraph(" "));

            //Health information to be disclosed section
            document.add(createSectionTitle("HEALTH INFORMATION TO BE DISCLOSED"));

            document.add(createHealthInformationToBeDisclose(consent));

            document.add(new Paragraph(" "));

            //Consent terms section
            document.add(createSectionTitle("CONSENT TERMS"));

            // Consent term
            document.add(createConsentTerms(patientProfile));

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
    private String getFullName(Patient patientProfile){
        return patientProfile.getFirstName() + " " + patientProfile.getLastName();
    }
    private PdfPTable createPatientNameAndDOBTable(Patient patientProfile) {
        PdfPTable consentReferenceNumberTable = createBorderlessTable(2);

        if (patientProfile != null) {
            Font patientInfoFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
            PdfPCell patientNameCell = new PdfPCell(createCellContent("Patient Name: ", null, getFullName(patientProfile), patientInfoFont));
            patientNameCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell patientDOBCell = new PdfPCell(createCellContent("Patient DOB: ", null, formatDate(patientProfile.getBirthDay()), patientInfoFont));
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

    private Paragraph createConsentTerms(Patient patientProfile){
       String term =  "I, "+ getFullName(patientProfile) + ", understand that my records are protected under the federal regulations governing Confidentiality of " +
                "Alcohol and Drug Abuse Patient Records, 42 CFR part 2, and cannot be disclosed without my written permission or " +
                "as otherwise permitted by 42 CFR part 2. I also understand that I may revoke this consent at any time except to the " +
                "extent that action has been taken in reliance on it, and that any event this consent expires automatically as follows:";

        return createParagraphWithContent(term, null);
    }

    private PdfPTable createProviderPropertyValueTable(String propertyName, String propertyValue){
        PdfPTable providerTable = createBorderlessTable(1);

        Font propertNameFont = new Font(Font.FontFamily.TIMES_ROMAN,10);
        providerTable.addCell(createBorderlessCell(propertyName, propertNameFont));
        Font valueFont = new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD);
        providerTable.addCell(createBorderlessCell(propertyValue, valueFont));

        return providerTable;
    }

    private String composeAddress(AbstractProvider provider){
        String address = "";
        if(provider != null){
            if(provider.getFirstLinePracticeLocationAddress() != null){
                address += provider.getFirstLinePracticeLocationAddress() + ", ";
            }

            if(provider.getPracticeLocationAddressCityName()!= null){
                address += provider.getPracticeLocationAddressCityName() + ", ";
            }

            if(provider.getPracticeLocationAddressStateName() != null && provider.getPracticeLocationAddressPostalCode() != null){
                address += provider.getPracticeLocationAddressStateName() + " " + provider.getPracticeLocationAddressPostalCode() ;
            }

        }
        return address;
    }

    private void setNpiAddressState(PdfPTable providerTable, AbstractProvider provider){
        providerTable.addCell(createProviderPropertyValueTable("NPI Number", provider.getNpi()));
        providerTable.addCell(createProviderPropertyValueTable("Address", composeAddress(provider)));
        providerTable.addCell(createProviderPropertyValueTable("Phone", provider.getPracticeLocationAddressTelephoneNumber()));
    }

    private PdfPTable createProviderPermittedToDiscloseTable(Consent consent){
        PdfPTable providerTable = createBorderlessTable(4);
        Set<OrganizationalProvider> ordProvidersPermittedToDisclose = getOrdProvidersPermittedToDisclose(consent);

        for(OrganizationalProvider provider : ordProvidersPermittedToDisclose){
            providerTable.addCell(createProviderPropertyValueTable("Provider Name", provider.getOrgName()));
            setNpiAddressState(providerTable,provider);
        }

        Set<IndividualProvider> indProvidersPermittedToDisclose = getIndProvidersPermittedToDisclose(consent);

        for(IndividualProvider provider : indProvidersPermittedToDisclose){
            providerTable.addCell(createProviderPropertyValueTable("Provider Name", provider.getFirstName() + " " + provider.getLastName()));
            setNpiAddressState(providerTable,provider);
        }

        return providerTable;
    }

    private PdfPTable createProviderDisclosureIsMadeToTable(Consent consent){
        PdfPTable providerTable = createBorderlessTable(4);
        Set<OrganizationalProvider> ordProvidersDisclosureIsMadeTo = getOrgProvidersDisclosureIsMadeTo(consent);

        for(OrganizationalProvider provider : ordProvidersDisclosureIsMadeTo){
            providerTable.addCell(createProviderPropertyValueTable("Provider Name", provider.getOrgName()));
            setNpiAddressState(providerTable,provider);
        }

        Set<IndividualProvider> indProvidersDisclosureIsMadeTo = getIndProvidersDisclosureIsMadeTo(consent);

        for(IndividualProvider provider : indProvidersDisclosureIsMadeTo){
            providerTable.addCell(createProviderPropertyValueTable("Provider Name", provider.getFirstName() + " " + provider.getLastName()));
            setNpiAddressState(providerTable,provider);
        }

        return providerTable;
    }

    private Chunk createUnderlineText(String text){
        Chunk underlineText = new Chunk(text);
        underlineText.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
        return underlineText;
    }

    private PdfPCell createEmptyBorderlessCell(){
        PdfPCell aCell = new PdfPCell();
        aCell.setBorder(0);
        return aCell;
    }

    private PdfPCell createCellWithUnderlineContent(String text){
        PdfPCell aCell = createEmptyBorderlessCell();
        aCell.addElement(createUnderlineText(text));
        return aCell;
    }
    private List createUnorderList(ArrayList<String> items){
        List unorderedList = new List(List.UNORDERED);
        for( String item :items){
            unorderedList.add(new ListItem(item));
        }
        return unorderedList;
    }

    private Set<OrganizationalProvider> getOrdProvidersPermittedToDisclose(Consent consent){
        final Set<OrganizationalProvider> consentProvidersPermittedToDisclose = new HashSet<OrganizationalProvider>();

        if(consent.getOrganizationalProvidersPermittedToDisclose() != null & consent.getOrganizationalProvidersPermittedToDisclose().size() >0){
            for (final ConsentOrganizationalProviderPermittedToDisclose item : consent.getOrganizationalProvidersPermittedToDisclose()) {
                consentProvidersPermittedToDisclose.add(item.getOrganizationalProvider());
            }
        }
        return consentProvidersPermittedToDisclose;
    }

    private Set<IndividualProvider> getIndProvidersPermittedToDisclose(Consent consent){
        final Set<IndividualProvider> consentProvidersPermittedToDisclose = new HashSet<IndividualProvider>();

        if(consent.getProvidersPermittedToDisclose() != null & consent.getProvidersPermittedToDisclose().size() >0) {
            for (final ConsentIndividualProviderPermittedToDisclose item : consent.getProvidersPermittedToDisclose()) {
                consentProvidersPermittedToDisclose.add(item.getIndividualProvider());
            }
        }
        return consentProvidersPermittedToDisclose;
    }

    private Set<OrganizationalProvider> getOrgProvidersDisclosureIsMadeTo(Consent consent){
        final Set<OrganizationalProvider> consentProvidersDisclosureIsMadeTo = new HashSet<OrganizationalProvider>();

        if(consent.getOrganizationalProvidersDisclosureIsMadeTo() != null & consent.getOrganizationalProvidersDisclosureIsMadeTo().size() >0){
            for (final ConsentOrganizationalProviderDisclosureIsMadeTo item : consent.getOrganizationalProvidersDisclosureIsMadeTo()) {
                consentProvidersDisclosureIsMadeTo.add(item.getOrganizationalProvider());
            }
        }
        return consentProvidersDisclosureIsMadeTo;
    }

    private Set<IndividualProvider> getIndProvidersDisclosureIsMadeTo(Consent consent){
        final Set<IndividualProvider> consentProvidersDisclosureIsMadeTo = new HashSet<IndividualProvider>();

        if(consent.getProvidersDisclosureIsMadeTo() != null & consent.getProvidersDisclosureIsMadeTo().size() >0){
            for (final ConsentIndividualProviderDisclosureIsMadeTo item : consent.getProvidersDisclosureIsMadeTo()) {
                consentProvidersDisclosureIsMadeTo.add(item.getIndividualProvider());
            }
        }
        return consentProvidersDisclosureIsMadeTo;
    }

    private PdfPTable createHealthInformationToBeDisclose(Consent consent){
        PdfPTable healthInformationToBeDisclose = createBorderlessTable(2);

        // Medical Information
        PdfPCell medicalInformation = createCellWithUnderlineContent("To SHARE the following medical information:");
        Paragraph sensitivityCategory = createParagraphWithContent("Sensitivity Categories:", null);
        medicalInformation.addElement(sensitivityCategory);

        ArrayList<String> medicalInformationList = getMedicalInformation(consent);
        medicalInformation.addElement(createUnorderList(medicalInformationList));
        healthInformationToBeDisclose.addCell(medicalInformation);

        //Purposes of use
        PdfPCell purposeOfUseCell = createCellWithUnderlineContent("To SHARE for the following purpose(s):");
        ArrayList<String> purposes = getPurposeOfUse(consent);
        purposeOfUseCell.addElement(createUnorderList(purposes));
        healthInformationToBeDisclose.addCell(purposeOfUseCell);

        return healthInformationToBeDisclose;
    }

    private ArrayList<String> getMedicalInformation(Consent consent){
        ArrayList<String> medicalInformationList = new ArrayList<String>();
        for (final ConsentDoNotShareSensitivityPolicyCode item : consent.getDoNotShareSensitivityPolicyCodes()) {
            medicalInformationList.add(item.getValueSetCategory().getName());
        }
        return medicalInformationList;
    }

    private ArrayList<String> getPurposeOfUse(Consent consent){
        ArrayList<String> purposesOfUseList = new ArrayList<String>();
        for (final ConsentShareForPurposeOfUseCode purposeOfUseCode : consent.getShareForPurposeOfUseCodes()) {
            purposesOfUseList.add(purposeOfUseCode.getPurposeOfUseCode().getDisplayName());
        }
        return purposesOfUseList;
    }
}
