package gov.samhsa.consent2share.infrastructure.report;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * {@link JdbcTemplate} based implementation of the {@link ReportDataProvider}.
 * Separate instances of this class can be used by for several reports the get
 * the report data using {@link JdbcTemplate}.
 *
 * @see JdbcTemplate
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Transactional(readOnly = true)
public class JdbcTemplateReportDataProvider implements ReportDataProvider {

	/** The bean property row mapper supplier. */
	public final Supplier<RowMapper> BEAN_PROPERTY_ROW_MAPPER_SUPPLIER = BeanPropertyRowMapper::new;

	/** The jdbc template. */
	private final JdbcTemplate jdbcTemplate;

	/** The sql script. */
	private final String sqlScript;

	/** The row mapper. */
	private final RowMapper rowMapper;

	/**
	 * Instantiates a new jdbc template report data provider.
	 *
	 * @param jdbcTemplate
	 *            the jdbc template
	 * @param sqlScriptProvider
	 *            the sql script provider
	 * @param rowMapper
	 *            the row mapper
	 */
	public JdbcTemplateReportDataProvider(JdbcTemplate jdbcTemplate,
			SqlScriptProvider sqlScriptProvider, Optional<RowMapper> rowMapper) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.sqlScript = sqlScriptProvider.getSqlScript();
		Assert.hasText(sqlScript);
		this.rowMapper = rowMapper.orElseGet(BEAN_PROPERTY_ROW_MAPPER_SUPPLIER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.infrastructure.report.ReportDataProvider#
	 * getReportData(java.lang.Object[])
	 */
	@Override
	public <T> Collection<T> getReportData(Object... args) {
		return this.jdbcTemplate.query(this.sqlScript, this.rowMapper, args);
	}

}
