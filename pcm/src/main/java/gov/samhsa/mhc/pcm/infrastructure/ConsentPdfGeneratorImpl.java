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

            document.add(createHealthInformationToBeDisclose());

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
    private PdfPTable createHealthInformationToBeDisclose(){
        PdfPTable healthInformationToBeDisclose = createBorderlessTable(2);

        // Medical Information
        PdfPCell medicalInformation = createCellWithUnderlineContent("To SHARE the following medical information:");
        Paragraph sensitivityCategory = createParagraphWithContent("Sensitivity Categories:", null);
        medicalInformation.addElement(sensitivityCategory);

        ArrayList<String> medInfo = new ArrayList<String>();
        medInfo.add("Addictions Information");
        medInfo.add("Alcohol use and Alcoholism Information");
        medicalInformation.addElement(createUnorderList(medInfo));

        healthInformationToBeDisclose.addCell(medicalInformation);

        //Purposes of use
        PdfPCell purposeOfUse = createCellWithUnderlineContent("To SHARE for the following purpose(s):");
        ArrayList<String> purposes = new ArrayList<String>();
        purposes.add("Health Treatment");
        purposeOfUse.addElement(createUnorderList(purposes));
        healthInformationToBeDisclose.addCell(purposeOfUse);

        return healthInformationToBeDisclose;
    }
}
