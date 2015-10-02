package oasis.names.tc.ebxml_regrep.xsd.rs._3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _RegistryResponse_QNAME = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0", "RegistryResponse");
  private static final QName _RegistryRequest_QNAME = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0", "RegistryRequest");

  public RegistryErrorList createRegistryErrorList()
  {
    return new RegistryErrorList();
  }

  public RegistryError createRegistryError()
  {
    return new RegistryError();
  }

  public RegistryResponseType createRegistryResponseType()
  {
    return new RegistryResponseType();
  }

  public RegistryRequestType createRegistryRequestType()
  {
    return new RegistryRequestType();
  }

  @XmlElementDecl(namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0", name="RegistryResponse")
  public JAXBElement<RegistryResponseType> createRegistryResponse(RegistryResponseType value)
  {
    return new JAXBElement(_RegistryResponse_QNAME, RegistryResponseType.class, null, value);
  }

  @XmlElementDecl(namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0", name="RegistryRequest")
  public JAXBElement<RegistryRequestType> createRegistryRequest(RegistryRequestType value)
  {
    return new JAXBElement(_RegistryRequest_QNAME, RegistryRequestType.class, null, value);
  }
}

