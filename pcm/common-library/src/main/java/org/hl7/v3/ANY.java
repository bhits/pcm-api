package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ANY")
@XmlSeeAlso({ANYNonNull.class, BL.class, SLISTPQ.class, CR.class, II.class, SLISTTS.class, GLISTTS.class, URL.class, QTY.class, CD.class, GLISTPQ.class, BIN.class})
public abstract class ANY
{

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }
}

