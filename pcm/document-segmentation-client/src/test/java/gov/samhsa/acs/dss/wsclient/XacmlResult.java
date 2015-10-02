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
package gov.samhsa.acs.dss.wsclient;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class XacmlResult.
 */
@XmlRootElement(name="xacmlResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class XacmlResult {
	
	/** The pdp decision. */
	private String pdpDecision;
	
	/** The subject purpose of use. */
	@XmlElement(name="purposeOfUse")
	private SubjectPurposeOfUse subjectPurposeOfUse;
	
	/** The message id. */
	private String messageId;
	
	/** The home community id. */
	private String homeCommunityId;
	
	/** The pdp obligations. */
	@XmlElement(name="pdpObligation")
	private List<String> pdpObligations;
	
	/** The patient id. */
	private String patientId;
	
	/**
	 * Instantiates a new xacml result.
	 */
	public XacmlResult(){
		pdpObligations = new LinkedList<String>();
	}
			
	/**
	 * Gets the home community id.
	 *
	 * @return the home community id
	 */
	public String getHomeCommunityId() {
		return homeCommunityId;
	}
	
	/**
	 * Sets the home community id.
	 *
	 * @param homeCommunityId the new home community id
	 */
	public void setHomeCommunityId(String homeCommunityId) {
		this.homeCommunityId = homeCommunityId;
	}
	
	/**
	 * Gets the message id.
	 *
	 * @return the message id
	 */
	public String getMessageId() {
		return messageId;
	}
	
	/**
	 * Sets the message id.
	 *
	 * @param messageId the new message id
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	/**
	 * Gets the pdp decision.
	 *
	 * @return the pdp decision
	 */
	public String getPdpDecision() {
		return pdpDecision;
	}
	
	/**
	 * Sets the pdp decision.
	 *
	 * @param pdpDecision the new pdp decision
	 */
	public void setPdpDecision(String pdpDecision) {
		this.pdpDecision = pdpDecision;
	}	
	
	/**
	 * Gets the subject purpose of use.
	 *
	 * @return the subject purpose of use
	 */
	public SubjectPurposeOfUse getSubjectPurposeOfUse() {
		return subjectPurposeOfUse;
	}

	/**
	 * Sets the subject purpose of use.
	 *
	 * @param subjectPurposeOfUse the new subject purpose of use
	 */
	public void setSubjectPurposeOfUse(SubjectPurposeOfUse subjectPurposeOfUse) {
		this.subjectPurposeOfUse = subjectPurposeOfUse;
	}

	/**
	 * Gets the pdp obligations.
	 *
	 * @return the pdp obligations
	 */
	public List<String> getPdpObligations() {
		return pdpObligations;
	}
	
	/**
	 * Sets the pdp obligations.
	 *
	 * @param pdpObligations the new pdp obligations
	 */
	public void setPdpObligations(List<String> pdpObligations) {
		this.pdpObligations = pdpObligations;
	}

	/**
	 * Gets the patient id.
	 *
	 * @return the patient id
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * Sets the patient id.
	 *
	 * @param patientId the new patient id
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}	
}
