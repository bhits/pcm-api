package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ResponseOptionType")
public class ResponseOptionType
{

  @XmlAttribute(name="returnType")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String returnType;

  @XmlAttribute(name="returnComposedObjects")
  protected Boolean returnComposedObjects;

  public String getReturnType()
  {
    if (this.returnType == null) {
      return "RegistryObject";
    }
    return this.returnType;
  }

  public void setReturnType(String value)
  {
    this.returnType = value;
  }

  public boolean isReturnComposedObjects()
  {
    if (this.returnComposedObjects == null) {
      return false;
    }
    return this.returnComposedObjects.booleanValue();
  }

  public void setReturnComposedObjects(Boolean value)
  {
    this.returnComposedObjects = value;
  }
}

