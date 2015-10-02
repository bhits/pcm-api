package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RegistryType")
public class RegistryType extends RegistryObjectType
{

  @XmlAttribute(name="operator", required=true)
  protected String operator;

  @XmlAttribute(name="specificationVersion", required=true)
  protected String specificationVersion;

  @XmlAttribute(name="replicationSyncLatency")
  protected Duration replicationSyncLatency;

  @XmlAttribute(name="catalogingLatency")
  protected Duration catalogingLatency;

  @XmlAttribute(name="conformanceProfile")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String conformanceProfile;

  public String getOperator()
  {
    return this.operator;
  }

  public void setOperator(String value)
  {
    this.operator = value;
  }

  public String getSpecificationVersion()
  {
    return this.specificationVersion;
  }

  public void setSpecificationVersion(String value)
  {
    this.specificationVersion = value;
  }

  public Duration getReplicationSyncLatency()
  {
    return this.replicationSyncLatency;
  }

  public void setReplicationSyncLatency(Duration value)
  {
    this.replicationSyncLatency = value;
  }

  public Duration getCatalogingLatency()
  {
    return this.catalogingLatency;
  }

  public void setCatalogingLatency(Duration value)
  {
    this.catalogingLatency = value;
  }

  public String getConformanceProfile()
  {
    if (this.conformanceProfile == null) {
      return "registryLite";
    }
    return this.conformanceProfile;
  }

  public void setConformanceProfile(String value)
  {
    this.conformanceProfile = value;
  }
}

