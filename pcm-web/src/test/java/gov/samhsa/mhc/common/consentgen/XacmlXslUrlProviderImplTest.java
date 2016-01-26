package gov.samhsa.mhc.common.consentgen;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XacmlXslUrlProviderImplTest {

    @Test
    public void testGetUrl() {
        // Arrange
        XacmlXslUrlProviderImpl sut = new XacmlXslUrlProviderImpl();

        final String xacmlXslFile = "c2xacml.xsl";
        String expectedXacmlXslUrl = this
                .getClass()
                .getClassLoader()
                .getResource(
                        this.getClass().getPackage().getName()
                                .replace(".", "/")
                                + "/" + xacmlXslFile).toString();

        // Act
        String returnedXacmlXslUrl = sut.getUrl(XslResource.XACMLXSLNAME);

        // Assert
        assertEquals(expectedXacmlXslUrl, returnedXacmlXslUrl);
    }
}
