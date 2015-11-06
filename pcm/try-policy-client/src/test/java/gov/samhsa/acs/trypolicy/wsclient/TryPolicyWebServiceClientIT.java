package gov.samhsa.acs.trypolicy.wsclient;

import static org.junit.Assert.*;

import gov.samhsa.pcm.commonunit.io.SpringBasedResourceFileReader;
import gov.samhsa.pcm.commonunit.xml.XmlComparator;

import java.util.ArrayList;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;

public class TryPolicyWebServiceClientIT {

	@Test
	public void testTryPolicy() throws Throwable {
		String endpointAddress = "http://localhost:8080/Pep/services/policyTryingService";
		TryPolicyWebServiceClient sut = new TryPolicyWebServiceClient(
				endpointAddress);

		String c32Xml = SpringBasedResourceFileReader.getStringFromResourceFile("c32.xml");

		String xacmlPolicy = SpringBasedResourceFileReader
				.getStringFromResourceFile("xacmlPolicyForTrying.xml");

		String purposeOfUse = "TREAT";
		
		String segmentedC32 = sut.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse);
		
		String expectedSegmentedC32 = SpringBasedResourceFileReader
				.getStringFromResourceFile("segmentedC32.xml");

		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalizeWhitespace(true);
		
		List<String> ignoredTags=new ArrayList<String>();
		ignoredTags.add("effectiveTime");
		ignoredTags.add("confidentialityCode");
		
		DetailedDiff detailedDiff = XmlComparator.compareXMLs(expectedSegmentedC32, segmentedC32, ignoredTags);
		
		assertTrue("test XML matches control skeleton XML",
				detailedDiff.similar());
	}
}
