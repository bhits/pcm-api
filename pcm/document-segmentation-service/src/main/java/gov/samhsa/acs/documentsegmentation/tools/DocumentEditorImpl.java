/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.documentsegmentation.tools;

import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.xdm.XdmZipUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.xpath.XPathExpressionException;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Class DocumentEditorImpl.
 */
public class DocumentEditorImpl implements DocumentEditor {

	/** The metadata generator. */
	private final MetadataGenerator metadataGenerator;

	/** The file reader. */
	private final FileReader fileReader;

	/** The document xml converter. */
	private final DocumentXmlConverter documentXmlConverter;

	/** The document accessor. */
	private final DocumentAccessor documentAccessor;

	/**
	 * Instantiates a new document editor impl.
	 *
	 * @param metadataGenerator
	 *            the metadata generator
	 * @param fileReader
	 *            the file reader
	 * @param documentXmlConverter
	 *            the document xml converter
	 * @param documentAccessor
	 *            the document accessor
	 */
	public DocumentEditorImpl(MetadataGenerator metadataGenerator,
			FileReader fileReader, DocumentXmlConverter documentXmlConverter,
			DocumentAccessor documentAccessor) {
		super();
		this.metadataGenerator = metadataGenerator;
		this.fileReader = fileReader;
		this.documentXmlConverter = documentXmlConverter;
		this.documentAccessor = documentAccessor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.util
	 * .DocumentEditor#setDocumentCreationDate(java.lang.String)
	 */
	@Override
	public String setDocumentCreationDate(String document) throws Exception,
	XPathExpressionException, XMLEncryptionException {
		Document xmlDocument;
		xmlDocument = documentXmlConverter.loadDocument(document);

		// current date
		final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		final Date date = new Date();
		final String xPathExprEffectiveDate = "//hl7:effectiveTime";

		final Element dateElement = documentAccessor.getElement(xmlDocument,
				xPathExprEffectiveDate).get();
		dateElement.setAttribute("value", dateFormat.format(date));

		document = documentXmlConverter.convertXmlDocToString(xmlDocument);
		return document;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.util
	 * .DocumentEditor#setDocumentPayloadRawData(java.lang.Object,
	 * java.lang.String, boolean, java.lang.String, java.lang.String,
	 * gov.samhsa.acs.common.bean.XacmlResult, java.lang.String, byte[], byte[])
	 */
	@Override
	public ByteArrayDataSource setDocumentPayloadRawData(String document,
			boolean packageAsXdm, String senderEmailAddress,
			String recipientEmailAddress, XacmlResult xacmlResult,
			String executionResponseContainer, byte[] maskingKeyBytes,
			byte[] encryptionKeyBytes) throws Exception, IOException {
		ByteArrayDataSource rawData;
		byte[] documentPayload = null;
		if (packageAsXdm) {

			// generate metadata xml
			final String metadataXml = metadataGenerator.generateMetadataXml(
					document, executionResponseContainer,
					xacmlResult.getHomeCommunityId(), senderEmailAddress,
					recipientEmailAddress);
			// FileHelper.writeStringToFile(metadataXml, "metadata.xml");

			documentPayload = XdmZipUtils.createXDMPackage(metadataXml,
					fileReader.readFile("CCD.xsl"), document,
					fileReader.readFile("INDEX.htm"),
					fileReader.readFile("README.txt"), maskingKeyBytes,
					encryptionKeyBytes);
		} else {
			documentPayload = document.getBytes();
		}

		rawData = new ByteArrayDataSource(documentPayload);
		return rawData;
	}
}
