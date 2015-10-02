package gov.samhsa.consent2share.infrastructure.report;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.samhsa.consent2share.infrastructure.report.JRDataSourceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

import org.junit.Test;

public class JRDataSourceFactoryTest {

	@Test
	public void testNewJRDataSource() throws JRException {
		// Arrange
		final DataRow dataRow = new DataRow("col1", "col2");
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final Supplier dataProvider = () -> {
			final List l = new ArrayList();
			l.add(dataRow);
			return l;
		};

		// Act
		@SuppressWarnings("unchecked")
		final JRDataSource newJRDataSource = JRDataSourceFactory
		.newJRDataSource(dataProvider);

		// Assert
		assertNotNull(newJRDataSource);
		assertTrue(newJRDataSource.next());
	}

	public class DataRow {
		private String col1;
		private String col2;

		public DataRow() {
			super();
		}

		public DataRow(String col1, String col2) {
			super();
			this.col1 = col1;
			this.col2 = col2;
		}

		public String getCol1() {
			return col1;
		}

		public String getCol2() {
			return col2;
		}

		public void setCol1(String col1) {
			this.col1 = col1;
		}

		public void setCol2(String col2) {
			this.col2 = col2;
		}

	}
}
