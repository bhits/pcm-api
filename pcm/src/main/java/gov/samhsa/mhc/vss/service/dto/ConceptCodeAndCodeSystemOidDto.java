package gov.samhsa.mhc.vss.service.dto;


import org.hibernate.validator.constraints.NotEmpty;

public class ConceptCodeAndCodeSystemOidDto {
    @NotEmpty
    private String conceptCode;
    @NotEmpty
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