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
package gov.samhsa.acs.audit;

public enum AcsAuditVerb implements AuditVerb {
	// PEP-registryStoredQuery (ITI-18)
	REGISTRY_STORED_QUERY_REQUEST,
	REGISTRY_STORED_QUERY_RESPONSE,
	
	// PEP-retrieveDocumentSet (ITI-43)
	RETRIEVE_DOCUMENT_SET_REQUEST, 
	RETRIEVE_DOCUMENT_SET_RESPONSE,
	
	// XDS-registryStoredQuery (ITI-18)
	XDS_REGISTRY_STORED_QUERY_RESPONSE,	
	// XDS-retrieveDocumentSet (ITI-43)	
	XDS_RETRIEVE_DOCUMENT_SET_RESPONSE,
	
	// PDP
	DEPLOY_POLICY,
	EVALUATE_PDP,
	
	// DocumentSegmentation
	SEGMENT_DOCUMENT,
	
	// DirectEmailSend	 
	DIRECT_EMAIL_SEND_REQUEST, 
	DIRECT_EMAIL_SEND_RESPONSE,
	
	// ERROR-DocQuery
	REGISTRY_STORED_QUERY_RESPONSE_ERROR_SYSTEM, 
	REGISTRY_STORED_QUERY_RESPONSE_ERROR_CONSENT, 
	// ERROR-DocRetrieve
	RETRIEVE_DOCUMENT_SET_RESPONSE_ERROR_SYSTEM,
	RETRIEVE_DOCUMENT_SET_RESPONSE_ERROR_CONSENT, 
	// ERROR-DirectEmailSend
	DIRECT_EMAIL_SEND_RESPONSE_ERROR_SYSTEM, 
	DIRECT_EMAIL_SEND_RESPONSE_ERROR_CONSENT,;

	@Override
	public String getAuditVerb() {
		return this.toString();
	} 
}
