package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ENXP")
@XmlSeeAlso({EnDelimiter.class, EnPrefix.class, EnSuffix.class, EnGiven.class, EnFamily.class})
public class ENXP extends ST
{

  @XmlAttribute(name="partType")
  protected String partType;

  @XmlAttribute(name="qualifier")
  protected List<String> qualifier;

  public String getPartType()
  {
    return this.partType;
  }

  public void setPartType(String value)
  {
    this.partType = value;
  }

  public List<String> getQualifier()
  {
    if (this.qualifier == null) {
      this.qualifier = new ArrayList();
    }
    return this.qualifier;
  }
}

