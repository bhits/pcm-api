package gov.samhsa.c2s.vss.service.dto;

import java.util.HashMap;
import java.util.Map;

public class ValueSetVSCDto extends AbstractNodeDto {

	private ValueSetDto valueSetDto = new ValueSetDto();
	private Map<Long,String> valueSetCategoryMap =  new HashMap<Long, String>();
	
	public ValueSetDto getValueSetDto() {
		return valueSetDto;
	}
	public void setValueSetDto(ValueSetDto valueSetDto) {
		this.valueSetDto = valueSetDto;
	}
	public Map<Long, String> getValueSetCategoryMap() {
		return valueSetCategoryMap;
	}
	public void setValueSetCategoryMap(Map<Long, String> valueSetCategoryMap) {
		this.valueSetCategoryMap = valueSetCategoryMap;
	}

	
}
