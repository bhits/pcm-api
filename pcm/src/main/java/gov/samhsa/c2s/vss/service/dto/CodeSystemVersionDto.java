package gov.samhsa.c2s.vss.service.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CodeSystemVersionDto extends AbstractNodeDto {

    private String description;
    private List<CodeSystemDto> codeSystems = new ArrayList<CodeSystemDto>();
    private Map<Long, String> codeSystemMap = new HashMap<Long, String>();
    private Long codeSystemId;

    private String codeSystemName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CodeSystemDto> getCodeSystems() {
        return codeSystems;
    }

    public void setCodeSystems(List<CodeSystemDto> codeSystems) {
        this.codeSystems = codeSystems;
    }

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

    public Map<Long, String> getCodeSystemMap() {
        return codeSystemMap;
    }

    public void setCodeSystemMap(Map<Long, String> codeSystemMap) {
        this.codeSystemMap = codeSystemMap;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
