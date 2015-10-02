package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="VersionInfoType")
public class VersionInfoType
{

  @XmlAttribute(name="versionName")
  protected String versionName;

  @XmlAttribute(name="comment")
  protected String comment;

  public String getVersionName()
  {
    if (this.versionName == null) {
      return "1.1";
    }
    return this.versionName;
  }

  public void setVersionName(String value)
  {
    this.versionName = value;
  }

  public String getComment()
  {
    return this.comment;
  }

  public void setComment(String value)
  {
    this.comment = value;
  }
}

