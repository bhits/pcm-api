package gov.samhsa.mhc.vss.service.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class CodeSystemDto extends AbstractNodeDto {


    private String displayName;

    private List<ValueSetDto> valueSets = new ArrayList<ValueSetDto>();

    @NotEmpty
    private String codeSystemOId;


    public String getDisplayName() {
        return displayName;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public String getCodeSystemOId() {
        return codeSystemOId;
    }


    public void setCodeSystemOId(String codeSystemOId) {
        this.codeSystemOId = codeSystemOId;
    }

    public List<ValueSetDto> getValueSets() {
        return valueSets;
    }


    public void setValueSets(List<ValueSetDto> valueSets) {
        this.valueSets = valueSets;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
