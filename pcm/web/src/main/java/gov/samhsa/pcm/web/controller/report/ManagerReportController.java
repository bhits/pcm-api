package gov.samhsa.pcm.web.controller.report;

import static gov.samhsa.pcm.infrastructure.report.ReportFormat.HTML;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
import gov.samhsa.pcm.infrastructure.report.AbstractReportController;
import gov.samhsa.pcm.infrastructure.report.ReportDataProvider;
import gov.samhsa.pcm.infrastructure.report.ReportUtils;
import gov.samhsa.pcm.infrastructure.report.ReportFormat;
import gov.samhsa.pcm.infrastructure.report.RequestScopedParameters;
import gov.samhsa.pcm.infrastructure.report.RequestScopedParametersProvider;
import gov.samhsa.pcm.web.config.report.ManagerReportConfig;
import gov.samhsa.pcm.web.config.report.ReportPath;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@link AbstractReportController} implementation for ManagerReport.
 */
@Controller
@RequestMapping(ReportPath.ADMIN_REPORT_CONTROLLER_BASE_PATH)
public class ManagerReportController extends AbstractReportController {

	private ReportUtils reportUtils;
	private RequestScopedParametersProvider requestScopedParametersProvider;


	/**
	 * Instantiates a new manager report controller.
	 *
	 * @param reportDataProvider the report data provider
	 * @param abstractReportConfig the abstract report config
	 * @param reportUtils the report utils
	 * @param requestScopedParametersProvider the request scoped parameters provider
	 */
	@Autowired
	public ManagerReportController(
			@Qualifier(ManagerReportConfig.REPORT_DATA_PROVIDER_NAME) ReportDataProvider reportDataProvider,
			@Qualifier(ManagerReportConfig.REPORT_CONFIG_NAME) AbstractReportConfig abstractReportConfig,
			ReportUtils reportUtils,
			RequestScopedParametersProvider requestScopedParametersProvider) {
		super(reportDataProvider, abstractReportConfig);
		this.reportUtils = reportUtils;
		this.requestScopedParametersProvider = requestScopedParametersProvider;
	}

	/**
	 * Handle report request.
	 *
	 * @param format
	 *            the format
	 * @return the model and view
	 */
	@RequestMapping(method = GET, value = ManagerReportConfig.REPORT_NAME)
	public ModelAndView handleReportRequest(
			@RequestParam Optional<ReportFormat> format,
			@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate startDate,
			@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate endDate,
			@RequestParam Optional<Boolean> noAccountInformation,
			@RequestParam Optional<Boolean> noCatagoryDescription) {
		final ReportFormat reportFormat = format.orElse(HTML);
		Boolean noInformation = noAccountInformation.orElse(false);
		Boolean noDescription = noCatagoryDescription.orElse(false);
		RequestScopedParameters requestScopedParameters = requestScopedParametersProvider
				.getRequestScopedParameters();

		LocalDateTime startDateTime = reportUtils.getStartDateTime(startDate);
		LocalDateTime endDateTime = reportUtils.getEndDateTime(endDate);

		long startSqlTimeStamp = reportUtils
				.convertLocalDateTimeToEpoch(startDateTime);
		long endSqlTimeStamp = reportUtils
				.convertLocalDateTimeToEpoch(endDateTime);

		requestScopedParameters
				.add("startDateTime",
						startDateTime.format(DateTimeFormatter
								.ofPattern("MM/dd/yyyy HH:mm:ss")))
				.add("endDateTime",
						endDateTime.format(DateTimeFormatter
								.ofPattern("MM/dd/yyyy HH:mm:ss")))
				.add("noInformation", noInformation)
				.add("noDescription", noDescription);

		Object[] args = new Object[] { startSqlTimeStamp, endSqlTimeStamp };
		Collection<Object> reportData = this.reportDataProvider
				.getReportData(args);
		
		if (reportData.size() == 0) {
			return new ModelAndView("views/Administrator/noReportDataFound");
		} else {
			return reportModelAndView(reportFormat, () -> reportData);
		}

	}
}
