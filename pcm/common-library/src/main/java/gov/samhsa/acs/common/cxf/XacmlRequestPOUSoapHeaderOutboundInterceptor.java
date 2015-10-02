package gov.samhsa.acs.common.cxf;

import gov.samhsa.acs.brms.domain.SubjectPurposeOfUse;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.phase.Phase;
import org.herasaf.xacml.core.context.impl.AttributeType;
import org.herasaf.xacml.core.context.impl.AttributeValueType;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.context.impl.SubjectType;
import org.herasaf.xacml.core.dataTypeAttribute.impl.StringDataTypeAttribute;

/**
 * The Class XacmlRequestPOUSoapHeaderOutboundInterceptor.
 */
public class XacmlRequestPOUSoapHeaderOutboundInterceptor extends
AbstractSoapInterceptor {

	/** The Constant URN_OASIS_NAMES_TC_XACML_2_0_CONTEXT_SCHEMA_OS. */
	private static final String URN_OASIS_NAMES_TC_XACML_2_0_CONTEXT_SCHEMA_OS = "urn:oasis:names:tc:xacml:2.0:context:schema:os";

	/** The Constant URN_OASIS_NAMES_TC_XSPA_1_0_SUBJECT_PURPOSEOFUSE. */
	private static final String URN_OASIS_NAMES_TC_XSPA_1_0_SUBJECT_PURPOSEOFUSE = "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse";

	/** The Constant REQUEST_LOCAL_PART. */
	private static final String REQUEST_LOCAL_PART = "Request";

	/** The Constant XACML_CONTEXT_PREFIX. */
	private static final String XACML_CONTEXT_PREFIX = "xacml-context";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory.getLogger(this);

	/** The purpose of use. */
	private final SubjectPurposeOfUse purposeOfUse;

	/**
	 * Instantiates a new XACML request pou soap header outbound interceptor.
	 *
	 * @param purposeOfUse
	 *            the purpose of use
	 */
	public XacmlRequestPOUSoapHeaderOutboundInterceptor(
			SubjectPurposeOfUse purposeOfUse) {
		super(Phase.WRITE);
		this.purposeOfUse = purposeOfUse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message
	 * .Message)
	 */
	@Override
	public void handleMessage(SoapMessage message) throws Fault {

		try {
			final AttributeType attribute = new AttributeType();
			attribute.setDataType(new StringDataTypeAttribute());
			attribute
			.setAttributeId(URN_OASIS_NAMES_TC_XSPA_1_0_SUBJECT_PURPOSEOFUSE);
			final AttributeValueType attributeValue = new AttributeValueType();
			attributeValue.getContent().add(purposeOfUse.getPurpose());
			attribute.getAttributeValues().add(attributeValue);
			final SubjectType subject = new SubjectType();
			subject.getAttributes().add(attribute);
			final RequestType request = new RequestType();
			request.getSubjects().add(subject);
			final Header header = new Header(new QName(
					URN_OASIS_NAMES_TC_XACML_2_0_CONTEXT_SCHEMA_OS,
					REQUEST_LOCAL_PART, XACML_CONTEXT_PREFIX), request,
					new JAXBDataBinding(RequestType.class));
			logger.debug(":::WRITING CUSTOM SOAP HEADER:::");
			logger.debug(header.toString());
			message.getHeaders().add(header);
			logger.debug(message.getHeaders().toString());
			logger.debug(message.toString());
		} catch (final JAXBException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Of.
	 *
	 * @param purposeOfUse
	 *            the purpose of use
	 * @return the XACML request pou soap header outbound interceptor
	 */
	public static final XacmlRequestPOUSoapHeaderOutboundInterceptor of(
			SubjectPurposeOfUse purposeOfUse) {
		return new XacmlRequestPOUSoapHeaderOutboundInterceptor(purposeOfUse);
	}
}
