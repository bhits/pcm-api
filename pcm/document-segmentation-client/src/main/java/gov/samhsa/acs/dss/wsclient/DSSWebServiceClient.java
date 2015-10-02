package gov.samhsa.acs.dss.wsclient;

import gov.samhsa.acs.common.cxf.AbstractCXFLoggingConfigurerClient;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.dss.ws.contract.DSS;
import gov.samhsa.acs.dss.ws.contract.DSS.DSSPortTypeProxy;
import gov.samhsa.acs.dss.ws.schema.DSSRequest;
import gov.samhsa.acs.dss.ws.schema.DSSRequestForDirect;
import gov.samhsa.acs.dss.ws.schema.DSSResponse;
import gov.samhsa.acs.dss.ws.schema.DSSResponseForDirect;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.springframework.util.StringUtils;

/**
 * The Class DSSWebServiceClient.
 */
public class DSSWebServiceClient extends AbstractCXFLoggingConfigurerClient {

	/** The Constant WSDL_FILE_NAME. */
	private static final String WSDL_FILE_NAME = "DSS.wsdl";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The endpoint address. */
	private final String endpointAddress;

	/**
	 * Instantiates a new DSS web service client.
	 *
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public DSSWebServiceClient(String endpointAddress) {
		super();
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Segment document.
	 *
	 * @param request
	 *            the request
	 * @return the DSS response
	 */
	public DSSResponse segmentDocument(DSSRequest request) {
		try (DSSPortTypeProxy port = createPort()) {
			return port.segmentDocument(request);
		} catch (final Exception e) {
			throw toDSSWebServiceClient(e);
		}
	}

	/**
	 * Segment document for direct.
	 *
	 * @param request
	 *            the request
	 * @return the DSS response for direct
	 */
	public DSSResponseForDirect segmentDocumentForDirect(
			DSSRequestForDirect request) {
		try (DSSPortTypeProxy port = createPort()) {
			return port.segmentDocumentForDirect(request);
		} catch (final Exception e) {
			throw toDSSWebServiceClient(e);
		}
	}

	/**
	 * Creates the port.
	 *
	 * @return the DSS port type proxy
	 */
	private DSSPortTypeProxy createPort() {
		return configurePort(this::createPortProxy);
	}

	/**
	 * Creates the port proxy.
	 *
	 * @return the DSS port type
	 */
	private DSSPortTypeProxy createPortProxy() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource(WSDL_FILE_NAME);
		final QName SERVICE = new QName(
				"http://acs.samhsa.gov/dss/ws/contract", "DSS");

		final DSSPortTypeProxy port = new DSS(WSDL_LOCATION, SERVICE)
				.getDSSPort();

		if (StringUtils.hasText(this.endpointAddress)) {
			final BindingProvider bp = port;
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		}
		return port;
	}

	/**
	 * To dss web service client.
	 *
	 * @param exception
	 *            the exception
	 * @return the DSS web service client exception
	 */
	private DSSWebServiceClientException toDSSWebServiceClient(
			Exception exception) {
		logger.error("Error closing DSSWebServiceClient port");
		logger.error(exception.getMessage(), exception);
		return new DSSWebServiceClientException(exception);
	}
}
