package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Patient", propOrder={"realmCode", "typeId", "templateId", "id", "name", "administrativeGenderCode", "birthTime", "maritalStatusCode", "religiousAffiliationCode", "raceCode", "ethnicGroupCode", "guardian", "birthplace", "languageCommunication"})
public class POCDMT000040Patient
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected II id;
  protected List<PN> name;
  protected CE administrativeGenderCode;
  protected TS birthTime;
  protected CE maritalStatusCode;
  protected CE religiousAffiliationCode;
  protected CE raceCode;
  protected CE ethnicGroupCode;
  protected List<POCDMT000040Guardian> guardian;
  protected POCDMT000040Birthplace birthplace;
  protected List<POCDMT000040LanguageCommunication> languageCommunication;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected List<String> classCode;

  @XmlAttribute(name="determinerCode")
  protected String determinerCode;

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

  public II getId()
  {
    return this.id;
  }

  public void setId(II value)
  {
    this.id = value;
  }

  public List<PN> getName()
  {
    if (this.name == null) {
      this.name = new ArrayList();
    }
    return this.name;
  }

  public CE getAdministrativeGenderCode()
  {
    return this.administrativeGenderCode;
  }

  public void setAdministrativeGenderCode(CE value)
  {
    this.administrativeGenderCode = value;
  }

  public TS getBirthTime()
  {
    return this.birthTime;
  }

  public void setBirthTime(TS value)
  {
    this.birthTime = value;
  }

  public CE getMaritalStatusCode()
  {
    return this.maritalStatusCode;
  }

  public void setMaritalStatusCode(CE value)
  {
    this.maritalStatusCode = value;
  }

  public CE getReligiousAffiliationCode()
  {
    return this.religiousAffiliationCode;
  }

  public void setReligiousAffiliationCode(CE value)
  {
    this.religiousAffiliationCode = value;
  }

  public CE getRaceCode()
  {
    return this.raceCode;
  }

  public void setRaceCode(CE value)
  {
    this.raceCode = value;
  }

  public CE getEthnicGroupCode()
  {
    return this.ethnicGroupCode;
  }

  public void setEthnicGroupCode(CE value)
  {
    this.ethnicGroupCode = value;
  }

  public List<POCDMT000040Guardian> getGuardian()
  {
    if (this.guardian == null) {
      this.guardian = new ArrayList();
    }
    return this.guardian;
  }

  public POCDMT000040Birthplace getBirthplace()
  {
    return this.birthplace;
  }

  public void setBirthplace(POCDMT000040Birthplace value)
  {
    this.birthplace = value;
  }

  public List<POCDMT000040LanguageCommunication> getLanguageCommunication()
  {
    if (this.languageCommunication == null) {
      this.languageCommunication = new ArrayList();
    }
    return this.languageCommunication;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public List<String> getClassCode()
  {
    if (this.classCode == null) {
      this.classCode = new ArrayList();
    }
    return this.classCode;
  }

  public String getDeterminerCode()
  {
    if (this.determinerCode == null) {
      return "INSTANCE";
    }
    return this.determinerCode;
  }

  public void setDeterminerCode(String value)
  {
    this.determinerCode = value;
  }
}

