package gov.samhsa.consent2share.web.config.report;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import gov.samhsa.consent2share.infrastructure.report.AbstractReportConfig;
import gov.samhsa.consent2share.infrastructure.report.ReportFormat;
import gov.samhsa.consent2share.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.consent2share.infrastructure.report.ReportProps;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRDataSource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

@RunWith(MockitoJUnitRunner.class)
public class ReportConfigUniqueBeanNamesTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testAll() {
		final List<String> configNames = getAbstractReportConfigSubclassesAsStream()
				.map(AbstractReportConfig::getReportConfigName).collect(
						toList());
		final List<String> reportNames = getAbstractReportConfigSubclassesAsStream()
				.map(AbstractReportConfig::getReportName).collect(toList());
		final List<String> reportDataProviders = getAbstractReportConfigSubclassesAsStream()
				.map(AbstractReportConfig::getReportDataProviderName).collect(
						toList());
		assertEquals("All report bean names must be unique",
				Arrays.asList(configNames, reportNames, reportDataProviders)
						.stream().flatMap(List::stream).distinct().count(),
				Arrays.asList(configNames, reportNames, reportDataProviders)
						.stream().flatMap(List::stream).count());
	}

	@Test
	public void testGetReportConfigName() {
		assertEquals(
				"Duplicate report config names found, report config names must be unique!",
				getAbstractReportConfigSubclassesAsStream()
				.map(AbstractReportConfig::getReportConfigName)
				.distinct().count(),
				getAbstractReportConfigSubclassesAsStream().map(
						AbstractReportConfig::getReportConfigName).count());
	}

	@Test
	public void testGetReportDataProviderName() {
		assertEquals(
				"Duplicate report data provider names found, report data provider names must be unique!",
				getAbstractReportConfigSubclassesAsStream()
				.map(AbstractReportConfig::getReportDataProviderName)
				.distinct().count(),
				getAbstractReportConfigSubclassesAsStream().map(
						AbstractReportConfig::getReportDataProviderName)
						.count());
	}

	@Test
	public void testGetReportName() {
		assertEquals(
				"Duplicate report names found, report names must be unique!",
				getAbstractReportConfigSubclassesAsStream()
				.map(AbstractReportConfig::getReportName).distinct()
				.count(), getAbstractReportConfigSubclassesAsStream()
				.map(AbstractReportConfig::getReportName).count());
	}

	private Stream<AbstractReportConfig> getAbstractReportConfigSubclassesAsStream() {
		// create scanner and disable default filters (that is the 'false'
		// argument)
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);
		// add include filters which matches all the classes (or use your own)
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern
				.compile(".*")));

		// get matching classes defined in the package
		final String package1 = ManagerReportConfig.class.getPackage()
				.getName();
		final Set<BeanDefinition> classes = provider
				.findCandidateComponents(package1);
		return classes
				.stream()
				.map(this::getAsClass)
				.filter(clazz -> clazz.getSuperclass() == AbstractReportConfig.class)
				.map(this::getAsAbstractReportConfig);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AbstractReportConfig getAsAbstractReportConfig(Class clazz) {
		try {
			final List<Supplier<ReportParameterConfigurerTask>> list = Arrays
					.asList(() -> new ReportParameterConfigurerTaskImpl());
			return (AbstractReportConfig) clazz.getConstructor(
					ServletContext.class, List.class).newInstance(null, list);

		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	private Class getAsClass(BeanDefinition bean) {
		try {
			return Class.forName(bean.getBeanClassName());
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public class ReportParameterConfigurerTaskImpl implements
			ReportParameterConfigurerTask {

		@Override
		public Map<String, Object> configure(ReportProps reportProps,
				ReportFormat reportFormat, JRDataSource datasource) {
			return null;
		}
	}
}
