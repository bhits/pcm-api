package org.oasis.names.tc.xspa.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"xacmlObligations"})
@XmlRootElement(name="xacmlObligationsType")
public class XacmlObligationsType
{
  protected List<String> xacmlObligations;

  public List<String> getXacmlObligations()
  {
    if (this.xacmlObligations == null) {
      this.xacmlObligations = new ArrayList();
    }
    return this.xacmlObligations;
  }
}

