package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StrucDoc.RenderMultiMedia", propOrder={"caption"})
public class StrucDocRenderMultiMedia
{
  protected StrucDocCaption caption;

  @XmlAttribute(name="referencedObject", required=true)
  @XmlIDREF
  @XmlSchemaType(name="IDREFS")
  protected List<Object> referencedObject;

  @XmlAttribute(name="ID")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name="ID")
  protected String id;

  @XmlAttribute(name="language")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name="NMTOKEN")
  protected String language;

  @XmlAttribute(name="styleCode")
  @XmlSchemaType(name="NMTOKENS")
  protected List<String> styleCode;

  public StrucDocCaption getCaption()
  {
    return this.caption;
  }

  public void setCaption(StrucDocCaption value)
  {
    this.caption = value;
  }

  public List<Object> getReferencedObject()
  {
    if (this.referencedObject == null) {
      this.referencedObject = new ArrayList();
    }
    return this.referencedObject;
  }

  public String getID()
  {
    return this.id;
  }

  public void setID(String value)
  {
    this.id = value;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public void setLanguage(String value)
  {
    this.language = value;
  }

  public List<String> getStyleCode()
  {
    if (this.styleCode == null) {
      this.styleCode = new ArrayList();
    }
    return this.styleCode;
  }
}

