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

import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.acs.documentsegmentation.dto.SegmentDocumentResponse;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;

import java.io.IOException;

import ch.qos.logback.audit.AuditException;

/**
 * The Interface DocumentSegmentation.
 */
public interface DocumentSegmentation {

	/**
	 * Segment document.
	 *
	 * @param document
	 *            the document
	 * @param enforcementPolicies
	 *            the enforcement policies
	 * @param isAudited
	 *            the is audited
	 * @param isAuditFailureByPass
	 *            the is audit failure by pass
	 * @param enableTryPolicyResponse
	 *            the enable try policy response
	 * @return the segment document response
	 * @throws XmlDocumentReadFailureException
	 *             the xml document read failure exception
	 * @throws InvalidSegmentedClinicalDocumentException
	 *             the invalid segmented clinical document exception
	 * @throws AuditException
	 *             the audit exception
	 */
	public SegmentDocumentResponse segmentDocument(String document,
			String enforcementPolicies, boolean isAudited,
			boolean isAuditFailureByPass, boolean enableTryPolicyResponse)
					throws XmlDocumentReadFailureException,
					InvalidSegmentedClinicalDocumentException, AuditException;

	/**
	 * Sets the additional metadata for segmented clinical document.
	 *
	 * @param segmentDocumentResponse
	 *            the segment document response
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @param xdsDocumentEntryUniqueId
	 *            the xds document entry unique id
	 * @param xacmlResult
	 *            the xacml result
	 */
	public void setAdditionalMetadataForSegmentedClinicalDocument(
			SegmentDocumentResponse segmentDocumentResponse,
			String senderEmailAddress, String recipientEmailAddress,
			String xdsDocumentEntryUniqueId, XacmlResult xacmlResult);

	/**
	 * Sets the document payload raw data.
	 *
	 * @param segmentDocumentResponse
	 *            the segment document response
	 * @param packageAsXdm
	 *            the package as xdm
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @param xacmlResult
	 *            the xacml result
	 * @throws Exception
	 *             the exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void setDocumentPayloadRawData(
			SegmentDocumentResponse segmentDocumentResponse,
			boolean packageAsXdm, String senderEmailAddress,
			String recipientEmailAddress, XacmlResult xacmlResult)
					throws Exception, IOException;
}
