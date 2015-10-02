package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PPD_PQ", propOrder={"standardDeviation"})
@XmlSeeAlso({IVXBPPDPQ.class, SXCMPPDPQ.class})
public class PPDPQ extends PQ
{
  protected PQ standardDeviation;

  @XmlAttribute(name="distributionType")
  protected ProbabilityDistributionType distributionType;

  public PQ getStandardDeviation()
  {
    return this.standardDeviation;
  }

  public void setStandardDeviation(PQ value)
  {
    this.standardDeviation = value;
  }

  public ProbabilityDistributionType getDistributionType()
  {
    return this.distributionType;
  }

  public void setDistributionType(ProbabilityDistributionType value)
  {
    this.distributionType = value;
  }
}

