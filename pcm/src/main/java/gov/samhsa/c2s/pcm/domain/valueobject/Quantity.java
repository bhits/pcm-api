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
package gov.samhsa.c2s.pcm.domain.valueobject;

import gov.samhsa.c2s.pcm.domain.reference.UnitOfMeasureCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * The Class Quantity.
 */
@Embeddable
public class Quantity {

    /** The measured value. */
    @NotNull
    private Double measuredValue;

    /** The unit of measure code. */
    @ManyToOne
    private UnitOfMeasureCode unitOfMeasureCode;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	/**
	 * Gets the measured value.
	 *
	 * @return the measured value
	 */
	public Double getMeasuredValue() {
        return this.measuredValue;
    }

	/**
	 * Sets the measured value.
	 *
	 * @param measuredValue the new measured value
	 */
	public void setMeasuredValue(Double measuredValue) {
        this.measuredValue = measuredValue;
    }

	/**
	 * Gets the unit of measure code.
	 *
	 * @return the unit of measure code
	 */
	public UnitOfMeasureCode getUnitOfMeasureCode() {
        return this.unitOfMeasureCode;
    }

	/**
	 * Sets the unit of measure code.
	 *
	 * @param unitOfMeasureCode the new unit of measure code
	 */
	public void setUnitOfMeasureCode(UnitOfMeasureCode unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
    }
}
