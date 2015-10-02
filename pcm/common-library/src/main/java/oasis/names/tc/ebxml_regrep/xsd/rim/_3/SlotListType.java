package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SlotListType", propOrder={"slot"})
public class SlotListType
{

  @XmlElement(name="Slot")
  protected List<SlotType1> slot;

  public List<SlotType1> getSlot()
  {
    if (this.slot == null) {
      this.slot = new ArrayList();
    }
    return this.slot;
  }
}

