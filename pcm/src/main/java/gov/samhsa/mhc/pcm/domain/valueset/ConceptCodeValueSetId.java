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
package gov.samhsa.mhc.pcm.domain.valueset;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ConceptCodeValueSetId implements Serializable{

	private static final long serialVersionUID = -2133911199050221007L;
	
	@ManyToOne
	private ConceptCode conceptCode;
	
	@ManyToOne
	private ValueSet valueSet;	
	
	public ConceptCode getConceptCode() {
		return conceptCode;
	}

	public void setConceptCode(ConceptCode conceptCode) {
		this.conceptCode = conceptCode;
	}

	public ValueSet getValueSet() {
		return valueSet;
	}

	public void setValueSet(ValueSet valueSet) {
		this.valueSet = valueSet;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConceptCodeValueSetId that = (ConceptCodeValueSetId) o;

        if (conceptCode != null ? !conceptCode.equals(that.conceptCode) : that.conceptCode != null) return false;
        if (valueSet != null ? !valueSet.equals(that.valueSet) : that.valueSet != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (conceptCode != null ? conceptCode.hashCode() : 0);
        result = 31 * result + (valueSet != null ? valueSet.hashCode() : 0);
        return result;
    }

}
