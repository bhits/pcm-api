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
package gov.samhsa.acs.xdsb.registry.wsclient;

import gov.samhsa.acs.common.cxf.AbstractCXFLoggingConfigurerClient;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryClientException;
import gov.samhsa.ds4p.xdsbregistry.DocumentRegistryService;
import gov.samhsa.ds4p.xdsbregistry.DocumentRegistryService.XDSRegistryProxy;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
import org.hl7.v3.PRPAIN201304UV02;
import org.springframework.util.StringUtils;

/**
 * The Class XdsbRegistryWebServiceClient.
 */
public class XdsbRegistryWebServiceClient extends
		AbstractCXFLoggingConfigurerClient {

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The endpoint address. */
	private final String endpointAddress;

	/** The wsdl url. */
	final URL wsdlURL = this.getClass().getClassLoader()
			.getResource("XDS.b_registry.net.wsdl");

	/** The service name. */
	final QName serviceName = new QName("http://samhsa.gov/ds4p/XDSbRegistry/",
			"DocumentRegistryService");

	/** The port name. */
	final QName portName = new QName("http://samhsa.gov/ds4p/XDSbRegistry/",
			"XDSRegistry_HTTP_Endpoint");

	/** The marshaller. */
	private final SimpleMarshaller marshaller;

	/**
	 * Instantiates a new xdsb registry web service client.
	 *
	 * @param endpointAddress
	 *            the endpoint address
	 * @param marshaller
	 *            the marshaller
	 */
	public XdsbRegistryWebServiceClient(String endpointAddress,
			SimpleMarshaller marshaller) {
		this.endpointAddress = endpointAddress;
		this.marshaller = marshaller;
	}

	/**
	 * Adds the patient registry record.
	 *
	 * @param input
	 *            the input
	 * @return the string
	 * @throws XdsbRegistryClientException
	 *             the xdsb registry client exception
	 */
	public String addPatientRegistryRecord(PRPAIN201301UV02 input)
			throws XdsbRegistryClientException {
		try (XDSRegistryProxy port = createPort()) {
			final MCCIIN000002UV01 patientRegistryRecordAddedResponse = port
					.patientRegistryRecordAdded(input);
			final String patientRegistryRecordAddedResponseString = marshaller
					.marshal(patientRegistryRecordAddedResponse);
			return patientRegistryRecordAddedResponseString;
		} catch (final SimpleMarshallerException e) {
			throw new XdsbRegistryClientException(e);
		} catch (final Exception e) {
			throw toXdsbRegistryClientException(e);
		}
	}

	/**
	 * Registry stored query.
	 *
	 * @param registryStoredQuery
	 *            the registry stored query
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse registryStoredQuery(
			AdhocQueryRequest registryStoredQuery) {
		try (XDSRegistryProxy port = createPort()) {
			final AdhocQueryResponse adhocQueryResponse = port
					.registryStoredQuery(registryStoredQuery);
			return adhocQueryResponse;
		} catch (final Exception e) {
			throw toXdsbRegistryClientException(e);
		}
	}

	/**
	 * Resolve patient registry duplicates.
	 *
	 * @param input
	 *            the input
	 * @return the string
	 * @throws XdsbRegistryClientException
	 *             the xdsb registry client exception
	 */
	public String resolvePatientRegistryDuplicates(PRPAIN201304UV02 input)
			throws XdsbRegistryClientException {
		try (XDSRegistryProxy port = createPort()) {
			final MCCIIN000002UV01 patientRegistryDuplicatesResolvedResponse = port
					.patientRegistryDuplicatesResolved(input);
			final String patientRegistryDuplicatesResolvedResponseString = marshaller
					.marshal(patientRegistryDuplicatesResolvedResponse);
			return patientRegistryDuplicatesResolvedResponseString;
		} catch (final SimpleMarshallerException e) {
			throw new XdsbRegistryClientException(e);
		} catch (final Exception e) {
			throw toXdsbRegistryClientException(e);
		}
	}

	/**
	 * Revise patient registry record.
	 *
	 * @param input
	 *            the input
	 * @return the string
	 * @throws XdsbRegistryClientException
	 *             the xdsb registry client exception
	 */
	public String revisePatientRegistryRecord(PRPAIN201302UV02 input)
			throws XdsbRegistryClientException {
		try (XDSRegistryProxy port = createPort()) {
			final MCCIIN000002UV01 patientRegistryRecordRevisedResponse = port
					.patientRegistryRecordRevised(input);
			final String patientRegistryRecordRevisedResponseString = marshaller
					.marshal(patientRegistryRecordRevisedResponse);
			return patientRegistryRecordRevisedResponseString;
		} catch (final SimpleMarshallerException e) {
			throw new XdsbRegistryClientException(e);
		} catch (final Exception e) {
			throw toXdsbRegistryClientException(e);
		}
	}

	/**
	 * Creates the port.
	 *
	 * @return the XDS registry proxy
	 */
	private XDSRegistryProxy createPort() {
		return configurePort(this::createPortProxy);
	}

	/**
	 * Creates the port proxy.
	 *
	 * @return the XDS registry proxy
	 */
	private XDSRegistryProxy createPortProxy() {
		final XDSRegistryProxy port = new DocumentRegistryService(wsdlURL,
				serviceName).getXDSRegistryHTTPEndpoint();
		if (StringUtils.hasText(this.endpointAddress)) {
			final BindingProvider bp = port;
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		}
		return port;
	}

	/**
	 * To xdsb registry client exception.
	 *
	 * @param exception
	 *            the exception
	 * @return the xdsb registry client exception
	 */
	private XdsbRegistryClientException toXdsbRegistryClientException(
			Exception exception) {
		logger.error("Error closing XDS.b Registry client port");
		logger.error(exception.getMessage(), exception);
		return new XdsbRegistryClientException(exception);
	}
}
