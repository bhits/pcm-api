package gov.samhsa.acs.common.util;

import javax.xml.transform.TransformerException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringURIResolverTest {
	StringURIResolver stringURIResolver;
	
	@Before
	public void setUp(){
		stringURIResolver=new StringURIResolver();
		stringURIResolver.put("uri", "document");
	}

	@Test
	public void testPut() {
		assertEquals(stringURIResolver.documents.get("uri"),"document");
	}
	
	@Test
	public void testResolve_when_document_does_not_exist() throws TransformerException{
		assertEquals(stringURIResolver.resolve("nonexist","base"),null);
	}
	
	@Test
	public void testResolve_when_document_exists() throws TransformerException{
		assertEquals(stringURIResolver.resolve("uri","base").getClass().getName(),"javax.xml.transform.stream.StreamSource");
	}
	

}
