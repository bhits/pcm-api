package oasis.names.tc.ebxml_regrep.xsd.lcm._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ObjectRefType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryRequestType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"adhocQuery", "sourceRegistry", "destinationRegistry", "ownerAtSource", "ownerAtDestination"})
@XmlRootElement(name="RelocateObjectsRequest")
public class RelocateObjectsRequest extends RegistryRequestType
{

  @XmlElement(name="AdhocQuery", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", required=true)
  protected AdhocQueryType adhocQuery;

  @XmlElement(name="SourceRegistry", required=true)
  protected ObjectRefType sourceRegistry;

  @XmlElement(name="DestinationRegistry", required=true)
  protected ObjectRefType destinationRegistry;

  @XmlElement(name="OwnerAtSource", required=true)
  protected ObjectRefType ownerAtSource;

  @XmlElement(name="OwnerAtDestination", required=true)
  protected ObjectRefType ownerAtDestination;

  public AdhocQueryType getAdhocQuery()
  {
    return this.adhocQuery;
  }

  public void setAdhocQuery(AdhocQueryType value)
  {
    this.adhocQuery = value;
  }

  public ObjectRefType getSourceRegistry()
  {
    return this.sourceRegistry;
  }

  public void setSourceRegistry(ObjectRefType value)
  {
    this.sourceRegistry = value;
  }

  public ObjectRefType getDestinationRegistry()
  {
    return this.destinationRegistry;
  }

  public void setDestinationRegistry(ObjectRefType value)
  {
    this.destinationRegistry = value;
  }

  public ObjectRefType getOwnerAtSource()
  {
    return this.ownerAtSource;
  }

  public void setOwnerAtSource(ObjectRefType value)
  {
    this.ownerAtSource = value;
  }

  public ObjectRefType getOwnerAtDestination()
  {
    return this.ownerAtDestination;
  }

  public void setOwnerAtDestination(ObjectRefType value)
  {
    this.ownerAtDestination = value;
  }
}

