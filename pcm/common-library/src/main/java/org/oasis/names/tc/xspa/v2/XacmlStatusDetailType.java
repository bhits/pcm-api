package org.oasis.names.tc.xspa.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"xacmlStatusDetail"})
@XmlRootElement(name="xacmlStatusDetailType")
public class XacmlStatusDetailType
{
  protected List<String> xacmlStatusDetail;

  public List<String> getXacmlStatusDetail()
  {
    if (this.xacmlStatusDetail == null) {
      this.xacmlStatusDetail = new ArrayList();
    }
    return this.xacmlStatusDetail;
  }
}

