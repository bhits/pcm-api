package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FederationType")
public class FederationType extends RegistryObjectType
{

  @XmlAttribute(name="replicationSyncLatency")
  protected Duration replicationSyncLatency;

  public Duration getReplicationSyncLatency()
  {
    return this.replicationSyncLatency;
  }

  public void setReplicationSyncLatency(Duration value)
  {
    this.replicationSyncLatency = value;
  }
}

