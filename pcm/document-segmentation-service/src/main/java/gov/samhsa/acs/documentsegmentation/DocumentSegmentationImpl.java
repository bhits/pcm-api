/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.documentsegmentation;

import static gov.samhsa.acs.audit.AcsAuditVerb.SEGMENT_DOCUMENT;
import static gov.samhsa.acs.audit.AcsPredicateKey.CATEGORY_OBLIGATIONS_APPLIED;
import static gov.samhsa.acs.audit.AcsPredicateKey.ORIGINAL_DOCUMENT;
import static gov.samhsa.acs.audit.AcsPredicateKey.ORIGINAL_DOCUMENT_VALID;
import static gov.samhsa.acs.audit.AcsPredicateKey.RULES_FIRED;
import static gov.samhsa.acs.audit.AcsPredicateKey.SECTION_OBLIGATIONS_APPLIED;
import static gov.samhsa.acs.audit.AcsPredicateKey.SEGMENTED_DOCUMENT;
import static gov.samhsa.acs.audit.AcsPredicateKey.SEGMENTED_DOCUMENT_VALID;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.brms.RuleExecutionService;
import gov.samhsa.acs.brms.domain.ClinicalFact;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.validation.XmlValidation;
import gov.samhsa.acs.common.validation.XmlValidationResult;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.acs.documentsegmentation.dto.SegmentDocumentResponse;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;
import gov.samhsa.acs.documentsegmentation.tools.AdditionalMetadataGeneratorForSegmentedClinicalDocument;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEditor;
import gov.samhsa.acs.documentsegmentation.tools.DocumentFactModelExtractor;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactor;
import gov.samhsa.acs.documentsegmentation.tools.DocumentTagger;
import gov.samhsa.acs.documentsegmentation.tools.EmbeddedClinicalDocumentExtractor;
import gov.samhsa.acs.documentsegmentation.tools.dto.RedactedDocument;
import gov.samhsa.acs.documentsegmentation.valueset.ValueSetService;
import gov.samhsa.acs.documentsegmentation.valueset.dto.CodeAndCodeSystemSetDto;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBException;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.springframework.util.Assert;
import org.xml.sax.SAXParseException;

import ch.qos.logback.audit.AuditException;

/**
 * The Class DocumentSegmentationImpl.
 */
public class DocumentSegmentationImpl implements DocumentSegmentation {

	/** The Constant C32_CDA_XSD_PATH. */
	public static final String C32_CDA_XSD_PATH = "schema/cdar2c32/infrastructure/cda/";

	/** The Constant C32_CDA_XSD_NAME. */
	public static final String C32_CDA_XSD_NAME = "C32_CDA.xsd";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The rule execution web service client. */
	private final RuleExecutionService ruleExecutionService;

	/** The audit service. */
	private final AuditService auditService;

	/** The document editor. */
	private final DocumentEditor documentEditor;

	/** The marshaller. */
	private final SimpleMarshaller marshaller;

	/** The document redactor. */
	private final DocumentRedactor documentRedactor;

	/** The document tagger. */
	private final DocumentTagger documentTagger;

	/** The document fact model extractor. */
	private final DocumentFactModelExtractor documentFactModelExtractor;

	/** The embedded clinical document extractor. */
	private final EmbeddedClinicalDocumentExtractor embeddedClinicalDocumentExtractor;

	/** The value set service. */
	private final ValueSetService valueSetService;

	/** The additional metadata generator for segmented clinical document. */
	private final AdditionalMetadataGeneratorForSegmentedClinicalDocument additionalMetadataGeneratorForSegmentedClinicalDocument;

	/** The xml validator. */
	private final XmlValidation xmlValidator;

