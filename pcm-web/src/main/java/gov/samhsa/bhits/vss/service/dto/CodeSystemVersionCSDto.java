package gov.samhsa.bhits.vss.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeSystemVersionCSDto {

	private CodeSystemVersionDto codeSystemVersionDto = new CodeSystemVersionDto();
	private Map<Long,String> codeSystemDtoMap =  new HashMap<Long, String>();
	private List<Map<Long,String>> codeSystemVersions = new ArrayList<Map<Long,String>>();

	public CodeSystemVersionDto getCodeSystemVersionDto() {
		return codeSystemVersionDto;
	}
	public void setCodeSystemVersionDto(CodeSystemVersionDto codeSystemVersionDto) {
		this.codeSystemVersionDto = codeSystemVersionDto;
	}
	public Map<Long, String> getCodeSystemDtoMap() {
		return codeSystemDtoMap;
	}
	public void setCodeSystemDtoMap(Map<Long, String> codeSystemDtoMap) {
		this.codeSystemDtoMap = codeSystemDtoMap;
	}
	public List<Map<Long, String>> getCodeSystemVersions() {
		return codeSystemVersions;
	}
	public void setCodeSystemVersions(List<Map<Long, String>> codeSystemVersions) {
		this.codeSystemVersions = codeSystemVersions;
	}

	
}
