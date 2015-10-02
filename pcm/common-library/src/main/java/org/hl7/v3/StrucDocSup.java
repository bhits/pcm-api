package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StrucDoc.Sup", propOrder={"content"})
public class StrucDocSup
{

  @XmlValue
  protected String content;

  public String getContent()
  {
    return this.content;
  }

  public void setContent(String value)
  {
    this.content = value;
  }
}

