package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;
import gov.samhsa.acs.xdsb.common.AdhocQueryResponseParser;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryAdapterException;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.herasaf.xacml.core.policy.Evaluatable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class XdsbPolicyProviderTest {

	private static final String FIELD_NAME_URN_POLICY_COMBINING_ALGORITHM = "urnPolicyCombiningAlgorithm";

	public static final String URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides";

	@Mock
	private XdsbRegistryAdapter xdsbRegistry;

	@Mock
	private XdsbRepositoryAdapter xdsbRepository;

	@Mock
	private XacmlPolicyListFilter xacmlPolicyListFilter;

	@Mock
	private AdhocQueryResponseParser adhocQueryResponseParser;

	@InjectMocks
	private XdsbPolicyProvider sut;

	@Test
	public void testConstructorWithXdsbRegistryAdapterAndXdsbRepositoryAdapter() {
		// Arrange
		sut = new XdsbPolicyProvider(xdsbRegistry, xdsbRepository,
				xacmlPolicyListFilter, adhocQueryResponseParser);

		// Act
		final String urn = getUrn();

		// Assert
		assertEquals(urn, URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES);
	}

	@Test
	public void testConstructorWithXdsbRegistryAdapterAndXdsbRepositoryAdapterAndEmptyUrnPolicyCombiningAlgorithm() {
		// Arrange
		sut = new XdsbPolicyProvider(xdsbRegistry, xdsbRepository,
				xacmlPolicyListFilter, adhocQueryResponseParser, "");

		// Act
		final String urn = getUrn();

		// Assert
		assertEquals(urn, URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES);
	}

	@Test
	public void testConstructorWithXdsbRegistryAdapterAndXdsbRepositoryAdapterAndMeaningfulUrnPolicyCombiningAlgorithm() {
		// Arrange
		sut = new XdsbPolicyProvider(xdsbRegistry, xdsbRepository,
				xacmlPolicyListFilter, adhocQueryResponseParser, null);

		// Act
		final String urn = getUrn();

		// Assert
		assertEquals(urn, URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES);
	}

	@Test
	public void testConstructorWithXdsbRegistryAdapterAndXdsbRepositoryAdapterAndNullUrnPolicyCombiningAlgorithm() {
		// Arrange
		sut = new XdsbPolicyProvider(xdsbRegistry, xdsbRepository,
				xacmlPolicyListFilter, adhocQueryResponseParser,
				URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES);

		// Act
		final String urn = getUrn();

		// Assert
		assertEquals(urn, URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES);
	}

	@Test
	// unable to mock static class
	public void testGetPolicies() throws Exception, Throwable {
		// Arrange
		final XdsbPolicyProvider spy = spy(sut);
		final String xacmlPolicyXml = "<Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\"  PolicyId=\"a07478e8-3642-42ff-980e-911e26ec3f47\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable\">\r\n   <Description>This is a reference policy forconsent2share@outlook.com</Description>\r\n   <Target></Target>\r\n   <Rule Effect=\"Permit\" RuleId=\"primary-group-rule\">\r\n      <Target>\r\n         <Resources>\r\n            <Resource>\r\n               <ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\">\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">consent2share@outlook.com</AttributeValue>\r\n                  <ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ResourceAttributeDesignator>\r\n               </ResourceMatch>\r\n            </Resource>\r\n         </Resources>\r\n         <Actions>\r\n            <Action>\r\n               <ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue>\r\n                  <ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ActionAttributeDesignator>\r\n               </ActionMatch>\r\n            </Action>\r\n         </Actions>\r\n      </Target>\r\n      <Condition>\r\n         <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\">\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1568797520</AttributeValue>\r\n               </Apply>\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1083949036</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1285969170</AttributeValue>\r\n               </Apply>\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1346575297</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREAT</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                  <ActionAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ActionAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\">\r\n                  <EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"></EnvironmentAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-06-12T00:00:00-04:00</AttributeValue>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\">\r\n                  <EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"></EnvironmentAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-07-18T00:00:00-04:00</AttributeValue>\r\n            </Apply>\r\n         </Apply>\r\n      </Condition>\r\n   </Rule>\r\n   \r\n   <Rule Effect=\"Deny\" RuleId=\"Deny-the-else\"/>\r\n   \r\n   <Obligations>\r\n   \t\t<Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\">\r\n   \t\t\t<AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">47420-5</AttributeAssignment>\r\n   \t\t</Obligation>\r\n   \t\t\r\n   \t\t<Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-sensitivity-code\" FulfillOn=\"Permit\">\r\n         <AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment>\r\n      </Obligation>\r\n   </Obligations>\r\n   \r\n   \r\n   \r\n   \r\n</Policy>    ";
		final byte[] xacmlPolicy = xacmlPolicyXml.getBytes();
		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = mock(RetrieveDocumentSetResponse.class);
		final RetrieveDocumentSetRequest retrieveDocumentSetRequest = mock(RetrieveDocumentSetRequest.class);
		final AdhocQueryResponse response = mock(AdhocQueryResponse.class);
		final DocumentResponse docResponse1 = mock(DocumentResponse.class);
		final DocumentResponse docResponse2 = mock(DocumentResponse.class);
		final List<RetrieveDocumentSetResponse.DocumentResponse> policyDocuments = new ArrayList<RetrieveDocumentSetResponse.DocumentResponse>();
		policyDocuments.add(docResponse1);
		policyDocuments.add(docResponse2);
		final Evaluatable evaluatable = mock(Evaluatable.class);
		doReturn(evaluatable).when(spy).unmarshal(any(InputStream.class));
		when(
				xdsbRegistry.findDocuments("1", null,
						XdsbDocumentType.PRIVACY_CONSENT, true)).thenReturn(
								response);
		when(
				adhocQueryResponseParser
				.parseXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(response))
				.thenReturn(retrieveDocumentSetRequest);
		when(xdsbRepository.retrieveDocumentSet(retrieveDocumentSetRequest))
		.thenReturn(retrieveDocumentSetResponse);
		when(retrieveDocumentSetResponse.getDocumentResponse()).thenReturn(
				policyDocuments);
		when(docResponse2.getDocument()).thenReturn(xacmlPolicy);
		when(docResponse1.getDocument()).thenReturn(xacmlPolicy);
		@SuppressWarnings("unchecked")
		final List<DocumentRequest> docReqListMock = mock(List.class);
		when(retrieveDocumentSetRequest.getDocumentRequest()).thenReturn(
				docReqListMock);
		when(docReqListMock.size()).thenReturn(1);
		final XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setPatientUniqueId("1");
		xacmlRequest.setRecipientSubjectNPI("1568797520");
		xacmlRequest.setIntermediarySubjectNPI("1285969170");
		xacmlRequest.setMessageId("");

		// Act
		final List<Evaluatable> policies = spy.getPolicies(xacmlRequest);

		// Assert
		assertEquals(1, policies.size());
		assertEquals(evaluatable, policies.get(0));
		verify(adhocQueryResponseParser, times(1))
		.parseXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(
				response);
		verify(xdsbRepository, times(1)).retrieveDocumentSet(
				retrieveDocumentSetRequest);
	}

	@Test(expected = PolicyProviderException.class)
	public void testGetPoliciesWhenDocumentCannotBeFound() throws Exception,
	Throwable {
		// Arrange
		final XdsbPolicyProvider spy = spy(sut);
		final byte[] xacmlPolicy = "<Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\"  PolicyId=\"a07478e8-3642-42ff-980e-911e26ec3f47\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable\">\r\n   <Description>This is a reference policy forconsent2share@outlook.com</Description>\r\n   <Target></Target>\r\n   <Rule Effect=\"Permit\" RuleId=\"primary-group-rule\">\r\n      <Target>\r\n         <Resources>\r\n            <Resource>\r\n               <ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\">\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">consent2share@outlook.com</AttributeValue>\r\n                  <ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ResourceAttributeDesignator>\r\n               </ResourceMatch>\r\n            </Resource>\r\n         </Resources>\r\n         <Actions>\r\n            <Action>\r\n               <ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue>\r\n                  <ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ActionAttributeDesignator>\r\n               </ActionMatch>\r\n            </Action>\r\n         </Actions>\r\n      </Target>\r\n      <Condition>\r\n         <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\">\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1568797520</AttributeValue>\r\n               </Apply>\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1083949036</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1285969170</AttributeValue>\r\n               </Apply>\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1346575297</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREAT</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                  <ActionAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ActionAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\">\r\n                  <EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"></EnvironmentAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-06-12T00:00:00-04:00</AttributeValue>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\">\r\n                  <EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"></EnvironmentAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-07-18T00:00:00-04:00</AttributeValue>\r\n            </Apply>\r\n         </Apply>\r\n      </Condition>\r\n   </Rule>\r\n   \r\n   <Rule Effect=\"Deny\" RuleId=\"Deny-the-else\"/>\r\n   \r\n   <Obligations>\r\n   \t\t<Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\">\r\n   \t\t\t<AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">47420-5</AttributeAssignment>\r\n   \t\t</Obligation>\r\n   \t\t\r\n   \t\t<Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-sensitivity-code\" FulfillOn=\"Permit\">\r\n         <AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment>\r\n      </Obligation>\r\n   </Obligations>\r\n   \r\n   \r\n   \r\n   \r\n</Policy>    "
				.getBytes();
		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = mock(RetrieveDocumentSetResponse.class);
		final RetrieveDocumentSetRequest retrieveDocumentSetRequest = mock(RetrieveDocumentSetRequest.class);
		final AdhocQueryResponse response = mock(AdhocQueryResponse.class);
		final DocumentResponse docResponse1 = mock(DocumentResponse.class);
		final DocumentResponse docResponse2 = mock(DocumentResponse.class);
		final List<RetrieveDocumentSetResponse.DocumentResponse> policyDocuments = new ArrayList<RetrieveDocumentSetResponse.DocumentResponse>();
		policyDocuments.add(docResponse1);
		policyDocuments.add(docResponse2);
		final Evaluatable evaluatable = mock(Evaluatable.class);
		doReturn(evaluatable).when(spy).unmarshal(any(InputStream.class));
		when(
				xdsbRegistry.findDocuments("1", null,
						XdsbDocumentType.PRIVACY_CONSENT, true)).thenReturn(
								response);
		doThrow(new XdsbRegistryAdapterException()).when(
				adhocQueryResponseParser)
				.parseXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(
						response);
		when(xdsbRepository.retrieveDocumentSet(retrieveDocumentSetRequest))
		.thenReturn(retrieveDocumentSetResponse);
		when(retrieveDocumentSetResponse.getDocumentResponse()).thenReturn(
				policyDocuments);
		when(docResponse2.getDocument()).thenReturn(xacmlPolicy);
		when(docResponse1.getDocument()).thenReturn(xacmlPolicy);
		final XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setPatientUniqueId("1");
		xacmlRequest.setRecipientSubjectNPI("1568797520");
		xacmlRequest.setIntermediarySubjectNPI("1285969170");
		xacmlRequest.setMessageId("");

		// Act
		sut.getPolicies(xacmlRequest);
	}

	private String getUrn() {
		return (String) ReflectionTestUtils.getField(sut,
				FIELD_NAME_URN_POLICY_COMBINING_ALGORITHM);
	}
}
