package gov.samhsa.acs.documentsegmentation.tools.redact.base;

import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import gov.samhsa.acs.common.tool.DocumentAccessor;

public abstract class AbstractDocumentLevelRedactionHandler extends
AbstractRedactionHandler{

	public AbstractDocumentLevelRedactionHandler(
			DocumentAccessor documentAccessor) {
		super(documentAccessor);
	}

	public abstract void execute(Document xmlDocument,
			Set<String> redactSectionCodesAndGeneratedEntryIds,
			List<Node> listOfNodes) throws XPathExpressionException;
}
