package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ObjectRefType")
public class ObjectRefType extends IdentifiableType
{

  @XmlAttribute(name="createReplica")
  protected Boolean createReplica;

  public boolean isCreateReplica()
  {
    if (this.createReplica == null) {
      return false;
    }
    return this.createReplica.booleanValue();
  }

  public void setCreateReplica(Boolean value)
  {
    this.createReplica = value;
  }
}

