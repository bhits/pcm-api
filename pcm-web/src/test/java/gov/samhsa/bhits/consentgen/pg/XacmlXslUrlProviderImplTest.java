package gov.samhsa.bhits.consentgen.pg;

import static org.junit.Assert.assertEquals;

import gov.samhsa.bhits.consentgen.XslResource;
import org.junit.Test;

public class XacmlXslUrlProviderImplTest {

    @Test
    public void testGetUrl() {
        // Arrange
        XacmlXslUrlProviderImpl sut = new XacmlXslUrlProviderImpl();

        final String xacmlXslFile = XacmlXslUrlProviderImpl.class.getPackage().getName().replace(".", "/")+"/c2xacml.xsl";
        String expectedXacmlXslUrl = this.getClass().getClassLoader()
                .getResource(xacmlXslFile).toString();

        // Act
        String returnedXacmlXslUrl = sut.getUrl(XslResource.XACMLXSLNAME);

        // Assert
        assertEquals(expectedXacmlXslUrl, returnedXacmlXslUrl);
    }
}
