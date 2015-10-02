package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;
import gov.samhsa.acs.polrep.client.PolRepRestClient;
import gov.samhsa.acs.polrep.client.PolicyCombiningAlgIds;
import gov.samhsa.acs.polrep.client.dto.PolicyDto;
import gov.samhsa.consent2share.hl7.Hl7v3Transformer;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.service.PixManagerService;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.PixManagerMessageHelper;
import gov.samhsa.consent2share.pixclient.util.PixManagerRequestXMLToJava;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

@RunWith(MockitoJUnitRunner.class)
public class PolRepPolicyProviderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private Hl7v3Transformer hl7v3Transformer;

	@Mock
	private PixManagerRequestXMLToJava requestXMLToJava;

	@Mock
	private PixManagerMessageHelper pixManagerMessageHelper;

	@Mock
	private PixManagerService pixManagerService;

	@Mock
	private PolRepRestClient polRepRestClient;

	@InjectMocks
	private PolRepPolicyProvider sut;

	@Test
	public void testGetPolicies() throws NoPolicyFoundException,
			PolicyProviderException, Hl7v3TransformerException, JAXBException,
			IOException {
		SimplePDPFactory.getSimplePDP();
		final String homeCommunityId = "homeCommunityId";
		final String intermediarySubjectNPI = "intermediarySubjectNPI";
		final String messageId = "messageId";
		final String patientId = "patientId";
		final String patientUniqueId = "patientUniqueId";
		final String purposeOfUse = "purposeOfUse";
		final String recipientSubjectNPI = "recipientSubjectNPI";
		final String xacml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"a07478e8-3642-42ff-980e-911e26ec3f47\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable\"><Description>This is a reference policy for	consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">PUI100010060001</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:accessor-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1083949036</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:accessor-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1568797520</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:accessor-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1902131865</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:receiver-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1346575297</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:receiver-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1285969170</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:receiver-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1174858088</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREATMENT</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">EMERGENCY</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">RESEARCH</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">PAYMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-06-12T00:00:00-04:00</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2020-07-18T00:00:00-04:00</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">51848-0</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">121181</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">47420-5</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">46240-8</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">PSY</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">SEX</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">18748-4</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">11504-8</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">34117-2</AttributeAssignment></Obligation></Obligations></Policy>";
		final byte[] bytes = xacml.getBytes("UTF-8");
		final XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setHomeCommunityId(homeCommunityId);
		xacmlRequest.setIntermediarySubjectNPI(intermediarySubjectNPI);
		xacmlRequest.setMessageId(messageId);
		xacmlRequest.setPatientId(patientId);
		xacmlRequest.setPatientUniqueId(patientUniqueId);
		xacmlRequest.setPurposeOfUse(purposeOfUse);
		xacmlRequest.setRecipientSubjectNPI(recipientSubjectNPI);
		final PolicyDto policyDto = mock(PolicyDto.class);
		when(
				polRepRestClient.getPoliciesCombinedAsPolicySet(anyString(),
						anyString(), anyString(),
						eq(PolicyCombiningAlgIds.DENY_OVERRIDES))).thenReturn(
				policyDto);
		when(policyDto.getPolicy()).thenReturn(bytes);
		final String hl7Query = "hl7Query";
		when(
				hl7v3Transformer.getPixQueryXml(eq(patientId), anyString(),
						eq(Hl7v3Transformer.XML_PIX_QUERY))).thenReturn(
				hl7Query);
		final PRPAIN201309UV02 req = mock(PRPAIN201309UV02.class);
		when(
				requestXMLToJava.getPIXQueryReqObject(hl7Query,
						PixManagerConstants.ENCODE_STRING)).thenReturn(req);
		final PRPAIN201310UV02 resp = mock(PRPAIN201310UV02.class);
		when(pixManagerService.pixManagerPRPAIN201309UV02(req))
				.thenReturn(resp);
		final PixManagerBean pixManagerBean = mock(PixManagerBean.class);
		when(
				pixManagerMessageHelper.getQueryMessage(eq(resp),
						any(PixManagerBean.class))).thenReturn(pixManagerBean);

		// Act
		final List<Evaluatable> response = sut.getPolicies(xacmlRequest);

		// Assert
		assertNotNull(response);
		assertTrue(response.size() == 1);
		assertNotNull(response.get(0));
	}

	@Test
	public void testGetPolicies_Throws_NoPolicyFoundException()
			throws NoPolicyFoundException, PolicyProviderException,
			Hl7v3TransformerException, JAXBException, IOException {
		thrown.expect(NoPolicyFoundException.class);
		SimplePDPFactory.getSimplePDP();
		final String homeCommunityId = "homeCommunityId";
		final String intermediarySubjectNPI = "intermediarySubjectNPI";
		final String messageId = "messageId";
		final String patientId = "patientId";
		final String patientUniqueId = "patientUniqueId";
		final String purposeOfUse = "purposeOfUse";
		final String recipientSubjectNPI = "recipientSubjectNPI";
		final String xacml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"a07478e8-3642-42ff-980e-911e26ec3f47\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable\"><Description>This is a reference policy for	consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">PUI100010060001</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:accessor-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1083949036</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:accessor-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1568797520</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:accessor-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1902131865</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:receiver-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1346575297</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:receiver-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1285969170</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:samhsa:names:tc:consent2share:1.0:subject:receiver-provider-npi\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1174858088</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREATMENT</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">EMERGENCY</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">RESEARCH</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">PAYMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-06-12T00:00:00-04:00</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2020-07-18T00:00:00-04:00</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">51848-0</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">121181</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">47420-5</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">46240-8</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">PSY</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">SEX</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">18748-4</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">11504-8</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">34117-2</AttributeAssignment></Obligation></Obligations></Policy>";
		final byte[] bytes = xacml.getBytes("UTF-8");
		final XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setHomeCommunityId(homeCommunityId);
		xacmlRequest.setIntermediarySubjectNPI(intermediarySubjectNPI);
		xacmlRequest.setMessageId(messageId);
		xacmlRequest.setPatientId(patientId);
		xacmlRequest.setPatientUniqueId(patientUniqueId);
		xacmlRequest.setPurposeOfUse(purposeOfUse);
		xacmlRequest.setRecipientSubjectNPI(recipientSubjectNPI);
		final PolicyDto policyDto = mock(PolicyDto.class);
		final HttpStatusCodeException e = new HttpStatusCodeExceptionImpl(
				HttpStatus.NOT_FOUND);
		when(
				polRepRestClient.getPoliciesCombinedAsPolicySet(anyString(),
						anyString(), anyString(),
						eq(PolicyCombiningAlgIds.DENY_OVERRIDES))).thenThrow(e);
		when(policyDto.getPolicy()).thenReturn(bytes);
		final String hl7Query = "hl7Query";
		when(
				hl7v3Transformer.getPixQueryXml(eq(patientId), anyString(),
						eq(Hl7v3Transformer.XML_PIX_QUERY))).thenReturn(
				hl7Query);
		final PRPAIN201309UV02 req = mock(PRPAIN201309UV02.class);
		when(
				requestXMLToJava.getPIXQueryReqObject(hl7Query,
						PixManagerConstants.ENCODE_STRING)).thenReturn(req);
		final PRPAIN201310UV02 resp = mock(PRPAIN201310UV02.class);
		when(pixManagerService.pixManagerPRPAIN201309UV02(req))
				.thenReturn(resp);
		final PixManagerBean pixManagerBean = mock(PixManagerBean.class);
		when(
				pixManagerMessageHelper.getQueryMessage(eq(resp),
						any(PixManagerBean.class))).thenReturn(pixManagerBean);

		// Act
		final List<Evaluatable> response = sut.getPolicies(xacmlRequest);

		// Assert
		assertNotNull(response);
		assertTrue(response.size() == 1);
		assertNotNull(response.get(0));
	}

	@Test
	public void testGetPolicies_Throws_PolicyProviderException()
			throws NoPolicyFoundException, PolicyProviderException,
			Hl7v3TransformerException, JAXBException, IOException {
		thrown.expect(PolicyProviderException.class);
		SimplePDPFactory.getSimplePDP();
		final String homeCommunityId = "homeCommunityId";
		final String intermediarySubjectNPI = "intermediarySubjectNPI";
		final String messageId = "messageId";
		final String patientId = "patientId";
		final String patientUniqueId = "patientUniqueId";
		final String purposeOfUse = "purposeOfUse";
		final String recipientSubjectNPI = "recipientSubjectNPI";
		final String xacml = "NotXacml";
		final byte[] bytes = xacml.getBytes("UTF-8");
		final XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setHomeCommunityId(homeCommunityId);
		xacmlRequest.setIntermediarySubjectNPI(intermediarySubjectNPI);
		xacmlRequest.setMessageId(messageId);
		xacmlRequest.setPatientId(patientId);
		xacmlRequest.setPatientUniqueId(patientUniqueId);
		xacmlRequest.setPurposeOfUse(purposeOfUse);
		xacmlRequest.setRecipientSubjectNPI(recipientSubjectNPI);
		final PolicyDto policyDto = mock(PolicyDto.class);
		when(
				polRepRestClient.getPoliciesCombinedAsPolicySet(anyString(),
						anyString(), anyString(),
						eq(PolicyCombiningAlgIds.DENY_OVERRIDES))).thenReturn(
				policyDto);
		when(policyDto.getPolicy()).thenReturn(bytes);
		final String hl7Query = "hl7Query";
		when(
				hl7v3Transformer.getPixQueryXml(eq(patientId), anyString(),
						eq(Hl7v3Transformer.XML_PIX_QUERY))).thenReturn(
				hl7Query);
		final PRPAIN201309UV02 req = mock(PRPAIN201309UV02.class);
		when(
				requestXMLToJava.getPIXQueryReqObject(hl7Query,
						PixManagerConstants.ENCODE_STRING)).thenReturn(req);
		final PRPAIN201310UV02 resp = mock(PRPAIN201310UV02.class);
		when(pixManagerService.pixManagerPRPAIN201309UV02(req))
				.thenReturn(resp);
		final PixManagerBean pixManagerBean = mock(PixManagerBean.class);
		when(
				pixManagerMessageHelper.getQueryMessage(eq(resp),
						any(PixManagerBean.class))).thenReturn(pixManagerBean);

		// Act
		final List<Evaluatable> response = sut.getPolicies(xacmlRequest);

		// Assert
		assertNotNull(response);
		assertTrue(response.size() == 1);
		assertNotNull(response.get(0));
	}

	private class HttpStatusCodeExceptionImpl extends HttpStatusCodeException {

		private static final long serialVersionUID = 2057644650762168357L;

		protected HttpStatusCodeExceptionImpl(HttpStatus statusCode) {
			super(statusCode);
		}

	}
}