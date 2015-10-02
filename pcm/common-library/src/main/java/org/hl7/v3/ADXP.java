package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ADXP")
@XmlSeeAlso({AdxpDeliveryMode.class, AdxpDeliveryInstallationType.class, AdxpDelimiter.class, AdxpPrecinct.class, AdxpStreetAddressLine.class, AdxpUnitType.class, AdxpCountry.class, AdxpHouseNumberNumeric.class, AdxpUnitID.class, AdxpCareOf.class, AdxpDeliveryInstallationQualifier.class, AdxpCounty.class, AdxpHouseNumber.class, AdxpBuildingNumberSuffix.class, AdxpCensusTract.class, AdxpDeliveryAddressLine.class, AdxpDeliveryModeIdentifier.class, AdxpStreetName.class, AdxpStreetNameType.class, AdxpDirection.class, AdxpState.class, AdxpCity.class, AdxpPostalCode.class, AdxpStreetNameBase.class, AdxpAdditionalLocator.class, AdxpPostBox.class, AdxpDeliveryInstallationArea.class})
public class ADXP extends ST
{

  @XmlAttribute(name="partType")
  protected List<String> partType;

  public List<String> getPartType()
  {
    if (this.partType == null) {
      this.partType = new ArrayList();
    }
    return this.partType;
  }
}

