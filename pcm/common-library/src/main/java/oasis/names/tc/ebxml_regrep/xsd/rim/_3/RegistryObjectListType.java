package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RegistryObjectListType", propOrder={"identifiable"})
public class RegistryObjectListType
{

  @XmlElementRef(name="Identifiable", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", type=JAXBElement.class)
  protected List<JAXBElement<? extends IdentifiableType>> identifiable;

  public List<JAXBElement<? extends IdentifiableType>> getIdentifiable()
  {
    if (this.identifiable == null) {
      this.identifiable = new ArrayList();
    }
    return this.identifiable;
  }
}

