package gov.samhsa.consent2share.infrastructure.report;

import static gov.samhsa.consent2share.infrastructure.report.JRDataSourceFactory.newJRDataSource;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is the base abstract class for the concrete report controllers. For each
 * report, there needs to be a separate concrete report controller. This
 * abstract controller only defines the dependencies on
 * {@link #reportDataProvider} and {@link #abstractReportConfig}, and provides a
 * convenience method {@link #reportModelAndView(ReportFormat, Supplier)} to
 * build a {@link ModelAndView} from {@link ReportFormat},
 * {@link #reportDataProvider} and {@link #abstractReportConfig};
 *
 * @see ReportDataProvider
 * @see AbstractReportConfig
 */
public abstract class AbstractReportController {

	/** The report data provider. */
	protected final ReportDataProvider reportDataProvider;

	/** The abstract report config. */
	protected final AbstractReportConfig abstractReportConfig;

	/**
	 * Instantiates a new abstract report controller.
	 *
	 * @param reportDataProvider
	 *            the report data provider
	 * @param abstractReportConfig
	 *            the abstract report config
	 */
	public AbstractReportController(ReportDataProvider reportDataProvider,
			AbstractReportConfig abstractReportConfig) {
		super();
		this.reportDataProvider = reportDataProvider;
		this.abstractReportConfig = abstractReportConfig;
	}

	/**
	 * Convenience method to convert the report data to {@link JRDataSource},
	 * run all the {@link ReportParameterConfigurerTask}s in
	 * {@link AbstractReportConfig#reportParameterConfigurerChain} by invoking
	 * {@link AbstractReportConfig#configure(ReportFormat, JRDataSource)},
	 * instantiates and returns {@link ModelAndView} with the
	 * <b>{viewName=reportName; model=
	 * {@link AbstractReportConfig#configure(ReportFormat, JRDataSource)}
	 * result}. </b>
	 *
	 * @param <T>
	 *            the generic type
	 * @param reportFormat
	 *            the report format
	 * @param dataSupplier
	 *            the data supplier
	 * @return the model and view
	 */
	public final <T> ModelAndView reportModelAndView(ReportFormat reportFormat,
			Supplier<? extends Collection<T>> dataSupplier) {
		// Prepare datasource for the report
		final JRDataSource reportDatasource = newJRDataSource(dataSupplier);

		// Configure report parameters
		final Map<String, Object> parameters = this.abstractReportConfig
				.configure(reportFormat, reportDatasource);

		// Build and return ModelAndView
		final String reportName = this.abstractReportConfig.getReportName();
		Assert.hasText(reportName, "No report name");
		Assert.notEmpty(parameters, "Report parameters cannot be empty");
		return new ModelAndView(reportName, parameters);
	}

	/**
	 * Gets the request scoped parameters instance to set custom parameters to
	 * the report model/parameters. This method must be used to get
	 * {@link RequestScopedParameters} from
	 * {@link RequestScopedParametersProviderImpl}, because it is also asserting
	 * that the bean's state is valid.
	 *
	 * @param requestScopedParametersProvider
	 *            the request scoped parameters provider
	 * @return the request scoped parameters
	 */
	public static final RequestScopedParameters getRequestScopedParameters(
			RequestScopedParametersProvider requestScopedParametersProvider) {
		final RequestScopedParameters bean = requestScopedParametersProvider
				.getRequestScopedParameters();
		Assert.notNull(bean, "RequestScopedParameters instance cannot be null");
		Assert.notNull(bean.getParameters(),
				"RequestScopedParameters.parameters cannot be null");
		Assert.isTrue(bean.getParameters().size() == 0,
				"RequestScopedParameters.parameters must have '0' elements");
		return bean;
	}
}
