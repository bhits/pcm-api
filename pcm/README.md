# 1. Externalizing and Encrypting 

For bl details, please see the following reference.
[https://github.com/PilotDS4P/ds4p-prod/blob/dev/DS4P/pcm/bl/web-bl/README.md](https://github.com/PilotDS4P/ds4p-prod/blob/dev/DS4P/pcm/bl/web-bl/README.md)

For pg details, please see the following reference.
[https://github.com/PilotDS4P/ds4p-prod/blob/dev/DS4P/pcm/pg/web-pg/README.md](https://github.com/PilotDS4P/ds4p-prod/blob/dev/DS4P/pcm/pg/web-pg/README.md)

# 2. Logback Configuration Externalization

For bl details, please see the following reference.
[https://github.com/PilotDS4P/ds4p-prod/blob/dev/DS4P/pcm/bl/web-bl/README.md](https://github.com/PilotDS4P/ds4p-prod/blob/dev/DS4P/pcm/bl/web-bl/README.md)

For pg details, please see the following reference.
[https://github.com/PilotDS4P/ds4p-prod/blob/dev/DS4P/pcm/pg/web-pg/README.md](https://github.com/PilotDS4P/ds4p-prod/blob/dev/DS4P/pcm/pg/web-pg/README.md)

# 3. C2S PCM Reports

pcm PCM application is using [JasperReports framework](http://community.jaspersoft.com/project/jasperreports-library) and utilizing [Spring Framework support](http://docs.spring.io/spring-framework/docs/3.2.0.BUILD-SNAPSHOT/reference/htmlsingle/#view-jasper-reports) to power application reports. In order to simplify the common report configuration and minimize the implementation overhead for future reports, several abstractions have been implemented in the infrastructure, service and presentation layers. If a certain coding convention is followed, these abstractions are able to auto-configure report back-end with minimum additional code. This section explains the coding convention to follow for C2S PCM report configurations.

## 3.1. Abstractions used in PCM

These are the high level abstractions used in PCM to configure a single report:

1. **Report Data Provider:** This is the bean responsible for providing the report data. This report data will be eventually converted to `JRDataSource` type and added to the report *model*, so JasperReport views can fill the report template with the data provided and present it as expected. There are many ways to create a `JRDataSource`, but in PCM **"Collection of JavaBeans"** option is preferred (`JRBeanCollectionDataSource(Collection<?> beanCollection)`). Therefore, `ReportDataProvider` interface has `<T> Collection<T> getReportData(Object... args)` abstract method.

2. **Report Configuration:** This bean is mainly responsible for holding the report metadata and properties including the *report name, report data provider bean name, report configuration bean name, report image mappings, report configuration tasks...etc*. The minimum requirements to implement a concrete *Report Configuration* is to provide **report name, report data provider name and report configuration name**. `AbstractReportConfig` class makes several assumptions such as *location and name of the SQL script and JasperReport template (jrxml) files* if no other methods are overridden. See `AbstractReportConfig` class for the current auto-configuration and override the needed methods for customization.

3. **Report DI (Dependency Injection) Configuration:** This is an interface to guide the developers to setup DI configuration for a single report. In many use cases, the reports will require a bean (`@Bean`) for each method declaration in this interface (`@Configuration`). If there are any bean declarations that will **NOT** be needed for some reason, it is recommended to throw `UnsupportedOperationException` and just ignore that method for that particular report. See `ReportDIConfig` interface for details.

4. **Report Controller:** This bean is responsible for receiving the requests from front-end, getting the parameters from the requests (if any), passing the parameters to the *Report Data Provider* and getting the report data, constructing the *report model* using *Report Configuration* and returning `ModelAndView` with appropriate *view name* and *report model*. As explained, *Report Controller* is depending on *Report Data Provider* and *Report Configuration*. See `AbstractReportController` for details.

## 3.2. Creating a new report by following a convention

### 3.2.1. Report Configuration (AbstractReportConfig class)


This is the main configuration class for reports that contains the important report properties. At a minimum, **report name**, **report config name** and **report data provider name** need to be implemented. These are `String` values and need to be globally unique. It is also highly recommended to have these as `public static final` fields in the class, because they need to be referred by other classes as explained in the following topics. The concrete report configuration implementation should be placed in `gov.samhsa.pcm.web.config.report` package in the web project. Additionally, it is recommended to follow a convention for naming the properties like *reportName*, *reportName*Config and *reportName*DataProvider. A sample implementation for this class can be seen as below:


	package gov.samhsa.pcm.web.config.report;
	
	import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
	import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerTask;
	
	import java.util.List;
	import java.util.Map;
	import java.util.function.Supplier;
	
	import javax.servlet.ServletContext;
	
	public class TestReportConfig extends AbstractReportConfig {
	
		public static final String REPORT_NAME = "testReport";
		public static final String REPORT_CONFIG_NAME = "testReportConfig";
		public static final String REPORT_DATA_PROVIDER_NAME = "testReportDataProvider";
	
		// image mappings (not needed if there are no images)
		public static final String IMAGE1_PARAM_NAME_IN_TEMPLATE = "logo1";
		public static final String IMAGE2_PARAM_NAME_IN_TEMPLATE = "logo2";
		public static final String IMAGE1_FILE_NAME = "image1.png";
		public static final String IMAGE2_FILE_NAME = "image2.png";
	
		public TestReportConfig(
				ServletContext servletContext,
				List<Supplier<ReportParameterConfigurerTask>> reportParameterConfigurerChain) {
			super(servletContext, reportParameterConfigurerChain);
		}
	
		@Override
		public String getReportConfigName() {
			return REPORT_CONFIG_NAME;
		}
	
		@Override
		public String getReportDataProviderName() {
			return REPORT_DATA_PROVIDER_NAME;
		}
	
		@Override
		public String getReportName() {
			return REPORT_NAME;
		}
	
		// do not implement this method if there are no images in the report
		@Override
		public Map<String, String> imageMapping() {
			return imageMappings(
					mapping(IMAGE1_PARAM_NAME_IN_TEMPLATE, IMAGE1_FILE_NAME),
					mapping(IMAGE2_PARAM_NAME_IN_TEMPLATE, IMAGE2_FILE_NAME));
		}
	}



### 3.2.2. Report Data Provider (ReportDataProvider interface)

This is the main interface to provide the report data (`Collection<T>`). In PCM, it is decided to use Spring `JdbcTemplate` to query the database and get the report data, although this is not really enforced by the `ReportDataProvider` interface. For convenience, `JdbcTemplateReportDataProvider` has been implemented in the infrastructure layer. This implementation depends on `JdbcTemplate`, `SqlScriptProvider` and `Optional<RowMapper>`. If `Optional<RowMapper>` is passed as `Optional.empty()`, Spring's `BeanPropertyRowMapper` is used as the default implementation. 

The `SqlScriptProvider` interface only has one abstract method `String getSqlScript()` and there is also a `ClasspathSqlScriptProvider` implementation in the service layer that can read the sql script files from `pcm-service` classpath resources. In order to utilize `ClasspathSqlScriptProvider`, the sql script for the report data can be saved as a sql file in `service/src/main/resources/report/sql` default location (this location can be configured by overriding `AbstractReportConfig.getBaseClasspathForSqlScriptResources()` and `AbstractReportConfig.getSqlScriptFileName()`). When the convention is followed, the default name for the sql file will be same as the report name (`AbstractReportConfig.getReportName()`).

There is no need to implement a new concrete class if `JdbcTemplateReportDataProvider` and `ClasspathSqlScriptProvider` are used. The required configuration will be done in report dependency injection implementation (`ReportDIConfig` interface).

### 3.2.3. Report Template (.jrxml)

If the convention is followed, the report template file (.jrxml) can be saved in the web project's report resources same name with the *report name* (`web/src/main/resources/report/testReport.jrxml`). This default location and file name can be customized by overriding `AbstractReportConfig.getTemplateUrl()`.

### 3.2.4. Report Images (if any)

The default location for report images is in the web project: `web/src/main/webapp/resources/report/img`. The default configuration will be able to resolve both classpath and webpath for image resources (HTML export format requires webpath, all other binary formats like PDF, XLS require classpath resolution). These locations can be customized by overriding `AbstractReportConfig.getBaseWebpathForImgResources()` and `AbstractReportConfig.getBaseClasspathForImgResources()`.

In the report template (.jrxml) the location of image resources should be parameterized, so depending on the export format the correct location for the image can be resolved. Therefore, while implementing `AbstractReportConfig.imageMapping()`, the first argument of the `mapping` should be the parameter name of the image location in the report template and the second argument should be the file name. An implementation `ReportImageResolverImpl` for `ReportImageResolver` interface has been provided in the infrastructure layer. This can be used in associated with `SetImageMappingsTask` to resolve the correct location of the images based on the export format and set them as report parameters at runtime.

### 3.2.5. Report Dependency Injection Configuration (ReportDIConfig interface)

An implementation of `ReportDIConfig` interface is required to manage the report dependencies. The proper place for this implementation is `gov.samhsa.pcm.web.config.di.root.report` package in the web project. The methods of this interface can be seen as a list of beans that will be required to configure a report. A sample implementation of it can be as below. Please pay attention to the beans that has explicit names. These names are important, because they will be referred by the report controller.



	package gov.samhsa.pcm.web.config.di.root.report;
	
	import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
	import gov.samhsa.pcm.infrastructure.report.JdbcTemplateReportDataProvider;
	import gov.samhsa.pcm.infrastructure.report.ReportDIConfig;
	import gov.samhsa.pcm.infrastructure.report.ReportDataProvider;
	import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerChainBuilder;
	import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerTask;
	import gov.samhsa.pcm.infrastructure.report.ReportViewFactory;
	import gov.samhsa.pcm.infrastructure.report.SqlScriptProvider;
	import gov.samhsa.pcm.infrastructure.report.configurer.OnlyPaginatePdfTask;
	import gov.samhsa.pcm.infrastructure.report.configurer.SetDatasourceKeyTask;
	import gov.samhsa.pcm.infrastructure.report.configurer.SetExportFormatTask;
	import gov.samhsa.pcm.infrastructure.report.configurer.SetImageMappingsTask;
	import gov.samhsa.pcm.service.report.ClasspathSqlScriptProvider;
	import gov.samhsa.pcm.service.report.TestReportRowMapper;
	import gov.samhsa.pcm.web.config.report.TestReportConfig;
	
	import java.util.List;
	import java.util.Optional;
	import java.util.function.Supplier;
	
	import javax.servlet.ServletContext;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.jdbc.core.JdbcTemplate;
	import org.springframework.jdbc.core.RowMapper;
	import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
	
	@Configuration
	public class TestReportDIConfig implements ReportDIConfig {
	
		@Autowired
		private OnlyPaginatePdfTask onlyPaginatePdfTask;
	
		// required by view
		@Autowired
		private SetDatasourceKeyTask setDatasourceKeyTask;
	
		// required by view
		@Autowired
		private SetExportFormatTask setExportFormatTask;
	
		@Autowired
		private ServletContext servletContext;
	
		@Override
		@Bean(name = TestReportConfig.REPORT_NAME)
		public AbstractReportConfig reportConfig() {
			return new TestReportConfig(servletContext,
					reportParameterConfigurerChain());
		}
	
		@Override
		@Bean(name = TestReportConfig.REPORT_DATA_PROVIDER_NAME)
		public ReportDataProvider reportDataProvider(JdbcTemplate jdbcTemplate) {
			return new JdbcTemplateReportDataProvider(jdbcTemplate,
					sqlScriptProvider(), Optional.of(rowMapper()));
		}
	
		// this is a list of tasks that will be run while building the report model/parameters
		@Override
		@Bean
		public List<Supplier<ReportParameterConfigurerTask>> reportParameterConfigurerChain() {
			return ReportParameterConfigurerChainBuilder
					.add(this::getOnlyPaginatePdfTask)
					.add(this::getSetDatasourceKeyTask)
					.add(this::getSetExportFormatTask)
					// this one is required if there are any images
					.add(() -> SetImageMappingsTask.newInstance(reportConfig()))
					.build();
		}
	
		@Override
		@Bean(name = TestReportConfig.REPORT_NAME)
		public JasperReportsMultiFormatView reportView() {
			return ReportViewFactory.newJasperReportsMultiFormatView(reportConfig()
					.getReportProps());
		}
	
		@Override
		@Bean
		public RowMapper rowMapper() {
			return new TestReportRowMapper();
		}
	
		@Override
		@Bean
		public SqlScriptProvider sqlScriptProvider() {
			return new ClasspathSqlScriptProvider(reportConfig());
		}
	
		private OnlyPaginatePdfTask getOnlyPaginatePdfTask() {
			return onlyPaginatePdfTask;
		}
	
		private SetDatasourceKeyTask getSetDatasourceKeyTask() {
			return setDatasourceKeyTask;
		}
	
		private SetExportFormatTask getSetExportFormatTask() {
			return setExportFormatTask;
		}
	}


### 3.2.6. Report Controller (AbstractReportController class)

The `AbstractReportController` is provided in the infrastructure layer. It contains `ReportDataProvider` and `AbstractReportConfig` fields with a single constructor to initialize them. It also has a `reportModelAndView` method to conveniently construct and return a `ModelAndView` using the class fields. This method builds the `ModelAndView` with `viewName=reportName` value, therefore `ReportDIConfig.reportView()` bean must exactly have the same name for Spring's `BeanNameViewResolver` to forward the `ModelAndView` to this particular report's view. The proper place to implement a concrete report controller is `gov.samhsa.pcm.web.controller.report` package in the web project. A sample implementation for admin reports can be seen below. As it can be seen, Spring's `@Qualifier` annotation is used to explicitly wire the certain beans defined in the `TestReportDIConfig`. This is the main reason that these beans must have particular names that can be explicitly wired by the controller.


	package gov.samhsa.pcm.web.controller.report;
	
	import static gov.samhsa.pcm.infrastructure.report.ReportFormat.HTML;
	import static org.springframework.web.bind.annotation.RequestMethod.GET;
	import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
	import gov.samhsa.pcm.infrastructure.report.AbstractReportController;
	import gov.samhsa.pcm.infrastructure.report.ReportDataProvider;
	import gov.samhsa.pcm.infrastructure.report.ReportFormat;
	import gov.samhsa.pcm.web.config.report.ManagerReportConfig;
	import gov.samhsa.pcm.web.config.report.ReportPath;
	import gov.samhsa.pcm.web.config.report.TestReportConfig;
	
	import java.util.Optional;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.servlet.ModelAndView;
	
	@Controller
	@RequestMapping(ReportPath.ADMIN_REPORT_CONTROLLER_BASE_PATH)
	public class TestReportController extends AbstractReportController {
	
		@Autowired
		public TestReportController(
				@Qualifier(TestReportConfig.REPORT_DATA_PROVIDER_NAME) ReportDataProvider reportDataProvider,
				@Qualifier(TestReportConfig.REPORT_CONFIG_NAME) AbstractReportConfig abstractReportConfig) {
			super(reportDataProvider, abstractReportConfig);
		}
	
		@RequestMapping(method = GET, value = TestReportConfig.REPORT_NAME)
		public ModelAndView handleReportRequest(
				@RequestParam Optional<ReportFormat> format) {
			final ReportFormat reportFormat = format.orElse(HTML);
			return reportModelAndView(reportFormat,
					this.reportDataProvider::getReportData);
		}	
	}