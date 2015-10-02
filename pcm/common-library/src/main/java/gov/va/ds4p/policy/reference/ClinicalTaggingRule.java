package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"clinicalFact", "actReason", "actInformationSensitivityPolicy", "impliedConfidentiality"})
@XmlRootElement(name="ClinicalTaggingRule")
public class ClinicalTaggingRule
{

  @XmlElement(name="ClinicalFact", required=true)
  protected ClinicalFact clinicalFact;

  @XmlElement(name="ActReason", required=true)
  protected ActReason actReason;

  @XmlElement(name="ActInformationSensitivityPolicy", required=true)
  protected ActInformationSensitivityPolicy actInformationSensitivityPolicy;

  @XmlElement(name="ImpliedConfidentiality", required=true)
  protected ImpliedConfidentiality impliedConfidentiality;

  @XmlAttribute(name="code", required=true)
  protected String code;

  @XmlAttribute(name="codeSystem", required=true)
  protected String codeSystem;

  @XmlAttribute(name="codeSystemName", required=true)
  protected String codeSystemName;

  @XmlAttribute(name="codeSystemVersion", required=true)
  protected String codeSystemVersion;

  @XmlAttribute(name="displayName", required=true)
  protected String displayName;

  public ClinicalFact getClinicalFact()
  {
    return this.clinicalFact;
  }

  public void setClinicalFact(ClinicalFact value)
  {
    this.clinicalFact = value;
  }

  public ActReason getActReason()
  {
    return this.actReason;
  }

  public void setActReason(ActReason value)
  {
    this.actReason = value;
  }

  public ActInformationSensitivityPolicy getActInformationSensitivityPolicy()
  {
    return this.actInformationSensitivityPolicy;
  }

  public void setActInformationSensitivityPolicy(ActInformationSensitivityPolicy value)
  {
    this.actInformationSensitivityPolicy = value;
  }

  public ImpliedConfidentiality getImpliedConfidentiality()
  {
    return this.impliedConfidentiality;
  }

  public void setImpliedConfidentiality(ImpliedConfidentiality value)
  {
    this.impliedConfidentiality = value;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public String getCodeSystem() {
		return this.codeSystem;
	}

	public void setCodeSystem(String value) {
		this.codeSystem = value;
	}

	public String getCodeSystemName() {
		return this.codeSystemName;
	}

	public void setCodeSystemName(String value) {
		this.codeSystemName = value;
	}

	public String getCodeSystemVersion() {
		return this.codeSystemVersion;
	}

	public void setCodeSystemVersion(String value) {
		this.codeSystemVersion = value;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String value) {
		this.displayName = value;
	}
 }
