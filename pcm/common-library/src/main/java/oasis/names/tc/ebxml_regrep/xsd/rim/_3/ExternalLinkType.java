package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ExternalLinkType")
public class ExternalLinkType extends RegistryObjectType
{

  @XmlAttribute(name="externalURI", required=true)
  @XmlSchemaType(name="anyURI")
  protected String externalURI;

  public String getExternalURI()
  {
    return this.externalURI;
  }

  public void setExternalURI(String value)
  {
    this.externalURI = value;
  }
}

