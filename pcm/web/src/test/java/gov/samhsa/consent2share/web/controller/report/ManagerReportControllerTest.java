package gov.samhsa.consent2share.web.controller.report;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.consent2share.infrastructure.report.AbstractReportConfig;
import gov.samhsa.consent2share.infrastructure.report.ReportDataProvider;
import gov.samhsa.consent2share.infrastructure.report.ReportFormat;
import gov.samhsa.consent2share.infrastructure.report.ReportUtils;
import gov.samhsa.consent2share.infrastructure.report.RequestScopedParameters;
import gov.samhsa.consent2share.infrastructure.report.RequestScopedParametersProvider;
import gov.samhsa.consent2share.service.dto.ManagerReportEntryDto;
import gov.samhsa.consent2share.web.config.report.ManagerReportConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(MockitoJUnitRunner.class)
public class ManagerReportControllerTest {

	@Rule
	public ExpectedException thrownException = ExpectedException.none();

	@Mock
	private ReportDataProvider reportDataProvider;
	@Mock
	private AbstractReportConfig abstractReportConfig;
	
	@Mock
	private RequestScopedParametersProvider requestScopedParametersProvider;
	@Mock
	private ReportUtils reportUtils;

	@InjectMocks
	private ManagerReportController sut;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(this.sut)
				.setViewResolvers(viewResolver).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHandleReportRequest() throws Exception {
		final ReportFormat format = ReportFormat.HTML;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		final LocalDate startDate = LocalDate.parse("03/01/2015",df);
		final LocalDate endDate = LocalDate.parse("06/01/2015",df);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		final LocalDateTime startDateTime = LocalDateTime.parse("03/01/2015 00:00:00", dtf);
		final LocalDateTime endDateTime = LocalDateTime.parse("06/01/2015 23:59:59", dtf);
		when(reportUtils.getStartDateTime(startDate)).thenReturn(startDateTime);
		when(reportUtils.getEndDateTime(endDate)).thenReturn(endDateTime);
		
		final Boolean noInformation = false;
		final Boolean noDescription = false;
		
		final ManagerReportEntryDto entry = new ManagerReportEntryDto();
		final long startSqlTimeStamp = 1425186000000l;
		when(reportUtils.convertLocalDateTimeToEpoch(startDateTime)).thenReturn(startSqlTimeStamp);
		final long endSqlTimeStamp = 1433217599000l;
		when(reportUtils.convertLocalDateTimeToEpoch(endDateTime)).thenReturn(endSqlTimeStamp);
		Object[] args = new Object[] { startSqlTimeStamp, endSqlTimeStamp };
		@SuppressWarnings("rawtypes")
		final Collection managerReport = Arrays.asList(entry);
		when(reportDataProvider.getReportData(args)).thenReturn(managerReport);
		
		final RequestScopedParameters requestScopedParameters = new RequestScopedParameters();
		requestScopedParameters.add("startDateTime", startDateTime);
		requestScopedParameters.add("endDateTime", endDateTime);
		requestScopedParameters.add("noInformation", noInformation);
		requestScopedParameters.add("noDescription", noDescription);
		when(requestScopedParametersProvider.getRequestScopedParameters()).thenReturn(requestScopedParameters);
		
		final Map<String, Object> parameters = new HashMap<>();
		final String key = "key";
		final Object value = "value";
		parameters.put(key, value);
		when(
				abstractReportConfig.configure(eq(format),
						any(JRDataSource.class))).thenReturn(parameters);
		when(abstractReportConfig.getReportName()).thenReturn(
				ManagerReportConfig.REPORT_NAME);

		// Act
		mockMvc.perform(get("/Administrator/reports/managerReport?startDate="+startDate.format(df)+"&endDate="+endDate.format(df)))
		.andExpect(status().isOk())
		.andExpect(model().attribute("key", "value"))
		.andExpect(model().size(parameters.size()))
		.andExpect(view().name(ManagerReportConfig.REPORT_NAME));

		// Assert
		verify(reportDataProvider, times(1)).getReportData(args);
		verify(abstractReportConfig, times(1)).configure(eq(format),
				any(JRDataSource.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHandleReportRequest_CSV() throws Exception {
		final ReportFormat format = ReportFormat.CSV;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		final LocalDate startDate = LocalDate.parse("03/01/2015",df);
		final LocalDate endDate = LocalDate.parse("06/01/2015",df);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		final LocalDateTime startDateTime = LocalDateTime.parse("03/01/2015 00:00:00", dtf);
		final LocalDateTime endDateTime = LocalDateTime.parse("06/01/2015 23:59:59", dtf);
		when(reportUtils.getStartDateTime(startDate)).thenReturn(startDateTime);
		when(reportUtils.getEndDateTime(endDate)).thenReturn(endDateTime);
		
		final Boolean noInformation = false;
		final Boolean noDescription = false;
		
		final ManagerReportEntryDto entry = new ManagerReportEntryDto();
		final long startSqlTimeStamp = 1425186000000l;
		when(reportUtils.convertLocalDateTimeToEpoch(startDateTime)).thenReturn(startSqlTimeStamp);
		final long endSqlTimeStamp = 1433217599000l;
		when(reportUtils.convertLocalDateTimeToEpoch(endDateTime)).thenReturn(endSqlTimeStamp);
		Object[] args = new Object[] { startSqlTimeStamp, endSqlTimeStamp };
		@SuppressWarnings("rawtypes")
		final Collection managerReport = Arrays.asList(entry);
		when(reportDataProvider.getReportData(args)).thenReturn(managerReport);
		
		final RequestScopedParameters requestScopedParameters = new RequestScopedParameters();
		requestScopedParameters.add("startDateTime", startDateTime);
		requestScopedParameters.add("endDateTime", endDateTime);
		requestScopedParameters.add("noInformation", noInformation);
		requestScopedParameters.add("noDescription", noDescription);
		when(requestScopedParametersProvider.getRequestScopedParameters()).thenReturn(requestScopedParameters);
		
		final Map<String, Object> parameters = new HashMap<>();
		final String key = "key";
		final Object value = "value";
		parameters.put(key, value);
		when(
				abstractReportConfig.configure(eq(format),
						any(JRDataSource.class))).thenReturn(parameters);
		when(abstractReportConfig.getReportName()).thenReturn(
				ManagerReportConfig.REPORT_NAME);

		// Act
		mockMvc.perform(get("/Administrator/reports/managerReport?format="
						+ format.getFormat().toUpperCase()+"&startDate="+startDate.format(df)+"&endDate="+endDate.format(df)))
		.andExpect(status().isOk())
		.andExpect(model().attribute("key", "value"))
		.andExpect(model().size(parameters.size()))
		.andExpect(view().name(ManagerReportConfig.REPORT_NAME));

		// Assert
		verify(reportDataProvider, times(1)).getReportData(args);
		verify(abstractReportConfig, times(1)).configure(eq(format),
				any(JRDataSource.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHandleReportRequest_HTML() throws Exception {
		final ReportFormat format = ReportFormat.HTML;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		final LocalDate startDate = LocalDate.parse("03/01/2015",df);
		final LocalDate endDate = LocalDate.parse("06/01/2015",df);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		final LocalDateTime startDateTime = LocalDateTime.parse("03/01/2015 00:00:00", dtf);
		final LocalDateTime endDateTime = LocalDateTime.parse("06/01/2015 23:59:59", dtf);
		when(reportUtils.getStartDateTime(startDate)).thenReturn(startDateTime);
		when(reportUtils.getEndDateTime(endDate)).thenReturn(endDateTime);
		
		final Boolean noInformation = false;
		final Boolean noDescription = false;
		
		final ManagerReportEntryDto entry = new ManagerReportEntryDto();
		final long startSqlTimeStamp = 1425186000000l;
		when(reportUtils.convertLocalDateTimeToEpoch(startDateTime)).thenReturn(startSqlTimeStamp);
		final long endSqlTimeStamp = 1433217599000l;
		when(reportUtils.convertLocalDateTimeToEpoch(endDateTime)).thenReturn(endSqlTimeStamp);
		Object[] args = new Object[] { startSqlTimeStamp, endSqlTimeStamp };
		@SuppressWarnings("rawtypes")
		final Collection managerReport = Arrays.asList(entry);
		when(reportDataProvider.getReportData(args)).thenReturn(managerReport);
		
		final RequestScopedParameters requestScopedParameters = new RequestScopedParameters();
		requestScopedParameters.add("startDateTime", startDateTime);
		requestScopedParameters.add("endDateTime", endDateTime);
		requestScopedParameters.add("noInformation", noInformation);
		requestScopedParameters.add("noDescription", noDescription);
		when(requestScopedParametersProvider.getRequestScopedParameters()).thenReturn(requestScopedParameters);
		
		final Map<String, Object> parameters = new HashMap<>();
		final String key = "key";
		final Object value = "value";
		parameters.put(key, value);
		when(
				abstractReportConfig.configure(eq(format),
						any(JRDataSource.class))).thenReturn(parameters);
		when(abstractReportConfig.getReportName()).thenReturn(
				ManagerReportConfig.REPORT_NAME);

		// Act
		mockMvc.perform(get("/Administrator/reports/managerReport?format="
						+ format.getFormat().toUpperCase()+"&startDate="+startDate.format(df)+"&endDate="+endDate.format(df)))
		.andExpect(status().isOk())
		.andExpect(model().attribute("key", "value"))
		.andExpect(model().size(parameters.size()))
		.andExpect(view().name(ManagerReportConfig.REPORT_NAME));

		// Assert
		verify(reportDataProvider, times(1)).getReportData(args);
		verify(abstractReportConfig, times(1)).configure(eq(format),
				any(JRDataSource.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHandleReportRequest_PDF() throws Exception {
		final ReportFormat format = ReportFormat.PDF;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		final LocalDate startDate = LocalDate.parse("03/01/2015",df);
		final LocalDate endDate = LocalDate.parse("06/01/2015",df);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		final LocalDateTime startDateTime = LocalDateTime.parse("03/01/2015 00:00:00", dtf);
		final LocalDateTime endDateTime = LocalDateTime.parse("06/01/2015 23:59:59", dtf);
		when(reportUtils.getStartDateTime(startDate)).thenReturn(startDateTime);
		when(reportUtils.getEndDateTime(endDate)).thenReturn(endDateTime);
		
		final Boolean noInformation = false;
		final Boolean noDescription = false;
		
		final ManagerReportEntryDto entry = new ManagerReportEntryDto();
		final long startSqlTimeStamp = 1425186000000l;
		when(reportUtils.convertLocalDateTimeToEpoch(startDateTime)).thenReturn(startSqlTimeStamp);
		final long endSqlTimeStamp = 1433217599000l;
		when(reportUtils.convertLocalDateTimeToEpoch(endDateTime)).thenReturn(endSqlTimeStamp);
		Object[] args = new Object[] { startSqlTimeStamp, endSqlTimeStamp };
		@SuppressWarnings("rawtypes")
		final Collection managerReport = Arrays.asList(entry);
		when(reportDataProvider.getReportData(args)).thenReturn(managerReport);
		
		final RequestScopedParameters requestScopedParameters = new RequestScopedParameters();
		requestScopedParameters.add("startDateTime", startDateTime);
		requestScopedParameters.add("endDateTime", endDateTime);
		requestScopedParameters.add("noInformation", noInformation);
		requestScopedParameters.add("noDescription", noDescription);
		when(requestScopedParametersProvider.getRequestScopedParameters()).thenReturn(requestScopedParameters);
		
		final Map<String, Object> parameters = new HashMap<>();
		final String key = "key";
		final Object value = "value";
		parameters.put(key, value);
		when(
				abstractReportConfig.configure(eq(format),
						any(JRDataSource.class))).thenReturn(parameters);
		when(abstractReportConfig.getReportName()).thenReturn(
				ManagerReportConfig.REPORT_NAME);

		// Act
		mockMvc.perform(get("/Administrator/reports/managerReport?format="
						+ format.getFormat().toUpperCase()+"&startDate="+startDate.format(df)+"&endDate="+endDate.format(df)))
		.andExpect(status().isOk())
		.andExpect(model().attribute("key", "value"))
		.andExpect(model().size(parameters.size()))
		.andExpect(view().name(ManagerReportConfig.REPORT_NAME));

		// Assert
		verify(reportDataProvider, times(1)).getReportData(args);
		verify(abstractReportConfig, times(1)).configure(eq(format),
				any(JRDataSource.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHandleReportRequest_XLS() throws Exception {
		final ReportFormat format = ReportFormat.XLS;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		final LocalDate startDate = LocalDate.parse("03/01/2015",df);
		final LocalDate endDate = LocalDate.parse("06/01/2015",df);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		final LocalDateTime startDateTime = LocalDateTime.parse("03/01/2015 00:00:00", dtf);
		final LocalDateTime endDateTime = LocalDateTime.parse("06/01/2015 23:59:59", dtf);
		when(reportUtils.getStartDateTime(startDate)).thenReturn(startDateTime);
		when(reportUtils.getEndDateTime(endDate)).thenReturn(endDateTime);
		
		final Boolean noInformation = false;
		final Boolean noDescription = false;
		
		final ManagerReportEntryDto entry = new ManagerReportEntryDto();
		final long startSqlTimeStamp = 1425186000000l;
		when(reportUtils.convertLocalDateTimeToEpoch(startDateTime)).thenReturn(startSqlTimeStamp);
		final long endSqlTimeStamp = 1433217599000l;
		when(reportUtils.convertLocalDateTimeToEpoch(endDateTime)).thenReturn(endSqlTimeStamp);
		Object[] args = new Object[] { startSqlTimeStamp, endSqlTimeStamp };
		@SuppressWarnings("rawtypes")
		final Collection managerReport = Arrays.asList(entry);
		when(reportDataProvider.getReportData(args)).thenReturn(managerReport);
		
		final RequestScopedParameters requestScopedParameters = new RequestScopedParameters();
		requestScopedParameters.add("startDateTime", startDateTime);
		requestScopedParameters.add("endDateTime", endDateTime);
		requestScopedParameters.add("noInformation", noInformation);
		requestScopedParameters.add("noDescription", noDescription);
		when(requestScopedParametersProvider.getRequestScopedParameters()).thenReturn(requestScopedParameters);
		
		final Map<String, Object> parameters = new HashMap<>();
		final String key = "key";
		final Object value = "value";
		parameters.put(key, value);
		when(
				abstractReportConfig.configure(eq(format),
						any(JRDataSource.class))).thenReturn(parameters);
		when(abstractReportConfig.getReportName()).thenReturn(
				ManagerReportConfig.REPORT_NAME);

		// Act
		mockMvc.perform(get("/Administrator/reports/managerReport?format="
						+ format.getFormat().toUpperCase()+"&startDate="+startDate.format(df)+"&endDate="+endDate.format(df)))
		.andExpect(status().isOk())
		.andExpect(model().attribute("key", "value"))
		.andExpect(model().size(parameters.size()))
		.andExpect(view().name(ManagerReportConfig.REPORT_NAME));

		// Assert
		verify(reportDataProvider, times(1)).getReportData(args);
		verify(abstractReportConfig, times(1)).configure(eq(format),
				any(JRDataSource.class));
	}

}
