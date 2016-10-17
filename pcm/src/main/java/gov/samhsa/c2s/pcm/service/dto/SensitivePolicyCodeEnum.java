package gov.samhsa.c2s.pcm.service.dto;

public enum SensitivePolicyCodeEnum {

    SUBSTANCE_ABUSE("ETH", "Substance abuse information sensitivity", "ActInformationSensitivityPolicy", "2.16.840.1.113883.1.11.20428", true),
    GENETIC_DISEASE("GDIS", "Genetic disease information sensitivity", "ActInformationSensitivityPolicy", "2.16.840.1.113883.1.11.20428", true),
    HIV("HIV", "HIV/AIDS information sensitivity", "ActInformationSensitivityPolicy", "2.16.840.1.113883.1.11.20428", true),
    PSYCHIATRIC("PSY", "Psychiatry information sensitivity", "ActInformationSensitivityPolicy", "2.16.840.1.113883.1.11.20428", true),
    SEXUAL_ASSAULT("SDV", "Sexual assault, abuse, or domestic violence information sensitivity", "ActInformationSensitivityPolicy", "2.16.840.1.113883.1.11.20428", true),
    SEXUALITY("SEX", "Sexuality and reproductive health information sensitivity", "ActInformationSensitivityPolicy", "2.16.840.1.113883.1.11.20428", true),
    ALCOHOLIC("ALC", "Alcohol use and Alcoholism Information", "ActInformationSensitivityPolicy", "2.16.840.1.113883.1.11.20428", true),
    SEXUALLY_TRANSMITTED("STD", "Sexually transmitted disease information sensitivity", "ActInformationSensitivityPolicy", "2.16.840.1.113883.1.11.20428", true);

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
