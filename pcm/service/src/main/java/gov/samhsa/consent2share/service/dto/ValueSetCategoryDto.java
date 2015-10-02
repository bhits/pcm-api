package gov.samhsa.consent2share.service.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ValueSetCategoryDto extends AbstractNodeDto{
	
	private String description;
	
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }	

}
