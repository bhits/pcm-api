package oasis.names.tc.ebxml_regrep.xsd.lcm._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ObjectRefListType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryRequestType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"adhocQuery", "objectRefList"})
@XmlRootElement(name="UndeprecateObjectsRequest")
public class UndeprecateObjectsRequest extends RegistryRequestType
{

  @XmlElement(name="AdhocQuery", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
  protected AdhocQueryType adhocQuery;

  @XmlElement(name="ObjectRefList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")
  protected ObjectRefListType objectRefList;

  public AdhocQueryType getAdhocQuery()
  {
    return this.adhocQuery;
  }

  public void setAdhocQuery(AdhocQueryType value)
  {
    this.adhocQuery = value;
  }

  public ObjectRefListType getObjectRefList()
  {
    return this.objectRefList;
  }

  public void setObjectRefList(ObjectRefListType value)
  {
    this.objectRefList = value;
  }
}

