package gov.samhsa.mhc.pcm.service.consent.pg;

import java.util.Set;

import gov.samhsa.mhc.pcm.service.dto.ConsentDto;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsentOneToOneAssertionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private ConsentOneToOneAssertion sut;

    @Test
    public void testAssertConsentDto_Fail_With_To_Ind_To_Org_From_Ind_From_Org() {
        thrown.expect(IllegalArgumentException.class);
        // To Org
        Set<String> mockOrganizationalProvidersDisclosureIsMadeTo = Sets
                .newSet("mockOrganizationalProvidersDisclosureIsMadeTo");
        // To Ind
        Set<String> mockProvidersDisclosureIsMadeTo = Sets
                .newSet("mockProvidersDisclosureIsMadeTo");
        // From Org
        Set<String> mockOrganizationalProvidersPermittedToDisclose = Sets
                .newSet("mockOrganizationalProvidersPermittedToDisclose");
        // From Ind
        Set<String> mockProvidersPermittedToDisclose = Sets
                .newSet("mockProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();
        mockConsentDto
                .setOrganizationalProvidersDisclosureIsMadeTo(mockOrganizationalProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setProvidersDisclosureIsMadeTo(mockProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setOrganizationalProvidersPermittedToDisclose(mockOrganizationalProvidersPermittedToDisclose);
        mockConsentDto
                .setProvidersPermittedToDisclose(mockProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }

    @Test
    public void testAssertConsentDto_Fail_With_To_Org_From_Ind_From_Org() {
        thrown.expect(IllegalArgumentException.class);
        // To Org
        Set<String> mockOrganizationalProvidersDisclosureIsMadeTo = Sets
                .newSet("mockOrganizationalProvidersDisclosureIsMadeTo");
        // From Org
        Set<String> mockOrganizationalProvidersPermittedToDisclose = Sets
                .newSet("mockOrganizationalProvidersPermittedToDisclose");
        // From Ind
        Set<String> mockProvidersPermittedToDisclose = Sets
                .newSet("mockProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();
        mockConsentDto
                .setOrganizationalProvidersDisclosureIsMadeTo(mockOrganizationalProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setOrganizationalProvidersPermittedToDisclose(mockOrganizationalProvidersPermittedToDisclose);
        mockConsentDto
                .setProvidersPermittedToDisclose(mockProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }

    @Test
    public void testAssertConsentDto_Fail_With_To_Ind_From_Ind_From_Org() {
        thrown.expect(IllegalArgumentException.class);
        // To Ind
        Set<String> mockProvidersDisclosureIsMadeTo = Sets
                .newSet("mockProvidersDisclosureIsMadeTo");
        // From Org
        Set<String> mockOrganizationalProvidersPermittedToDisclose = Sets
                .newSet("mockOrganizationalProvidersPermittedToDisclose");
        // From Ind
        Set<String> mockProvidersPermittedToDisclose = Sets
                .newSet("mockProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();
        mockConsentDto
                .setProvidersDisclosureIsMadeTo(mockProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setOrganizationalProvidersPermittedToDisclose(mockOrganizationalProvidersPermittedToDisclose);
        mockConsentDto
                .setProvidersPermittedToDisclose(mockProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }

    @Test
    public void testAssertConsentDto_Fail_With_To_Ind_To_Org_From_Org() {
        thrown.expect(IllegalArgumentException.class);
        // To Org
        Set<String> mockOrganizationalProvidersDisclosureIsMadeTo = Sets
                .newSet("mockOrganizationalProvidersDisclosureIsMadeTo");
        // To Ind
        Set<String> mockProvidersDisclosureIsMadeTo = Sets
                .newSet("mockProvidersDisclosureIsMadeTo");
        // From Org
        Set<String> mockOrganizationalProvidersPermittedToDisclose = Sets
                .newSet("mockOrganizationalProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();
        mockConsentDto
                .setOrganizationalProvidersDisclosureIsMadeTo(mockOrganizationalProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setProvidersDisclosureIsMadeTo(mockProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setOrganizationalProvidersPermittedToDisclose(mockOrganizationalProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }

    @Test
    public void testAssertConsentDto_Fail_With_To_Ind_To_Org_From_Ind() {
        thrown.expect(IllegalArgumentException.class);
        // To Org
        Set<String> mockOrganizationalProvidersDisclosureIsMadeTo = Sets
                .newSet("mockOrganizationalProvidersDisclosureIsMadeTo");
        // To Ind
        Set<String> mockProvidersDisclosureIsMadeTo = Sets
                .newSet("mockProvidersDisclosureIsMadeTo");
        // From Ind
        Set<String> mockProvidersPermittedToDisclose = Sets
                .newSet("mockProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();
        mockConsentDto
                .setOrganizationalProvidersDisclosureIsMadeTo(mockOrganizationalProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setProvidersDisclosureIsMadeTo(mockProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setProvidersPermittedToDisclose(mockProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }

    @Test
    public void testAssertConsentDto_Success_With_To_Ind_From_Org() {
        // To Ind
        Set<String> mockProvidersDisclosureIsMadeTo = Sets
                .newSet("mockProvidersDisclosureIsMadeTo");
        // From Org
        Set<String> mockOrganizationalProvidersPermittedToDisclose = Sets
                .newSet("mockOrganizationalProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();

        mockConsentDto
                .setProvidersDisclosureIsMadeTo(mockProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setOrganizationalProvidersPermittedToDisclose(mockOrganizationalProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }

    @Test
    public void testAssertConsentDto_Success_With_To_Org_From_Org() {
        // To Org
        Set<String> mockOrganizationalProvidersDisclosureIsMadeTo = Sets
                .newSet("mockOrganizationalProvidersDisclosureIsMadeTo");
        // From Org
        Set<String> mockOrganizationalProvidersPermittedToDisclose = Sets
                .newSet("mockOrganizationalProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();
        mockConsentDto
                .setOrganizationalProvidersDisclosureIsMadeTo(mockOrganizationalProvidersDisclosureIsMadeTo);

        mockConsentDto
                .setOrganizationalProvidersPermittedToDisclose(mockOrganizationalProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }

    @Test
    public void testAssertConsentDto_Success_With_To_Org_From_Ind() {
        // To Org
        Set<String> mockOrganizationalProvidersDisclosureIsMadeTo = Sets
                .newSet("mockOrganizationalProvidersDisclosureIsMadeTo");
        // From Ind
        Set<String> mockProvidersPermittedToDisclose = Sets
                .newSet("mockProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();
        mockConsentDto
                .setOrganizationalProvidersDisclosureIsMadeTo(mockOrganizationalProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setProvidersPermittedToDisclose(mockProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }

    @Test
    public void testAssertConsentDto_Success_With_To_Ind_From_Ind() {
        // To Ind
        Set<String> mockProvidersDisclosureIsMadeTo = Sets
                .newSet("mockProvidersDisclosureIsMadeTo");

        // From Ind
        Set<String> mockProvidersPermittedToDisclose = Sets
                .newSet("mockProvidersPermittedToDisclose");
        ConsentDto mockConsentDto = new ConsentDto();
        mockConsentDto
                .setProvidersDisclosureIsMadeTo(mockProvidersDisclosureIsMadeTo);
        mockConsentDto
                .setProvidersPermittedToDisclose(mockProvidersPermittedToDisclose);
        sut.assertConsentDto(mockConsentDto);
    }
}
