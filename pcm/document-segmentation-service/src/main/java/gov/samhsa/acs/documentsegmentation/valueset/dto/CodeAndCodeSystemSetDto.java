package gov.samhsa.acs.documentsegmentation.valueset.dto;

public class CodeAndCodeSystemSetDto {
	/** The concept code. */
	private String conceptCode;

	/** The code system oid. */
	private String codeSystemOid;

	public String getConceptCode() {
		return conceptCode;
	}

	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}

	public String getCodeSystemOid() {
		return codeSystemOid;
	}

	public void setCodeSystemOid(String codeSystemOid) {
		this.codeSystemOid = codeSystemOid;
	}
	
	
}
