package gov.samhsa.pcm.infrastructure.report.configurer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
import gov.samhsa.pcm.infrastructure.report.ReportFormat;
import gov.samhsa.pcm.infrastructure.report.ReportImageResolver;
import gov.samhsa.pcm.infrastructure.report.ReportImageResolverImpl;
import gov.samhsa.pcm.infrastructure.report.ReportProps;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import gov.samhsa.pcm.infrastructure.report.configurer.SetImageMappingsTask;
import net.sf.jasperreports.engine.JRDataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class SetImageMappingsTaskTest {
	@Mock
	private ReportProps reportProps;

	@Mock
	private JRDataSource datasource;

	@Mock
	private Supplier<ReportImageResolver> reportImageResolverSupplier;

	@Mock
	private ReportImageResolverImpl reportImageResolver;

	@InjectMocks
	private SetImageMappingsTask sut;

	@Test
	public void testConfigure_CSV() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.CSV;
		final Map<String, String> map = new HashMap<>();
		final String param1 = "param1";
		final String param2 = "param2";
		final String image1 = "image1";
		final String image2 = "image2";
		final String webpath1 = "webpath1";
		final String webpath2 = "webpath2";
		final String classpath1 = "classpath1";
		final String classpath2 = "classpath2";
		map.put(param1, image1);
		map.put(param2, image2);
		when(reportProps.getImageMapping()).thenReturn(Optional.of(map));
		when(reportImageResolverSupplier.get()).thenReturn(reportImageResolver);
		when(reportImageResolver.webpath(image1)).thenReturn(webpath1);
		when(reportImageResolver.webpath(image2)).thenReturn(webpath2);
		when(reportImageResolver.classpath(image1)).thenReturn(classpath1);
		when(reportImageResolver.classpath(image2)).thenReturn(classpath2);

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(2, configure.size());
		assertEquals(classpath1, configure.get(param1));
		assertEquals(classpath2, configure.get(param2));
	}

	@Test
	public void testConfigure_HTML() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.HTML;
		final Map<String, String> map = new HashMap<>();
		final String param1 = "param1";
		final String param2 = "param2";
		final String image1 = "image1";
		final String image2 = "image2";
		final String webpath1 = "webpath1";
		final String webpath2 = "webpath2";
		final String classpath1 = "classpath1";
		final String classpath2 = "classpath2";
		map.put(param1, image1);
		map.put(param2, image2);
		when(reportProps.getImageMapping()).thenReturn(Optional.of(map));
		when(reportImageResolverSupplier.get()).thenReturn(reportImageResolver);
		when(reportImageResolver.webpath(image1)).thenReturn(webpath1);
		when(reportImageResolver.webpath(image2)).thenReturn(webpath2);
		when(reportImageResolver.classpath(image1)).thenReturn(classpath1);
		when(reportImageResolver.classpath(image2)).thenReturn(classpath2);

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(2, configure.size());
		assertEquals(webpath1, configure.get(param1));
		assertEquals(webpath2, configure.get(param2));
	}

	@Test
	public void testConfigure_PDF() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.PDF;
		final Map<String, String> map = new HashMap<>();
		final String param1 = "param1";
		final String param2 = "param2";
		final String image1 = "image1";
		final String image2 = "image2";
		final String webpath1 = "webpath1";
		final String webpath2 = "webpath2";
		final String classpath1 = "classpath1";
		final String classpath2 = "classpath2";
		map.put(param1, image1);
		map.put(param2, image2);
		when(reportProps.getImageMapping()).thenReturn(Optional.of(map));
		when(reportImageResolverSupplier.get()).thenReturn(reportImageResolver);
		when(reportImageResolver.webpath(image1)).thenReturn(webpath1);
		when(reportImageResolver.webpath(image2)).thenReturn(webpath2);
		when(reportImageResolver.classpath(image1)).thenReturn(classpath1);
		when(reportImageResolver.classpath(image2)).thenReturn(classpath2);

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(2, configure.size());
		assertEquals(classpath1, configure.get(param1));
		assertEquals(classpath2, configure.get(param2));
	}

	@Test
	public void testConfigure_XLS() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.XLS;
		final Map<String, String> map = new HashMap<>();
		final String param1 = "param1";
		final String param2 = "param2";
		final String image1 = "image1";
		final String image2 = "image2";
		final String webpath1 = "webpath1";
		final String webpath2 = "webpath2";
		final String classpath1 = "classpath1";
		final String classpath2 = "classpath2";
		map.put(param1, image1);
		map.put(param2, image2);
		when(reportProps.getImageMapping()).thenReturn(Optional.of(map));
		when(reportImageResolverSupplier.get()).thenReturn(reportImageResolver);
		when(reportImageResolver.webpath(image1)).thenReturn(webpath1);
		when(reportImageResolver.webpath(image2)).thenReturn(webpath2);
		when(reportImageResolver.classpath(image1)).thenReturn(classpath1);
		when(reportImageResolver.classpath(image2)).thenReturn(classpath2);

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(2, configure.size());
		assertEquals(classpath1, configure.get(param1));
		assertEquals(classpath2, configure.get(param2));
	}

	@Test
	public void testNewInstance() {
		// Arrange
		final AbstractReportConfig reportConfig = mock(AbstractReportConfig.class);
		when(reportConfig.getReportImageResolver()).thenReturn(
				Optional.of(reportImageResolver));

		// Act
		final SetImageMappingsTask newInstance = SetImageMappingsTask
				.newInstance(reportConfig);

		// Assert
		@SuppressWarnings("unchecked")
		final Supplier<ReportImageResolver> field = (Supplier<ReportImageResolver>) ReflectionTestUtils
				.getField(newInstance, "reportImageResolverSupplier");
		assertEquals(reportImageResolver, field.get());
	}

}
