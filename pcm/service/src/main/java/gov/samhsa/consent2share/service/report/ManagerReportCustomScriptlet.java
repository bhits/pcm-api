package gov.samhsa.consent2share.service.report;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 * The Class ManagerReportCustomScriptlet.
 */
public class ManagerReportCustomScriptlet extends JRDefaultScriptlet {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Constant MILLISTODAY. */
	private static final double MILLISTODAY = 1000 * 60 * 60 * 24;

	/**
	 * Diff days creation and acvtivation.
	 *
	 * @return the double
	 */
	public double diffDaysCreationAndAcvtivation() {

		return diffDays("accountCreatedDate", "activeAccountDateTime");
	}

	/**
	 * Diff days creation and initial.
	 *
	 * @return the double
	 */
	public double diffDaysCreationAndInitial() {

		return diffDays("accountCreatedDate", "consentInitialDateTime");
	}

	/**
	 * Diff days.
	 *
	 * @param startFieldValue
	 *            the start field value
	 * @param endFieldValue
	 *            the end field value
	 * @return the double
	 */
	public double diffDays(String startFieldValue, String endFieldValue) {

		double diffDays = 0.0;

		try {
			Date endDate = (Date) this.getFieldValue(endFieldValue);
			Date startDate = (Date) this.getFieldValue(startFieldValue);

			if (endDate != null) {
				long diffMillis = endDate.getTime() - startDate.getTime();

				diffDays = new Long(diffMillis).doubleValue() / MILLISTODAY;
			}
		} catch (JRScriptletException e) {
			logger.error(EXCEPTION_MESSAGE_KEY_FIELD_NOT_FOUND);
		}
		return diffDays;
	}

}
