package gov.va.ds4p.cas.providers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gov.va.ds4p.policy.reference.Addr;
import gov.va.ds4p.policy.reference.DefaultPatientDemographics;
import gov.va.ds4p.policy.reference.HumanReadibleText;
import gov.va.ds4p.policy.reference.OrganizationConsentPolicyInfo;
import gov.va.ds4p.policy.reference.OrganizationPolicy;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PDFBuilderForCDA {
	private Document document = null;
	private String organizationName;
	private String oAddress;
	private String oCity;
	private String oState;
	private String oZip;
	private String oPhone;
	private String patientId;
	private String patientName;
	private String pAddress;
	private String pCity;
	private String pState;
	private String pZip;
	private String pPhone;
	private String pDoB;
	private String privacyLaw;
	private String privacyAct;
	private String privacyStatement;
	private List<String> recipients;
	private List<String> mask;
	private List<String> redact;
	private List<String> pous;
	private String patientAuthorization;
	private Font tmBig = new Font(Font.FontFamily.TIMES_ROMAN, 14.0F);
	private Font tmbase = new Font(Font.FontFamily.TIMES_ROMAN, 10.0F);
	private Font tmsmall = new Font(Font.FontFamily.TIMES_ROMAN, 8.0F);

	public PDFBuilderForCDA(OrganizationPolicy orgPolicy, String patientid,
			String patientname, String authorization, List<String> allowedPOU,
			List<String> allowedRecipients, List<String> redactActions,
			List<String> maskingActions) {
		this.document = null;
		this.organizationName = orgPolicy.getOrgName();
		this.oAddress = orgPolicy.getOrganizationConsentPolicyInfo().getAddr()
				.getStreetAddressLine();
		this.oCity = orgPolicy.getOrganizationConsentPolicyInfo().getAddr()
				.getCity();
		this.oState = orgPolicy.getOrganizationConsentPolicyInfo().getAddr()
				.getState();
		this.oZip = orgPolicy.getOrganizationConsentPolicyInfo().getAddr()
				.getPostalCode();

		this.patientId = patientid;
		this.patientName = patientname;
		this.pAddress = orgPolicy.getOrganizationConsentPolicyInfo()
				.getDefaultPatientDemographics().getAddr()
				.getStreetAddressLine();
		this.pCity = orgPolicy.getOrganizationConsentPolicyInfo()
				.getDefaultPatientDemographics().getAddr().getCity();
		this.pState = orgPolicy.getOrganizationConsentPolicyInfo()
				.getDefaultPatientDemographics().getAddr().getState();
		this.pZip = orgPolicy.getOrganizationConsentPolicyInfo()
				.getDefaultPatientDemographics().getAddr().getPostalCode();
		this.pPhone = orgPolicy.getOrganizationConsentPolicyInfo()
				.getDefaultPatientDemographics().getPatientTelcom();

		this.privacyLaw = orgPolicy.getUsPrivacyLaw();
		HumanReadibleText hText = orgPolicy.getOrganizationConsentPolicyInfo()
				.getHumanReadibleText();
		this.privacyStatement = hText.getDisplayText();

		this.recipients = allowedRecipients;

		this.mask = maskingActions;
		this.redact = redactActions;
		this.pous = allowedPOU;
		this.patientAuthorization = authorization;
	}

	public String getPDFConsentDirective() {
		String res = "";
		try {
			PdfConsentBuilder builder = new PdfConsentBuilder();
			res = builder.getDocumentString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	public class PdfConsentBuilder {
		private final ByteArrayOutputStream os = new ByteArrayOutputStream();

		public PdfConsentBuilder() {
			String header = "Request for an Authorization to Release Protected Health Information (PHI)";
			if (PDFBuilderForCDA.this.privacyLaw.equals("Title38Section7338")) {
				PDFBuilderForCDA.this.privacyAct = "Privacy Act: The execution of this form does not authorize the release of information other than that specifically described below. The information requested on this form is solicited under Title 38, U.S.C. 7332. Your disclosure of the information requested on this form is voluntary. However, if the information including Social Security Number (SSN) (the SSN will be used to locate records for release) is not furnished completely and accurately, Department of Veterans Affairs will be unable to comply with your request to participate in the NHIN program. The Veterans Health Administration may not condition treatment on signing of this form. Failure to furnish the information will not have any effect on any other benefits to which you may be entitled.";
			} else if (PDFBuilderForCDA.this.privacyLaw.equals("42CFRPart2")) {
				PDFBuilderForCDA.this.privacyAct = "Privacy Act: 42 CFR Part 2 protects clients who have applied for, participated in, or received an interview, counseling, or any other service from a federally assisted alcohol or drug abuse program, identified as an alcohol or drug client during an evaluation of eligibility for treatment. Programs may not disclose any identifying information unless the patient has given consent or otherwise specifically permitted under 42 CFR Part 2.";
			} else {
				PDFBuilderForCDA.this.privacyAct = PDFBuilderForCDA.this.privacyStatement;
			}
			String infoRequested = "Information Requested:";
			String infoRequested2 = "Pertinent health information that may include but is not limited to the following: ";
			String infoRequested3 = "Medications, Vitals, Problem List, Health Summary, Progress Notes";

			String forPeriodOfLBL = "This authorization will remain in effect until I revoke my authorization or for the period of five years whichever is sooner.";

			String certificationLBL = "AUTHORIZATION: I certify that this request has been made freely, voluntarily and without ";
			String certificationLBL2 = "coercion and that the information given above is accurate and complete to the best of my knowledge.";

			String documentDate = "Date: " + getCurrentDate();

			String disclosureAction = "Disclosure Action Requested: "
					+ PDFBuilderForCDA.this.patientAuthorization;
			try {
				PDFBuilderForCDA.this.document = new Document(PageSize.A4,
						25.0F, 25.0F, 25.0F, 25.0F);
				PdfWriter.getInstance(PDFBuilderForCDA.this.document, this.os);

				PDFBuilderForCDA.this.document.open();

				addHeaderImage();
				PDFBuilderForCDA.this.document.add(getNamesTable());
				PDFBuilderForCDA.this.document.add(new Paragraph(documentDate,
						PDFBuilderForCDA.this.tmbase));
				Paragraph headerP = new Paragraph(header,
						PDFBuilderForCDA.this.tmbase);
				headerP.setSpacingBefore(1.0F);
				headerP.setSpacingAfter(1.0F);
				PDFBuilderForCDA.this.document.add(headerP);

				PDFBuilderForCDA.this.document.add(getPrivacyStatement());

				PDFBuilderForCDA.this.document.add(new Paragraph(infoRequested,
						PDFBuilderForCDA.this.tmbase));
				PDFBuilderForCDA.this.document.add(new Paragraph(
						infoRequested2, PDFBuilderForCDA.this.tmbase));
				Paragraph infoP = new Paragraph(infoRequested3,
						PDFBuilderForCDA.this.tmbase);
				infoP.setSpacingAfter(1.0F);
				PDFBuilderForCDA.this.document.add(infoP);

				Paragraph disclosure = new Paragraph(disclosureAction,
						PDFBuilderForCDA.this.tmbase);
				disclosure.setSpacingAfter(1.0F);
				PDFBuilderForCDA.this.document.add(disclosure);

				PDFBuilderForCDA.this.document.add(getAuthorizedRecipients());

				PDFBuilderForCDA.this.document.add(getAllowedPOUs());

				if ((PDFBuilderForCDA.this.patientAuthorization
						.equals("Permit"))
						|| (PDFBuilderForCDA.this.patientAuthorization
								.equals("Disclose"))) {
					PDFBuilderForCDA.this.document.add(getRedactActions());
					PDFBuilderForCDA.this.document.add(getMaskingActions());
				}
				Paragraph period = new Paragraph(forPeriodOfLBL,
						PDFBuilderForCDA.this.tmbase);
				period.setSpacingAfter(1.0F);
				PDFBuilderForCDA.this.document.add(period);
				PDFBuilderForCDA.this.document.add(new Paragraph(
						certificationLBL, PDFBuilderForCDA.this.tmsmall));
				PDFBuilderForCDA.this.document.add(new Paragraph(
						certificationLBL2, PDFBuilderForCDA.this.tmsmall));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (PDFBuilderForCDA.this.document != null)
					PDFBuilderForCDA.this.document.close();
			}
		}

		private void addHeaderImage() {
			try {
				URL imageURL = getClass().getResource("/images/logo.png");
				Image logo = Image.getInstance(imageURL);
				float x = logo.getWidth();
				float y = logo.getHeight();

				System.out.println("IMAGE HEIGHT: " + y + "IMAGE WIDTH: " + x);
				PDFBuilderForCDA.this.document.add(logo);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		private Paragraph getPrivacyStatement() {
			Paragraph p = new Paragraph(PDFBuilderForCDA.this.privacyAct,
					PDFBuilderForCDA.this.tmbase);
			p.setSpacingAfter(1.0F);
			p.setSpacingAfter(1.0F);
			return p;
		}

		private PdfPTable getNamesTable() {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100.0F);
			table.getDefaultCell().setBorder(0);
			table.getDefaultCell().setHorizontalAlignment(0);
			table.addCell(PDFBuilderForCDA.this.patientName);
			table.getDefaultCell().setHorizontalAlignment(2);
			table.addCell(PDFBuilderForCDA.this.organizationName);
			table.getDefaultCell().setHorizontalAlignment(0);
			table.addCell(PDFBuilderForCDA.this.pAddress);
			table.getDefaultCell().setHorizontalAlignment(2);
			table.addCell(PDFBuilderForCDA.this.oAddress);
			table.getDefaultCell().setHorizontalAlignment(0);
			table.addCell(PDFBuilderForCDA.this.pCity + ", "
					+ PDFBuilderForCDA.this.pState + "  "
					+ PDFBuilderForCDA.this.pZip);
			table.getDefaultCell().setHorizontalAlignment(2);
			table.addCell(PDFBuilderForCDA.this.oCity + ", "
					+ PDFBuilderForCDA.this.oState + "  "
					+ PDFBuilderForCDA.this.oZip);
			PdfPCell mCell = new PdfPCell(new Phrase("Phone: "
					+ PDFBuilderForCDA.this.pPhone + "  DoB: "
					+ PDFBuilderForCDA.this.pDoB));
			mCell.setColspan(2);
			table.getDefaultCell().setHorizontalAlignment(0);
			table.addCell(mCell);
			return table;
		}

		private PdfPTable getAuthorizedRecipients() {
			PdfPTable table = new PdfPTable(1);
			table.setSpacingAfter(1.0F);
			table.setSpacingBefore(1.0F);
			table.getDefaultCell().setBackgroundColor(BaseColor.CYAN);
			table.setWidthPercentage(40.0F);
			table.addCell("Authorized Recipient(s)");
			table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
			Iterator iter = PDFBuilderForCDA.this.recipients.iterator();
			while (iter.hasNext()) {
				String r = (String) iter.next();
				table.addCell(r);
			}
			return table;
		}

		private PdfPTable getMaskingActions() {
			PdfPTable table = new PdfPTable(1);
			table.setSpacingAfter(1.0F);
			table.setSpacingBefore(1.0F);
			table.getDefaultCell().setBackgroundColor(BaseColor.CYAN);
			table.setWidthPercentage(40.0F);
			table.addCell("Data Sensitivities Requiring Masking(Encryption)");
			table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
			Iterator iter = PDFBuilderForCDA.this.mask.iterator();
			while (iter.hasNext()) {
				String r = (String) iter.next();
				table.addCell(r);
			}
			return table;
		}

		private PdfPTable getRedactActions() {
			PdfPTable table = new PdfPTable(1);
			table.setSpacingAfter(1.0F);
			table.setSpacingBefore(1.0F);
			table.getDefaultCell().setBackgroundColor(BaseColor.CYAN);
			table.setWidthPercentage(40.0F);
			table.addCell("Data Sensitivities Requiring Redaction(Removal From Record)");
			table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
			Iterator iter = PDFBuilderForCDA.this.redact.iterator();
			while (iter.hasNext()) {
				String r = (String) iter.next();
				table.addCell(r);
			}
			return table;
		}

		private PdfPTable getAllowedPOUs() {
			PdfPTable table = new PdfPTable(1);
			table.setSpacingAfter(1.0F);
			table.setSpacingBefore(1.0F);
			table.getDefaultCell().setBackgroundColor(BaseColor.CYAN);
			table.setWidthPercentage(40.0F);
			table.addCell("Intended Purpose(s) of Use");
			table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
			Iterator iter = PDFBuilderForCDA.this.pous.iterator();
			while (iter.hasNext()) {
				String r = (String) iter.next();
				table.addCell(r);
			}
			return table;
		}

		private String getCurrentDate() {
			String res = "";
			Date dt = new Date();
			SimpleDateFormat sdt = new SimpleDateFormat("M dd, yyyy");
			try {
				res = sdt.format(dt);
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}

			return res;
		}

		public String getDocumentString() {
			return new String(this.os.toByteArray());
		}
	}
}
