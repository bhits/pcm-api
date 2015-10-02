package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="InternationalStringType", propOrder={"localizedString"})
public class InternationalStringType
{

  @XmlElement(name="LocalizedString")
  protected List<LocalizedStringType> localizedString;

  public List<LocalizedStringType> getLocalizedString()
  {
    if (this.localizedString == null) {
      this.localizedString = new ArrayList();
    }
    return this.localizedString;
  }
}

