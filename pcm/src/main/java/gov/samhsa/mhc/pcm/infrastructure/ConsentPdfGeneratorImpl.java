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

            Paragraph parConsentId = new Paragraph(20);
            parConsentId.setAlignment(Element.ALIGN_LEFT);

            Chunk chunkConsentIdLabel = new Chunk();
            chunkConsentIdLabel.append("Consent Reference Number: ");

            chunkConsentIdLabel.append(strConsentIdValue);
            parConsentId.add(chunkConsentIdLabel);


            Paragraph parConsentDescription = new Paragraph(20);
            parConsentDescription.setSpacingAfter(10);
            parConsentDescription.setAlignment(Element.ALIGN_CENTER);

            Chunk chunkConsentDescription = new Chunk(strConsentDescriptionValue, fontheader);

            parConsentDescription.add(chunkConsentDescription);


            Paragraph parPatientInfoAndProviders = new Paragraph(20);
            parPatientInfoAndProviders.setSpacingBefore(10);

            PdfPTable headerNotification = new PdfPTable(1);
            headerNotification.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerNotification.setWidthPercentage(100f);
            headerNotification.addCell(new PdfPCell(new Phrase("***PLEASE READ THE ENTIRE FORM BEFORE SIGNING BELOW***", fontnotification)));

            Chunk chunkPatientInfoFieldsHeader = new Chunk("Patient (name and information of person whose health information is being disclosed): \n\n", fontbold);
            Chunk chunkPatientNameLabel = new Chunk("Name (First Middle Last):");
            Chunk chunkPatientDOBLabel = new Chunk("Date of Birth(mm/dd/yyyy): ");
            Chunk chunkPatientStreetLabel = new Chunk("Street: ");
            Chunk chunkPatientCityLabel = new Chunk("City: ");
            Chunk chunkPatientStateLabel = new Chunk("State: ");
            Chunk chunkPatientZipLabel = new Chunk("Zip: ");

            Chunk chunkPatientDOBValue = null;
            Chunk chunkPatientStreetValue = null;
            Chunk chunkPatientCityValue = null;
            Chunk chunkPatientStateValue = null;
            Chunk chunkPatientZipValue = null;

            if (datePatientDOB != null) {
                chunkPatientDOBValue = new Chunk(String.format("%tm/%td/%tY", datePatientDOB, datePatientDOB, datePatientDOB) + "\n");
                chunkPatientDOBValue.setUnderline(1, -2);
            }
            if (addrsPatientAddress != null) {
                String strPatientStreetAddress = addrsPatientAddress.getStreetAddressLine();
                String strPatientCity = addrsPatientAddress.getCity();
                StateCode scPatientStateCode = addrsPatientAddress.getStateCode();
                String strPatientPostalCode = addrsPatientAddress.getPostalCode();

                if (strPatientStreetAddress != null) {
                    chunkPatientStreetValue = new Chunk(strPatientStreetAddress + "\n");
                    chunkPatientStreetValue.setUnderline(1, -2);
                }

                if (strPatientCity != null) {
                    chunkPatientCityValue = new Chunk(strPatientCity + "  ");
                    chunkPatientCityValue.setUnderline(1, -2);
                }

                if (scPatientStateCode != null) {
                    chunkPatientStateValue = new Chunk(scPatientStateCode.getDisplayName() + "  ");
                    chunkPatientStateValue.setUnderline(1, -2);
                }

                if (strPatientPostalCode != null) {
                    chunkPatientZipValue = new Chunk(strPatientPostalCode);
                    chunkPatientZipValue.setUnderline(1, -2);
                }
            }

            Chunk chunkPatientNameValue = new Chunk(strPatientNameValue);
            chunkPatientNameValue.setUnderline(1, -2);

            Chunk chunkAuthorizesLabel = new Chunk("authorizes ");
            Chunk chunkDiscloseToLabel = new Chunk(" to disclose to ");

            parPatientInfoAndProviders.add(headerNotification);
            parPatientInfoAndProviders.add(chunkPatientInfoFieldsHeader);
            parPatientInfoAndProviders.add(chunkPatientNameLabel);
            parPatientInfoAndProviders.add(chunkPatientNameValue);

            if (chunkPatientDOBValue != null) {
                parPatientInfoAndProviders.add(chunkPatientDOBLabel);
                parPatientInfoAndProviders.add(chunkPatientDOBValue);
            }

            if (addrsPatientAddress != null) {
                parPatientInfoAndProviders.add(chunkPatientStreetLabel);
                parPatientInfoAndProviders.add(chunkPatientStreetValue);
                parPatientInfoAndProviders.add(chunkPatientCityLabel);
                parPatientInfoAndProviders.add(chunkPatientCityValue);
                parPatientInfoAndProviders.add(chunkPatientStateLabel);
                parPatientInfoAndProviders.add(chunkPatientStateValue);
                parPatientInfoAndProviders.add(chunkPatientZipLabel);
                parPatientInfoAndProviders.add(chunkPatientZipValue);
            }

            parPatientInfoAndProviders.add(new Chunk("\n\n"));
            parPatientInfoAndProviders.add(chunkAuthorizesLabel);
            parPatientInfoAndProviders.add(createConsentMadeFromTable(consent));
            parPatientInfoAndProviders.add(chunkDiscloseToLabel);
            parPatientInfoAndProviders.add(createConsentMadeToTable(consent));

            Paragraph parConsentShareMedInfoHeader = new Paragraph(20);
            parConsentShareMedInfoHeader.setSpacingBefore(10);

            Chunk chunkShareFollowingMedInfoLabel = new Chunk("Share the following medical information", fontbold);
            chunkShareFollowingMedInfoLabel.setUnderline(1, -2);


            parConsentShareMedInfoHeader.add(chunkShareFollowingMedInfoLabel);

            Paragraph sensitivityParagraph = getSensitivityParagraph(consent);

            Paragraph clinicalDocumentTypeParagraph = getClinicalDocumentTypeParagraph(consent);

            Paragraph clinicalConceptCodesParagraph = getClinicalConceptCodesParagraph(consent);


            Chunk chunkEndShareMedInfoHeaderSymbol = null;

            if (sensitivityParagraph != null
                    || clinicalDocumentTypeParagraph != null
                    || clinicalConceptCodesParagraph != null) {
                // chunkEndShareMedInfoHeaderSymbol = new Chunk(" except the following:");
                chunkEndShareMedInfoHeaderSymbol = new Chunk(" :\n\n");
                // chunkEndShareMedInfoHeaderSymbol.setUnderline(1, -2);
            } else {
                chunkEndShareMedInfoHeaderSymbol = new Chunk(".");
            }

            parConsentShareMedInfoHeader.add(chunkEndShareMedInfoHeaderSymbol);

            Paragraph parShareForFollowingPurposesHeader = new Paragraph(20);
            parShareForFollowingPurposesHeader.setSpacingAfter(10);
            parShareForFollowingPurposesHeader.setSpacingBefore(10);

            Chunk chunkShareForFollowingPurposesLabel = new Chunk("Share for the following purpose(s):", fontbold);
            chunkShareForFollowingPurposesLabel.setUnderline(1, -2);

            parShareForFollowingPurposesHeader.add(new Chunk("\n"));
            parShareForFollowingPurposesHeader.add(chunkShareForFollowingPurposesLabel);

            // Do Not Share Purpose of Use List
            Paragraph purposeOfUseParagraph = getPurposeOfUseParagraph(consent);

            ArrayList<Paragraph> ary_parConsentAgreementText = new ArrayList<>();

            for(String curParTextString : ary_parConsentAgreementTextStrings) {
                Paragraph parConsentAgreementText = new Paragraph(20);
                parConsentAgreementText.setSpacingBefore(10);

                Chunk chunkCurParText = new Chunk(curParTextString);
                parConsentAgreementText.add(chunkCurParText);

                ary_parConsentAgreementText.add(parConsentAgreementText);
            }

            Paragraph parConsentDateRange = new Paragraph(20);

            Chunk chunkExpirationDate;
            if (dateConsentEndDate != null) {
                chunkExpirationDate = new Chunk(String.format("Expiration Date: %tm/%td/%ty", dateConsentEndDate, dateConsentEndDate, dateConsentEndDate));
            } else {
                chunkExpirationDate = new Chunk("N/A");
            }

            chunkExpirationDate.setUnderline(1, -2);


            Chunk chunkEffectiveDate;
            if (dateConsentStartDate != null) {
                chunkEffectiveDate = new Chunk(String.format("Effective Date: %tm/%td/%ty\n", dateConsentStartDate, dateConsentStartDate, dateConsentStartDate));
            } else {
                chunkEffectiveDate = new Chunk("N/A");
            }

            chunkEffectiveDate.setUnderline(1, -2);


            parConsentDateRange.add(chunkEffectiveDate);
            parConsentDateRange.add(chunkExpirationDate);

            document.add(parConsentId);
            document.add(parConsentDescription);
            document.add(parPatientInfoAndProviders);

            document.add(parConsentShareMedInfoHeader);

            if (sensitivityParagraph != null) {
                document.add(sensitivityParagraph);
            }
            if (clinicalDocumentTypeParagraph != null) {
                document.add(clinicalDocumentTypeParagraph);
            }
            if (clinicalConceptCodesParagraph != null) {
                document.add(clinicalConceptCodesParagraph);
            }

            document.add(parShareForFollowingPurposesHeader);

            if (purposeOfUseParagraph != null) {
                document.add(purposeOfUseParagraph);
            }


            for(Paragraph curPar : ary_parConsentAgreementText){
                document.add(curPar);
            }


            document.add(parConsentDateRange);

            document.close();

        } catch (Throwable e) {
            // TODO log exception
            e.printStackTrace();
            throw new ConsentPdfGenerationException(
                    "Excecption when trying to generate pdf", e);
        }

        byte[] pdfBytes = pdfOutputStream.toByteArray();

        return pdfBytes;
    }

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
