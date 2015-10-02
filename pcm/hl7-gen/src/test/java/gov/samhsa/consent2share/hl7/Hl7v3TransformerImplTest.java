package gov.samhsa.consent2share.hl7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.XmlTransformer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Hl7v3TransformerImplTest {

	@Mock
	private XmlTransformer xmlTransformer;

	@InjectMocks
	private Hl7v3TransformerImpl sut;

	@Test
	public void testGetPixQueryXml() throws IOException, URISyntaxException,
			Hl7v3TransformerException {
		// Arrange
		final String mrnMock = "mrnMock";
		final String mrnDomainMock = "mrnDomainMock";
		final String picQueryxml = FileUtils.readFileToString(new File(
				getClass().getClassLoader()
						.getResource("xml/c32ToHl7v3PixQuery.xsl").toURI()));
		// String picQueryxml = "xm/c32ToHl7v3PixQuery.xsl";

		// Act
		final String hl7v3PixXML = sut.getPixQueryXml(mrnMock, mrnDomainMock,
				picQueryxml);

		// Assert
		assertNotNull(hl7v3PixXML);
	}

	@Test
	public void testTransformC32ToHl7v3PixXml() throws IOException,
			URISyntaxException, Hl7v3TransformerException {
		// Arrange
		final String transformedXmlMock = "transformedXmlMock";
		final String c32XMLString = FileUtils
				.readFileToString(new File(getClass().getClassLoader()
						.getResource("xml/c32.xml").toURI()));
		when(
				xmlTransformer.transform(eq(c32XMLString), anyString(),
						any(Optional.class), any(Optional.class))).thenReturn(
				transformedXmlMock);

		// Act
		final String hl7v3PixXML = sut.transformToHl7v3PixXml(c32XMLString,
				Hl7v3Transformer.XSLT_C32_TO_PIX_ADD);

		// Assert
		assertNotNull(hl7v3PixXML);
		assertEquals(transformedXmlMock, hl7v3PixXML);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = Hl7v3TransformerException.class)
	public void testTransformC32ToHl7v3PixXml_exception() throws IOException,
			URISyntaxException, Hl7v3TransformerException {
		// Arrange
		final String c32XMLString = FileUtils
				.readFileToString(new File(getClass().getClassLoader()
						.getResource("xml/c32.xml").toURI()));
		when(
				xmlTransformer.transform(eq(c32XMLString), anyString(),
						any(Optional.class), any(Optional.class))).thenThrow(
				DS4PException.class);

		// Act
		final String hl7v3PixXML = sut.transformToHl7v3PixXml(c32XMLString,
				Hl7v3Transformer.XSLT_C32_TO_PIX_ADD);

		// Assert
		assertNotNull(hl7v3PixXML);
	}

	@Test
	public void testTransformC32ToHl7v3PixXml_hl7v3exception()
			throws IOException, URISyntaxException, Hl7v3TransformerException {
		// Arrange
		final String errorMsg = " Testing Error Msg";

		// Act
		final Hl7v3TransformerException hl7v3 = new Hl7v3TransformerException(
				errorMsg);

		// Assert
		assertEquals(errorMsg, hl7v3.getMessage());
	}

}
