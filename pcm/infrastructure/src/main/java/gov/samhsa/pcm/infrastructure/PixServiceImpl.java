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
package gov.samhsa.pcm.infrastructure;

import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.pcm.hl7.Hl7v3Transformer;
import gov.samhsa.pcm.hl7.Hl7v3TransformerException;
import gov.samhsa.pcm.hl7.dto.PixPatientDto;
import gov.samhsa.pcm.pixclient.service.PixManagerService;
import gov.samhsa.pcm.pixclient.util.PixManagerBean;
import gov.samhsa.pcm.pixclient.util.PixManagerConstants;
import gov.samhsa.pcm.pixclient.util.PixManagerMessageHelper;
import gov.samhsa.pcm.pixclient.util.PixManagerRequestXMLToJava;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.PRPAIN201301UV02;
import org.hl7.v3.types.PRPAIN201302UV02;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * The Class PixServiceImpl.
 */
public class PixServiceImpl implements PixService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The mrn domain. */
	private String mrnDomain;

	/** The pid prefix. */
	private String pidPrefix;

	/** The request xml to java. */
	private PixManagerRequestXMLToJava requestXMLToJava;

	/** The pix manager service. */
	private PixManagerService pixManagerService;

	/** The pix manager message helper. */
	private PixManagerMessageHelper pixManagerMessageHelper;

	/** pix operations. */
	private Hl7v3Transformer hl7v3Transformer;

	/** The simple marshaller. */
	private SimpleMarshaller simpleMarshaller;

	/**
	 * Instantiates a new pix query service impl.
	 *
	 * @param mrnDomain
	 *            the mrn domain
	 * @param pidPrefix
	 *            the pid prefix
	 * @param requestXMLToJava
	 *            the request xml to java
	 * @param pixManagerService
	 *            the pix manager service
	 * @param pixManagerMessageHelper
	 *            the pix manager message helper
	 * @param hl7v3Transformer
	 *            the hl7v3 transformer
	 * @param simpleMarshaller
	 *            the simple marshaller
	 */
	public PixServiceImpl(String mrnDomain, String pidPrefix,
			PixManagerRequestXMLToJava requestXMLToJava,
			PixManagerService pixManagerService,
			PixManagerMessageHelper pixManagerMessageHelper,
			Hl7v3Transformer hl7v3Transformer, SimpleMarshaller simpleMarshaller) {
		super();
		this.mrnDomain = mrnDomain;
		this.pidPrefix = pidPrefix;
		this.requestXMLToJava = requestXMLToJava;
		this.pixManagerService = pixManagerService;
		this.pixManagerMessageHelper = pixManagerMessageHelper;
		this.hl7v3Transformer = hl7v3Transformer;
		this.simpleMarshaller = simpleMarshaller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.PixService#getEid(java.lang.String
	 * )
	 */
	@Override
	public String getEid(String mrn) {
		String eid = null;
		PixManagerBean pixManagerBean = queryPatient(mrn, this.mrnDomain);

		if (pixManagerBean != null) {
			Map<String, String> queryMap = pixManagerBean.getQueryIdMap();

			if (null != queryMap) {
				// person already available in EMPI get global identifier
				// id(EID)
				// get Eid Values
				eid = queryMap.get(PixManagerConstants.GLOBAL_DOMAIN_ID);
			}
		}
		Assert.hasText(eid,
				"The MRN cannot be transformed to an HL7v3 message or EID cannot be retrieved!");
		return eid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.PixService#queryPatient(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public PixManagerBean queryPatient(String mrn, String mrnDomain) {
		logger.debug("Received request to PIXQuery");
		PixManagerBean pixMgrBean = new PixManagerBean();

		try {
			String xmlFilePath = hl7v3Transformer.getPixQueryXml(mrn,
					mrnDomain, Hl7v3Transformer.XML_PIX_QUERY);

			PRPAIN201309UV02 request = new PRPAIN201309UV02();
			PRPAIN201310UV02 response = new PRPAIN201310UV02();

			request = requestXMLToJava.getPIXQueryReqObject(xmlFilePath,
					PixManagerConstants.ENCODE_STRING);
			logDebug("PIX QUERY Request: ", request);

			response = pixManagerService.pixManagerPRPAIN201309UV02(request);
			logDebug("PIX QUERY Response: ", response);

			pixManagerMessageHelper.getQueryMessage(response, pixMgrBean);
		} catch (Hl7v3TransformerException e) {
			pixManagerMessageHelper.getGeneralExpMessage(e, pixMgrBean,
					PixManagerConstants.PIX_QUERY);
			throw new IllegalArgumentException(e);
		} catch (JAXBException | IOException e) {
			pixManagerMessageHelper.getGeneralExpMessage(e, pixMgrBean,
					PixManagerConstants.PIX_QUERY);
		}
		return pixMgrBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.PixService#addPatient(gov.samhsa
	 * .consent2share.hl7.dto.PixPatientDto)
	 */
	@Override
	public PixManagerBean addPatient(PixPatientDto pixPatientDto) {
		PixManagerBean pixMgrBean = init(pixPatientDto);
		try {
			String hl7PixAddXml = hl7v3Transformer.transformToHl7v3PixXml(
					simpleMarshaller.marshal(pixPatientDto),
					Hl7v3Transformer.XSLT_PATIENT_DTO_TO_PIX_ADD);
			logDebug("PIX ADD Request (XSLT Transformation): ", hl7PixAddXml);
			PRPAIN201301UV02 request = requestXMLToJava.getPIXAddReqObject(
					hl7PixAddXml, PixManagerConstants.ENCODE_STRING);
			logDebug("PIX ADD Request: ", request);
			MCCIIN000002UV01 response = pixManagerService
					.pixManagerPRPAIN201301UV02(request);
			logDebug("PIX ADD Response: ", response);
			pixManagerMessageHelper.getAddUpdateMessage(response, pixMgrBean,
					PixManagerConstants.PIX_ADD);
			logger.debug("PIX ADD Response Message:",
					pixMgrBean.getAddMessage());
			pixMgrBean.setSuccess(pixManagerMessageHelper.isSuccess(response));
		} catch (JAXBException | IOException | Hl7v3TransformerException e) {
			pixManagerMessageHelper.getGeneralExpMessage(e, pixMgrBean,
					PixManagerConstants.PIX_ADD);
			logger.error(e.getMessage(), e);
			logger.error(pixMgrBean.getAddMessage());
		}
		return pixMgrBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.PixService#updatePatient(gov.
	 * samhsa.consent2share.hl7.dto.PixPatientDto)
	 */
	@Override
	public PixManagerBean updatePatient(PixPatientDto pixPatientDto) {
		PixManagerBean pixMgrBean = init(pixPatientDto);
		try {
			String hl7PixUpdateXml = hl7v3Transformer.transformToHl7v3PixXml(
					simpleMarshaller.marshal(pixPatientDto),
					Hl7v3Transformer.XSLT_PATIENT_DTO_TO_PIX_UPDATE);
			logDebug("PIX UPDATE Request (XSLT Transformation): ",
					hl7PixUpdateXml);
			PRPAIN201302UV02 request = requestXMLToJava.getPIXUpdateReqObject(
					hl7PixUpdateXml, PixManagerConstants.ENCODE_STRING);
			logDebug("PIX UPDATE Request: ", request);
			MCCIIN000002UV01 response = pixManagerService
					.pixManagerPRPAIN201302UV02(request);
			logDebug("PIX UPDATE Response: ", response);
			pixManagerMessageHelper.getAddUpdateMessage(response, pixMgrBean,
					PixManagerConstants.PIX_UPDATE);
			logDebug("PIX UPDATE Response Message: ",
					pixMgrBean.getUpdateMessage());
			pixMgrBean.setSuccess(pixManagerMessageHelper.isSuccess(response));
		} catch (Hl7v3TransformerException | JAXBException | IOException e) {
			pixManagerMessageHelper.getGeneralExpMessage(e, pixMgrBean,
					PixManagerConstants.PIX_UPDATE);
			logger.error(e.getMessage(), e);
			logger.error(pixMgrBean.getUpdateMessage());
		}
		return pixMgrBean;
	}

	/**
	 * Inits the.
	 *
	 * @param pixPatientDto
	 *            the pix patient dto
	 * @return the pix manager bean
	 */
	private PixManagerBean init(PixPatientDto pixPatientDto) {
		pixPatientDto.setIdRoot(this.mrnDomain);
		pixPatientDto.setIdAssigningAuthorityName(this.pidPrefix);
		return new PixManagerBean();
	}

	/**
	 * Log debug.
	 *
	 * @param <T>
	 *            the generic type
	 * @param prefix
	 *            the prefix
	 * @param object
	 *            the object
	 * @throws SimpleMarshallerException
	 *             the simple marshaller exception
	 */
	private <T> void logDebug(String prefix, T object)
			throws SimpleMarshallerException {
		if (logger.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append(prefix);
			if (object instanceof String) {
				builder.append(object);
			} else {
				builder.append(simpleMarshaller.marshal(object));
			}
			logger.debug(builder.toString());
		}
	}
}
