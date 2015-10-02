package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.ManufacturedProduct", propOrder={"realmCode", "typeId", "templateId", "id", "manufacturedLabeledDrug", "manufacturedMaterial", "manufacturerOrganization"})
public class POCDMT000040ManufacturedProduct
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected List<II> id;
  protected POCDMT000040LabeledDrug manufacturedLabeledDrug;
  protected POCDMT000040Material manufacturedMaterial;
  protected POCDMT000040Organization manufacturerOrganization;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected RoleClassManufacturedProduct classCode;

  public List<CS> getRealmCode()
  {
    if (this.realmCode == null) {
      this.realmCode = new ArrayList();
    }
    return this.realmCode;
  }

  public POCDMT000040InfrastructureRootTypeId getTypeId()
  {
    return this.typeId;
  }

  public void setTypeId(POCDMT000040InfrastructureRootTypeId value)
  {
    this.typeId = value;
  }

  public List<II> getTemplateId()
  {
    if (this.templateId == null) {
      this.templateId = new ArrayList();
    }
    return this.templateId;
  }

  public List<II> getId()
  {
    if (this.id == null) {
      this.id = new ArrayList();
    }
    return this.id;
  }

  public POCDMT000040LabeledDrug getManufacturedLabeledDrug()
  {
    return this.manufacturedLabeledDrug;
  }

  public void setManufacturedLabeledDrug(POCDMT000040LabeledDrug value)
  {
    this.manufacturedLabeledDrug = value;
  }

  public POCDMT000040Material getManufacturedMaterial()
  {
    return this.manufacturedMaterial;
  }

  public void setManufacturedMaterial(POCDMT000040Material value)
  {
    this.manufacturedMaterial = value;
  }

  public POCDMT000040Organization getManufacturerOrganization()
  {
    return this.manufacturerOrganization;
  }

  public void setManufacturerOrganization(POCDMT000040Organization value)
  {
    this.manufacturerOrganization = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public RoleClassManufacturedProduct getClassCode()
  {
    if (this.classCode == null) {
      return RoleClassManufacturedProduct.MANU;
    }
    return this.classCode;
  }

  public void setClassCode(RoleClassManufacturedProduct value)
  {
    this.classCode = value;
  }
}

