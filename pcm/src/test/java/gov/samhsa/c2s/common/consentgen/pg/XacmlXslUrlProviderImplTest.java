package gov.samhsa.c2s.common.consentgen.pg;

import static org.junit.Assert.assertEquals;

import gov.samhsa.c2s.common.consentgen.XslResource;
import gov.samhsa.c2s.common.consentgen.pg.XacmlXslUrlProviderImpl;
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
