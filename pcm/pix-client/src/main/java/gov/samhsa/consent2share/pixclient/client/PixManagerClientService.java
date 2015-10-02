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
package gov.samhsa.consent2share.pixclient.client;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;

/**
 * The Interface PixManagerClientService.
 */
public interface PixManagerClientService {

	/**
	 * Adds the person.
	 * 
	 * @param reqXMLPath
	 *            the req xml path
	 * @return the string
	 */
	public String addPerson(String reqXMLPath);

	/**
	 * Update person.
	 * 
	 * @param reqXMLPath
	 *            the req xml path
	 * @return the string
	 */
	public String updatePerson(String reqXMLPath);

	/**
	 * Query person.
	 * 
	 * @param xmlFilePath
	 *            the xml file path
	 * @return the pix manager bean
	 */
	public PixManagerBean queryPerson(String xmlFilePath);

	/**
	 * Provide and register clinical document.
	 * 
	 * @param c32xml
	 *            the c32xml
	 * @return the registry response
	 */
	public RegistryResponse provideAndRegisterClinicalDocument(String c32xml);
}
