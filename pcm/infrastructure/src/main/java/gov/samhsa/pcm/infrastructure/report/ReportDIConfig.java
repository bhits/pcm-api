package gov.samhsa.pcm.infrastructure.report;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

/**
 * The Interface ReportDIConfig specifies the required beans for a report
 * dependency injection configuration. For each report, a new configuration (
 * {@link org.springframework.context.annotation.Configuration @Configuration} )
 * file can be implemented using this interface. Depending on a particular
 * report's requirements, some of the beans specified in this interface may not
 * be needed (ie: {@link #rowMapper()}, {@link #sqlScriptProvider()} ...etc.).
 * In such case, it is recommended to throw
 * {@link UnsupportedOperationException} and <b>NOT</b> to annotate the method
 * with {@link Bean @Bean}, so it won't be initialized by Spring. The
 * {@link Bean @Bean} annotation must be added to all other methods, so the
 * beans can be initialized by Spring when the implementing class is annotated
 * with {@code @Configuration}.
 */
public interface ReportDIConfig {

	/**
	 * Bean for report config (this bean is required).
	 *
	 * @return the abstract report config
	 * @see AbstractReportConfig
	 */
	public abstract AbstractReportConfig reportConfig();

	/**
	 * Bean for report data provider (this bean is required).
	 *
	 * @param jdbcTemplate
	 *            the jdbc template
	 * @return the report data provider
	 * @see ReportDataProvider
	 */
	public abstract ReportDataProvider reportDataProvider(
			JdbcTemplate jdbcTemplate);

	/**
	 * Bean for report parameter configurer chain (this bean is required).
	 *
	 * @return the list
	 * @see ReportParameterConfigurerTask
	 * @see AbstractReportConfig#reportParameterConfigurerChain
	 */
	public abstract List<Supplier<ReportParameterConfigurerTask>> reportParameterConfigurerChain();

	/**
	 * Bean for report view (this bean is required). <br>
	 * <br>
	 * <b>IMPORTANT:</b><br>
	 * The name of this bean must be same with the view name of
	 * {@link ModelAndView} instance returned by the report controller that is
	 * handling the report request. {@link BeanNameViewResolver} configuration
	 * has been added to support view resolution by view name.
	 *
	 * @return the jasper reports multi format view
	 * @see JasperReportsMultiFormatView
	 * @see AbstractReportConfig
	 * @see AbstractReportController
	 * @see BeanNameViewResolver
	 */
	public abstract JasperReportsMultiFormatView reportView();

	/**
	 * Bean for row mapper. If not needed, it is recommended to throw
	 * {@link UnsupportedOperationException} and <b>NOT</b> to annotate the this
	 * method with {@link Bean @Bean}
	 *
	 * @return the row mapper
	 * @see RowMapper
	 */
	@SuppressWarnings("rawtypes")
	public abstract RowMapper rowMapper();

	/**
	 * Bean for sql script provider. If not needed, it is recommended to throw
	 * {@link UnsupportedOperationException} and <b>NOT</b> to annotate the this
	 * method with {@link Bean @Bean}
	 *
	 *
	 * @return the sql script provider
	 */
	public abstract SqlScriptProvider sqlScriptProvider();

}
