package gov.samhsa.consent2share.service.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.service.dto.ManagerReportEntryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ManagerReportRowMapperTest {

	@InjectMocks
	private ManagerReportRowMapper sut;

	@Test
	public void testMapRow() throws SQLException {
		// Arrange
		final ResultSet rs = mock(ResultSet.class);
		final Long revtstmp = 1427736147200L;
		when(rs.getLong("revtstmp")).thenReturn(revtstmp);
		final Long active_account_date_time = 1427736147200L;
		when(rs.getLong("active_account_date_time")).thenReturn(active_account_date_time);
		final String admin_first_name = "admin_first_name";
		when(rs.getString(admin_first_name)).thenReturn(admin_first_name);
		final String patient_first_name = "patient_first_name";
		when(rs.getString(patient_first_name)).thenReturn(patient_first_name);
		final String patient_user_name = "patient_user_name";
		when(rs.getString(patient_user_name)).thenReturn(patient_user_name);
		final String admin_last_name = "admin_last_name";
		when(rs.getString(admin_last_name)).thenReturn(admin_last_name);
		final String code = "code";
		when(rs.getString(code)).thenReturn(code);
		final String patient_last_name = "patient_last_name";
		when(rs.getString(patient_last_name)).thenReturn(patient_last_name);
		final String medical_record_number = "medical_record_number";
		when(rs.getString(medical_record_number)).thenReturn(
				medical_record_number);
		final Integer num_of_signed_consents = 10;
		when(rs.getInt("num_of_signed_consents")).thenReturn(num_of_signed_consents);
		final String employee_id = "employee_id";
		when(rs.getString(employee_id)).thenReturn(employee_id);
		final Integer num_of_unsigned_consents = 3;
		when(rs.getInt("num_of_unsigned_consents")).thenReturn(num_of_unsigned_consents);
		final Integer num_of_effective_consents = 4;
		when(rs.getInt("num_of_effective_consents")).thenReturn(num_of_effective_consents);
		final Integer num_of_revoked_consents = 5;
		when(rs.getInt("num_of_revoked_consents")).thenReturn(num_of_revoked_consents);
		final Integer num_of_expired_consents = 6;
		when(rs.getInt("num_of_expired_consents")).thenReturn(num_of_expired_consents);

		// Act
		final ManagerReportEntryDto row = sut.mapRow(rs, 0);

		// Assert
		assertTrue(row.getAccountCreatedDate() != null);
		assertTrue(row.getAccountCreatedDate() instanceof Date);
		assertTrue(row.getActiveAccountDateTime() instanceof Date);
		assertEquals(admin_first_name + " " + admin_last_name,
				row.getCreatedByStaffAdministrator());
		assertEquals(patient_first_name, row.getPatientFirstName());
		assertEquals(code, row.getPatientGender());
		assertEquals(patient_last_name, row.getPatientLastName());
		assertEquals(patient_user_name, row.getPatientUsername());
		assertEquals(medical_record_number, row.getPatientMrn());
		assertEquals(num_of_signed_consents.intValue(), row.getNumOfSignedConsents());
		assertEquals(employee_id, row.getStaffAdministratorLocation());
		assertEquals(num_of_unsigned_consents.intValue(), row.getNumOfUnsignedConsents());
		assertEquals(num_of_effective_consents.intValue(), row.getNumOfEffectiveConsents());
		assertEquals(num_of_revoked_consents.intValue(), row.getNumOfRevokedConsents());
		assertEquals(num_of_expired_consents.intValue(), row.getNumOfExpiredConsents());
	}
}
