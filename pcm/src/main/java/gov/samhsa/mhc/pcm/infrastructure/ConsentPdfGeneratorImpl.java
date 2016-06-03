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
import gov.samhsa.mhc.pcm.domain.patient.Patient;
import gov.samhsa.mhc.pcm.domain.provider.AbstractProvider;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProvider;
import gov.samhsa.mhc.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * The Class ConsentPdfGeneratorImpl.
 */
@Service
public class ConsentPdfGeneratorImpl implements ConsentPdfGenerator {

    @Autowired
    private ITextPdfService iTextPdfService;

    /**
     * The value set category repository.
     */
    @Autowired
    private ValueSetCategoryRepository valueSetCategoryRepository;

    /**
     * The clinical document type code repository.
     */
    @Autowired
    private ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.domain.consent.ConsentPdfGenerator#
     * generate42CfrPart2Pdf(gov.samhsa.consent2share.domain.consent.Consent)
     */
    @Override
    public byte[] generate42CfrPart2Pdf(Consent consent, Patient patientProfile, boolean isSigned, Date attestedOn, String terms) {
        Assert.notNull(consent, "Consent is required.");

        Document document = new Document();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, pdfOutputStream);

            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            document.add(iTextPdfService.createParagraphWithContent("Consent to Share My Health Information", titleFont));

            // Blank line
            document.add(Chunk.NEWLINE);

            // Consent Created Date
            document.add(iTextPdfService.createParagraphWithContent("Created On: " + iTextPdfService.formatDate(consent.getCreatedDateTime()), null));

            //consent Reference Number
            document.add(iTextPdfService.createConsentReferenceNumberTable(consent));

            document.add(new Paragraph(" "));

            //Patient Name and date of birth
            document.add(iTextPdfService.createPatientNameAndDOBTable(patientProfile.getFirstName(), patientProfile.getLastName(), patientProfile.getBirthDay()));

            document.add(new Paragraph(" "));

            //Authorization to disclose section
            document.add(iTextPdfService.createSectionTitle(" AUTHORIZATION TO DISCLOSE"));
            //Authorizes
            document.add(new Paragraph("Authorizes: "));

            document.add(createProviderPermittedToDiscloseTable(consent));
            //To disclose to
            document.add(new Paragraph("To disclose to: "));

            document.add(createProviderDisclosureIsMadeToTable(consent));

            document.add(new Paragraph(" "));

            //Health information to be disclosed section
            document.add(iTextPdfService.createSectionTitle(" HEALTH INFORMATION TO BE DISCLOSED"));

            document.add(createHealthInformationToBeDisclose(consent));

            document.add(new Paragraph(" "));

            //Consent terms section
            document.add(iTextPdfService.createSectionTitle(" CONSENT TERMS"));

            // Consent term
            document.add(createConsentTerms(terms, patientProfile));

            document.add(new Paragraph(" "));

            // Consent effective and expiration date
            document.add(createStartAndEndDateTable(consent));

            document.add(new Paragraph(" "));

            //Signing details
            document.add(iTextPdfService.createSigningDetailsTable(consent.getPatient().getFirstName(), consent.getPatient().getLastName(), consent.getPatient().getEmail(), isSigned, attestedOn));

