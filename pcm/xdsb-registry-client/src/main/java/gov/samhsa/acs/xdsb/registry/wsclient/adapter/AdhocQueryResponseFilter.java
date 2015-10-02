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
package gov.samhsa.acs.xdsb.registry.wsclient.adapter;

import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryAdapterException;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class AdhocQueryResponseFilter.
 */
public class AdhocQueryResponseFilter {

	/** The Constant UUID_CLASSIFICATIONSCHEME_XDSDOCUMENTENTRY_AUTHOR. */
	public static final String UUID_CLASSIFICATIONSCHEME_XDSDOCUMENTENTRY_AUTHOR = "urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d";

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/** The converter. */
	private DocumentXmlConverter converter;

	/** The document accessor. */
	private DocumentAccessor documentAccessor;

	/**
	 * Instantiates a new adhoc query response filter.
	 * 
	 * @param marshaller
	 *            the marshaller
	 * @param converter
	 *            the converter
	 * @param documentAccessor
	 *            the document accessor
	 */
	public AdhocQueryResponseFilter(SimpleMarshaller marshaller,
			DocumentXmlConverter converter, DocumentAccessor documentAccessor) {
		super();
		this.marshaller = marshaller;
		this.converter = converter;
		this.documentAccessor = documentAccessor;
	}

	/**
	 * Filter by author.
	 * 
	 * @param response
	 *            the response
	 * @param authorNPI
	 *            the author npi
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse filterByAuthor(AdhocQueryResponse response,
			String authorNPI) throws XdsbRegistryAdapterException {
		try {
			// select the ExtrinsicObjects that doesn't have an author id
			// starting
			// with authorNPI^
			String xPathExpr = "//rim:Classification[@classificationScheme='$']/descendant::rim:Value[not(starts-with(.,concat('%', '^')))]/ancestor::rim:ExtrinsicObject";
			xPathExpr = xPathExpr.replace("%", authorNPI);
			xPathExpr = xPathExpr.replace("$",
					UUID_CLASSIFICATIONSCHEME_XDSDOCUMENTENTRY_AUTHOR);

			String docString = marshaller.marshal(response);

			Document xmlDocument = converter.loadDocument(docString);

			// Evaluate XPath expression against parsed document
			NodeList nodeSet = documentAccessor.getNodeList(xmlDocument,
					xPathExpr);
			for (int i = 0; i < nodeSet.getLength(); i++) {
				Node node = nodeSet.item(i);
				Element elementToBeRedacted = (Element) node;
				// remove the ExtrinsicObjects that have an authorId not
				// starting
				// with authorNPI^
				elementToBeRedacted.getParentNode().removeChild(
						elementToBeRedacted);
			}
			xmlDocument.normalize();
			return marshaller.unmarshalFromXml(AdhocQueryResponse.class,
					converter.convertXmlDocToString(xmlDocument));

		} catch (SimpleMarshallerException | DocumentAccessorException e) {
			throw new XdsbRegistryAdapterException(e);
		}
	}
}
