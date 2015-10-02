package gov.samhsa.consent2share.service.dto;

import java.util.Set;

public class ValueSetQueryDto {
	
	private String conceptCode;
	
	private String codeSystemOid;
	
	private Set<String> vsCategoryCodes;

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

	public Set<String> getVsCategoryCodes() {
		return vsCategoryCodes;
	}

	public void setVsCategoryCodes(Set<String> vsCategoryCodes) {
		this.vsCategoryCodes = vsCategoryCodes;
	}
	
}
