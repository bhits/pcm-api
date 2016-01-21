package gov.samhsa.bhits.pcm.service.consent;

import gov.samhsa.bhits.pcm.domain.consent.*;
import gov.samhsa.bhits.pcm.domain.provider.IndividualProvider;
import gov.samhsa.bhits.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.bhits.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.bhits.pcm.service.dto.ConsentDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConsentHelperTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    /**
     * The cst.
     */
    @InjectMocks
    ConsentHelper cst = new ConsentHelper();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    //actual date range
    Date conStartDate = null;
    Date conEndDate = null;


    ConsentDto consentDto = new ConsentDto();

    @Before
    public void setUp() throws Exception {
        //actual date range
        conStartDate = (Date) dateFormat.parse("06/12/2013 00:00:00");
        conEndDate = (Date) dateFormat.parse("07/18/2013 23:59:59");

    }

    @Test
    public void testIsConsentTermOverlap_samerange() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("06/12/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("07/18/2013 22:59:59"));
        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }

    @Test
    public void testIsConsentTermOverlap_same_start_date() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("06/12/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("07/18/2014 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }

    @Test
    public void testIsConsentTermOverlap_same_start_date_ltend() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("06/12/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("07/14/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }

    @Test
    public void testIsConsentTermOverlap_same_start_date_enddate() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("06/12/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("06/12/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }

    @Test
    public void testIsConsentTermOverlap_start_date_enddate() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("07/18/2013 23:59:59"));
        consentDto.setConsentEnd((Date) dateFormat.parse("07/19/2014 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }

    @Test
    public void testIsConsentTermOverlap_same_end_date() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("07/12/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("07/18/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }


    @Test
    public void testIsConsentTermOverlap_false_selendLTconstart() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("05/12/2012 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("06/11/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, false);

    }

    @Test
    public void testIsConsentTermOverlap_true_selendGTconstart() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("05/12/2012 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("06/13/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }

    @Test
    public void testIsConsentTermOverlap_true_selendGTEconstartAndconendGTEselstart() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("06/12/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("07/18/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }

    @Test
    public void testIsConsentTermOverlap_true_selendGTconstartAndconendGTselstart() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("06/14/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("07/17/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }

    @Test
    public void testIsConsentTermOverlap_false_conendLTselstart() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("07/19/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("11/18/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, false);

    }

    @Test
    public void testIsConsentTermOverlap_true_conendGTselstart() throws ParseException {

        // Arrange
        //selected date range
        consentDto.setConsentStart((Date) dateFormat.parse("07/17/2013 00:00:00"));
        consentDto.setConsentEnd((Date) dateFormat.parse("11/18/2013 22:59:59"));

        //Act
        boolean isOverlap = cst.isConsentTermOverlap(consentDto, conStartDate, conEndDate);

        //Assert
        assertEquals(isOverlap, true);

    }


    //pou match check

    @Test
    public void testIsPOUMatches() {

        //Arrange
        Set<String> selPOU = new HashSet<String>();
        selPOU.add("TREAT");

        Set<ConsentShareForPurposeOfUseCode> conPOU = new HashSet<ConsentShareForPurposeOfUseCode>();
        PurposeOfUseCode pCode = new PurposeOfUseCode();
        pCode.setCode("TREAT");
        ConsentShareForPurposeOfUseCode cpCode = new ConsentShareForPurposeOfUseCode(pCode);
        conPOU.add(cpCode);

        //Act
        boolean isPouMatch = cst.isPOUMatches(selPOU, conPOU);


        //Assert
        assertEquals(isPouMatch, true);

    }

    @Test
    public void testIsPOUMatches_false() {

        //Arrange
        Set<String> selPOU = new HashSet<String>();
        selPOU.add("TREAT");

        Set<ConsentShareForPurposeOfUseCode> conPOU = new HashSet<ConsentShareForPurposeOfUseCode>();
        PurposeOfUseCode pCode = new PurposeOfUseCode();
        pCode.setCode("ETREAT");
        ConsentShareForPurposeOfUseCode cpCode = new ConsentShareForPurposeOfUseCode(pCode);
        conPOU.add(cpCode);

        //Act
        boolean isPouMatch = cst.isPOUMatches(selPOU, conPOU);


        //Assert
        assertEquals(isPouMatch, false);

    }

    @Test
    public void testIsPOUMatches_multrue() {

        //Arrange
        Set<String> selPOU = new HashSet<String>();
        selPOU.add("TREAT");
        selPOU.add("ETREAT");

        Set<ConsentShareForPurposeOfUseCode> conPOU = new HashSet<ConsentShareForPurposeOfUseCode>();
        PurposeOfUseCode pCode = new PurposeOfUseCode();
        pCode.setCode("ETREAT");
        ConsentShareForPurposeOfUseCode cpCode = new ConsentShareForPurposeOfUseCode(pCode);
        conPOU.add(cpCode);

        //Act
        boolean isPouMatch = cst.isPOUMatches(selPOU, conPOU);


        //Assert
        assertEquals(isPouMatch, true);

    }


    @Test
    public void testIsPOUMatches_mulpcodetrue() {

        //Arrange
        Set<String> selPOU = new HashSet<String>();
        selPOU.add("CLINTRCH");
        selPOU.add("HLEGAL");

        Set<ConsentShareForPurposeOfUseCode> conPOU = new HashSet<ConsentShareForPurposeOfUseCode>();
        PurposeOfUseCode pCode = new PurposeOfUseCode();
        pCode.setCode("ETREAT");
        ConsentShareForPurposeOfUseCode cpCode = new ConsentShareForPurposeOfUseCode(pCode);
        conPOU.add(cpCode);

        PurposeOfUseCode pCode2 = new PurposeOfUseCode();
        pCode.setCode("HLEGAL");
        ConsentShareForPurposeOfUseCode cpCode2 = new ConsentShareForPurposeOfUseCode(pCode2);
        conPOU.add(cpCode2);

        //Act
        boolean isPouMatch = cst.isPOUMatches(selPOU, conPOU);


        //Assert
        assertEquals(isPouMatch, true);

    }

    @Test
    public void testIsPOUMatches_mulpcodefalse() {

        //Arrange
        Set<String> selPOU = new HashSet<String>();
        selPOU.add("CLINTRCH");
        selPOU.add("HLEGAL");

        Set<ConsentShareForPurposeOfUseCode> conPOU = new HashSet<ConsentShareForPurposeOfUseCode>();
        PurposeOfUseCode pCode = new PurposeOfUseCode();
        pCode.setCode("ETREAT");
        ConsentShareForPurposeOfUseCode cpCode = new ConsentShareForPurposeOfUseCode(pCode);
        conPOU.add(cpCode);

        PurposeOfUseCode pCode2 = new PurposeOfUseCode();
        pCode.setCode("HMARKT");
        ConsentShareForPurposeOfUseCode cpCode2 = new ConsentShareForPurposeOfUseCode(pCode2);
        conPOU.add(cpCode2);

        //Act
        boolean isPouMatch = cst.isPOUMatches(selPOU, conPOU);


        //Assert
        assertEquals(isPouMatch, false);

    }


    private void setConsentProvidersMatchData(Consent consent) {

        // providers Permitted to disclose
        IndividualProvider ip = new IndividualProvider();
        ip.setNpi("1083949036");
        ip.setFirstName("MONICA");
        ip.setLastName("VAN DONGEN");
        ConsentIndividualProviderPermittedToDisclose item = new ConsentIndividualProviderPermittedToDisclose(ip);
        Set<ConsentIndividualProviderPermittedToDisclose> individualProviderPermittedToDiscloses = new HashSet<ConsentIndividualProviderPermittedToDisclose>();
        individualProviderPermittedToDiscloses.add(item);

        consent.setProvidersPermittedToDisclose(individualProviderPermittedToDiscloses);


        // organization permitted to disclose
        OrganizationalProvider op = new OrganizationalProvider();
        op.setNpi("1902131865");
        op.setOrgName("MASTER CARE, INC.");
        ConsentOrganizationalProviderPermittedToDisclose orgItem = new ConsentOrganizationalProviderPermittedToDisclose(op);
        Set<ConsentOrganizationalProviderPermittedToDisclose> organizationalProviderPermittedToDiscloses = new HashSet<ConsentOrganizationalProviderPermittedToDisclose>();
        organizationalProviderPermittedToDiscloses.add(orgItem);

        consent.setOrganizationalProvidersPermittedToDisclose(organizationalProviderPermittedToDiscloses);


        // DisclosureIsMadeTo
        IndividualProvider ip1 = new IndividualProvider();
        ip1.setNpi("1346575297");
        ip1.setFirstName("GEORGE");
        ip1.setLastName("CARLSON");
        ConsentIndividualProviderDisclosureIsMadeTo item1 = new ConsentIndividualProviderDisclosureIsMadeTo(ip1);
        Set<ConsentIndividualProviderDisclosureIsMadeTo> providersDisclosureIsMadeTo = new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>();
        providersDisclosureIsMadeTo.add(item1);

        consent.setProvidersDisclosureIsMadeTo(providersDisclosureIsMadeTo);


        // organization disclosureIsMadeTo
        OrganizationalProvider op1 = new OrganizationalProvider();
        op1.setNpi("1174858088");
        op1.setOrgName("NEVAEH LLC");
        ConsentOrganizationalProviderDisclosureIsMadeTo orgItem1 = new ConsentOrganizationalProviderDisclosureIsMadeTo(op1);
        Set<ConsentOrganizationalProviderDisclosureIsMadeTo> organizationalProviderDisclosureIsMadeTos = new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>();
        organizationalProviderDisclosureIsMadeTos.add(orgItem1);

        consent.setOrganizationalProvidersDisclosureIsMadeTo(organizationalProviderDisclosureIsMadeTos);


    }

    private void setDtoProvidersMatchData(ConsentDto consentDto) {

        // providers Permitted to disclose
        Set<String> selIsMadeToNpi = new HashSet<String>();
        selIsMadeToNpi.add("1083949036");
        // organizations Permitted to disclose
        Set<String> selIsMadeToOrgNpi = new HashSet<String>();
        selIsMadeToOrgNpi.add("1902131865");

        selIsMadeToNpi.addAll(selIsMadeToOrgNpi);

        consentDto.setProvidersPermittedToDiscloseNpi(selIsMadeToNpi);

        // providers to disclose
        Set<String> selToDiscloseNpi = new HashSet<String>();
        selToDiscloseNpi.add("1346575297");
        // organizations to disclose
        Set<String> selToDiscloseOrgNpi = new HashSet<String>();
        selToDiscloseOrgNpi.add("1174858088");

        selToDiscloseNpi.addAll(selToDiscloseOrgNpi);

        consentDto.setProvidersDisclosureIsMadeToNpi(selToDiscloseNpi);

    }

    // provider combination check
    @Test
    public void testIsProviderCombomatch_samecombo() {
        //Arrange
        Consent consent = new Consent();

        setConsentProvidersMatchData(consent);

        setDtoProvidersMatchData(consentDto);

        //Act
        boolean isProviderComboMatch = cst.isProviderComboMatch(consent, consentDto);


        //Assert
        assertEquals(isProviderComboMatch, true);

    }


    @Test
    public void testIsProviderCombomatch_selsamePermittedToAndToDisclose() {
        //Arrange
        Consent consent = new Consent();

        setConsentProvidersMatchData(consent);

        setDtoProvidersMatchData(consentDto);

        consentDto.setProvidersDisclosureIsMadeToNpi(consentDto.getProvidersPermittedToDiscloseNpi());

        //Act
        boolean isProviderComboMatch = cst.isProviderComboMatch(consent, consentDto);


        //Assert
        assertEquals(isProviderComboMatch, false);

    }

    @Test
    public void testIsProviderCombomatch_selsameToDiscloseAndPermittedTo() {
        //Arrange
        Consent consent = new Consent();

        setConsentProvidersMatchData(consent);

        setDtoProvidersMatchData(consentDto);

        consentDto.setProvidersPermittedToDiscloseNpi(consentDto.getProvidersDisclosureIsMadeToNpi());

        //Act
        boolean isProviderComboMatch = cst.isProviderComboMatch(consent, consentDto);


        //Assert
        assertEquals(isProviderComboMatch, false);

    }


    @Test
    public void testIsProviderCombomatch_onematch() {
        //Arrange
        Consent consent = new Consent();

        setConsentProvidersMatchData(consent);

        // providers Permitted to disclose
        Set<String> selIsMadeToNpi = new HashSet<String>();
        selIsMadeToNpi.add("1083949036");
        // organizations Permitted to disclose
        Set<String> selIsMadeToOrgNpi = new HashSet<String>();
        selIsMadeToOrgNpi.add("1174858088");

        selIsMadeToNpi.addAll(selIsMadeToOrgNpi);

        consentDto.setProvidersPermittedToDiscloseNpi(selIsMadeToNpi);

        // providers to disclose
        Set<String> selToDiscloseNpi = new HashSet<String>();
        selToDiscloseNpi.add("1346575297");
        // organizations to disclose
        Set<String> selToDiscloseOrgNpi = new HashSet<String>();
        selToDiscloseOrgNpi.add("1902131865");

        selToDiscloseNpi.addAll(selToDiscloseOrgNpi);

        consentDto.setProvidersDisclosureIsMadeToNpi(selToDiscloseNpi);

        //Act
        boolean isProviderComboMatch = cst.isProviderComboMatch(consent, consentDto);


        //Assert
        assertEquals(isProviderComboMatch, true);

    }

    @Test
    public void testIsProviderCombomatch_onematch2() {
        //Arrange
        Consent consent = new Consent();

        setConsentProvidersMatchData(consent);

        // providers Permitted to disclose
        Set<String> selIsMadeToNpi = new HashSet<String>();
        selIsMadeToNpi.add("1174858088");
        // organizations Permitted to disclose
        Set<String> selIsMadeToOrgNpi = new HashSet<String>();
        selIsMadeToOrgNpi.add("1902131865");

        selIsMadeToNpi.addAll(selIsMadeToOrgNpi);

        consentDto.setProvidersPermittedToDiscloseNpi(selIsMadeToNpi);

        // providers to disclose
        Set<String> selToDiscloseNpi = new HashSet<String>();
        selToDiscloseNpi.add("1902131865");
        // organizations to disclose
        Set<String> selToDiscloseOrgNpi = new HashSet<String>();
        selToDiscloseOrgNpi.add("1346575297");

        selToDiscloseNpi.addAll(selToDiscloseOrgNpi);

        consentDto.setProvidersDisclosureIsMadeToNpi(selToDiscloseNpi);

        //Act
        boolean isProviderComboMatch = cst.isProviderComboMatch(consent, consentDto);


        //Assert
        assertEquals(isProviderComboMatch, true);

    }

    @Test
    public void testIsProviderCombomatch_alldifferent() {
        //Arrange
        Consent consent = new Consent();

        setConsentProvidersMatchData(consent);

        // providers Permitted to disclose
        Set<String> selIsMadeToNpi = new HashSet<String>();
        selIsMadeToNpi.add("12312314");
        // organizations Permitted to disclose
        Set<String> selIsMadeToOrgNpi = new HashSet<String>();
        selIsMadeToOrgNpi.add("5757556756");

        selIsMadeToNpi.addAll(selIsMadeToOrgNpi);

        consentDto.setProvidersPermittedToDisclose(selIsMadeToNpi);

        // providers to disclose
        Set<String> selToDiscloseNpi = new HashSet<String>();
        selToDiscloseNpi.add("76967886");
        // organizations to disclose
        Set<String> selToDiscloseOrgNpi = new HashSet<String>();
        selToDiscloseOrgNpi.add("23432423");

        selToDiscloseNpi.addAll(selToDiscloseOrgNpi);

        consentDto.setProvidersDisclosureIsMadeTo(selToDiscloseNpi);

        //Act
        boolean isProviderComboMatch = cst.isProviderComboMatch(consent, consentDto);


        //Assert
        assertEquals(isProviderComboMatch, false);

    }

    @Test
    public void testIsProviderCombomatch_difftodisclose() {
        //Arrange
        Consent consent = new Consent();

        setConsentProvidersMatchData(consent);

        // providers Permitted to disclose
        Set<String> selIsMadeToNpi = new HashSet<String>();
        selIsMadeToNpi.add("1083949036");
        // organizations Permitted to disclose
        Set<String> selIsMadeToOrgNpi = new HashSet<String>();
        selIsMadeToOrgNpi.add("1902131865");

        selIsMadeToNpi.addAll(selIsMadeToOrgNpi);

        // providers to disclose
        Set<String> selToDiscloseNpi = new HashSet<String>();
        selToDiscloseNpi.add("76967886");
        // organizations to disclose
        Set<String> selToDiscloseOrgNpi = new HashSet<String>();
        selToDiscloseOrgNpi.add("23432423");

        selToDiscloseNpi.addAll(selToDiscloseOrgNpi);

        consentDto.setProvidersDisclosureIsMadeTo(selToDiscloseNpi);

        //Act
        boolean isProviderComboMatch = cst.isProviderComboMatch(consent, consentDto);


        //Assert
        assertEquals(isProviderComboMatch, false);

    }

    @Test
    public void testIsProviderCombomatch_alldiffpermittedto() {
        //Arrange
        Consent consent = new Consent();

        setConsentProvidersMatchData(consent);

        // providers Permitted to disclose
        Set<String> selIsMadeToNpi = new HashSet<String>();
        selIsMadeToNpi.add("12312314");
        // organizations Permitted to disclose
        Set<String> selIsMadeToOrgNpi = new HashSet<String>();
        selIsMadeToOrgNpi.add("5757556756");

        selIsMadeToNpi.addAll(selIsMadeToOrgNpi);

        consentDto.setProvidersPermittedToDisclose(selIsMadeToNpi);

        // providers to disclose
        Set<String> selToDiscloseNpi = new HashSet<String>();
        selToDiscloseNpi.add("1346575297");
        // organizations to disclose
        Set<String> selToDiscloseOrgNpi = new HashSet<String>();
        selToDiscloseOrgNpi.add("1174858088");

        selToDiscloseNpi.addAll(selToDiscloseOrgNpi);

        consentDto.setProvidersDisclosureIsMadeTo(selToDiscloseNpi);

        //Act
        boolean isProviderComboMatch = cst.isProviderComboMatch(consent, consentDto);


        //Assert
        assertEquals(isProviderComboMatch, false);

    }

    /**
     * Test day is end of day.
     */
    @Test
    public void testDayIsEndOfDay() {
        Date date = new Date();
        ConsentHelper helper = new ConsentHelper();

        Date endOfDay = helper.setDateAsEndOfDay(date);

        Calendar today = Calendar.getInstance();
        today.setTime(endOfDay);

        assertEquals(today.get(Calendar.HOUR_OF_DAY), 23);
        assertEquals(today.get(Calendar.MINUTE), 59);
        assertEquals(today.get(Calendar.SECOND), 59);
    }
}
