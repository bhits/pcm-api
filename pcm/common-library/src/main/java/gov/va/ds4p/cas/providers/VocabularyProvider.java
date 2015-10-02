package gov.va.ds4p.cas.providers;

import gov.va.ds4p.policy.reference.ApplicableObligationPolicies;
import gov.va.ds4p.policy.reference.ApplicableRefrainPolicies;
import gov.va.ds4p.policy.reference.ApplicableSensitivityCodes;
import gov.va.ds4p.policy.reference.ApplicableUSLaws;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class VocabularyProvider {
	private String senseFile = "/vocabulary/SensitivityCodes.xml";
	private String obFile = "/vocabulary/ObligationPolicies.xml";
	private String refrainFile = "/vocabulary/RefrainPolicies.xml";
	private String lawFile = "/vocabulary/USPrivacyLawReferences.xml";
	private ApplicableObligationPolicies documentHandlingObligations;
	private ApplicableRefrainPolicies refrainObligations;
	private ApplicableSensitivityCodes dataSegmentationObligations;
	private ApplicableUSLaws privacyLawObligations;

	public VocabularyProvider() {
		ProcessVocabulary(this.senseFile, "sensitivities");
		ProcessVocabulary(this.obFile, "obligations");
		ProcessVocabulary(this.refrainFile, "refrainpolicies");
		ProcessVocabulary(this.lawFile, "privacylaw");
	}

	private void ProcessVocabulary(String fname, String vType) {
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					getClass().getResourceAsStream(fname)));
			ByteArrayOutputStream aout = new ByteArrayOutputStream();
			BufferedWriter wtr = new BufferedWriter(
					new OutputStreamWriter(aout));
			int result = r.read();
			while (result != -1) {
				byte b = (byte) result;
				aout.write(b);
				result = r.read();
			}
			String res = aout.toString();
			aout.close();
			r.close();

			if (vType.equals("sensitivities")) {
				createSensitivityObjectsFromXML(res);
			} else if (vType.equals("obligations")) {
				createObligationObjectsFromXML(res);
			} else if (vType.equals("refrainpolicies")) {
				createRefrainPolicyObjectsFromXML(res);
			} else if (vType.equals("privacylaw")) {
				createUSPrivacyLawObjectsFromXML(res);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ERROR: VOCABULARYPROVIDER: " + ex.getMessage());
		}
	}

	private void createSensitivityObjectsFromXML(String c32) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(new Class[] { ApplicableSensitivityCodes.class });
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(c32);

			Object o = unmarshaller.unmarshal(sr);
			setDataSegmentationObligations((ApplicableSensitivityCodes) o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createObligationObjectsFromXML(String c32) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(new Class[] { ApplicableObligationPolicies.class });
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(c32);

			Object o = unmarshaller.unmarshal(sr);
			setDocumentHandlingObligations((ApplicableObligationPolicies) o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createRefrainPolicyObjectsFromXML(String c32) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(new Class[] { ApplicableRefrainPolicies.class });
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(c32);

			Object o = unmarshaller.unmarshal(sr);
			setRefrainObligations((ApplicableRefrainPolicies) o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createUSPrivacyLawObjectsFromXML(String c32) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(new Class[] { ApplicableUSLaws.class });
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(c32);

			Object o = unmarshaller.unmarshal(sr);
			setPrivacyLawObligations((ApplicableUSLaws) o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ApplicableObligationPolicies getDocumentHandlingObligations() {
		return this.documentHandlingObligations;
	}

	public void setDocumentHandlingObligations(
			ApplicableObligationPolicies documentHandlingObligations) {
		this.documentHandlingObligations = documentHandlingObligations;
	}

	public ApplicableRefrainPolicies getRefrainObligations() {
		return this.refrainObligations;
	}

	public void setRefrainObligations(
			ApplicableRefrainPolicies refrainObligations) {
		this.refrainObligations = refrainObligations;
	}

	public ApplicableSensitivityCodes getDataSegmentationObligations() {
		return this.dataSegmentationObligations;
	}

	public void setDataSegmentationObligations(
			ApplicableSensitivityCodes dataSegmentationObligations) {
		this.dataSegmentationObligations = dataSegmentationObligations;
	}

	public ApplicableUSLaws getPrivacyLawObligations() {
		return this.privacyLawObligations;
	}

	public void setPrivacyLawObligations(ApplicableUSLaws privacyLawObligations) {
		this.privacyLawObligations = privacyLawObligations;
	}
}
