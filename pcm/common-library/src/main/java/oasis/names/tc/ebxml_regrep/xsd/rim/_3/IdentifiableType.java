package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="IdentifiableType", propOrder={"slot"})
@XmlSeeAlso({ObjectRefType.class, RegistryObjectType.class})
public class IdentifiableType
{

  @XmlElement(name="Slot")
  protected List<SlotType1> slot;

  @XmlAttribute(name="id", required=true)
  @XmlSchemaType(name="anyURI")
  protected String id;

  @XmlAttribute(name="home")
  @XmlSchemaType(name="anyURI")
  protected String home;

  public List<SlotType1> getSlot()
  {
    if (this.slot == null) {
      this.slot = new ArrayList();
    }
    return this.slot;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String value)
  {
    this.id = value;
  }

  public String getHome()
  {
    return this.home;
  }

  public void setHome(String value)
  {
    this.home = value;
  }
}

