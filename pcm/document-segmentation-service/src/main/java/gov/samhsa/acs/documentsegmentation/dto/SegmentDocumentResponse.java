
package gov.samhsa.acs.documentsegmentation.dto;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentPayloadRawData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="segmentedDocumentXml" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tryPolicyDocumentXml" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postSegmentationMetadataXml" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="executionResponseContainerXml" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documentPayloadRawData",
    "segmentedDocumentXml",
    "tryPolicyDocumentXml",
    "postSegmentationMetadataXml",
    "executionResponseContainerXml"
})
@XmlRootElement(name = "SegmentDocumentResponse")
public class SegmentDocumentResponse {

    @XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler documentPayloadRawData;
    @XmlElement(required = true)
    protected String segmentedDocumentXml;
    @XmlElement(required = true)
    protected String tryPolicyDocumentXml;
    @XmlElement(required = true)
    protected String postSegmentationMetadataXml;
    @XmlElement(required = true)
    protected String executionResponseContainerXml;

    /**
     * Gets the value of the documentPayloadRawData property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getDocumentPayloadRawData() {
        return documentPayloadRawData;
    }

    /**
     * Sets the value of the documentPayloadRawData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setDocumentPayloadRawData(DataHandler value) {
        this.documentPayloadRawData = value;
    }

    /**
     * Gets the value of the segmentedDocumentXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentedDocumentXml() {
        return segmentedDocumentXml;
    }

    /**
     * Sets the value of the segmentedDocumentXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentedDocumentXml(String value) {
        this.segmentedDocumentXml = value;
    }

    /**
     * Gets the value of the tryPolicyDocumentXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTryPolicyDocumentXml() {
        return tryPolicyDocumentXml;
    }

    /**
     * Sets the value of the tryPolicyDocumentXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTryPolicyDocumentXml(String value) {
        this.tryPolicyDocumentXml = value;
    }

    /**
     * Gets the value of the postSegmentationMetadataXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostSegmentationMetadataXml() {
        return postSegmentationMetadataXml;
    }

    /**
     * Sets the value of the postSegmentationMetadataXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostSegmentationMetadataXml(String value) {
        this.postSegmentationMetadataXml = value;
    }

    /**
     * Gets the value of the executionResponseContainerXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecutionResponseContainerXml() {
        return executionResponseContainerXml;
    }

    /**
     * Sets the value of the executionResponseContainerXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecutionResponseContainerXml(String value) {
        this.executionResponseContainerXml = value;
    }

}
