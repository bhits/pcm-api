package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="II")
@XmlSeeAlso({POCDMT000040InfrastructureRootTypeId.class})
public class II extends ANY
{

  @XmlAttribute(name="root")
  protected String root;

  @XmlAttribute(name="extension")
  protected String extension;

  @XmlAttribute(name="assigningAuthorityName")
  protected String assigningAuthorityName;

  @XmlAttribute(name="displayable")
  protected Boolean displayable;

  public String getRoot()
  {
    return this.root;
  }

  public void setRoot(String value)
  {
    this.root = value;
  }

  public String getExtension()
  {
    return this.extension;
  }

  public void setExtension(String value)
  {
    this.extension = value;
  }

  public String getAssigningAuthorityName()
  {
    return this.assigningAuthorityName;
  }

  public void setAssigningAuthorityName(String value)
  {
    this.assigningAuthorityName = value;
  }

  public Boolean isDisplayable()
  {
    return this.displayable;
  }

  public void setDisplayable(Boolean value)
  {
    this.displayable = value;
  }
}

