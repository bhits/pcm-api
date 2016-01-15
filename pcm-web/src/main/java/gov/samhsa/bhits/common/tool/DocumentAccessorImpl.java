/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
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
package gov.samhsa.bhits.common.tool;

import java.util.Optional;
import java.util.stream.Stream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import gov.samhsa.bhits.common.namespace.DefaultNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;

/**
 * The Class DocumentAccessorImpl.
 */
public class DocumentAccessorImpl implements DocumentAccessor {

    private Optional<NamespaceContext> namespaceContext;

    @Override
    public void setNamespaceContext(NamespaceContext namespaceContext) {
        this.namespaceContext = Optional.of(namespaceContext);
    }

    /*
         * (non-Javadoc)
         *
         * @see
         * gov.samhsa.acs.common.tool.DocumentAccessor#addingStylesheet(org.w3c.
         * dom.Document, java.lang.String)
         */
    @SuppressWarnings("unchecked")
    @Override
    public <ProcessingInstructionImpl> Document addingStylesheet(Document doc,
                                                                 String xslHref) throws TransformerConfigurationException,
            ParserConfigurationException {
        final StringBuilder builder = new StringBuilder();
        builder.append("type=\"text/xsl\" href=");
        builder.append(xslHref);

        final ProcessingInstructionImpl pi = (ProcessingInstructionImpl) doc
                .createProcessingInstruction("xml-stylesheet",
                        builder.toString());
        final Element root = doc.getDocumentElement();
        doc.insertBefore((Node) pi, root);
        return doc;

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.DocumentAccessor#getElement(org.w3c.dom.Document
     * , java.lang.String, java.lang.String[])
     */
    @Override
    public Optional<Element> getElement(Document xmlDocument, String xPathExpr,
                                        String... arguments) throws DocumentAccessorException {
        return getNode(xmlDocument, xPathExpr, arguments).filter(
                node -> node instanceof Element).map(
                element -> (Element) element);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.DocumentAccessor#getNode(org.w3c.dom.Document,
     * java.lang.String, java.lang.String[])
     */
    @Override
    public Optional<Node> getNode(Document xmlDocument, String xPathExpr,
                                  String... arguments) throws DocumentAccessorException {
        xPathExpr = setXpathArguments(xPathExpr, arguments);

        // Create XPath instance
        final XPath xpath = xpath();

        // Evaluate XPath expression against parsed document
        Node node = null;
        try {
            node = (Node) xpath.evaluate(xPathExpr, xmlDocument,
                    XPathConstants.NODE);
        } catch (final XPathExpressionException e) {
            throw new DocumentAccessorException(e);
        }
        return Optional.ofNullable(node);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.DocumentAccessor#getNodeList(org.w3c.dom.Document
     * , java.lang.String, java.lang.String[])
     */
    @Override
    public NodeList getNodeList(Document xmlDocument, String xPathExpr,
                                String... arguments) throws DocumentAccessorException {
        xPathExpr = setXpathArguments(xPathExpr, arguments);

        // Create XPath instance
        final XPath xpath = xpath();

        // Evaluate XPath expression against parsed document
        NodeList nodeList;
        try {
            nodeList = (NodeList) xpath.evaluate(xPathExpr, xmlDocument,
                    XPathConstants.NODESET);
        } catch (final XPathExpressionException e) {
            throw new DocumentAccessorException(e);
        }
        return nodeList;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.DocumentAccessor#getNodeListAsStream(org.w3c
     * .dom.Document, java.lang.String, java.lang.String[])
     */
    @Override
    public Stream<Node> getNodeListAsStream(Document xmlDocument,
                                            String xPathExpr, String... arguments)
            throws DocumentAccessorException {
        final NodeList nodeList = getNodeList(xmlDocument, xPathExpr, arguments);
        return DocumentAccessor.toNodeStream(nodeList);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.DocumentAccessor#getProcessingInstruction(
     * org.w3c.dom.Document, java.lang.String, java.lang.String[])
     */
    @Override
    public Optional<ProcessingInstruction> getProcessingInstruction(
            Document xmlDocument, String xPathExpr, String... arguments)
            throws DocumentAccessorException {
        try {
            return getNode(xmlDocument, xPathExpr, arguments).map(
                    node -> (ProcessingInstruction) node);
        } catch (final Exception e) {
            throw new DocumentAccessorException(e);
        }
    }

    /**
     * Sets the xpath arguments.
     *
     * @param xPath
     *            the x path
     * @param arguments
     *            the arguments
     * @return the string
     */
    private String setXpathArguments(String xPath, String... arguments) {
        for (int i = 0; i < arguments.length; i++) {
            xPath = xPath.replace("%" + Integer.toString(i + 1), arguments[i]);
        }
        return xPath;
    }

    /**
     * Creates the x path instance.
     *
     * @return the x path
     */
    private XPath xpath() {
        // Create XPath instance
        final XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(this.namespaceContext.orElseGet(DefaultNamespaceContext::new));
        return xpath;
    }
}
