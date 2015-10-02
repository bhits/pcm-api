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
package gov.samhsa.acs.common.tool;

import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;

/**
 * The Interface DocumentAccessor.
 */
public interface DocumentAccessor {

	/**
	 * Gets the node list.
	 *
	 * @param <ProcessingInstructionImpl>
	 *            the generic type
	 * @param doc
	 *            the doc
	 * @param xslHref
	 *            the xsl href
	 * @return the node list
	 * @throws TransformerConfigurationException
	 *             the transformer configuration exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 */
	public <ProcessingInstructionImpl> Document addingStylesheet(Document doc,
			String xslHref) throws TransformerConfigurationException,
			ParserConfigurationException;

	/**
	 * Gets the element.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @param xPathExpr
	 *            the x path expr
	 * @param arguments
	 *            the arguments
	 * @return the element
	 * @throws DocumentAccessorException
	 *             the document accessor exception
	 */
	public abstract Optional<Element> getElement(Document xmlDocument,
			String xPathExpr, String... arguments)
			throws DocumentAccessorException;

	/**
	 * Gets the node.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @param xPathExpr
	 *            the x path expr
	 * @param arguments
	 *            the arguments
	 * @return the node
	 * @throws DocumentAccessorException
	 *             the document accessor exception
	 */
	public abstract Optional<Node> getNode(Document xmlDocument,
			String xPathExpr, String... arguments)
			throws DocumentAccessorException;

	/**
	 * Gets the node list.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @param xPathExpr
	 *            the x path expr
	 * @param arguments
	 *            the arguments
	 * @return the node list
	 * @throws DocumentAccessorException
	 *             the document accessor exception
	 */
	public abstract NodeList getNodeList(Document xmlDocument,
			String xPathExpr, String... arguments)
					throws DocumentAccessorException;

	/**
	 * Gets the node list as stream.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @param xPathExpr
	 *            the x path expr
	 * @param arguments
	 *            the arguments
	 * @return the node list as stream
	 * @throws DocumentAccessorException
	 *             the document accessor exception
	 */
	public abstract Stream<Node> getNodeListAsStream(Document xmlDocument,
			String xPathExpr, String... arguments)
					throws DocumentAccessorException;

	/**
	 * Gets the processing instruction.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @param xPathExpr
	 *            the x path expr
	 * @param arguments
	 *            the arguments
	 * @return the processing instruction
	 * @throws DocumentAccessorException
	 *             the document accessor exception
	 */
	public abstract Optional<ProcessingInstruction> getProcessingInstruction(
			Document xmlDocument, String xPathExpr, String... arguments)
					throws DocumentAccessorException;

	/**
	 * To node stream.
	 *
	 * @param nodeList
	 *            the node list
	 * @return the stream
	 */
	public static Stream<Node> toNodeStream(NodeList nodeList) {
		Assert.notNull(nodeList);
		return IntStream.range(0, nodeList.getLength()).boxed()
				.map(nodeList::item);
	}
}
