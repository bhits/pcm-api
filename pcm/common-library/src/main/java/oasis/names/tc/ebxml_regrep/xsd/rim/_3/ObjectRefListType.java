package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ObjectRefListType", propOrder={"objectRef"})
public class ObjectRefListType
{

  @XmlElement(name="ObjectRef")
  protected List<ObjectRefType> objectRef;

  public List<ObjectRefType> getObjectRef()
  {
    if (this.objectRef == null) {
      this.objectRef = new ArrayList();
    }
    return this.objectRef;
  }
}

