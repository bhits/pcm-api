package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RegistryObjectType", propOrder={"name", "description", "versionInfo", "classification", "externalIdentifier"})
@XmlSeeAlso({ClassificationSchemeType.class, ServiceType.class, ClassificationNodeType.class, AssociationType1.class, ExtrinsicObjectType.class, OrganizationType.class, AdhocQueryType.class, RegistryType.class, ClassificationType.class, FederationType.class, ServiceBindingType.class, RegistryPackageType.class, NotificationType.class, SpecificationLinkType.class, ExternalLinkType.class, AuditableEventType.class, SubscriptionType.class, ExternalIdentifierType.class, PersonType.class})
public class RegistryObjectType extends IdentifiableType
{

  @XmlElement(name="Name")
  protected InternationalStringType name;

  @XmlElement(name="Description")
  protected InternationalStringType description;

  @XmlElement(name="VersionInfo")
  protected VersionInfoType versionInfo;

  @XmlElement(name="Classification")
  protected List<ClassificationType> classification;

  @XmlElement(name="ExternalIdentifier")
  protected List<ExternalIdentifierType> externalIdentifier;

  @XmlAttribute(name="lid")
  @XmlSchemaType(name="anyURI")
  protected String lid;

  @XmlAttribute(name="objectType")
  protected String objectType;

  @XmlAttribute(name="status")
  protected String status;

  public InternationalStringType getName()
  {
    return this.name;
  }

  public void setName(InternationalStringType value)
  {
    this.name = value;
  }

  public InternationalStringType getDescription()
  {
    return this.description;
  }

  public void setDescription(InternationalStringType value)
  {
    this.description = value;
  }

  public VersionInfoType getVersionInfo()
  {
    return this.versionInfo;
  }

  public void setVersionInfo(VersionInfoType value)
  {
    this.versionInfo = value;
  }

  public List<ClassificationType> getClassification()
  {
    if (this.classification == null) {
      this.classification = new ArrayList();
    }
    return this.classification;
  }

  public List<ExternalIdentifierType> getExternalIdentifier()
  {
    if (this.externalIdentifier == null) {
      this.externalIdentifier = new ArrayList();
    }
    return this.externalIdentifier;
  }

  public String getLid()
  {
    return this.lid;
  }

  public void setLid(String value)
  {
    this.lid = value;
  }

  public String getObjectType()
  {
    return this.objectType;
  }

  public void setObjectType(String value)
  {
    this.objectType = value;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String value)
  {
    this.status = value;
  }
}

