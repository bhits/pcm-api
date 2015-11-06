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
package gov.samhsa.pcm.domain.consent;

import gov.samhsa.pcm.domain.valueset.MedicalSection;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

/**
 * The Class ConsentDoNotShareClinicalDocumentSectionTypeCode.
 */
@Embeddable
public class ConsentDoNotShareClinicalDocumentSectionTypeCode {
	
	/** The clinical document section type code. */
	@NotNull
	@ManyToOne
	private MedicalSection medicalSection;
	
	/**
	 * Instantiates a new consent do not share clinical document section type code.
	 */
	@SuppressWarnings("unused")
	private ConsentDoNotShareClinicalDocumentSectionTypeCode(){}

	/**
	 * Instantiates a new consent do not share clinical document section type code.
	 *
	 * @param clinicalDocumentSectionTypeCode the clinical document section type code
	 */
	public ConsentDoNotShareClinicalDocumentSectionTypeCode(
			MedicalSection medicalSection) {
		Assert.notNull(medicalSection,
				"ClinicalDocumentSectionTypeCode is required.");
		this.medicalSection = medicalSection;
	}
	
	/**
	 * Gets the clinical document section type code.
	 *
	 * @return the clinical document section type code
	 */
	public MedicalSection getMedicalSection() {
		return medicalSection;
	}
}
