/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.xdsb.common;

import gov.samhsa.acs.common.param.Params;
import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;

/**
 * The Interface XdsbMetadataGenerator.
 */
public interface XdsbMetadataGenerator {

	/**
	 * Generate metadata xml.
	 * 
	 * @param document
	 *            the document (Pass XdsbRepositoryAdapter.EMPTY_XML_DOCUMENT if
	 *            deprecating a document. Otherwise, pass the actual document to
	 *            be provided.)
	 * @param homeCommunityId
	 *            the home community id (May pass null if deprecating a
	 *            document.)
	 * @param patientUniqueId
	 *            the patient unique id (Pass this only if deprecating a
	 *            document. Otherwise, pass null.)
	 * @param entryUUID
	 *            the entry uuid (Pass this only if deprecating a document.
	 *            Otherwise, pass null.)
	 * @return the string
	 */
	public String generateMetadataXml(String document, String homeCommunityId,
			String patientUniqueId, String entryUUID);

	/**
	 * Generate metadata xml.
	 *
	 * @param document
	 *            the document
	 * @param params
	 *            the params
	 * @return the string
	 */
	public String generateMetadataXml(String document, Params params);

	/**
	 * Generate metadata.
	 * 
	 * @param document
	 *            the document (Pass XdsbRepositoryAdapter.EMPTY_XML_DOCUMENT if
	 *            deprecating a document. Otherwise, pass the actual document to
	 *            be provided.)
	 * @param homeCommunityId
	 *            the home community id (May pass null if deprecating a
	 *            document.)
	 * @param patientUniqueId
	 *            the patient unique id (Pass this only if deprecating a
	 *            document. Otherwise, pass null.)
	 * @param entryUUID
	 *            the entry uuid (Pass this only if deprecating a document.
	 *            Otherwise, pass null.)
	 * @return the submit objects request
	 */
	public SubmitObjectsRequest generateMetadata(String document,
			String homeCommunityId, String patientUniqueId, String entryUUID);
}
