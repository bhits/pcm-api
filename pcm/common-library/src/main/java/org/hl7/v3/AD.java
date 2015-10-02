package org.hl7.v3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AD", propOrder={"content"})
public class AD
{

  @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="unitID", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="county", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="unitType", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="streetNameType", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="postBox", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="streetName", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="country", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="deliveryInstallationType", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="postalCode", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="houseNumberNumeric", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="useablePeriod", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="houseNumber", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="buildingNumberSuffix", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="deliveryAddressLine", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="additionalLocator", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="precinct", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="city", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="streetAddressLine", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="direction", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="delimiter", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="deliveryMode", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="deliveryInstallationArea", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="deliveryInstallationQualifier", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="state", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="careOf", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="censusTract", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="streetNameBase", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="deliveryModeIdentifier", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class)})
  @XmlMixed
  protected List<Serializable> content;

  @XmlAttribute(name="use")
  protected List<String> use;

  @XmlAttribute(name="isNotOrdered")
  protected Boolean isNotOrdered;

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

  public Boolean isIsNotOrdered()
  {
    return this.isNotOrdered;
  }

  public void setIsNotOrdered(Boolean value)
  {
    this.isNotOrdered = value;
  }
}

