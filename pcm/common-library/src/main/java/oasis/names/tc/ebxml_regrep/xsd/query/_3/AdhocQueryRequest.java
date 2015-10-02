package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryRequestType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"responseOption", "adhocQuery"})
@XmlRootElement(name="AdhocQueryRequest")
public class AdhocQueryRequest extends RegistryRequestType
{

  @XmlElement(name="ResponseOption", required=true)
  protected ResponseOptionType responseOption;

  @XmlElement(name="AdhocQuery", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", required=true)
  protected AdhocQueryType adhocQuery;

  @XmlAttribute(name="federated")
  protected Boolean federated;

  @XmlAttribute(name="federation")
  @XmlSchemaType(name="anyURI")
  protected String federation;

  @XmlAttribute(name="startIndex")
  protected BigInteger startIndex;

  @XmlAttribute(name="maxResults")
  protected BigInteger maxResults;

  public ResponseOptionType getResponseOption()
  {
    return this.responseOption;
  }

  public void setResponseOption(ResponseOptionType value)
  {
    this.responseOption = value;
  }

  public AdhocQueryType getAdhocQuery()
  {
    return this.adhocQuery;
  }

  public void setAdhocQuery(AdhocQueryType value)
  {
    this.adhocQuery = value;
  }

  public boolean isFederated()
  {
    if (this.federated == null) {
      return false;
    }
    return this.federated.booleanValue();
  }

  public void setFederated(Boolean value)
  {
    this.federated = value;
  }

  public String getFederation()
  {
    return this.federation;
  }

  public void setFederation(String value)
  {
    this.federation = value;
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

  public BigInteger getMaxResults()
  {
    if (this.maxResults == null) {
      return new BigInteger("-1");
    }
    return this.maxResults;
  }

  public void setMaxResults(BigInteger value)
  {
    this.maxResults = value;
  }
}

