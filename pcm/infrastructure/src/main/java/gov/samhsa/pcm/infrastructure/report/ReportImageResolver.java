package gov.samhsa.pcm.infrastructure.report;

/**
 * This is a specialized version of {@link ReportResourceResolver} for image
 * resources. Currently, it doesn't have any additional method definitions.
 *
 * @see ReportResourceResolver
 */
public interface ReportImageResolver extends ReportResourceResolver {

	/** The Constant WEB_IDX. */
	public static final int WEB_IDX = 0;

	/** The Constant CLASSPATH_IDX. */
	public static final int CLASSPATH_IDX = 1;
}
