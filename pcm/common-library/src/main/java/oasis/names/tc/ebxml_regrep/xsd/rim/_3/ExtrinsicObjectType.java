package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ExtrinsicObjectType", propOrder={"contentVersionInfo"})
public class ExtrinsicObjectType extends RegistryObjectType
{

  @XmlElement(name="ContentVersionInfo")
  protected VersionInfoType contentVersionInfo;

  @XmlAttribute(name="mimeType")
  protected String mimeType;

  @XmlAttribute(name="isOpaque")
  protected Boolean isOpaque;

  public VersionInfoType getContentVersionInfo()
  {
    return this.contentVersionInfo;
  }

  public void setContentVersionInfo(VersionInfoType value)
  {
    this.contentVersionInfo = value;
  }

  public String getMimeType()
  {
    if (this.mimeType == null) {
      return "application/octet-stream";
    }
    return this.mimeType;
  }

  public void setMimeType(String value)
  {
    this.mimeType = value;
  }

  public boolean isIsOpaque()
  {
    if (this.isOpaque == null) {
      return false;
    }
    return this.isOpaque.booleanValue();
  }

  public void setIsOpaque(Boolean value)
  {
    this.isOpaque = value;
  }
}

