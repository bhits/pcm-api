package gov.samhsa.pcm.service.report;

import static java.util.stream.Collectors.joining;
import gov.samhsa.pcm.service.dto.ManagerReportEntryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

/**
 * {@link RowMapper} implementation for ManagerReport.
 */
public class ManagerReportRowMapper implements RowMapper<ManagerReportEntryDto> {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 * int)
	 */
	@Override
	public ManagerReportEntryDto mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		final ManagerReportEntryDto entry = new ManagerReportEntryDto();
		entry.setAccountCreatedDate(convertTimestamptoDate(rs
				.getLong("revtstmp")));
		entry.setCreatedByStaffAdministrator(createStaffAdministratorNameFromResultSet(rs));
		entry.setPatientFirstName(rs.getString("patient_first_name"));
		entry.setPatientGender(rs.getString("code"));
		entry.setPatientBirthDay(rs.getDate("birth_day"));
		entry.setActiveAccountDateTime(convertTimestamptoDate(rs.getLong("active_account_date_time")));
		entry.setConsentInitialDateTime(rs.getDate("consent_initial_date_time"));
		entry.setPatientLastName(rs.getString("patient_last_name"));
		entry.setPatientMrn(rs.getString("medical_record_number"));
		entry.setPatientUsername(rs.getString("patient_user_name"));
		entry.setNumOfSignedConsents(rs.getInt("num_of_signed_consents"));
		entry.setStaffAdministratorLocation(rs.getString("employee_id"));
		entry.setNumOfUnsignedConsents(rs.getInt("num_of_unsigned_consents"));
		entry.setNumOfEffectiveConsents(rs.getInt("num_of_effective_consents"));
		entry.setNumOfRevokedConsents(rs.getInt("num_of_revoked_consents"));
		entry.setNumOfExpiredConsents(rs.getInt("num_of_expired_consents"));
		return entry;
	}

	/**
	 * Creates the staff administrator name from result set.
	 *
	 * @param rs
	 *            the rs
	 * @return the string
	 * @throws SQLException
	 *             the SQL exception
	 */
	private String createStaffAdministratorNameFromResultSet(ResultSet rs)
			throws SQLException {
		return Arrays
				.asList(Optional.ofNullable(rs.getString("admin_first_name")),
						Optional.ofNullable(rs.getString("admin_last_name")))
				.stream().filter(Optional::isPresent).map(Optional::get)
				.collect(joining(" "));
	}

	/**
	 * Convert {@code long timestamp} to {@link Date}.
	 *
	 * @param revtstmp
	 *            the revtstmp
	 * @return the date
	 */
	private static Date convertTimestamptoDate(long revtstmp) {
		if (revtstmp == 0) {
			return null;
		} else {
			final Timestamp timestamp = new Timestamp(revtstmp);
			final long milliseconds = timestamp.getTime()
					+ timestamp.getNanos() / 1000000;
			return new Date(milliseconds);
		}
	}

}
