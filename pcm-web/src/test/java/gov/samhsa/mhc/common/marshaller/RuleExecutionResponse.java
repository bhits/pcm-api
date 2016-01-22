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
package gov.samhsa.mhc.common.marshaller;

import javax.xml.bind.annotation.XmlTransient;

/**
 * The Class RuleExecutionResponse.
 */
public class RuleExecutionResponse {
	
	/** The Constant ITEM_ACTION_REDACT. */
	public static final String ITEM_ACTION_REDACT = "REDACT";
	
	/** The Constant ITEM_ACTION_NO_ACTION. */
	public static final String ITEM_ACTION_NO_ACTION = "NO_ACTION";

    /** The Implied conf section. */
    private Confidentiality ImpliedConfSection;

    /** The Sensitivity. */
    private Sensitivity Sensitivity;
    
    /** The Item action. */
    private String ItemAction;
    
    /** The US privacy law. */
    private UsPrivacyLaw USPrivacyLaw;
    
    /** The Document obligation policy. */
    private ObligationPolicyDocument DocumentObligationPolicy;
    
    /** The Document refrain policy. */
    private RefrainPolicy DocumentRefrainPolicy;
    
    /** The clinical fact. */
    @XmlTransient
    private ClinicalFact clinicalFact;   
    
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
    
    /**
     * Instantiates a new rule execution response.
     */
    public RuleExecutionResponse() {
    	ItemAction = ITEM_ACTION_NO_ACTION;
    } 
   
    
    /**
     * Gets the clinical fact.
     *
     * @return the clinical fact
     */
    public ClinicalFact getClinicalFact() {
		return clinicalFact;
	}


	/**
	 * Sets the clinical fact.
	 *
	 * @param clinicalFact the new clinical fact
	 */
	public void setClinicalFact(ClinicalFact clinicalFact) {
		this.code = clinicalFact.getCode();
		this.codeSystem = clinicalFact.getCodeSystem();
		this.c32SectionLoincCode = clinicalFact.getC32SectionLoincCode();
		this.displayName = clinicalFact.getDisplayName();
		this.observationId = clinicalFact.getObservationId();
	}


	/**
     * Gets the implied conf section.
     *
     * @return the ImpliedConfSection
     */
    public Confidentiality getImpliedConfSection() {
        return ImpliedConfSection;
    }

    /**
     * Sets the implied conf section.
     *
     * @param ImpliedConfSection the ImpliedConfSection to set
     */
    public void setImpliedConfSection(Confidentiality ImpliedConfSection) {
        this.ImpliedConfSection = ImpliedConfSection;
    }

    /**
     * Gets the uS privacy law.
     *
     * @return the uS privacy law
     */
    public UsPrivacyLaw getUSPrivacyLaw() {
		return USPrivacyLaw;
	}

	/**
	 * Sets the uS privacy law.
	 *
	 * @param uSPrivacyLaw the new uS privacy law
	 */
	public void setUSPrivacyLaw(UsPrivacyLaw uSPrivacyLaw) {
		USPrivacyLaw = uSPrivacyLaw;
	}

	/**
	 * Gets the document obligation policy.
	 *
	 * @return the document obligation policy
	 */
	public ObligationPolicyDocument getDocumentObligationPolicy() {
		return DocumentObligationPolicy;
	}

	/**
	 * Sets the document obligation policy.
	 *
	 * @param documentObligationPolicy the new document obligation policy
	 */
	public void setDocumentObligationPolicy(
			ObligationPolicyDocument documentObligationPolicy) {
		DocumentObligationPolicy = documentObligationPolicy;
	}	
	
    /**
     * Gets the document refrain policy.
     *
     * @return the document refrain policy
     */
    public RefrainPolicy getDocumentRefrainPolicy() {
		return DocumentRefrainPolicy;
	}

	/**
	 * Sets the document refrain policy.
	 *
	 * @param documentRefrainPolicy the new document refrain policy
	 */
	public void setDocumentRefrainPolicy(RefrainPolicy documentRefrainPolicy) {
		DocumentRefrainPolicy = documentRefrainPolicy;
	}	

	/**
	 * Gets the sensitivity.
	 *
	 * @return the sensitivity
	 */
	public Sensitivity getSensitivity() {
		return Sensitivity;
	}

	/**
	 * Sets the sensitivity.
	 *
	 * @param sensitivity the new sensitivity
	 */
	public void setSensitivity(Sensitivity sensitivity) {
		Sensitivity = sensitivity;
	}

	/**
	 * Gets the item action.
	 *
	 * @return the ItemAction
	 */
    public String getItemAction() {
        return ItemAction;
    }

    /**
     * Sets the item action.
     *
     * @param ItemAction the ItemAction to set
     */
    public void setItemAction(String ItemAction) {
        this.ItemAction = ItemAction;
    }


	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
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
	 * Gets the code system name.
	 *
	 * @return the code system name
	 */
	public String getCodeSystemName() {
		return codeSystemName;
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
	 * Gets the c32 section title.
	 *
	 * @return the c32 section title
	 */
	public String getC32SectionTitle() {
		return c32SectionTitle;
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
	 * Gets the observation id.
	 *
	 * @return the observation id
	 */
	public String getObservationId() {
		return observationId;
	}


	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * Sets the code system.
	 *
	 * @param codeSystem the new code system
	 */
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}


	/**
	 * Sets the code system name.
	 *
	 * @param codeSystemName the new code system name
	 */
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}


	/**
	 * Sets the display name.
	 *
	 * @param displayName the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	/**
	 * Sets the c32 section title.
	 *
	 * @param c32SectionTitle the new c32 section title
	 */
	public void setC32SectionTitle(String c32SectionTitle) {
		this.c32SectionTitle = c32SectionTitle;
	}


	/**
	 * Sets the c32 section loinc code.
	 *
	 * @param c32SectionLoincCode the new c32 section loinc code
	 */
	public void setC32SectionLoincCode(String c32SectionLoincCode) {
		this.c32SectionLoincCode = c32SectionLoincCode;
	}


	/**
	 * Sets the observation id.
	 *
	 * @param observationId the new observation id
	 */
	public void setObservationId(String observationId) {
		this.observationId = observationId;
	}
}
