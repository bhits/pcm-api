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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * The Enum SubjectPurposeOfUse.
 */
@XmlEnum
public enum SubjectPurposeOfUse {
	@XmlEnumValue("TREATMENT")
	HEALTHCARE_TREATMENT("TREATMENT"),
	@XmlEnumValue("PAYMENT")
	PAYMENT("PAYMENT"),
	@XmlEnumValue("EMERGENCY")
	EMERGENCY_TREATMENT("EMERGENCY"),
	@XmlEnumValue("RESEARCH")
	RESEARCH("RESEARCH");
	
    private final String purpose;

    SubjectPurposeOfUse(String p) {purpose = p;}

    public static SubjectPurposeOfUse fromValue(String v) {
        return valueOf(v);
    }
    public String getPurpose() {return purpose;}
    
	public static SubjectPurposeOfUse fromAbbreviation(String purposeOfUse){
		for(SubjectPurposeOfUse p: SubjectPurposeOfUse.values()){
			if(p.getPurpose().equals(purposeOfUse)){
				return p;
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append("The abbreviation '");
		builder.append(purposeOfUse);
		builder.append("' is not defined in this enum.");
		throw new IllegalArgumentException(builder.toString());
	}
}
