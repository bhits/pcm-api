/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.hl7;

/**
 * The Interface Hl7v3Transformer.
 */
public interface Hl7v3Transformer {

	/** The Constant XSLT_PATIENT_DTO_TO_PIX_ADD. */
	public final static String XSLT_PATIENT_DTO_TO_PIX_ADD = "patientDtoHl7v3PixAdd.xsl";

	/** The Constant XSLT_PATIENT_DTO_TO_PIX_UPDATE. */
	public final static String XSLT_PATIENT_DTO_TO_PIX_UPDATE = "patientDtoHl7v3PixUpdate.xsl";

	/** The Constant XSLT_C32_TO_PIX_ADD. */
	public final static String XSLT_C32_TO_PIX_ADD = "c32ToHl7v3PixAdd.xsl";

	/** The Constant XSLT_C32_TO_PIX_UPDATE. */
	public final static String XSLT_C32_TO_PIX_UPDATE = "c32ToHl7v3PixUpdate.xsl";

	/** The Constant XSLT_C32_TO_PIX_QUERY. */
	public final static String XSLT_C32_TO_PIX_QUERY = "c32ToHl7v3PixQuery.xsl";

	/** The Constant XML_PIX_QUERY. */
	public final static String XML_PIX_QUERY = "Hl7v3PixQuery.xml";

	/**
	 * Transform to hl7v3 pix xml.
	 *
	 * @param xml
	 *            the xml
	 * @param XSLTURI
	 *            the xslturi
	 * @return the string
	 * @throws Hl7v3TransformerException
	 *             the hl7v3 transformer exception
	 */
	public String transformToHl7v3PixXml(String xml, String XSLTURI)
			throws Hl7v3TransformerException;

	/**
	 * Transform c32 to green ccd.
	 *
	 * @param mrn
	 *            the medical record no of patient
	 * @param mrnDomain
	 *            the eHRdomain id
	 * @param xsltUri
	 *            the xsl for pixquery
	 * @return the string
	 * @throws Hl7v3TransformerException
	 *             the hl7v3 transformer exception
	 */
	public String getPixQueryXml(String mrn, String mrnDomain, String xsltUri)
			throws Hl7v3TransformerException;
}
