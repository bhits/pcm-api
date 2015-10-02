package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PIVL_PPD_TS", propOrder={"phase", "period"})
public class PIVLPPDTS extends SXCMPPDTS
{
  protected IVLPPDTS phase;
  protected PPDPQ period;

  @XmlAttribute(name="alignment")
  protected List<String> alignment;

  @XmlAttribute(name="institutionSpecified")
  protected Boolean institutionSpecified;

  public IVLPPDTS getPhase()
  {
    return this.phase;
  }

  public void setPhase(IVLPPDTS value)
  {
    this.phase = value;
  }

  public PPDPQ getPeriod()
  {
    return this.period;
  }

  public void setPeriod(PPDPQ value)
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

