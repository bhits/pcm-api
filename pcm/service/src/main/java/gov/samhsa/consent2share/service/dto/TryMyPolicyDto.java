package gov.samhsa.consent2share.service.dto;

import java.util.HashMap;
import java.util.List;

/**
 * The Class TryMyPolicyDto.
 */
public class TryMyPolicyDto {

	/** The c32 documents. */
	List<ClinicalDocumentDto> c32Documents;

	/** The share for purpose of use codes. */
	HashMap<String, String> shareForPurposeOfUseCodesAndValues;

	/**
	 * Gets the share for purpose of use codes and values.
	 *
	 * @return the share for purpose of use codes and values
	 */
	public HashMap<String, String> getShareForPurposeOfUseCodesAndValues() {
		return shareForPurposeOfUseCodesAndValues;
	}

	/**
	 * Sets the share for purpose of use codes and values.
	 *
	 * @param shareForPurposeOfUseCodesAndValues the share for purpose of use codes and values
	 */
	public void setShareForPurposeOfUseCodesAndValues(
			HashMap<String, String> shareForPurposeOfUseCodesAndValues) {
		this.shareForPurposeOfUseCodesAndValues = shareForPurposeOfUseCodesAndValues;
	}

	/**
	 * Gets the c32 documents.
	 * 
	 * @return the c32 documents
	 */
	public List<ClinicalDocumentDto> getC32Documents() {
		return c32Documents;
	}

	/**
	 * Sets the c32 documents.
	 * 
	 * @param c32Documents
	 *            the new c32 documents
	 */
	public void setC32Documents(List<ClinicalDocumentDto> c32Documents) {
		this.c32Documents = c32Documents;
	}

}
