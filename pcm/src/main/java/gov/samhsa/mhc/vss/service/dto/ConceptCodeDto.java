package gov.samhsa.mhc.vss.service.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConceptCodeDto extends AbstractNodeDto {

    private Map<Long, String> valueSetMap = new HashMap<Long, String>();

    private Long valueSetId;
    private String valueSetName;

    private Long codeSystemId;
    private String codeSystemName;

    private Long codeSystemVersionId;
    private String codeSystemVersionName;

    private List<Long> valueSetIds;


    private List<Integer> listOfDuplicatesCodes;
    private int conceptCodesInserted;

    private String description;

    public Long getCodeSystemId() {
        return codeSystemId;
    }

    public String getCodeSystemName() {
        return codeSystemName;
    }

    public Long getCodeSystemVersionId() {
        return codeSystemVersionId;
    }

    public String getCodeSystemVersionName() {
        return codeSystemVersionName;
    }

    public Long getValueSetId() {
        return valueSetId;
    }

    public String getValueSetName() {
        return valueSetName;
    }

    public void setCodeSystemId(Long codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }

    public void setCodeSystemVersionId(Long codeSystemVersionId) {
        this.codeSystemVersionId = codeSystemVersionId;
    }

    public void setCodeSystemVersionName(String codeSystemVersionName) {
        this.codeSystemVersionName = codeSystemVersionName;
    }

    public void setValueSetId(Long valueSetId) {
        this.valueSetId = valueSetId;
    }

    public void setValueSetName(String valueSetName) {
        this.valueSetName = valueSetName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getValueSetIds() {
        return valueSetIds;
    }

    public void setValueSetIds(List<Long> valueSetIds) {
        this.valueSetIds = valueSetIds;
    }

    public Map<Long, String> getValueSetMap() {
        return valueSetMap;
    }

    public void setValueSetMap(Map<Long, String> valueSetMap) {
        this.valueSetMap = valueSetMap;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    public List<Integer> getListOfDuplicatesCodes() {
        return listOfDuplicatesCodes;
    }

    public void setListOfDuplicatesCodes(List<Integer> listOfDuplicatesCodes) {
        this.listOfDuplicatesCodes = listOfDuplicatesCodes;
    }

    public int getConceptCodesInserted() {
        return conceptCodesInserted;
    }

    public void setConceptCodesInserted(int conceptCodesInserted) {
        this.conceptCodesInserted = conceptCodesInserted;
    }

}
