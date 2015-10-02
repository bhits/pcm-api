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
package gov.samhsa.acs.xdsb.repository.wsclient;

import gov.samhsa.acs.common.cxf.AbstractCXFLoggingConfigurerClient;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.xdsb.repository.wsclient.exception.XdsbRepositoryClientException;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.springframework.util.StringUtils;
import org.tempuri.DocumentRepositoryService;
import org.tempuri.DocumentRepositoryService.XDSRepositoryProxy;

/**
 * The Class XDSRepositorybWebServiceClient.
 */
public class XDSRepositorybWebServiceClient extends
		AbstractCXFLoggingConfigurerClient {

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The endpoint address. */
	private final String endpointAddress;

	/** The out interceptors. */
	private Optional<List<Interceptor<? extends Message>>> outInterceptors = Optional
			.empty();

	/** The in interceptors. */
	private Optional<List<Interceptor<? extends Message>>> inInterceptors = Optional
			.empty();

	/**
	 * Instantiates a new xDS repositoryb web service client.
	 *
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public XDSRepositorybWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Provide and register document set response.
	 *
	 * @param provideAndRegisterDocumentSet
	 *            the provide and register document set
	 * @return the registry response
	 */
	public RegistryResponse provideAndRegisterDocumentSet(
			ProvideAndRegisterDocumentSetRequest provideAndRegisterDocumentSet) {
		try (XDSRepositoryProxy port = createPort()) {
			return port
					.provideAndRegisterDocumentSet(provideAndRegisterDocumentSet);
		} catch (final Exception e) {
			throw toXdsbRepositoryClientException(e);
		}
	}

	/**
	 * Retrieve document set request.
	 *
	 * @param retrieveDocumentSet
	 *            the retrieve document set
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest retrieveDocumentSet) {
		try (XDSRepositoryProxy port = createPort()) {
			return port.retrieveDocumentSet(retrieveDocumentSet);
		} catch (final Exception e) {
			throw toXdsbRepositoryClientException(e);
		}
	}

	/**
	 * Sets the in interceptors.
	 *
	 * @param inInterceptors
	 *            the new in interceptors
	 */
	public void setInInterceptors(
			List<Interceptor<? extends Message>> inInterceptors) {
		this.inInterceptors = Optional.of(inInterceptors);
	}

	/**
	 * Sets the out interceptors.
	 *
	 * @param outInterceptors
	 *            the new out interceptors
	 */
	public void setOutInterceptors(
			List<Interceptor<? extends Message>> outInterceptors) {
		this.outInterceptors = Optional.of(outInterceptors);
	}

	/**
	 * Creates the port.
	 *
	 * @return the XDS repository proxy
	 */
	private XDSRepositoryProxy createPort() {
		return configurePort(this::createPortProxy);
	}

	/**
	 * Creates the port proxy.
	 *
	 * @return the xDS repository
	 */
	private XDSRepositoryProxy createPortProxy() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("XDS.b_repository.net.wsdl");
		final QName SERVICE = new QName("http://tempuri.org/",
				"DocumentRepositoryService");

		final XDSRepositoryProxy port = new DocumentRepositoryService(
				WSDL_LOCATION, SERVICE).getXDSRepositoryHTTPEndpoint();

		if (StringUtils.hasText(this.endpointAddress)) {
			final BindingProvider bp = port;
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

		}
		this.outInterceptors.ifPresent(ClientProxy.getClient(port)
				.getOutInterceptors()::addAll);
		this.inInterceptors.ifPresent(ClientProxy.getClient(port)
				.getInInterceptors()::addAll);
		return port;
	}

	/**
	 * To xdsb repository client exception.
	 *
	 * @param exception
	 *            the exception
	 * @return the xdsb repository client exception
	 */
	private XdsbRepositoryClientException toXdsbRepositoryClientException(
			Exception exception) {
		logger.error("Error closing XDS.b Repository client port");
		logger.error(exception.getMessage(), exception);
		return new XdsbRepositoryClientException(exception);
	}
}
