package gov.va.ds4p.policy.reference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"actReason"})
@XmlRootElement(name="PurposeOfUse")
public class PurposeOfUse
{

  @XmlElement(name="ActReason", required=true)
  protected List<ActReason> actReason;

  public List<ActReason> getActReason()
  {
    if (this.actReason == null) {
      this.actReason = new ArrayList();
    }
    return this.actReason;
  }
}
