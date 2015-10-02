package oasis.names.tc.ebxml_regrep.xsd.rs._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"value"})
@XmlRootElement(name="RegistryError")
public class RegistryError
{

  @XmlValue
  protected String value;

  @XmlAttribute(name="codeContext", required=true)
  protected String codeContext;

  @XmlAttribute(name="errorCode", required=true)
  protected String errorCode;

  @XmlAttribute(name="severity")
  protected String severity;

  @XmlAttribute(name="location")
  protected String location;

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public String getCodeContext()
  {
    return this.codeContext;
  }

  public void setCodeContext(String value)
  {
    this.codeContext = value;
  }

  public String getErrorCode()
  {
    return this.errorCode;
  }

  public void setErrorCode(String value)
  {
    this.errorCode = value;
  }

  public String getSeverity()
  {
    if (this.severity == null) {
      return "urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Error";
    }
    return this.severity;
  }

  public void setSeverity(String value)
  {
    this.severity = value;
  }

  public String getLocation()
  {
    return this.location;
  }

  public void setLocation(String value)
  {
    this.location = value;
  }
}

