package gov.samhsa.acs.documentsegmentation.valueset;

public class ConceptCode {
	private String code;
	private String codeSystem;
	private String codeSystemName;
	private String valueSet;
	private String valueSetCategory;
	private String codeName;

	private static final String CODE = "ConceptCode:";
	private static final String CODESYSTEM = "CodeSystem:";
	private static final String CODESYSTEMNAME = "CodeSystemName:";
	private static final String VALUESET = "ValueSet:";
	private static final String VALUESETCATEGORY = "ValueSetCategory:";
	private static final String CODENAME = "ConceptCodeName:";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeSystem() {
		return codeSystem;
	}

	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	public String getCodeSystemName() {
		return codeSystemName;
	}

	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}

	public String getValueSet() {
		return valueSet;
	}

	public void setValueSet(String valueSet) {
		this.valueSet = valueSet;
	}

	public String getValueSetCategory() {
		return valueSetCategory;
	}

	public void setValueSetCategory(String valueSetCategory) {
		this.valueSetCategory = valueSetCategory;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public void setVariable(String value) {
		String actualValue = extractActualValue(value);
		if (value.startsWith(CODE)) {
			setCode(actualValue);
		} else if (value.startsWith(CODENAME)) {
			setCodeName(actualValue);
		} else if (value.startsWith(CODESYSTEM)) {
			setCodeSystem(actualValue);
		} else if (value.startsWith(CODESYSTEMNAME)) {
			setCodeSystemName(actualValue);
		} else if (value.startsWith(VALUESET)) {
			setValueSet(actualValue);
		} else if (value.startsWith(VALUESETCATEGORY)) {
			setValueSetCategory(actualValue);
		} else {
			throw new IllegalArgumentException(
					"The type of data cannot be identified.");
		}
	}
	
	@Override
	public String toString(){
		return code+":"+codeSystem+":"+valueSet+":"+valueSetCategory;
	}

	private String extractActualValue(String value) {
		if (value.indexOf(":") + 1 == value.length()) {
			return "";
		}
		return value.substring(value.indexOf(":") + 1);
	}
}
