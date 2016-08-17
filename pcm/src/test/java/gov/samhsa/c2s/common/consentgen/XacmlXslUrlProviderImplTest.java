package gov.samhsa.c2s.common.consentgen;

import static org.junit.Assert.assertEquals;

import gov.samhsa.mhc.common.consentgen.XacmlXslUrlProviderImpl;
import gov.samhsa.mhc.common.consentgen.XslResource;
import org.junit.Ignore;
import org.junit.Test;

public class XacmlXslUrlProviderImplTest {
    @Ignore
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
