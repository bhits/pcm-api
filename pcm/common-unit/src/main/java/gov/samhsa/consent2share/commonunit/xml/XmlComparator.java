package gov.samhsa.consent2share.commonunit.xml;

import java.io.IOException;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.ElementNameAndAttributeQualifier;
import org.custommonkey.xmlunit.ElementNameAndTextQualifier;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.xml.sax.SAXException;

public class XmlComparator {
	public static DetailedDiff compareXMLs(String expectedResult,
			String actualResult, List<String> ignorableXPathsRegex) {

		DetailedDiff diff = null;
		try {
			setXMLUnitConfig();

			diff = new DetailedDiff((XMLUnit.compareXML(expectedResult,
					actualResult)));
			diff.overrideElementQualifier(new ElementNameAndTextQualifier());
			diff.overrideElementQualifier(new ElementNameQualifier());
			diff.overrideElementQualifier(new ElementNameAndAttributeQualifier());
			diff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());

			if (ignorableXPathsRegex != null) {
				RegexBasedDifferenceListener ignorableElementsListener = new RegexBasedDifferenceListener(
						ignorableXPathsRegex);
				/** setting our custom difference listener */
				diff.overrideDifferenceListener(ignorableElementsListener);
			}

			@SuppressWarnings("unchecked")
			List<Difference> differences = diff.getAllDifferences();
			for (Object object : differences) {
				Difference difference = (Difference) object;
				System.out.println("***********************");
				System.out.println(difference);
				System.out.println("***********************");
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return diff;

	}

	private static void setXMLUnitConfig() {
		XMLUnit.setIgnoreWhitespace(Boolean.TRUE);
		XMLUnit.setIgnoreComments(Boolean.TRUE);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(Boolean.TRUE);
		XMLUnit.setIgnoreAttributeOrder(Boolean.TRUE);
	}
}
