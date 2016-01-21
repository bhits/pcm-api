package gov.samhsa.bhits.pcm.service.consent;

import gov.samhsa.bhits.pcm.domain.consent.Consent;
import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import gov.samhsa.bhits.pcm.service.dto.ConsentDto;
import gov.samhsa.bhits.pcm.service.dto.ConsentValidationDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsentCheckServiceImplTest {

    @Mock
    ConsentHelper consentHelperMock;
    @InjectMocks
    ConsentCheckServiceImpl cst;
    @Mock
    private ConsentRepository consentRepositoryMock;

    @Test
    public void testGetConflictConsent_no_overlap() {
        // Arrange
        ConsentDto consentDtoMock = mock(ConsentDto.class);
        consentDtoMock.setId("2");

        String userNameMock = "usernamemock";
        when(consentDtoMock.getUsername()).thenReturn(userNameMock);

        List<Consent> consentList = new ArrayList<Consent>();
        Consent consent = new Consent();
        consent.setId(new Long(1));
        consent.setStartDate(new Date());
        consent.setEndDate(new Date());
        consentList.add(consent);

        when(consentRepositoryMock.findAllByPatientUsername(userNameMock))
                .thenReturn(consentList);
        when(
                consentHelperMock.isConsentTermOverlap(consentDtoMock,
                        consent.getStartDate(), consent.getEndDate()))
                .thenReturn(false);
        // Act
        ConsentValidationDto consentValidationDto = cst
                .getConflictConsent(consentDtoMock);

        // Assert
        assertNull(consentValidationDto);

    }

    @Test
    public void testGetConflictConsent_no_poumatch() {
        // Arrange
        ConsentDto consentDtoMock = mock(ConsentDto.class);
        consentDtoMock.setId("2");

        String userNameMock = "usernamemock";
        when(consentDtoMock.getUsername()).thenReturn(userNameMock);

        List<Consent> consentList = new ArrayList<Consent>();
        Consent consent = new Consent();
        consent.setId(new Long(1));
        consent.setStartDate(new Date());
        consent.setEndDate(new Date());
        consentList.add(consent);

        when(consentRepositoryMock.findAllByPatientUsername(userNameMock))
                .thenReturn(consentList);
        when(
                consentHelperMock.isConsentTermOverlap(consentDtoMock,
                        consent.getStartDate(), consent.getEndDate()))
                .thenReturn(true);
        when(
                consentHelperMock.isPOUMatches(
                        consentDtoMock.getShareForPurposeOfUseCodes(),
                        consent.getShareForPurposeOfUseCodes())).thenReturn(
                false);
        // Act
        ConsentValidationDto consentValidationDto = cst
                .getConflictConsent(consentDtoMock);

        // Assert
        assertNull(consentValidationDto);

    }

    @Test
    public void testGetConflictConsent_no_providermatch() {
        // Arrange
        ConsentDto consentDtoMock = mock(ConsentDto.class);
        consentDtoMock.setId("2");

        String userNameMock = "usernamemock";
        when(consentDtoMock.getUsername()).thenReturn(userNameMock);

        List<Consent> consentList = new ArrayList<Consent>();
        Consent consent = new Consent();
        consent.setId(new Long(1));
        consent.setStartDate(new Date());
        consent.setEndDate(new Date());

        consentList.add(consent);

        when(consentRepositoryMock.findAllByPatientUsername(userNameMock))
                .thenReturn(consentList);
        when(
                consentHelperMock.isConsentTermOverlap(consentDtoMock,
                        consent.getStartDate(), consent.getEndDate()))
                .thenReturn(true);
        when(
                consentHelperMock.isPOUMatches(
                        consentDtoMock.getShareForPurposeOfUseCodes(),
                        consent.getShareForPurposeOfUseCodes())).thenReturn(
                true);
        when(consentHelperMock.isProviderComboMatch(consent, consentDtoMock))
                .thenReturn(false);
        // Act
        ConsentValidationDto consentValidationDto = cst
                .getConflictConsent(consentDtoMock);

        // Assert
        assertNull(consentValidationDto);

    }

    @Test
    public void testGetConflictConsent_no_consentrevokedmatch() {
        // Arrange
        ConsentDto consentDtoMock = mock(ConsentDto.class);
        consentDtoMock.setId("2");

        String userNameMock = "usernamemock";
        when(consentDtoMock.getUsername()).thenReturn(userNameMock);

        List<Consent> consentList = new ArrayList<Consent>();
        Consent consent = new Consent();
        consent.setId(new Long(1));
        consent.setStartDate(new Date());
        consent.setEndDate(new Date());
        consentList.add(consent);

        when(consentRepositoryMock.findAllByPatientUsername(userNameMock))
                .thenReturn(consentList);
        when(
                consentHelperMock.isConsentTermOverlap(consentDtoMock,
                        consent.getStartDate(), consent.getEndDate()))
                .thenReturn(true);
        when(
                consentHelperMock.isPOUMatches(
                        consentDtoMock.getShareForPurposeOfUseCodes(),
                        consent.getShareForPurposeOfUseCodes())).thenReturn(
                true);
        when(consentHelperMock.isProviderComboMatch(consent, consentDtoMock))
                .thenReturn(true);
        when(consentHelperMock.isConsentRevoked(consent)).thenReturn(true);

        // Act
        ConsentValidationDto consentValidationDto = cst
                .getConflictConsent(consentDtoMock);

        // Assert
        assertNull(consentValidationDto);

    }

    @Test
    public void testGetConflictConsent_editconsent() {
        // Arrange
        ConsentDto consentDto = new ConsentDto();
        consentDto.setId("2");
        consentDto.setUsername("usernamemock");

        List<Consent> consentList = new ArrayList<Consent>();
        Consent consent = new Consent();
        consent.setId(new Long(2));
        consent.setStartDate(new Date());
        consent.setEndDate(new Date());

        consentList.add(consent);

        when(consentRepositoryMock.findAllByPatientUsername("usernamemock"))
                .thenReturn(consentList);

        // Act
        ConsentValidationDto consentValidationDto = cst
                .getConflictConsent(consentDto);

        // Assert
        assertNull(consentValidationDto);

    }

    @Test
    public void testGetConflictConsent_allmatch() {
        // Arrange
        ConsentDto consentDtoMock = mock(ConsentDto.class);
        consentDtoMock.setId("2");

        String userNameMock = "usernamemock";
        when(consentDtoMock.getUsername()).thenReturn(userNameMock);

        List<Consent> consentList = new ArrayList<Consent>();
        Consent consent = new Consent();
        consent.setId(new Long(1));
        consent.setStartDate(new Date());
        consent.setEndDate(new Date());
        consentList.add(consent);

        when(consentRepositoryMock.findAllByPatientUsername(userNameMock))
                .thenReturn(consentList);
        when(
                consentHelperMock.isConsentTermOverlap(consentDtoMock,
                        consent.getStartDate(), consent.getEndDate()))
                .thenReturn(true);
        when(
                consentHelperMock.isPOUMatches(
                        consentDtoMock.getShareForPurposeOfUseCodes(),
                        consent.getShareForPurposeOfUseCodes())).thenReturn(
                true);
        when(consentHelperMock.isProviderComboMatch(consent, consentDtoMock))
                .thenReturn(true);
        when(consentHelperMock.isConsentRevoked(consent)).thenReturn(false);

        ConsentValidationDto consentValidationDtoMock = new ConsentValidationDto();
        when(
                consentHelperMock.convertConsentToConsentListDto(consent,
                        consentDtoMock)).thenReturn(consentValidationDtoMock);

        // Act
        ConsentValidationDto consentValidationDto = cst
                .getConflictConsent(consentDtoMock);

        // Assert
        assertEquals(consentValidationDtoMock, consentValidationDto);

    }

}
