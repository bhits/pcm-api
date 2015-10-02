package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XacmlPolicyListFilterTest {
	private DocumentXmlConverterImpl documentXmlConverter;
	private DocumentAccessorImpl documentAccessor;
	private FileReaderImpl fileReader;
	
	private String policy1;
	private String policy2;
	private LinkedList<String> policyList;
	
	private XacmlPolicyListFilter sut;

	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		documentAccessor = new DocumentAccessorImpl();
		policy1 = fileReader.readFile("xacml_policy2.xml");
		policy2 = fileReader.readFile("xacml_policy3.xml");
		policyList = new LinkedList<String>();
		policyList.add(policy1);
		policyList.add(policy2);
		sut = new XacmlPolicyListFilter(documentXmlConverter, documentAccessor);
	}
	
	@Test
	public void testFilterByNPI_Policy1() throws Exception {
		// Act
		sut.filterByNPI(policyList, "1760717789", "1114252178");
		
		// Assert
		assertTrue(policyList.contains(policy1));
		assertTrue(!policyList.contains(policy2));
	}

	@Test
	public void testFilterByNPI_Policy2() throws Exception {
		// Act
		sut.filterByNPI(policyList, "22222", "11111");
		
		// Assert
		assertTrue(!policyList.contains(policy1));
		assertTrue(policyList.contains(policy2));
	}

	@Test
	public void testContainsInValue() {
		// Arrange
		Node node = mock(Node.class);
		when(node.getNodeValue()).thenReturn("value");
		NodeList nodeList = mock(NodeList.class);
		when(nodeList.getLength()).thenReturn(1).thenReturn(1);
		when(nodeList.item(0)).thenReturn(node).thenReturn(node);
		
		// Act
		boolean test1 = sut.containsInValue(nodeList, "value");
		boolean test2 = sut.containsInValue(nodeList, "not_value");
		
		// Assert
		assertTrue(test1);
		assertFalse(test2);
	}
}
