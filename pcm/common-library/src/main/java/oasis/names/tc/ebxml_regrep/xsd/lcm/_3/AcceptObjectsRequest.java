package oasis.names.tc.ebxml_regrep.xsd.lcm._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryRequestType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="AcceptObjectsRequest")
public class AcceptObjectsRequest extends RegistryRequestType
{

  @XmlAttribute(name="correlationId", required=true)
  @XmlSchemaType(name="anyURI")
  protected String correlationId;

  public String getCorrelationId()
  {
    return this.correlationId;
  }

  public void setCorrelationId(String value)
  {
    this.correlationId = value;
  }
}

