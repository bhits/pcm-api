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
package gov.samhsa.acs.brms.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class FactModel.
 */
@XmlRootElement(name="FactModel")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactModel {
	
	/** The XACML result. */
	private XacmlResult xacmlResult;
	
	/** The clinical fact list. */
	@XmlElementWrapper(name="ClinicalFacts")
	@XmlElement(name="ClinicalFact")
	private List<ClinicalFact> clinicalFactList = new ArrayList<ClinicalFact>();
	
	/** The entry references. */
	@XmlElementWrapper(name="EntryReferences")
	@XmlElement(name="EntryReference")
	private List<EntryReference> entryReferences  = new LinkedList<EntryReference>();
		
	/**
	 * Gets the XACML result.
	 *
	 * @return the XACML result
	 */
	public XacmlResult getXacmlResult() {
		return xacmlResult;
	}
	
	/**
	 * Sets the XACML result.
	 *
	 * @param xacmlResult the new XACML result
	 */
	public void setXacmlResult(XacmlResult xacmlResult) {
		this.xacmlResult = xacmlResult;
	}
	
	/**
	 * Gets the clinical fact list.
	 *
	 * @return the clinical fact list
	 */
	public List<ClinicalFact> getClinicalFactList() {
		return clinicalFactList;
	}
	
	/**
	 * Sets the clinical fact list.
	 *
	 * @param clinicalFacts the new clinical fact list
	 */
	public void setClinicalFactList(List<ClinicalFact> clinicalFacts) {
		this.clinicalFactList = clinicalFacts;
	}

	/**
	 * Gets the entry references.
	 *
	 * @return the entry references
	 */
	public List<EntryReference> getEntryReferences() {
		return entryReferences;
	}

	/**
	 * Sets the entry references.
	 *
	 * @param entryReferences the new entry references
	 */
	public void setEntryReferences(List<EntryReference> entryReferences) {
		this.entryReferences = entryReferences;
	}	
}
