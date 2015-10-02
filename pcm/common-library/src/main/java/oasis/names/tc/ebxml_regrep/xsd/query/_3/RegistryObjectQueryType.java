package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RegistryObjectQueryType", propOrder={"slotBranch", "nameBranch", "descriptionBranch", "versionInfoFilter", "classificationQuery", "externalIdentifierQuery", "objectTypeQuery", "statusQuery", "sourceAssociationQuery", "targetAssociationQuery"})
@XmlSeeAlso({ExternalLinkQueryType.class, SpecificationLinkQueryType.class, AuditableEventQueryType.class, ExtrinsicObjectQueryType.class, ServiceBindingQueryType.class, RegistryPackageQueryType.class, RegistryQueryType.class, AdhocQueryQueryType.class, ClassificationNodeQueryType.class, OrganizationQueryType.class, FederationQueryType.class, NotificationQueryType.class, ExternalIdentifierQueryType.class, AssociationQueryType.class, ClassificationQueryType.class, PersonQueryType.class, ClassificationSchemeQueryType.class, SubscriptionQueryType.class, ServiceQueryType.class})
public class RegistryObjectQueryType extends FilterQueryType
{

  @XmlElement(name="SlotBranch")
  protected List<SlotBranchType> slotBranch;

  @XmlElement(name="NameBranch")
  protected InternationalStringBranchType nameBranch;

  @XmlElement(name="DescriptionBranch")
  protected InternationalStringBranchType descriptionBranch;

  @XmlElement(name="VersionInfoFilter")
  protected FilterType versionInfoFilter;

  @XmlElement(name="ClassificationQuery")
  protected List<ClassificationQueryType> classificationQuery;

  @XmlElement(name="ExternalIdentifierQuery")
  protected List<ExternalIdentifierQueryType> externalIdentifierQuery;

  @XmlElement(name="ObjectTypeQuery")
  protected ClassificationNodeQueryType objectTypeQuery;

  @XmlElement(name="StatusQuery")
  protected ClassificationNodeQueryType statusQuery;

  @XmlElement(name="SourceAssociationQuery")
  protected List<AssociationQueryType> sourceAssociationQuery;

  @XmlElement(name="TargetAssociationQuery")
  protected List<AssociationQueryType> targetAssociationQuery;

  public List<SlotBranchType> getSlotBranch()
  {
    if (this.slotBranch == null) {
      this.slotBranch = new ArrayList();
    }
    return this.slotBranch;
  }

  public InternationalStringBranchType getNameBranch()
  {
    return this.nameBranch;
  }

  public void setNameBranch(InternationalStringBranchType value)
  {
    this.nameBranch = value;
  }

  public InternationalStringBranchType getDescriptionBranch()
  {
    return this.descriptionBranch;
  }

  public void setDescriptionBranch(InternationalStringBranchType value)
  {
    this.descriptionBranch = value;
  }

  public FilterType getVersionInfoFilter()
  {
    return this.versionInfoFilter;
  }

  public void setVersionInfoFilter(FilterType value)
  {
    this.versionInfoFilter = value;
  }

  public List<ClassificationQueryType> getClassificationQuery()
  {
    if (this.classificationQuery == null) {
      this.classificationQuery = new ArrayList();
    }
    return this.classificationQuery;
  }

  public List<ExternalIdentifierQueryType> getExternalIdentifierQuery()
  {
    if (this.externalIdentifierQuery == null) {
      this.externalIdentifierQuery = new ArrayList();
    }
    return this.externalIdentifierQuery;
  }

  public ClassificationNodeQueryType getObjectTypeQuery()
  {
    return this.objectTypeQuery;
  }

  public void setObjectTypeQuery(ClassificationNodeQueryType value)
  {
    this.objectTypeQuery = value;
  }

  public ClassificationNodeQueryType getStatusQuery()
  {
    return this.statusQuery;
  }

  public void setStatusQuery(ClassificationNodeQueryType value)
  {
    this.statusQuery = value;
  }

  public List<AssociationQueryType> getSourceAssociationQuery()
  {
    if (this.sourceAssociationQuery == null) {
      this.sourceAssociationQuery = new ArrayList();
    }
    return this.sourceAssociationQuery;
  }

  public List<AssociationQueryType> getTargetAssociationQuery()
  {
    if (this.targetAssociationQuery == null) {
      this.targetAssociationQuery = new ArrayList();
    }
    return this.targetAssociationQuery;
  }
}

