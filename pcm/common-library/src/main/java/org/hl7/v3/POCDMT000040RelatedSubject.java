package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.RelatedSubject", propOrder={"realmCode", "typeId", "templateId", "code", "addr", "telecom", "subject"})
public class POCDMT000040RelatedSubject
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected CE code;
  protected List<AD> addr;
  protected List<TEL> telecom;
  protected POCDMT000040SubjectPerson subject;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected XDocumentSubject classCode;

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

  public CE getCode()
  {
    return this.code;
  }

  public void setCode(CE value)
  {
    this.code = value;
  }

  public List<AD> getAddr()
  {
    if (this.addr == null) {
      this.addr = new ArrayList();
    }
    return this.addr;
  }

  public List<TEL> getTelecom()
  {
    if (this.telecom == null) {
      this.telecom = new ArrayList();
    }
    return this.telecom;
  }

  public POCDMT000040SubjectPerson getSubject()
  {
    return this.subject;
  }

  public void setSubject(POCDMT000040SubjectPerson value)
  {
    this.subject = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public XDocumentSubject getClassCode()
  {
    if (this.classCode == null) {
      return XDocumentSubject.PRS;
    }
    return this.classCode;
  }

  public void setClassCode(XDocumentSubject value)
  {
    this.classCode = value;
  }
}

