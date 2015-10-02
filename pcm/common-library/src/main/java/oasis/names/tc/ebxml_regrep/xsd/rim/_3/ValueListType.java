package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ValueListType", propOrder={"value"})
public class ValueListType
{

  @XmlElement(name="Value")
  protected List<String> value;

  public List<String> getValue()
  {
    if (this.value == null) {
      this.value = new ArrayList();
    }
    return this.value;
  }
}

