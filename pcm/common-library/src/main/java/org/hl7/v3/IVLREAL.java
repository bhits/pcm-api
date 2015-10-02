package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="IVL_REAL", propOrder={"rest"})
public class IVLREAL extends SXCMREAL
{

  @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="low", namespace="urn:hl7-org:v3", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="center", namespace="urn:hl7-org:v3", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="width", namespace="urn:hl7-org:v3", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="high", namespace="urn:hl7-org:v3", type=JAXBElement.class)})
  protected List<JAXBElement<? extends REAL>> rest;

  public List<JAXBElement<? extends REAL>> getRest()
  {
    if (this.rest == null) {
      this.rest = new ArrayList();
    }
    return this.rest;
  }
}

