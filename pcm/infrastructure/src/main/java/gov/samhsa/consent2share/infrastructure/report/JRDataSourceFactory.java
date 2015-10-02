package gov.samhsa.consent2share.infrastructure.report;

import java.util.Collection;
import java.util.function.Supplier;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * A factory for creating JRDataSource objects.
 */
public class JRDataSourceFactory {

	/**
	 * This class cannot be instantiated.
	 */
	private JRDataSourceFactory() {
	}

	/**
	 * Factory method to create a new {@link JRDataSource} instance from a
	 * {@code Supplier<? extends Collection<T>>} type.
	 *
	 * @param <T>
	 *            the generic type
	 * @param dataProvider
	 *            the data provider
	 * @return the JR data source
	 */
	public static final <T> JRDataSource newJRDataSource(
			Supplier<? extends Collection<T>> dataProvider) {
		return new JRBeanCollectionDataSource(dataProvider.get());
	}
}
