package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SXPR_TS", propOrder={"comp"})
public class SXPRTS extends SXCMTS
{

  @XmlElement(required=true)
  protected List<SXCMTS> comp;

  public List<SXCMTS> getComp()
  {
    if (this.comp == null) {
      this.comp = new ArrayList();
    }
    return this.comp;
  }
}

