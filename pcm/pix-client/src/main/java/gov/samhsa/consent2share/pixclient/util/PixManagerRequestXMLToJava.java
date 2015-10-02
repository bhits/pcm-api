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
package gov.samhsa.consent2share.pixclient.util;

import gov.samhsa.acs.common.tool.SimpleMarshaller;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.hl7.v3.types.PRPAIN201301UV02;
import org.hl7.v3.types.PRPAIN201302UV02;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PixManagerRequestXMLToJava.
 */
public class PixManagerRequestXMLToJava {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/**
	 * Instantiates a new pix manager request xml to java.
	 */
	public PixManagerRequestXMLToJava() {
	}

	/**
	 * Instantiates a new pix manager request xml to java.
	 * 
	 * @param marshaller
	 *            the marshaller
	 */
	public PixManagerRequestXMLToJava(SimpleMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	/**
	 * Gets the pIX add req object.
	 * 
	 * @param reqXMLFilePath
	 *            the req xml file path
	 * @param encodeString
	 *            the encode string
	 * @return the pIX add req object
	 * @throws JAXBException
	 *             the jAXB exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PRPAIN201301UV02 getPIXAddReqObject(String reqXMLFilePath,
			String encodeString) throws JAXBException, IOException {
		PRPAIN201301UV02 reqObj = null;
		reqObj = getPIXReqObject(PRPAIN201301UV02.class, reqXMLFilePath);
		return reqObj;
	}

	/**
	 * Gets the pIX update req object.
	 * 
	 * @param reqXMLFilePath
	 *            the req xml file path
	 * @param encodeString
	 *            the encode string
	 * @return the pIX update req object
	 * @throws JAXBException
	 *             the jAXB exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PRPAIN201302UV02 getPIXUpdateReqObject(String reqXMLFilePath,
			String encodeString) throws JAXBException, IOException {
		PRPAIN201302UV02 reqObj = null;
		reqObj = getPIXReqObject(PRPAIN201302UV02.class, reqXMLFilePath);
		return reqObj;
	}

	/**
	 * Gets the pIX query req object.
	 * 
	 * @param reqXMLFilePath
	 *            the req xml file path
	 * @param encodeString
	 *            the encode string
	 * @return the pIX query req object
	 * @throws JAXBException
	 *             the jAXB exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PRPAIN201309UV02 getPIXQueryReqObject(String reqXMLFilePath,
			String encodeString) throws JAXBException, IOException {
		PRPAIN201309UV02 reqObj = null;
		reqObj = getPIXReqObject(PRPAIN201309UV02.class, reqXMLFilePath);
		return reqObj;
	}

	/**
	 * Gets the pIX req object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param reqXMLFilePath
	 *            the req xml file path
	 * @return the pIX req object
	 * @throws JAXBException
	 *             the jAXB exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private <T> T getPIXReqObject(Class<T> clazz, String reqXMLFilePath)
			throws JAXBException, IOException {
		T reqObj = null;
		if (reqXMLFilePath == null) {
			throw new JAXBException("input is null");
		}

		// if the string starts with <?xml then its a xml document
		// otherwise its xml file path
		if (reqXMLFilePath.startsWith("<?xml")) {
			// 3. Use the Unmarshaller to unmarshal the XML document to get an
			// instance of JAXBElement.
			// 4. Get the instance of the required JAXB Root Class from the
			// JAXBElement.
			try {
				reqObj = marshaller.unmarshalFromXml(clazz, reqXMLFilePath);
			} catch (JAXBException e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		} else {
			// 3. Use the Unmarshaller to unmarshal the XML document to get an
			// instance of JAXBElement.
			// 4. Get the instance of the required JAXB Root Class from the
			// JAXBElement.
			try {
				reqObj = marshaller.unmarshalFromXml(clazz, IOUtils
						.toString(getClass().getClassLoader()
								.getResourceAsStream(reqXMLFilePath)));
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw e;
			} catch (JAXBException e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return reqObj;
	}
}
