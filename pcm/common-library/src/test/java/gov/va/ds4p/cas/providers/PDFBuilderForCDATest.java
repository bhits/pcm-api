package gov.va.ds4p.cas.providers;

import gov.va.ds4p.policy.reference.Addr;
import gov.va.ds4p.policy.reference.AssignedAuthor;
import gov.va.ds4p.policy.reference.AssignedAuthoringDevice;
import gov.va.ds4p.policy.reference.AssignedPerson;
import gov.va.ds4p.policy.reference.DefaultCustodianInfo;
import gov.va.ds4p.policy.reference.DefaultPatientDemographics;
import gov.va.ds4p.policy.reference.HumanReadibleText;
import gov.va.ds4p.policy.reference.ManufacturingModelName;
import gov.va.ds4p.policy.reference.Name;
import gov.va.ds4p.policy.reference.OrganizationConsentPolicyInfo;
import gov.va.ds4p.policy.reference.OrganizationPolicy;
import gov.va.ds4p.policy.reference.SoftwareName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)

public class PDFBuilderForCDATest {
	
	PDFBuilderForCDA pDFBuilderForCDA; 
	
	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		OrganizationPolicy orgPolicy=new OrganizationPolicy();
		Field usPrivacyLaw=orgPolicy.getClass().getDeclaredField("usPrivacyLaw");
		usPrivacyLaw.setAccessible(true);
		usPrivacyLaw.set(orgPolicy, "Title38Section7332");
		
		List<String> maskingActions=new ArrayList<String>();
		maskingActions.add("action1");
		maskingActions.add("action2");
		
		List<String> allowedRecipients= new ArrayList<String>();
		allowedRecipients.add("recipient1");
		allowedRecipients.add("recipient2");
		
		List<String> allowedPOU=new ArrayList<String>();
		allowedPOU.add("POU1");
		allowedPOU.add("POU2");
		
		Addr addr=new Addr();
		addr.setCity("Columbia");
		addr.setCountry("United States");
		addr.setCounty("Howard");
		addr.setPostalCode("21044");
		addr.setState("MD");
		addr.setStreetAddressLine("7175 Columbia Gateway Dr.");
		Field organizationConsentPolicyInfo=orgPolicy.getClass().getDeclaredField("organizationConsentPolicyInfo");
		OrganizationConsentPolicyInfo organizationConsentPolicyInfoField=new OrganizationConsentPolicyInfo();
		DefaultPatientDemographics defaultPatientDemographics=new DefaultPatientDemographics();
		defaultPatientDemographics.setAddr(addr);
		organizationConsentPolicyInfoField.setDefaultPatientDemographics(defaultPatientDemographics);
		organizationConsentPolicyInfoField.setAddr(addr);
		
		HumanReadibleText humanReadibleText=new HumanReadibleText();
		humanReadibleText.setDisplayText("Human Readible Text");
		organizationConsentPolicyInfoField.setHumanReadibleText(humanReadibleText);
		
		Name name=new Name();
		name.setFamily("Smith");
		name.setGiven("Albert");
		AssignedPerson person=new AssignedPerson();
		person.setName(name);
		AssignedAuthor assignedAuthor=new AssignedAuthor();
		assignedAuthor.setAssignedPerson(person);
		
		ManufacturingModelName manufacturingModelName=new ManufacturingModelName();
		manufacturingModelName.setCode("1");
		manufacturingModelName.setDisplayName("Company One");
		SoftwareName softwareName=new SoftwareName();
		softwareName.setCode("2");
		softwareName.setDisplayName("Software Company");
		AssignedAuthoringDevice assignedAuthoringDevice=new AssignedAuthoringDevice();
		assignedAuthoringDevice.setManufacturingModelName(manufacturingModelName);
		assignedAuthoringDevice.setSoftwareName(softwareName);
		
		DefaultCustodianInfo defaultCustodianInfo=new DefaultCustodianInfo();
		defaultCustodianInfo.setTelcom("telcom");
		organizationConsentPolicyInfoField.setDefaultCustodianInfo(defaultCustodianInfo);
		
		
		organizationConsentPolicyInfoField.setAssignedAuthor(assignedAuthor);
		organizationConsentPolicyInfoField.setAssignedAuthoringDevice(assignedAuthoringDevice);
		organizationConsentPolicyInfo.setAccessible(true);
		organizationConsentPolicyInfo.set(orgPolicy, organizationConsentPolicyInfoField);
		
		List<String> redactActions=new ArrayList<String>();
		redactActions.add("action1");
		redactActions.add("action2");
		
		pDFBuilderForCDA=new PDFBuilderForCDA (orgPolicy, "1", "John Doe", "Permit", allowedPOU, allowedRecipients, redactActions, maskingActions);
		pDFBuilderForCDA.getPDFConsentDirective();
	}

}
