package gov.samhsa.consent2share.infrastructure.report.configurer;

import gov.samhsa.consent2share.infrastructure.report.AbstractReportConfig;
import gov.samhsa.consent2share.infrastructure.report.ReportFormat;
import gov.samhsa.consent2share.infrastructure.report.ReportImageResolver;
import gov.samhsa.consent2share.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.consent2share.infrastructure.report.ReportProps;

import java.util.Map;
import java.util.function.Supplier;

import net.sf.jasperreports.engine.JRDataSource;

/**
 * This task returns the {@code imageParameterNameInTemplate=imageAbsolutePath }
 * mappings to be added to the report model/parameters. It depends on
 * {@link ReportImageResolver} to resolve the absolute paths for webpath or
 * classpath locations. If the report format is {@link ReportFormat#HTML}, it is
 * resolved by {@link ReportImageResolver#webpath(String)}. Otherwise, it is
 * resolved by {@link ReportImageResolver#classpath(String)}.
 *
 * @see ReportImageResolver
 */
public class SetImageMappingsTask implements ReportParameterConfigurerTask {

	/** The report image resolver supplier. */
	private final Supplier<ReportImageResolver> reportImageResolverSupplier;

	/**
	 * Instantiates a new sets the image mappings task.
	 *
	 * @param reportImageResolverSupplier
	 *            the report image resolver supplier
	 */
	public SetImageMappingsTask(
			Supplier<ReportImageResolver> reportImageResolverSupplier) {
		super();
		this.reportImageResolverSupplier = reportImageResolverSupplier;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.infrastructure.report.ReportParameterConfigurerTask
	 * #configure(gov.samhsa.consent2share.infrastructure.report.ReportProps,
	 * gov.samhsa.consent2share.infrastructure.report.ReportFormat,
	 * net.sf.jasperreports.engine.JRDataSource)
	 */
	@Override
	public Map<String, Object> configure(ReportProps reportProps,
			ReportFormat reportFormat, JRDataSource datasource) {
		final Map<String, Object> parameters = newMap();
		// Add image mappings (if any)
		reportProps.getImageMapping().ifPresent(
				map -> map.forEach((param, name) -> {
					switch (reportFormat) {
					// For html, resolve image files from webpath
					case HTML:
						parameters.put(param, reportImageResolverSupplier.get()
								.webpath(name));
						break;
						// For others, resolve image files from classpath
					default:
						parameters.put(param, reportImageResolverSupplier.get()
								.classpath(name));
						break;
					}
				}));
		return parameters;
	}

	/**
	 * A factory method to create a new instance of {@code SetImageMappingsTask}
	 * with given {@link AbstractReportConfig}.
	 *
	 * @param reportConfig
	 *            the report config
	 * @return the sets the image mappings task
	 */
	public static final SetImageMappingsTask newInstance(
			AbstractReportConfig reportConfig) {
		return new SetImageMappingsTask(() -> reportConfig
				.getReportImageResolver().get());
	}

}
