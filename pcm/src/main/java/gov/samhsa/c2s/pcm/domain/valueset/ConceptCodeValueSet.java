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
package gov.samhsa.c2s.pcm.domain.valueset;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="conceptcode_valueset")
@AssociationOverrides({
	@AssociationOverride(name = "pk.conceptCode", joinColumns = @JoinColumn(name = "fk_concept_code_id")),
	@AssociationOverride(name = "pk.valueSet", joinColumns = @JoinColumn(name = "fk_valueset_id")) })
public class ConceptCodeValueSet implements Serializable {

	private static final long serialVersionUID = -4000325032501400417L;
	
	@EmbeddedId
	ConceptCodeValueSetId pk = new ConceptCodeValueSetId();
	
	@Transient
	private ConceptCode conceptCode;
	
	@Transient
	private ValueSet valueSet;	
	
	//it is required by Hibernate
	public ConceptCodeValueSet(){
		
	}
	/**
     * A Builder class used to create new CodeSystem objects.
     */
    public static class Builder {
    	ConceptCodeValueSet built;

        /**
         * Creates a new Builder instance.
         * @param code  The code of the created ConceptCodeValueSetVersion object.
         * @param conceptCode  The conceptCode of the created  ConceptCodeValueSetVersion object.
         * @param valueSet  The valueSet of the created  ConceptCodeValueSetVersion object.
         */
        Builder(ConceptCode conceptCode, ValueSet valueSet) {
            built = new ConceptCodeValueSet();
            built.setConceptCode(conceptCode);
            built.setValueSet(valueSet);
         }
        
        /**
         * Builds the new CodeSystem object.
         * @return  The created CodeSystem object.
         */
        public ConceptCodeValueSet build() {
            return built;
        }
   
    }
	
    /**
     * Creates a new Builder instance.
     * @param code  The code of the created ConceptCodeValueSetVersion object.
     * @param conceptCode  The conceptCode of the created  ConceptCodeValueSetVersion object.
     * @param valueSetVersion  The valueSetVersion of the created  ConceptCodeValueSetVersion object.
     */
    public static Builder getBuilder(ConceptCode conceptCode,ValueSet valueSet) {
        return new Builder(conceptCode, valueSet);
    }
    

	public ConceptCodeValueSetId getPk() {
		return pk;
	}


	public void setPk(ConceptCodeValueSetId pk) {
		this.pk = pk;
	}


	public ConceptCode getConceptCode() {
		return getPk().getConceptCode();
	}

	public void setConceptCode(ConceptCode conceptCode) {
		getPk().setConceptCode(conceptCode);
	}

	public ValueSet getValueSet() {
		return getPk().getValueSet();
	}

	public void setValueSet(ValueSet valueSet) {
		getPk().setValueSet(valueSet);
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ConceptCodeValueSet that = (ConceptCodeValueSet) o;

		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}	
}