	/**
	 * Instantiates a new document processor impl.
	 *
	 * @param ruleExecutionService
	 *            the rule execution service
	 * @param auditService
	 *            the audit service
	 * @param documentEditor
	 *            the document editor
	 * @param marshaller
	 *            the marshaller
	 * @param documentRedactor
	 *            the document redactor
	 * @param documentTagger
	 *            the document tagger
	 * @param documentFactModelExtractor
	 *            the document fact model extractor
	 * @param embeddedClinicalDocumentExtractor
	 *            the embedded clinical document extractor
	 * @param valueSetService
	 *            the value set service
	 * @param additionalMetadataGeneratorForSegmentedClinicalDocument
	 *            the additional metadata generator for segmented clinical
	 *            document
	 */
	public DocumentSegmentationImpl(
			RuleExecutionService ruleExecutionService,
			AuditService auditService,
			DocumentEditor documentEditor,
			SimpleMarshaller marshaller,
			DocumentRedactor documentRedactor,
			DocumentTagger documentTagger,
			DocumentFactModelExtractor documentFactModelExtractor,
			EmbeddedClinicalDocumentExtractor embeddedClinicalDocumentExtractor,
			ValueSetService valueSetService,
			AdditionalMetadataGeneratorForSegmentedClinicalDocument additionalMetadataGeneratorForSegmentedClinicalDocument) {
		this.ruleExecutionService = ruleExecutionService;
		this.auditService = auditService;
		this.documentEditor = documentEditor;
		this.marshaller = marshaller;
		this.documentRedactor = documentRedactor;
		this.documentTagger = documentTagger;
		this.documentFactModelExtractor = documentFactModelExtractor;
		this.embeddedClinicalDocumentExtractor = embeddedClinicalDocumentExtractor;
		this.valueSetService = valueSetService;
		this.additionalMetadataGeneratorForSegmentedClinicalDocument = additionalMetadataGeneratorForSegmentedClinicalDocument;
		this.xmlValidator = createXmlValidator();
	}

