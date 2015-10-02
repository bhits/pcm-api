package gov.va.ds4p.policy.reference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"obligationPolicy"})
@XmlRootElement(name="ApplicableObligationPolicies")
public class ApplicableObligationPolicies
{

  @XmlElement(name="ObligationPolicy", required=true)
  protected List<ObligationPolicy> obligationPolicy;

  public List<ObligationPolicy> getObligationPolicy()
  {
    if (this.obligationPolicy == null) {
      this.obligationPolicy = new ArrayList();
    }
    return this.obligationPolicy;
  }
}