package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="InternationalStringBranchType", propOrder={"localizedStringFilter"})
public class InternationalStringBranchType extends BranchType
{

  @XmlElement(name="LocalizedStringFilter")
  protected List<FilterType> localizedStringFilter;

  public List<FilterType> getLocalizedStringFilter()
  {
    if (this.localizedStringFilter == null) {
      this.localizedStringFilter = new ArrayList();
    }
    return this.localizedStringFilter;
  }
}

