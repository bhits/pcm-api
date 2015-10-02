package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SpecificationLinkType", propOrder={"usageDescription", "usageParameter"})
public class SpecificationLinkType extends RegistryObjectType
{

  @XmlElement(name="UsageDescription")
  protected InternationalStringType usageDescription;

  @XmlElement(name="UsageParameter")
  protected List<String> usageParameter;

  @XmlAttribute(name="serviceBinding", required=true)
  protected String serviceBinding;

  @XmlAttribute(name="specificationObject", required=true)
  protected String specificationObject;

  public InternationalStringType getUsageDescription()
  {
    return this.usageDescription;
  }

  public void setUsageDescription(InternationalStringType value)
  {
    this.usageDescription = value;
  }

  public List<String> getUsageParameter()
  {
    if (this.usageParameter == null) {
      this.usageParameter = new ArrayList();
    }
    return this.usageParameter;
  }

  public String getServiceBinding()
  {
    return this.serviceBinding;
  }

  public void setServiceBinding(String value)
  {
    this.serviceBinding = value;
  }

  public String getSpecificationObject()
  {
    return this.specificationObject;
  }

  public void setSpecificationObject(String value)
  {
    this.specificationObject = value;
  }
}

