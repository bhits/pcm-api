 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="", propOrder={"streetAddressLine", "city", "state", "postalCode", "county", "country"})
 @XmlRootElement(name="addr")
 public class Addr
 {
 
   @XmlElement(required=true)
   protected String streetAddressLine;
 
   @XmlElement(required=true)
   protected String city;
 
   @XmlElement(required=true)
   protected String state;
 
   @XmlElement(required=true)
   protected String postalCode;
 
   @XmlElement(required=true)
   protected String county;
 
   @XmlElement(required=true)
   protected String country;
 
   public String getStreetAddressLine()
   {
     return this.streetAddressLine;
   }
 
   public void setStreetAddressLine(String value)
   {
     this.streetAddressLine = value;
   }
 
   public String getCity()
   {
     return this.city;
   }
 
   public void setCity(String value)
   {
     this.city = value;
   }
 
   public String getState()
   {
     return this.state;
   }
 
   public void setState(String value)
   {
     this.state = value;
   }
 
   public String getPostalCode()
   {
     return this.postalCode;
   }
 
   public void setPostalCode(String value)
   {
     this.postalCode = value;
   }
 
   public String getCounty()
   {
     return this.county;
   }
 
   public void setCounty(String value)
   {
     this.county = value;
   }
 
   public String getCountry()
   {
     return this.country;
   }
 
   public void setCountry(String value)
   {
     this.country = value;
   }
 }
