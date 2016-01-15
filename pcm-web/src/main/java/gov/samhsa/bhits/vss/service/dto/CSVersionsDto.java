package gov.samhsa.bhits.vss.service.dto;

import java.util.HashMap;
import java.util.Map;

public class CSVersionsDto {

	private Long codeSystemId;
	private String codeSystemName;
	Map<Long,String> versionsMap = new HashMap<Long, String>();
	public Long getCodeSystemId() {
		return codeSystemId;
	}
	public void setCodeSystemId(Long codeSystemId) {
		this.codeSystemId = codeSystemId;
	}
	public String getCodeSystemName() {
		return codeSystemName;
	}
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}
	public Map<Long, String> getVersionsMap() {
		return versionsMap;
	}
	public void setVersionsMap(Map<Long, String> versionsMap) {
		this.versionsMap = versionsMap;
	}
	

	
}
