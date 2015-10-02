package oasis.names.tc.ebxml_regrep.xsd.rs._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"registryError"})
@XmlRootElement(name="RegistryErrorList")
public class RegistryErrorList
{

  @XmlElement(name="RegistryError", required=true)
  protected List<RegistryError> registryError;

  @XmlAttribute(name="highestSeverity")
  protected String highestSeverity;

  public List<RegistryError> getRegistryError()
  {
    if (this.registryError == null) {
      this.registryError = new ArrayList();
    }
    return this.registryError;
  }

  public String getHighestSeverity()
  {
    return this.highestSeverity;
  }

  public void setHighestSeverity(String value)
  {
    this.highestSeverity = value;
  }
}

