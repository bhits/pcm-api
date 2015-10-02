package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="LocalizedStringType")
public class LocalizedStringType
{

  @XmlAttribute(name="lang", namespace="http://www.w3.org/XML/1998/namespace")
  protected String lang;

  @XmlAttribute(name="charset")
  @XmlSchemaType(name="anySimpleType")
  protected String charset;

  @XmlAttribute(name="value", required=true)
  protected String value;

  public String getLang()
  {
    if (this.lang == null) {
      return "en-US";
    }
    return this.lang;
  }

  public void setLang(String value)
  {
    this.lang = value;
  }

  public String getCharset()
  {
    if (this.charset == null) {
      return "UTF-8";
    }
    return this.charset;
  }

  public void setCharset(String value)
  {
    this.charset = value;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }
}

