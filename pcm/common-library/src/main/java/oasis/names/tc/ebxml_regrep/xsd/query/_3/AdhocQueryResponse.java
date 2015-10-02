package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"registryObjectList"})
@XmlRootElement(name="AdhocQueryResponse")
public class AdhocQueryResponse extends RegistryResponseType
{

  @XmlElement(name="RegistryObjectList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", required=true)
  protected RegistryObjectListType registryObjectList;

  @XmlAttribute(name="startIndex")
  protected BigInteger startIndex;

  @XmlAttribute(name="totalResultCount")
  protected BigInteger totalResultCount;

  public RegistryObjectListType getRegistryObjectList()
  {
    return this.registryObjectList;
  }

  public void setRegistryObjectList(RegistryObjectListType value)
  {
    this.registryObjectList = value;
  }

  public BigInteger getStartIndex()
  {
    if (this.startIndex == null) {
      return new BigInteger("0");
    }
    return this.startIndex;
  }

  public void setStartIndex(BigInteger value)
  {
    this.startIndex = value;
  }

  public BigInteger getTotalResultCount()
  {
    return this.totalResultCount;
  }

  public void setTotalResultCount(BigInteger value)
  {
    this.totalResultCount = value;
  }
}

