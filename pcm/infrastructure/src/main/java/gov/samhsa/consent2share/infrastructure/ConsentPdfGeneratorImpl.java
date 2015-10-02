/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
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
package gov.samhsa.consent2share.infrastructure;

import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentSectionTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareSensitivityPolicyCode;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentPdfGenerator;
import gov.samhsa.consent2share.domain.consent.ConsentShareForPurposeOfUseCode;
import gov.samhsa.consent2share.domain.reference.ClinicalConceptCode;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.consent2share.domain.reference.PurposeOfUseCode;
import gov.samhsa.consent2share.domain.valueset.MedicalSection;
import gov.samhsa.consent2share.domain.valueset.MedicalSectionRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategoryRepository;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The Class ConsentPdfGeneratorImpl.
 */
public class ConsentPdfGeneratorImpl implements ConsentPdfGenerator {

	/** The value set category repository. */
	private ValueSetCategoryRepository valueSetCategoryRepository;

	/** The clinical document type code repository. */
	private ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

	/** The medical section repository. */
	private MedicalSectionRepository medicalSectionRepository;

	/**
	 * Instantiates a new consent pdf generator impl.
	 *
	 * @param valueSetCategoryRepository
	 *            the value set category repository
	 * @param clinicalDocumentTypeCodeRepository
	 *            the clinical document type code repository
	 * @param medicalSectionRepository
	 *            the medical section repository
	 */
	public ConsentPdfGeneratorImpl(
			ValueSetCategoryRepository valueSetCategoryRepository,
			ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository,
			MedicalSectionRepository medicalSectionRepository) {
		super();
		this.valueSetCategoryRepository = valueSetCategoryRepository;
		this.clinicalDocumentTypeCodeRepository = clinicalDocumentTypeCodeRepository;
		this.medicalSectionRepository = medicalSectionRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.domain.consent.ConsentPdfGenerator#
	 * generate42CfrPart2Pdf(gov.samhsa.consent2share.domain.consent.Consent)
	 */
	@Override
	public byte[] generate42CfrPart2Pdf(Consent consent) {
		Assert.notNull(consent, "Consent is required.");

		// Step 1
		Document document = new Document();

		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		try {
			Font fontbold = FontFactory.getFont("Helvetica", 12, Font.BOLD);
			Font fontheader = FontFactory.getFont("Helvetica", 19, Font.BOLD);
			Font fontnotification = FontFactory.getFont("Helvetica", 16,
					Font.ITALIC);

			PdfWriter.getInstance(document, pdfOutputStream);

			document.open();

			Paragraph consentID = new Paragraph(20);
			consentID.setAlignment(Element.ALIGN_LEFT);
			Chunk chuckId = new Chunk();
			chuckId.append("Consent Reference Number: ");
			String consentIdString = String.valueOf(consent
					.getConsentReferenceId());
			chuckId.append(consentIdString);
			consentID.add(chuckId);

			Paragraph paragraph1 = new Paragraph(20);
			paragraph1.setSpacingAfter(10);

			paragraph1.setAlignment(Element.ALIGN_CENTER);
			Chunk chunk = new Chunk(consent.getDescription(), fontheader);
			paragraph1.add(chunk);

			Paragraph paragraph2 = new Paragraph(20);
			paragraph2.setSpacingBefore(10);

			PdfPTable headerNotification = new PdfPTable(1);
			headerNotification.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerNotification.setWidthPercentage(100f);
			headerNotification.addCell(new PdfPCell(new Phrase(
					"***PLEASE READ THE ENTIRE FORM BEFORE SIGNING BELOW***",
					fontnotification)));

			Chunk chunk2 = new Chunk(
					"Patient (name and information of person whose health information is being disclosed): \n\n",
					fontbold);
			Chunk chunk31 = new Chunk("Name (First Middle Last):");
			Chunk chunk32 = new Chunk("Date of Birth(mm/dd/yyyy): ");
			Chunk chunk33 = new Chunk("Street: ");
			Chunk chunk34 = new Chunk("City: ");
			Chunk chunk35 = new Chunk("State: ");
			Chunk chunk36 = new Chunk("Zip: ");
			String patientName = consent.getPatient().getFirstName() + " "
					+ consent.getPatient().getLastName() + "\n";
			Chunk chunkDob = null;
			Chunk chunkStreet = null;
			Chunk chunkCity = null;
			Chunk chunkState = null;
			Chunk chunkZip = null;

			if (consent.getPatient().getBirthDay() != null) {
				chunkDob = new Chunk(String.format("%tm/%td/%tY", consent
						.getPatient().getBirthDay(), consent.getPatient()
						.getBirthDay(), consent.getPatient().getBirthDay())
						+ "\n");
				chunkDob.setUnderline(1, -2);
			}
			if (consent.getPatient().getAddress() != null) {
				if (consent.getPatient().getAddress().getStreetAddressLine() != null) {
					chunkStreet = new Chunk(consent.getPatient().getAddress()
							.getStreetAddressLine()
							+ "\n");
					chunkStreet.setUnderline(1, -2);
				}
				if (consent.getPatient().getAddress().getCity() != null) {
					chunkCity = new Chunk(consent.getPatient().getAddress()
							.getCity()
							+ "  ");
					chunkCity.setUnderline(1, -2);
				}
				if (consent.getPatient().getAddress().getStateCode() != null) {
					chunkState = new Chunk(consent.getPatient().getAddress()
							.getStateCode().getDisplayName()
							+ "  ");
					chunkState.setUnderline(1, -2);
				}
				if (consent.getPatient().getAddress().getPostalCode() != null) {
					chunkZip = new Chunk(consent.getPatient().getAddress()
							.getPostalCode());
					chunkZip.setUnderline(1, -2);
				}
			}

			Chunk chunk3 = new Chunk(patientName);
			chunk3.setUnderline(1, -2);

			Chunk chunk4 = new Chunk("authorizes ");
			Chunk chunk6 = new Chunk(" to disclose to ");
			Chunk chunk8 = new Chunk("Share the following medical information",
					fontbold);
			chunk8.setUnderline(1, -2);
			paragraph2.add(headerNotification);
			paragraph2.add(chunk2);
			paragraph2.add(chunk31);
			paragraph2.add(chunk3);
			if (chunkDob != null) {
				paragraph2.add(chunk32);
				paragraph2.add(chunkDob);
			}
			if (consent.getPatient().getAddress() != null) {
				paragraph2.add(chunk33);
				paragraph2.add(chunkStreet);
				paragraph2.add(chunk34);
				paragraph2.add(chunkCity);
				paragraph2.add(chunk35);
				paragraph2.add(chunkState);
				paragraph2.add(chunk36);
				paragraph2.add(chunkZip);
			}
			paragraph2.add(new Chunk("\n\n"));
			paragraph2.add(chunk4);
			paragraph2.add(createConsentMadeFromTable(consent));
			paragraph2.add(chunk6);
			paragraph2.add(createConsentMadeToTable(consent));
			paragraph2.add(chunk8);

			// Do Not Share Sensitivity List
			Paragraph sensitivityParagraph = getSensitivityParagraph(consent);

			// Do Not Share Clinical Document Type List
			Paragraph clinicalDocumentTypeParagraph = getClinicalDocumentTypeParagraph(consent);

			// Do Not Share Clinical Document Section Type List
			Paragraph clinicalDocumentSectionTypeParagraph = getClinicalDocumentSectionTypeParagraph(consent);

			Paragraph clinicalConceptCodesParagraph = getClinicalConceptCodesParagraph(consent);

			Chunk chunk9 = null;
			if (sensitivityParagraph != null
					|| clinicalDocumentTypeParagraph != null
					|| clinicalDocumentSectionTypeParagraph != null
					|| clinicalConceptCodesParagraph != null) {
				// chunk9 = new Chunk(" except the following:");
				chunk9 = new Chunk(" :\n\n");
				// chunk9.setUnderline(1, -2);
			} else {
				chunk9 = new Chunk(".");
			}

			paragraph2.add(chunk9);

			Paragraph paragraph4 = new Paragraph(20);
			paragraph4.setSpacingAfter(10);
			paragraph4.setSpacingBefore(10);

			Chunk chunk11 = new Chunk("Share for the following purpose(s):",
					fontbold);
			chunk11.setUnderline(1, -2);

			paragraph4.add(new Chunk("\n"));
			paragraph4.add(chunk11);

			// Do Not Share Purpose of Use List
			Paragraph purposeOfUseParagraph = getPurposeOfUseParagraph(consent);

			Paragraph paragraph5 = new Paragraph(20);
			paragraph5.setSpacingBefore(10);

			Chunk chunk13 = new Chunk(
					"I understand that my records are protected under the federal regulations governing Confidentiality of Alcohol and Drug Abuse Patient Records, 42 CFR Part 2, and cannot be disclosed without my written consent unless otherwise provided for in the regulations, and may not be redisclosed without my written permission or as otherwise permitted by 42 CFR part 2. I also understand that I may revoke this consent at any time except to the extent that action has been taken in reliance on it, and that in any event this consent expires automatically as follows:");
			paragraph5.add(chunk13);

			Paragraph paragraph6 = new Paragraph(20);

			Chunk chunk14;
			if (consent.getEndDate() != null) {
				chunk14 = new Chunk(String.format(
						"Expiration Date: %tm/%td/%ty", consent.getEndDate(),
						consent.getEndDate(), consent.getEndDate()));
			} else {
				chunk14 = new Chunk("N/A");
			}

			chunk14.setUnderline(1, -2);

			Chunk chunk15;
			if (consent.getEndDate() != null) {
				chunk15 = new Chunk(String.format(
						"Effective Date: %tm/%td/%ty\n",
						consent.getStartDate(), consent.getStartDate(),
						consent.getStartDate()));
			} else {
				chunk15 = new Chunk("N/A");
			}

			chunk15.setUnderline(1, -2);

			paragraph6.add(chunk15);
			paragraph6.add(chunk14);

			document.add(consentID);
			document.add(paragraph1);
			document.add(paragraph2);

			if (sensitivityParagraph != null) {
				document.add(sensitivityParagraph);
			}
			if (clinicalDocumentTypeParagraph != null) {
				document.add(clinicalDocumentTypeParagraph);
			}
			if (clinicalDocumentSectionTypeParagraph != null) {
				document.add(clinicalDocumentSectionTypeParagraph);
			}
			if (clinicalConceptCodesParagraph != null) {
				document.add(clinicalConceptCodesParagraph);
			}

			document.add(paragraph4);

			if (purposeOfUseParagraph != null) {
				document.add(purposeOfUseParagraph);
			}

			document.add(paragraph5);
			document.add(paragraph6);

			// Step 5
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
	 * Gets the clinical document section type paragraph.
	 *
	 * @param consent
	 *            the consent
	 * @return the clinical document section type paragraph
	 */
	private Paragraph getClinicalDocumentSectionTypeParagraph(Consent consent) {
		Paragraph paragraph = null;
		Set<ConsentDoNotShareClinicalDocumentSectionTypeCode> doNotShareClinicalDocumentSectionTypeCodes = consent
				.getDoNotShareClinicalDocumentSectionTypeCodes();
		Set<MedicalSection> medicalSections = new HashSet<MedicalSection>(
				medicalSectionRepository.findAll());

		if (doNotShareClinicalDocumentSectionTypeCodes.size() < medicalSections
				.size()) {
			paragraph = new Paragraph();
			paragraph.add(new Chunk("Medical Information Categories:"));

			List<String> nameList = new ArrayList<String>();
			for (ConsentDoNotShareClinicalDocumentSectionTypeCode consentClinicalDocumentSectionTypeCode : doNotShareClinicalDocumentSectionTypeCodes) {
				medicalSections.remove(consentClinicalDocumentSectionTypeCode
						.getMedicalSection());

			}

			for (MedicalSection medicalSection : medicalSections)
				nameList.add(medicalSection.getName());
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
		PdfPTable table = new PdfPTable(new float[] { 0.2f, 0.2f, 0.4f, 0.2f });
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
		PdfPTable table = new PdfPTable(new float[] { 0.2f, 0.2f, 0.4f, 0.2f });
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
