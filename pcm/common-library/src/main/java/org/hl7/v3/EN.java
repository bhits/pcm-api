package org.hl7.v3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="EN", propOrder={"content"})
@XmlSeeAlso({ON.class, PN.class, TN.class})
public class EN
{

  @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="validTime", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="given", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="delimiter", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="family", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="suffix", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="prefix", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class)})
  @XmlMixed
  protected List<Serializable> content;

  @XmlAttribute(name="use")
  protected List<String> use;

  public List<Serializable> getContent()
  {
    if (this.content == null) {
      this.content = new ArrayList();
    }
    return this.content;
  }

  public List<String> getUse()
  {
    if (this.use == null) {
      this.use = new ArrayList();
    }
    return this.use;
  }
}

