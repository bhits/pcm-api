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

import gov.samhsa.acs.common.tool.XmlTransformer;

import java.io.InputStream;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Hl7v3TransformerImpl.
 */
public class Hl7v3TransformerImpl implements Hl7v3Transformer {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The xml transformer. */
	private final XmlTransformer xmlTransformer;

	/**
	 * Instantiates a new hl7v3 transformer impl.
	 *
	 * @param xmlTransformer
	 *            the xml transformer
	 */
	public Hl7v3TransformerImpl(XmlTransformer xmlTransformer) {
		super();
		this.xmlTransformer = xmlTransformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.hl7.Hl7v3Transformer#getPixQueryXml(java.lang
	 * .String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getPixQueryXml(String mrn, String mrnDomain, String xsltUri)
			throws Hl7v3TransformerException {
		String newxsltUri = "";
		try {
			final String extension = "@extension";
			final String root = "@root";
			String queryXML = xsltUri;
			if (null != xsltUri && !xsltUri.startsWith("<?xml")) {
				// read the xsl file from resources folder
				final InputStream styleIs = Thread.currentThread()
						.getContextClassLoader().getResourceAsStream(xsltUri);
				queryXML = IOUtils.toString(styleIs, "UTF-8");
			}
			newxsltUri = queryXML.replaceAll(extension, mrn);
			newxsltUri = newxsltUri.replaceAll(root, mrnDomain);
		} catch (final Exception e) {
			final String errorMessage = "Error happended when trying to mrn data to hl7v3PixQuery";
			logger.error(errorMessage, e);
			final Hl7v3TransformerException transformerException = new Hl7v3TransformerException(
					errorMessage, e);
			throw transformerException;
		}
		return newxsltUri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.hl7.Hl7v3Transformer#transformToHl7v3PixXml(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public String transformToHl7v3PixXml(String xml, String XSLTURI)
			throws Hl7v3TransformerException {
		try {
			final String xslUrl = Thread.currentThread()
					.getContextClassLoader().getResource(XSLTURI).toString();
			final String hl7v3PixXML = xmlTransformer.transform(xml, xslUrl,
					Optional.empty(), Optional.empty());
			logger.debug("hl7v3PixXML:");
			logger.debug(hl7v3PixXML);
			return hl7v3PixXML;
		} catch (final Exception e) {
			final String errorMessage = "Error happended when trying to transform xml to PIX xml";
			logger.error(errorMessage, e);
			final Hl7v3TransformerException transformerException = new Hl7v3TransformerException(
					errorMessage, e);
			throw transformerException;
		}
	}
}
