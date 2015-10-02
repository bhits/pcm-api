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
package gov.samhsa.acs.documentsegmentation.valueset.dto;

import java.util.Set;

/**
 * The Class ValueSetQueryDto.
 */
public class ValueSetQueryDto {

	/** The concept code. */
	private String conceptCode;

	/** The code system oid. */
	private String codeSystemOid;

	/** The vs category codes. */
	private Set<String> vsCategoryCodes;

	/**
	 * Gets the concept code.
	 * 
	 * @return the concept code
	 */
	public String getConceptCode() {
		return conceptCode;
	}

	/**
	 * Sets the concept code.
	 * 
	 * @param conceptCode
	 *            the new concept code
	 */
	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}

	/**
	 * Gets the code system oid.
	 * 
	 * @return the code system oid
	 */
	public String getCodeSystemOid() {
		return codeSystemOid;
	}

	/**
	 * Sets the code system oid.
	 * 
	 * @param codeSystemOid
	 *            the new code system oid
	 */
	public void setCodeSystemOid(String codeSystemOid) {
		this.codeSystemOid = codeSystemOid;
	}

	/**
	 * Gets the vs category codes.
	 * 
	 * @return the vs category codes
	 */
	public Set<String> getVsCategoryCodes() {
		return vsCategoryCodes;
	}

	/**
	 * Sets the vs category codes.
	 * 
	 * @param vsCategoryCodes
	 *            the new vs category codes
	 */
	public void setVsCategoryCodes(Set<String> vsCategoryCodes) {
		this.vsCategoryCodes = vsCategoryCodes;
	}
}
