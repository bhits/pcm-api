package gov.samhsa.consent2share.infrastructure.report;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReportImageResolverImplTest {

	private static final String contextPath = "contextPath";
	private static final String baseWebpathForImgResources = "baseWebpathForImgResources";
	private static final String baseClasspathForImgResources = "baseClasspathForImgResources";
	private static final Map<String, String> imageMapping = new HashMap<>();
	private static final String key1 = "key1";
	private static final String value1 = "value1";
	private static final String key2 = "key2";
	private static final String value2 = "value2";

	// expected values
	private final String contextPathbaseWebpathForImgResourcesvalue1 = "contextPathbaseWebpathForImgResourcesvalue1";
	private final String contextPathbaseWebpathForImgResourcesvalue2 = "contextPathbaseWebpathForImgResourcesvalue2";
	private final String baseClasspathForImgResourcesvalue1 = "baseClasspathForImgResourcesvalue1";
	private final String baseClasspathForImgResourcesvalue2 = "baseClasspathForImgResourcesvalue2";

	private ReportImageResolverImpl sut;

	@Before
	public void setUp() throws Exception {
		imageMapping.put(key1, value1);
		imageMapping.put(key2, value2);
		sut = new ReportImageResolverImpl(contextPath,
				baseWebpathForImgResources, baseClasspathForImgResources,
				imageMapping);
	}

	@Test
	public void testClasspath() {
		assertEquals(baseClasspathForImgResourcesvalue1, sut.classpath(value1));
		assertEquals(baseClasspathForImgResourcesvalue2, sut.classpath(value2));
	}

	@Test
	public void testWebpath() {
		assertEquals(contextPathbaseWebpathForImgResourcesvalue1,
				sut.webpath(value1));
		assertEquals(contextPathbaseWebpathForImgResourcesvalue2,
				sut.webpath(value2));
	}

}