            document.close();

        } catch (Throwable e) {
            // TODO log exception
            e.printStackTrace();
            throw new ConsentPdfGenerationException("Exception when trying to generate pdf", e);
        }

        byte[] pdfBytes = pdfOutputStream.toByteArray();

        return pdfBytes;
    }

    private String getFullName(Patient patientProfile) {
        return patientProfile.getFirstName() + " " + patientProfile.getLastName();
    }

    private PdfPTable createStartAndEndDateTable(Consent consent) {
        PdfPTable consentStartAndEndDateTable = iTextPdfService.createBorderlessTable(3);

        if (consent != null) {
            Font patientDateFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
            PdfPCell EffectiveDateCell = new PdfPCell(iTextPdfService.createCellContent("Effective Date: ", patientDateFont, iTextPdfService.formatDate(consent.getStartDate()), patientDateFont));
            EffectiveDateCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell expirationDateCell = new PdfPCell(iTextPdfService.createCellContent("Expiration Date: ", patientDateFont, iTextPdfService.formatDate(consent.getEndDate()), patientDateFont));
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

    private Paragraph createConsentTerms(String terms, Patient patientProfile){
        String userNameKey = "ATTESTER_FULL_NAME";
        String termsWithAttestedName =  terms.replace(userNameKey, getFullName(patientProfile));
        return iTextPdfService.createParagraphWithContent(termsWithAttestedName, null);
    }

    private PdfPTable createProviderPropertyValueTable(String propertyName, String propertyValue) {
        PdfPTable providerTable = iTextPdfService.createBorderlessTable(1);

        Font propertNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        providerTable.addCell(iTextPdfService.createBorderlessCell(propertyName, propertNameFont));
        Font valueFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        providerTable.addCell(iTextPdfService.createBorderlessCell(propertyValue, valueFont));

        return providerTable;
    }

    private String composeAddress(AbstractProvider provider) {
        String address = "";
        if (provider != null) {
            if (provider.getFirstLinePracticeLocationAddress() != null) {
                address += provider.getFirstLinePracticeLocationAddress() + ", ";
            }

            if (provider.getPracticeLocationAddressCityName() != null) {
                address += provider.getPracticeLocationAddressCityName() + ", ";
            }

            if (provider.getPracticeLocationAddressStateName() != null && provider.getPracticeLocationAddressPostalCode() != null) {
                address += provider.getPracticeLocationAddressStateName() + " " + provider.getPracticeLocationAddressPostalCode();
            }

        }
        return address;
    }

    private void setNpiAddressState(PdfPTable providerTable, AbstractProvider provider) {
        providerTable.addCell(createProviderPropertyValueTable("NPI Number", provider.getNpi()));
        providerTable.addCell(createProviderPropertyValueTable("Address", composeAddress(provider)));
        providerTable.addCell(createProviderPropertyValueTable("Phone", provider.getPracticeLocationAddressTelephoneNumber()));
    }

    private PdfPTable createProviderPermittedToDiscloseTable(Consent consent) {
        PdfPTable providerTable = iTextPdfService.createBorderlessTable(4);
        Set<OrganizationalProvider> ordProvidersPermittedToDisclose = getOrdProvidersPermittedToDisclose(consent);

        for (OrganizationalProvider provider : ordProvidersPermittedToDisclose) {
            providerTable.addCell(createProviderPropertyValueTable("Provider Name", provider.getOrgName()));
            setNpiAddressState(providerTable, provider);
        }

        Set<IndividualProvider> indProvidersPermittedToDisclose = getIndProvidersPermittedToDisclose(consent);

        for (IndividualProvider provider : indProvidersPermittedToDisclose) {
            providerTable.addCell(createProviderPropertyValueTable("Provider Name", provider.getFirstName() + " " + provider.getLastName()));
            setNpiAddressState(providerTable, provider);
        }

        return providerTable;
    }

    private PdfPTable createProviderDisclosureIsMadeToTable(Consent consent) {
        PdfPTable providerTable = iTextPdfService.createBorderlessTable(4);
        Set<OrganizationalProvider> ordProvidersDisclosureIsMadeTo = getOrgProvidersDisclosureIsMadeTo(consent);

        for (OrganizationalProvider provider : ordProvidersDisclosureIsMadeTo) {
            providerTable.addCell(createProviderPropertyValueTable("Provider Name", provider.getOrgName()));
            setNpiAddressState(providerTable, provider);
        }

        Set<IndividualProvider> indProvidersDisclosureIsMadeTo = getIndProvidersDisclosureIsMadeTo(consent);

        for (IndividualProvider provider : indProvidersDisclosureIsMadeTo) {
            providerTable.addCell(createProviderPropertyValueTable("Provider Name", provider.getFirstName() + " " + provider.getLastName()));
            setNpiAddressState(providerTable, provider);
        }

        return providerTable;
    }

    private Set<OrganizationalProvider> getOrdProvidersPermittedToDisclose(Consent consent) {
        final Set<OrganizationalProvider> consentProvidersPermittedToDisclose = new HashSet<OrganizationalProvider>();

        if (consent.getOrganizationalProvidersPermittedToDisclose() != null & consent.getOrganizationalProvidersPermittedToDisclose().size() > 0) {
            for (final ConsentOrganizationalProviderPermittedToDisclose item : consent.getOrganizationalProvidersPermittedToDisclose()) {
                consentProvidersPermittedToDisclose.add(item.getOrganizationalProvider());
            }
        }
        return consentProvidersPermittedToDisclose;
    }

    private Set<IndividualProvider> getIndProvidersPermittedToDisclose(Consent consent) {
        final Set<IndividualProvider> consentProvidersPermittedToDisclose = new HashSet<IndividualProvider>();

        if (consent.getProvidersPermittedToDisclose() != null & consent.getProvidersPermittedToDisclose().size() > 0) {
            for (final ConsentIndividualProviderPermittedToDisclose item : consent.getProvidersPermittedToDisclose()) {
                consentProvidersPermittedToDisclose.add(item.getIndividualProvider());
            }
        }
        return consentProvidersPermittedToDisclose;
    }

    private Set<OrganizationalProvider> getOrgProvidersDisclosureIsMadeTo(Consent consent) {
        final Set<OrganizationalProvider> consentProvidersDisclosureIsMadeTo = new HashSet<OrganizationalProvider>();

        if (consent.getOrganizationalProvidersDisclosureIsMadeTo() != null & consent.getOrganizationalProvidersDisclosureIsMadeTo().size() > 0) {
            for (final ConsentOrganizationalProviderDisclosureIsMadeTo item : consent.getOrganizationalProvidersDisclosureIsMadeTo()) {
                consentProvidersDisclosureIsMadeTo.add(item.getOrganizationalProvider());
            }
        }
        return consentProvidersDisclosureIsMadeTo;
    }

    private Set<IndividualProvider> getIndProvidersDisclosureIsMadeTo(Consent consent) {
        final Set<IndividualProvider> consentProvidersDisclosureIsMadeTo = new HashSet<IndividualProvider>();

        if (consent.getProvidersDisclosureIsMadeTo() != null & consent.getProvidersDisclosureIsMadeTo().size() > 0) {
            for (final ConsentIndividualProviderDisclosureIsMadeTo item : consent.getProvidersDisclosureIsMadeTo()) {
                consentProvidersDisclosureIsMadeTo.add(item.getIndividualProvider());
            }
        }
        return consentProvidersDisclosureIsMadeTo;
    }

    private PdfPTable createHealthInformationToBeDisclose(Consent consent) {
        PdfPTable healthInformationToBeDisclose = iTextPdfService.createBorderlessTable(2);

        // Medical Information
        PdfPCell medicalInformation = iTextPdfService.createCellWithUnderlineContent("To SHARE the following medical information:");
        Paragraph sensitivityCategory = iTextPdfService.createParagraphWithContent("Sensitivity Categories:", null);
        medicalInformation.addElement(sensitivityCategory);

        ArrayList<String> medicalInformationList = getMedicalInformation(consent);
        medicalInformation.addElement(iTextPdfService.createUnorderList(medicalInformationList));
        healthInformationToBeDisclose.addCell(medicalInformation);

        //Purposes of use
        PdfPCell purposeOfUseCell = iTextPdfService.createCellWithUnderlineContent("To SHARE for the following purpose(s):");
        ArrayList<String> purposes = getPurposeOfUse(consent);
        purposeOfUseCell.addElement(iTextPdfService.createUnorderList(purposes));
        healthInformationToBeDisclose.addCell(purposeOfUseCell);

        return healthInformationToBeDisclose;
    }

    private ArrayList<String> getMedicalInformation(Consent consent) {
        ArrayList<String> medicalInformationList = new ArrayList<String>();
        for (final ConsentDoNotShareSensitivityPolicyCode item : consent.getDoNotShareSensitivityPolicyCodes()) {
            medicalInformationList.add(item.getValueSetCategory().getName());
        }
        return medicalInformationList;
    }

    private ArrayList<String> getPurposeOfUse(Consent consent) {
        ArrayList<String> purposesOfUseList = new ArrayList<String>();
        for (final ConsentShareForPurposeOfUseCode purposeOfUseCode : consent.getShareForPurposeOfUseCodes()) {
            purposesOfUseList.add(purposeOfUseCode.getPurposeOfUseCode().getDisplayName());
        }
        return purposesOfUseList;
    }

    private PdfPTable  createSigningDetailsTable(Consent consent, Boolean isSigned, Date attestedOn, Patient patientProfile){
        PdfPTable signingDetailsTable = createBorderlessTable(1);

        if(isSigned && consent != null && attestedOn != null){
            Font patientInfoFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);

            PdfPCell attesterEmailCell = new PdfPCell(createCellContent("Email: ", patientInfoFont, consent.getPatient().getEmail(), null));
            attesterEmailCell.setBorder(Rectangle.NO_BORDER);
            signingDetailsTable.addCell(attesterEmailCell);

            PdfPCell attesterFullNameCell = new PdfPCell(createCellContent("Attested By: ", patientInfoFont, getFullName(patientProfile), null));
            attesterFullNameCell.setBorder(Rectangle.NO_BORDER);
            signingDetailsTable.addCell(attesterFullNameCell);

            PdfPCell attesterSignDateCell = new PdfPCell(createCellContent("Attested On: ", patientInfoFont, formatDate(attestedOn), null));
            attesterSignDateCell.setBorder(Rectangle.NO_BORDER);
            signingDetailsTable.addCell(attesterSignDateCell);

        }
        return signingDetailsTable;
    }
}
