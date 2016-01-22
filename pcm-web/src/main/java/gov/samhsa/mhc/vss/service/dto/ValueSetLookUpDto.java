package gov.samhsa.mhc.vss.service.dto;

import java.util.HashSet;
import java.util.Set;


public class ValueSetLookUpDto extends AbstractNodeDto {
	
	private String conceptCode="";
	
	private String codeSystemOid="";
	
	private Set<String> vsCategoryCodes = new HashSet<String>();

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
