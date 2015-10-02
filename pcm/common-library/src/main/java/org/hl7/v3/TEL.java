package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="TEL", propOrder={"useablePeriod"})
public class TEL extends URL
{
  protected List<SXCMTS> useablePeriod;

  @XmlAttribute(name="use")
  protected List<String> use;

  public List<SXCMTS> getUseablePeriod()
  {
    if (this.useablePeriod == null) {
      this.useablePeriod = new ArrayList();
    }
    return this.useablePeriod;
  }

  public List<String> getUse()
  {
    if (this.use == null) {
      this.use = new ArrayList();
    }
    return this.use;
  }
}

