package gov.samhsa.c2s.pcm.service.dto;

//TODO::  Need to dynamically create from value set category table
public enum SensitivePolicyCodeEnum {

    DRUG_ABUSE("ETH", "Drug use information", "v3 Code System ActCode", "http://hl7.org/fhir/v3/ActCode", true),
    GENETIC_DISEASE("GDIS", "Genetic disease information", "v3 Code System ActCode", "http://hl7.org/fhir/v3/ActCode", true),
    HIV("HIV", "HIV/AIDS information'", "v3 Code System ActCode", "http://hl7.org/fhir/v3/ActCode", true),
    PSYCHIATRIC("PSY", "Mental health information", "v3 Code System ActCode", "http://hl7.org/fhir/v3/ActCode", true),
    COM_DISEASE("COM", "Communicable disease information", "v3 Code System ActCode", "http://hl7.org/fhir/v3/ActCode", true),
    SEX("SEX", "Sexuality and reproductive health information", "v3 Code System ActCode", "http://hl7.org/fhir/v3/ActCode", true),
    ALCOHOLIC("ALC", "Alcohol use and Alcoholism Information", "v3 Code System ActCode", "http://hl7.org/fhir/v3/ActCode", true),
    ADDICTION("ADD", "Addictions information", "v3 Code System ActCode", "http://hl7.org/fhir/v3/ActCode", true);

    private String code;
    private String displayName;
    private String codeSystemName;
    private String codeSystem;
    private boolean isInclude;

    SensitivePolicyCodeEnum(String code, String displayName, String codeSystemName, String codeSystem, boolean isInclude) {
        this.code = code;
        this.displayName = displayName;
        this.codeSystemName = codeSystemName;
        this.codeSystem = codeSystem;
        this.isInclude = isInclude;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCodeSystemName() {
        return codeSystemName;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    public boolean isInclude() {
        return isInclude;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }

    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    public void setInclude(boolean include) {
        isInclude = include;
    }
}
