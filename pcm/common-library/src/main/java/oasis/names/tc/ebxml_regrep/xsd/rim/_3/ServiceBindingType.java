package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceBindingType", propOrder={"specificationLink"})
public class ServiceBindingType extends RegistryObjectType
{

  @XmlElement(name="SpecificationLink")
  protected List<SpecificationLinkType> specificationLink;

  @XmlAttribute(name="service", required=true)
  protected String service;

  @XmlAttribute(name="accessURI")
  @XmlSchemaType(name="anyURI")
  protected String accessURI;

  @XmlAttribute(name="targetBinding")
  protected String targetBinding;

  public List<SpecificationLinkType> getSpecificationLink()
  {
    if (this.specificationLink == null) {
      this.specificationLink = new ArrayList();
    }
    return this.specificationLink;
  }

  public String getService()
  {
    return this.service;
  }

  public void setService(String value)
  {
    this.service = value;
  }

  public String getAccessURI()
  {
    return this.accessURI;
  }

  public void setAccessURI(String value)
  {
    this.accessURI = value;
  }

  public String getTargetBinding()
  {
    return this.targetBinding;
  }

  public void setTargetBinding(String value)
  {
    this.targetBinding = value;
  }
}

