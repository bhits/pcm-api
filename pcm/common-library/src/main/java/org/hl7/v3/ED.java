package org.hl7.v3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ED", propOrder={"reference", "thumbnail", "content"})
@XmlSeeAlso({Thumbnail.class, ST.class})
public class ED extends BIN
{
  protected TEL reference;
  protected Thumbnail thumbnail;

  @XmlAttribute(name="mediaType")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String mediaType;

  @XmlAttribute(name="language")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String language;

  @XmlAttribute(name="compression")
  protected CompressionAlgorithm compression;

  @XmlAttribute(name="integrityCheck")
  protected byte[] integrityCheck;

  @XmlAttribute(name="integrityCheckAlgorithm")
  protected IntegrityCheckAlgorithm integrityCheckAlgorithm;

  @XmlMixed
  protected List<Serializable> content;

  public TEL getReference()
  {
    return this.reference;
  }

  public void setReference(TEL value)
  {
    this.reference = value;
  }

  public Thumbnail getThumbnail()
  {
    return this.thumbnail;
  }

  public void setThumbnail(Thumbnail value)
  {
    this.thumbnail = value;
  }

  public String getMediaType()
  {
    if (this.mediaType == null) {
      return "text/plain";
    }
    return this.mediaType;
  }

  public void setMediaType(String value)
  {
    this.mediaType = value;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public void setLanguage(String value)
  {
    this.language = value;
  }

  public CompressionAlgorithm getCompression()
  {
    return this.compression;
  }

  public void setCompression(CompressionAlgorithm value)
  {
    this.compression = value;
  }

  public byte[] getIntegrityCheck()
  {
    return this.integrityCheck;
  }

  public void setIntegrityCheck(byte[] value)
  {
    this.integrityCheck = value;
  }

  public IntegrityCheckAlgorithm getIntegrityCheckAlgorithm()
  {
    if (this.integrityCheckAlgorithm == null) {
      return IntegrityCheckAlgorithm.SHA_1;
    }
    return this.integrityCheckAlgorithm;
  }

  public void setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm value)
  {
    this.integrityCheckAlgorithm = value;
  }

  public List<Serializable> getContent() {
    if (this.content == null) {
      this.content = new ArrayList();
    }
    return this.content;
  }
}

