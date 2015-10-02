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
package gov.samhsa.consent2share.domain.clinicaldata;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.reference.SocialHistoryStatusCode;
import gov.samhsa.consent2share.domain.reference.SocialHistoryTypeCode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class SocialHistory.
 */
@Entity
public class SocialHistory {

    /** The social history free text. */
    private String socialHistoryFreeText;

    /** The social history type code. */
    @ManyToOne
    private SocialHistoryTypeCode socialHistoryTypeCode;

    /** The social history status code. */
    @ManyToOne
    private SocialHistoryStatusCode socialHistoryStatusCode;

    /** The social history start date. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date socialHistoryStartDate;

    /** The Social history end date. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date SocialHistoryEndDate;

    /** The patient. */
    @ManyToOne
    private Patient patient;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	/** The version. */
	@Version
    @Column(name = "version")
    private Integer version;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
        return this.id;
    }

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
        this.id = id;
    }

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
        return this.version;
    }

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Integer version) {
        this.version = version;
    }

	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	/**
	 * From json to social history.
	 *
	 * @param json the json
	 * @return the social history
	 */
	public static SocialHistory fromJsonToSocialHistory(String json) {
        return new JSONDeserializer<SocialHistory>().use(null, SocialHistory.class).deserialize(json);
    }

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<SocialHistory> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	/**
	 * From json array to social historys.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<SocialHistory> fromJsonArrayToSocialHistorys(String json) {
        return new JSONDeserializer<List<SocialHistory>>().use(null, ArrayList.class).use("values", SocialHistory.class).deserialize(json);
    }

	/**
	 * Gets the social history free text.
	 *
	 * @return the social history free text
	 */
	public String getSocialHistoryFreeText() {
        return this.socialHistoryFreeText;
    }

	/**
	 * Sets the social history free text.
	 *
	 * @param socialHistoryFreeText the new social history free text
	 */
	public void setSocialHistoryFreeText(String socialHistoryFreeText) {
        this.socialHistoryFreeText = socialHistoryFreeText;
    }

	/**
	 * Gets the social history type code.
	 *
	 * @return the social history type code
	 */
	public SocialHistoryTypeCode getSocialHistoryTypeCode() {
        return this.socialHistoryTypeCode;
    }

	/**
	 * Sets the social history type code.
	 *
	 * @param socialHistoryTypeCode the new social history type code
	 */
	public void setSocialHistoryTypeCode(SocialHistoryTypeCode socialHistoryTypeCode) {
        this.socialHistoryTypeCode = socialHistoryTypeCode;
    }

	/**
	 * Gets the social history status code.
	 *
	 * @return the social history status code
	 */
	public SocialHistoryStatusCode getSocialHistoryStatusCode() {
        return this.socialHistoryStatusCode;
    }

	/**
	 * Sets the social history status code.
	 *
	 * @param socialHistoryStatusCode the new social history status code
	 */
	public void setSocialHistoryStatusCode(SocialHistoryStatusCode socialHistoryStatusCode) {
        this.socialHistoryStatusCode = socialHistoryStatusCode;
    }

	/**
	 * Gets the social history start date.
	 *
	 * @return the social history start date
	 */
	public Date getSocialHistoryStartDate() {
        return this.socialHistoryStartDate;
    }

	/**
	 * Sets the social history start date.
	 *
	 * @param socialHistoryStartDate the new social history start date
	 */
	public void setSocialHistoryStartDate(Date socialHistoryStartDate) {
        this.socialHistoryStartDate = socialHistoryStartDate;
    }

	/**
	 * Gets the social history end date.
	 *
	 * @return the social history end date
	 */
	public Date getSocialHistoryEndDate() {
        return this.SocialHistoryEndDate;
    }

	/**
	 * Sets the social history end date.
	 *
	 * @param SocialHistoryEndDate the new social history end date
	 */
	public void setSocialHistoryEndDate(Date SocialHistoryEndDate) {
        this.SocialHistoryEndDate = SocialHistoryEndDate;
    }

	/**
	 * Gets the patient.
	 *
	 * @return the patient
	 */
	public Patient getPatient() {
        return this.patient;
    }

	/**
	 * Sets the patient.
	 *
	 * @param patient the new patient
	 */
	public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
