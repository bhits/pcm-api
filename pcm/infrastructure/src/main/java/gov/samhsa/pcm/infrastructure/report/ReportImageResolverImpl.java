package gov.samhsa.pcm.infrastructure.report;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class ReportImageResolverImpl implements {@link ReportImageResolver}
 * interface.
 */
public class ReportImageResolverImpl implements ReportImageResolver {

	/** The base webpath for img resources. */
	private final String baseWebpathForImgResources;

	/** The base classpath for img resources. */
	private final String baseClasspathForImgResources;

	/** The webpath. */
	private final String webpath;

	/** The map. */
	private final Map<String, String[]> map;

	/**
	 * Instantiates a new report image resolver and initializes a map for
	 * holding both classpath and webpath locations of image resources that are
	 * accessible by image resource name.
	 *
	 * @param contextPath
	 *            the context path (required for webpath resolution)
	 * @param baseWebpathForImgResources
	 *            the base webpath for img resources (the path coming after
	 *            context path)
	 * @param baseClasspathForImgResources
	 *            the base classpath for img resources
	 * @param imageMapping
	 *            the image mapping (mapping from report template parameter name
	 *            to image name)
	 */
	public ReportImageResolverImpl(String contextPath,
			String baseWebpathForImgResources,
			String baseClasspathForImgResources,
			Map<String, String> imageMapping) {
		this.baseWebpathForImgResources = baseWebpathForImgResources;
		this.baseClasspathForImgResources = baseClasspathForImgResources;
		map = new HashMap<>();
		webpath = new StringBuilder().append(contextPath)
				.append(this.baseWebpathForImgResources).toString();
		imageMapping.values().stream().distinct().forEach(this::put);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.configuration.report.common.ReportResourceResolver
	 * #classpath(java.lang.String)
	 */
	@Override
	public String classpath(String resourceName) {
		return map.get(resourceName)[CLASSPATH_IDX];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.configuration.report.common.ReportResourceResolver
	 * #webpath(java.lang.String)
	 */
	@Override
	public String webpath(String resourceName) {
		return map.get(resourceName)[WEB_IDX];
	}

	/**
	 * Puts the image resource by image resource name to the resolution map.
	 *
	 * @param img
	 *            the img
	 */
	private void put(String img) {
		map.put(img, new String[] { this.webpath + img,
				this.baseClasspathForImgResources + img });
	}
}
