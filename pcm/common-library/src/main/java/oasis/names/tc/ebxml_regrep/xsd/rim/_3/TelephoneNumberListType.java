package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="TelephoneNumberListType", propOrder={"telephoneNumber"})
public class TelephoneNumberListType
{

  @XmlElement(name="TelephoneNumber")
  protected List<TelephoneNumberType> telephoneNumber;

  public List<TelephoneNumberType> getTelephoneNumber()
  {
    if (this.telephoneNumber == null) {
      this.telephoneNumber = new ArrayList();
    }
    return this.telephoneNumber;
  }
}

