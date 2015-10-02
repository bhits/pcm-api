package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PIVL_TS", propOrder={"phase", "period"})
public class PIVLTS extends SXCMTS
{
  protected IVLTS phase;
  protected PQ period;

  @XmlAttribute(name="alignment")
  protected List<String> alignment;

  @XmlAttribute(name="institutionSpecified")
  protected Boolean institutionSpecified;

  public IVLTS getPhase()
  {
    return this.phase;
  }

  public void setPhase(IVLTS value)
  {
    this.phase = value;
  }

  public PQ getPeriod()
  {
    return this.period;
  }

  public void setPeriod(PQ value)
  {
    this.period = value;
  }

  public List<String> getAlignment()
  {
    if (this.alignment == null) {
      this.alignment = new ArrayList();
    }
    return this.alignment;
  }

  public boolean isInstitutionSpecified()
  {
    if (this.institutionSpecified == null) {
      return false;
    }
    return this.institutionSpecified.booleanValue();
  }

  public void setInstitutionSpecified(Boolean value)
  {
    this.institutionSpecified = value;
  }
}

