package gov.samhsa.c2s.pcm.service.provider.pg;

import gov.samhsa.c2s.pcm.service.dto.LookupDto;
import gov.samhsa.c2s.pcm.service.provider.ProviderSearchLookupService;
import gov.samhsa.c2s.pcm.service.reference.pg.StateCodeServicePg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProviderSearchLookupServiceImplTest {

    @Mock
    StateCodeServicePg stateCodeService;

    @InjectMocks
    ProviderSearchLookupServiceImpl providerSearchLookupServiceImpl;

    @Test
    public void testGenerateProviderSearchURL_When_State_and_City_Given() {
        ProviderSearchLookupService pss = spy(providerSearchLookupServiceImpl);
        when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
        String usstate = "MD";
        String city = "baltimore";
        String callUrl = pss.generateProviderSearchURL(usstate, city, null,
                null, null, null, null, null, null, 0);
        assertEquals(
                "providerSearchUrl/pageNumber/0/usstate/MD/city/baltimore",
                callUrl);
    }

    @Test
    public void testGenerateProviderSearchURL_When_Zipcode_Given() {
        ProviderSearchLookupService pss = spy(providerSearchLookupServiceImpl);
        when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
        String zipcode = "21046";
        String callUrl = pss.generateProviderSearchURL(null, null, zipcode,
                null, null, null, null, null, null, 0);
        assertEquals("providerSearchUrl/pageNumber/0/zipcode/21046", callUrl);
    }

    @Test
    public void testGenerateProviderSearchURL_When_Firstname_and_Lastname_Given() {
        ProviderSearchLookupService pss = spy(providerSearchLookupServiceImpl);
        when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
        String firstname = "abc";
        String lastname = "cba";
        String callUrl = pss.generateProviderSearchURL(null, null, null, null,
                null, null, firstname, lastname, null, 0);
        assertEquals(
                "providerSearchUrl/pageNumber/0/firstname/abc/lastname/cba",
                callUrl);
    }

    @Test
    public void testGenerateProviderSearchURL_When_Specialty_And_Gender_Given() {
        ProviderSearchLookupService pss = spy(providerSearchLookupServiceImpl);
        when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
        String gender = "male";
        String specialty = "dentist";
        String callUrl = pss.generateProviderSearchURL(null, null, null,
                gender, specialty, null, null, null, null, 0);
        assertEquals(
                "providerSearchUrl/pageNumber/0/gender/male/specialty/dentist",
                callUrl);
    }

    @Test
    public void testGenerateProviderSearchURL_When_Phone_Given() {
        ProviderSearchLookupService pss = spy(providerSearchLookupServiceImpl);
        when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
        String phone = "410123456";
        String callUrl = pss.generateProviderSearchURL(null, null, null, null,
                null, phone, null, null, null, 0);
        assertEquals("providerSearchUrl/pageNumber/0/phone/410123456", callUrl);
    }

    @Test
    public void testIsValidatedSearch_All_Field_Is_Blank() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch(null, null, null, null, null, null, null,
                        null, null);
        assertEquals(false, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_Only_City_Given() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch(null, "columbia", null, null, null, null,
                        null, "smith", null);
        assertEquals(false, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_State_City_Given() {
        LookupDto stateCode = mock(LookupDto.class);
        List<LookupDto> stateCodes = new ArrayList<LookupDto>();
        stateCodes.add(stateCode);
        when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);
        when(stateCode.getCode()).thenReturn("MD");
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch("MD", "columbia", null, null, null, null,
                        null, "smith", null);
        assertEquals(true, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_City_Length_Less_Than_Three() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch("MD", "columbia", null, null, null, null,
                        null, "smith", null);
        assertEquals(false, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_Zipcode_Less_Than_Five() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch(null, null, "2104", null, null, null, null,
                        "smith", null);
        assertEquals(false, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_State_And_Zipcode_Given() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch("MD", null, "21046", null, null, null, null,
                        "smith", null);
        assertEquals(false, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_Specialty_Less_Than_Three() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch(null, "columbia", null, null, "br", null,
                        null, "smith", null);
        assertEquals(false, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_Phone_Length_Not_Equal_Ten() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch("MD", "columbia", null, null, null,
                        "410552", null, "smith", null);
        assertEquals(false, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_Firstname_Less_Than_Two() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch(null, "columbia", null, null, null, null,
                        "a", "smith", null);
        assertEquals(false, validateCall);
    }

    @Test
    public void testIsValidatedSearch_When_Lastname_Less_Than_Two() {
        Boolean validateCall = providerSearchLookupServiceImpl
                .isValidatedSearch(null, "columbia", null, null, null, null,
                        null, "smith", null);
        assertEquals(false, validateCall);
    }
}
