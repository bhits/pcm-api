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

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * The Class ClinicalFact.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ClinicalFact {

	/** The code. */
	private String code;

	/** The code system. */
	private String codeSystem;

	/** The code system name. */
	private String codeSystemName;

	/** The display name. */
	private String displayName;

	/** The c32 section title. */
	private String c32SectionTitle;

	/** The c32 section loinc code. */
	private String c32SectionLoincCode;

	/** The observation id. */
	private String observationId;

	/** The entry. */
	private String entry;

	/** The value set categories. */
	@XmlElementWrapper(name = "valueSetCategories")
	@XmlElement(name = "valueSetCategory")
	private Set<String> valueSetCategories = new HashSet<String>();

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the code system.
	 * 
	 * @return the code system
	 */
	public String getCodeSystem() {
		return codeSystem;
	}

	/**
	 * Sets the code system.
	 * 
	 * @param codeSystem
	 *            the new code system
	 */
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	/**
	 * Gets the code system name.
	 * 
	 * @return the code system name
	 */
	public String getCodeSystemName() {
		return codeSystemName;
	}

	/**
	 * Sets the code system name.
	 * 
	 * @param codeSystemName
	 *            the new code system name
	 */
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}

	/**
	 * Gets the display name.
	 * 
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 * 
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the c32 section title.
	 * 
	 * @return the c32 section title
	 */
	public String getC32SectionTitle() {
		return c32SectionTitle;
	}

	/**
	 * Sets the c32 section title.
	 * 
	 * @param c32SectionTitle
	 *            the new c32 section title
	 */
	public void setC32SectionTitle(String c32SectionTitle) {
		this.c32SectionTitle = c32SectionTitle;
	}

	/**
	 * Gets the c32 section loinc code.
	 * 
	 * @return the c32 section loinc code
	 */
	public String getC32SectionLoincCode() {
		return c32SectionLoincCode;
	}

	/**
	 * Sets the c32 section loinc code.
	 * 
	 * @param c32SectionLoincCode
	 *            the new c32 section loinc code
	 */
	public void setC32SectionLoincCode(String c32SectionLoincCode) {
		this.c32SectionLoincCode = c32SectionLoincCode;
	}

	/**
	 * Gets the observation id.
	 * 
	 * @return the observation id
	 */
	public String getObservationId() {
		return observationId;
	}

	/**
	 * Sets the observation id.
	 * 
	 * @param observationId
	 *            the new observation id
	 */
	public void setObservationId(String observationId) {
		this.observationId = observationId;
	}

	/**
	 * Gets the entry.
	 * 
	 * @return the entry
	 */
	public String getEntry() {
		return entry;
	}

	/**
	 * Sets the entry.
	 * 
	 * @param entry
	 *            the new entry
	 */
	public void setEntry(String entry) {
		this.entry = entry;
	}

	/**
	 * Gets the value set categories.
	 * 
	 * @return the value set categories
	 */
	public Set<String> getValueSetCategories() {
		return valueSetCategories;
	}

	/**
	 * Sets the value set categories.
	 * 
	 * @param valueSetCategories
	 *            the new value set categories
	 */
	public void setValueSetCategories(Set<String> valueSetCategories) {
		this.valueSetCategories = valueSetCategories;
	}

	/**
	 * Adds the value set category.
	 *
	 * @param valueSetCategory the value set category
	 */
	public void addValueSetCategory(String valueSetCategory){
		this.valueSetCategories.add(valueSetCategory);
	}
}
