package gov.samhsa.consent2share.service.admin;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.consent2share.domain.staff.Staff;
import gov.samhsa.consent2share.domain.staff.StaffRepository;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdminProfileDtoToAdministratorMapperTest {

	@Mock
	StaffRepository providerAdminRepository;

	@Mock
	AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	@InjectMocks
	AdminProfileDtoToAdministratorMapper sut;

	@Test
	public void testMap_Given_Null_Username_And_Null_Admin_Id() {
		// Arrange
		AdminProfileDto adminProfileDto = mock(AdminProfileDto.class);
		when(adminProfileDto.getUsername()).thenReturn(null);
		when(adminProfileDto.getId()).thenReturn(null);

		final String firstName = "Firstname";
		when(adminProfileDto.getFirstName()).thenReturn(firstName);

		// Act
		Staff administrator = sut.map(adminProfileDto);

		// Assert
		assertEquals(firstName, administrator.getFirstName());
	}

	@Test
	public void testMap_Given_Username() {
		// Arrange
		final String username = "username";
		AdminProfileDto adminProfileDto = mock(AdminProfileDto.class);
		when(adminProfileDto.getUsername()).thenReturn(username);

		Staff administrator = mock(Staff.class);
		when(providerAdminRepository.findByUsername(username)).thenReturn(
				administrator);

		final String firstName = "Firstname";
		when(adminProfileDto.getFirstName()).thenReturn(firstName);

		// Act
		Staff result = sut.map(adminProfileDto);

		// Assert
		assertEquals(administrator, result);
	}

	@Test
	public void test_General_Case() {
		final String username = "username";
		AdminProfileDto adminProfileDto = mock(AdminProfileDto.class);
		when(adminProfileDto.getUsername()).thenReturn(username);
		when(adminProfileDto.getEmail()).thenReturn("consent2share@gmail.com");

		Staff administrator = mock(Staff.class);
		when(providerAdminRepository.findByUsername(username)).thenReturn(
				administrator);
		final String firstName = "Firstname";
		when(adminProfileDto.getFirstName()).thenReturn(firstName);
		when(adminProfileDto.getAdministrativeGenderCode()).thenReturn("M");
		Staff result = sut.map(adminProfileDto);
		assertEquals(administrator, result);
	}

}