	/**
	 * Instantiates a new document segmentation impl.
	 *
	 * @param ruleExecutionService
	 *            the rule execution service
	 * @param documentEditor
	 *            the document editor
	 * @param marshaller
	 *            the marshaller
	 * @param documentRedactor
	 *            the document redactor
	 * @param documentTagger
	 *            the document tagger
	 * @param documentFactModelExtractor
	 *            the document fact model extractor
	 * @param embeddedClinicalDocumentExtractor
	 *            the embedded clinical document extractor
	 * @param valueSetService
	 *            the value set service
	 * @param additionalMetadataGeneratorForSegmentedClinicalDocument
	 *            the additional metadata generator for segmented clinical
	 *            document
	 */
	public DocumentSegmentationImpl(
			RuleExecutionService ruleExecutionService,
			DocumentEditor documentEditor,
			SimpleMarshaller marshaller,
			DocumentRedactor documentRedactor,
			DocumentTagger documentTagger,
			DocumentFactModelExtractor documentFactModelExtractor,
			EmbeddedClinicalDocumentExtractor embeddedClinicalDocumentExtractor,
			ValueSetService valueSetService,
			AdditionalMetadataGeneratorForSegmentedClinicalDocument additionalMetadataGeneratorForSegmentedClinicalDocument) {
		this.ruleExecutionService = ruleExecutionService;
		this.auditService = null;
		this.documentEditor = documentEditor;
		this.marshaller = marshaller;
		this.documentRedactor = documentRedactor;
		this.documentTagger = documentTagger;
		this.documentFactModelExtractor = documentFactModelExtractor;
		this.embeddedClinicalDocumentExtractor = embeddedClinicalDocumentExtractor;
		this.valueSetService = valueSetService;
		this.additionalMetadataGeneratorForSegmentedClinicalDocument = additionalMetadataGeneratorForSegmentedClinicalDocument;
		this.xmlValidator = createXmlValidator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.DocumentSegmentation#segmentDocument
	 * (java.lang.String, java.lang.String, boolean, boolean, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SegmentDocumentResponse segmentDocument(String document,
			String enforcementPolicies, boolean isAudited,
			boolean isAuditFailureByPass, boolean enableTryPolicyResponse)
			throws XmlDocumentReadFailureException,
			InvalidSegmentedClinicalDocumentException, AuditException {

		Assert.notNull(document);
		final String originalDocument = document;
		XmlValidationResult originalClinicalDocumentValidationResult = null;
		try {
			originalClinicalDocumentValidationResult = xmlValidator
					.validateWithAllErrors(document);
			Assert.notNull(originalClinicalDocumentValidationResult);
		} catch (final XmlDocumentReadFailureException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		if (!originalClinicalDocumentValidationResult.isValid()) {
			logger.error("Schema validation is failed for original clinical document.");
			final String err = "InvalidOriginalClinicalDocumentException: ";
			for (final SAXParseException e : originalClinicalDocumentValidationResult
					.getExceptions()) {
				logger.error(() -> err + e.getMessage());
			}
		}

		Assert.notNull(enforcementPolicies);

		RuleExecutionContainer ruleExecutionContainer = null;
		RedactedDocument redactedDocument = null;
		String rulesFired = null;
		final SegmentDocumentResponse segmentDocumentResponse = new SegmentDocumentResponse();
		FactModel factModel = null;

		try {

			document = documentEditor.setDocumentCreationDate(document);

			// FileHelper.writeStringToFile(document, "Original_C32.xml");

			// extract factModel
			String factModelXml = documentFactModelExtractor.extractFactModel(
					document, enforcementPolicies);
			// get clinical document with generatedEntryId elements
			document = embeddedClinicalDocumentExtractor
					.extractClinicalDocumentFromFactModel(factModelXml);
			// remove the embedded c32 from factmodel before unmarshalling
			factModelXml = documentRedactor
					.cleanUpEmbeddedClinicalDocumentFromFactModel(factModelXml);
			factModel = marshaller.unmarshalFromXml(FactModel.class,
					factModelXml);

			final List<CodeAndCodeSystemSetDto> codeAndCodeSystemSetDtoList = new ArrayList<CodeAndCodeSystemSetDto>();
			// Get and set value set categories to clinical facts
			for (final ClinicalFact fact : factModel.getClinicalFactList()) {
				final CodeAndCodeSystemSetDto codeAndCodeSystemSetDto = new CodeAndCodeSystemSetDto();
				codeAndCodeSystemSetDto.setConceptCode(fact.getCode());
				codeAndCodeSystemSetDto.setCodeSystemOid(fact.getCodeSystem());
				codeAndCodeSystemSetDtoList.add(codeAndCodeSystemSetDto);
			}

			// Get value set categories
			final List<Map<String, Object>> valueSetCategories = valueSetService
					.lookupValuesetCategoriesOfMultipleCodeAndCodeSystemSet(codeAndCodeSystemSetDtoList);
			// Iterator<HashMap<String, String>> iterator=valueSetCategories.k
			// TODO (BU): Refactor this code and make sure you get the value set
			// categories by code and code system (not by index)
			for (int i = 0; i < factModel.getClinicalFactList().size(); i++) {
				final Map<String, Object> valueSetMap = valueSetCategories
						.get(i);
				final ClinicalFact fact = factModel.getClinicalFactList()
						.get(i);
				Assert.isTrue(fact.getCode().equals(
						valueSetMap.get("conceptCode")));
				Assert.isTrue(fact.getCodeSystem().equals(
						valueSetMap.get("codeSystemOid")));
				if (valueSetMap.get("vsCategoryCodes") != null) {
					fact.setValueSetCategories(new HashSet<String>(
							(ArrayList<String>) valueSetMap
									.get("vsCategoryCodes")));
				}
			}
			// FileHelper.writeStringToFile(factModel, "FactModel.xml");

			// get execution response container
			final AssertAndExecuteClinicalFactsResponse brmsResponse = ruleExecutionService
					.assertAndExecuteClinicalFacts(factModel);
			String executionResponseContainer = brmsResponse
					.getRuleExecutionResponseContainer();
			rulesFired = brmsResponse.getRulesFired();

			// unmarshall from xml to RuleExecutionContainer
			ruleExecutionContainer = marshaller.unmarshalFromXml(
					RuleExecutionContainer.class, executionResponseContainer);

			// FileHelper.writeStringToFile(executionResponseContainer,
			// "ExecutionResponseContainer.xml");

			logger.info("Fact model: " + factModelXml);
			logger.info("Rule Execution Container size: "
					+ ruleExecutionContainer.getExecutionResponseList().size());

			// redact document
			redactedDocument = documentRedactor.redactDocument(document,
					ruleExecutionContainer, factModel);
			document = redactedDocument.getRedactedDocument();

			// set tryPolicyDocument in the response
			if (enableTryPolicyResponse) {
				segmentDocumentResponse
						.setTryPolicyDocumentXml(redactedDocument
								.getTryPolicyDocument());
			}

			// to get the itemActions from documentRedactor
			executionResponseContainer = marshaller
					.marshal(ruleExecutionContainer);

			// tag document
			document = documentTagger.tagDocument(document,
					executionResponseContainer);

			// clean up generatedEntryId elements from document
			document = documentRedactor.cleanUpGeneratedEntryIds(document);

			// clean up generatedServiceEventId elements from document
			document = documentRedactor
					.cleanUpGeneratedServiceEventIds(document);

			// FileHelper.writeStringToFile(document, "Tagged_C32.xml");

			// Set segmented document in response
			segmentDocumentResponse.setSegmentedDocumentXml(document);
			// Set execution response container in response
			segmentDocumentResponse
					.setExecutionResponseContainerXml(executionResponseContainer);

		} catch (final JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		} catch (final Throwable e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}

		XmlValidationResult segmentedClinicalDocumentValidationResult = null;
		try {
			segmentedClinicalDocumentValidationResult = xmlValidator
					.validateWithAllErrors(document);
			Assert.notNull(segmentedClinicalDocumentValidationResult);
			if (isAudited) {
				auditSegmentation(originalDocument, document,
						factModel.getXacmlResult(), redactedDocument,
						rulesFired, originalClinicalDocumentValidationResult,
						segmentedClinicalDocumentValidationResult,
						isAuditFailureByPass);
			}
		} catch (final XmlDocumentReadFailureException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		if (!segmentedClinicalDocumentValidationResult.isValid()) {
			final String segmentationErr = "Schema validation is failed for segmented clinical document.";
			logger.error(segmentationErr);
			final String err = "InvalidSegmentedClinicalDocumentException: ";
			for (final SAXParseException e : segmentedClinicalDocumentValidationResult
					.getExceptions()) {
				logger.error(err + e.getMessage());
			}
			throw new InvalidSegmentedClinicalDocumentException(segmentationErr);
		}

		return segmentDocumentResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.DocumentSegmentation#
	 * setAdditionalMetadataForSegmentedClinicalDocument
	 * (gov.samhsa.consent2share
	 * .schema.documentsegmentation.SegmentDocumentResponse, java.lang.String,
	 * java.lang.String, java.lang.String,
	 * gov.samhsa.acs.brms.domain.XacmlResult)
	 */
	@Override
	public void setAdditionalMetadataForSegmentedClinicalDocument(
			SegmentDocumentResponse segmentDocumentResponse,
			String senderEmailAddress, String recipientEmailAddress,
			String xdsDocumentEntryUniqueId, XacmlResult xacmlResult) {
		final String additionalMetadataForSegmentedClinicalDocument = additionalMetadataGeneratorForSegmentedClinicalDocument
				.generateMetadataXml(xacmlResult.getMessageId(),
						segmentDocumentResponse.getSegmentedDocumentXml(),
						segmentDocumentResponse
								.getExecutionResponseContainerXml(),
						senderEmailAddress, recipientEmailAddress, xacmlResult
								.getSubjectPurposeOfUse().getPurpose(),
						xdsDocumentEntryUniqueId);
		// FileHelper.writeStringToFile(additionalMetadataForSegmentedClinicalDocument,"additional_metadata.xml");

		segmentDocumentResponse
				.setPostSegmentationMetadataXml(additionalMetadataForSegmentedClinicalDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.DocumentSegmentation#
	 * setDocumentPayloadRawData
	 * (gov.samhsa.consent2share.schema.documentsegmentation
	 * .SegmentDocumentResponse, boolean, java.lang.String, java.lang.String,
	 * gov.samhsa.acs.brms.domain.XacmlResult)
	 */
	@Override
	public void setDocumentPayloadRawData(
			SegmentDocumentResponse segmentDocumentResponse,
			boolean packageAsXdm, String senderEmailAddress,
			String recipientEmailAddress, XacmlResult xacmlResult)
			throws Exception, IOException {
		final ByteArrayDataSource rawData = documentEditor
				.setDocumentPayloadRawData(segmentDocumentResponse
						.getSegmentedDocumentXml(), packageAsXdm,
						senderEmailAddress, recipientEmailAddress, xacmlResult,
						segmentDocumentResponse
						.getExecutionResponseContainerXml(), null, null);
		segmentDocumentResponse.setDocumentPayloadRawData(new DataHandler(
				rawData));
	}

	/**
	 * Audit segmentation.
	 *
	 * @param originalDocument
	 *            the original document
	 * @param segmentedDocument
	 *            the segmented document
	 * @param xacmlResult
	 *            the xacml result
	 * @param redactedDocument
	 *            the redacted document
	 * @param rulesFired
	 *            the rules fired
	 * @param originalClinicalDocumentValidationResult
	 *            the original clinical document validation result
	 * @param segmentedClinicalDocumentValidationResult
	 *            the segmented clinical document validation result
	 * @param isAuditFailureByPass
	 *            the is audit failure by pass
	 * @throws AuditException
	 *             the audit exception
	 */
	private void auditSegmentation(String originalDocument,
			String segmentedDocument, XacmlResult xacmlResult,
			RedactedDocument redactedDocument, String rulesFired,
			XmlValidationResult originalClinicalDocumentValidationResult,
			XmlValidationResult segmentedClinicalDocumentValidationResult,
			boolean isAuditFailureByPass) throws AuditException {
		final Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		if (redactedDocument.getRedactedSectionSet().size() > 0) {
			predicateMap.put(SECTION_OBLIGATIONS_APPLIED, redactedDocument
					.getRedactedSectionSet().toString());
		}
		if (redactedDocument.getRedactedCategorySet().size() > 0) {
			predicateMap.put(CATEGORY_OBLIGATIONS_APPLIED, redactedDocument
					.getRedactedCategorySet().toString());
		}
		if (rulesFired != null) {
			predicateMap.put(RULES_FIRED, rulesFired);
		}
		predicateMap.put(ORIGINAL_DOCUMENT, originalDocument);
		predicateMap.put(SEGMENTED_DOCUMENT, segmentedDocument);
		predicateMap.put(ORIGINAL_DOCUMENT_VALID, Boolean
				.toString(originalClinicalDocumentValidationResult.isValid()));
		predicateMap.put(SEGMENTED_DOCUMENT_VALID, Boolean
				.toString(segmentedClinicalDocumentValidationResult.isValid()));
		try {
			auditService.audit(this, xacmlResult.getMessageId(),
					SEGMENT_DOCUMENT, xacmlResult.getPatientId(), predicateMap);
		} catch (final AuditException e) {
			if (isAuditFailureByPass) {
				// main flow should work though the audit service has some
				// issues
				logger.error("Audit Service is Down");
				logger.debug(() -> "patient id" + xacmlResult.getPatientId());
				logger.debug(() -> "original document" + originalDocument);
				logger.debug(() -> "segmented document" + segmentedDocument);
				// TODO send the email notification to core team
				// Or send a notification using rabbitmq
			} else {
				// main flow shouldn't work if audit service has some issues
				throw e;
			}
		}
	}

	/**
	 * Creates the xml validator.
	 *
	 * @return the xml validation
	 */
	private XmlValidation createXmlValidator() {
		return new XmlValidation(this.getClass().getClassLoader()
				.getResourceAsStream(C32_CDA_XSD_PATH + C32_CDA_XSD_NAME),
				C32_CDA_XSD_PATH);
	}
}
