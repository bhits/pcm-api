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
package gov.samhsa.bhits.pcm.domain.account;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailToken.
 */
@Entity
public class EmailToken {
	
	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	/** The version. */
	@Version
    @Column(name = "version")
    private Integer version;
	
	/** The username. */
    @Size(min = 3, max = 30)
    private String username;
	
	/** The patientId. */
    private long patientId;
	
	/** The token. */
	@NotNull
	@Size(max  = 100)
	private String token;
	
	/** The request date time. */
	@NotNull
	private Date requestDateTime;
	
	/** The expire in hours. */
	@NotNull
	private Integer expireInHours;
	
	/** The token type. */
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private TokenType tokenType;	
	
	/** The is token used. */
	private Boolean isTokenUsed;	

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the request date time.
	 *
	 * @return the request date time
	 */
	public Date getRequestDateTime() {
		return requestDateTime;
	}

	/**
	 * Sets the request date time.
	 *
	 * @param requestDateTime the new request date time
	 */
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	/**
	 * Gets the expire in hours.
	 *
	 * @return the expire in hours
	 */
	public Integer getExpireInHours() {
		return expireInHours;
	}

	/**
	 * Sets the expire in hours.
	 *
	 * @param expireInHours the new expire in hours
	 */
	public void setExpireInHours(Integer expireInHours) {
		this.expireInHours = expireInHours;
	}

	/**
	 * Gets the expired date.
	 *
	 * @return the expired date
	 */
	public Date getExpiredDate(){
		Calendar cal = Calendar.getInstance();
	    cal.setTime(requestDateTime); 
	    
	    cal.add(Calendar.HOUR_OF_DAY, expireInHours);
	    Date expiredDate = cal.getTime();
	    
	    return expiredDate;
	}
	
	/**
	 * Checks if is token expired.
	 *
	 * @return the boolean
	 */
	public Boolean isTokenExpired(){
		if (!isTokenUsed) {
			Date now = new Date();
			Boolean expired = true;
			if (now.before(getExpiredDate())) {
				expired = false;
			}
			return expired;
		}
		else
			return true;
	}

	/**
	 * Gets the token type.
	 *
	 * @return the token type
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Sets the token type.
	 *
	 * @param tokenType the new token type
	 */
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Gets the checks if is token used.
	 *
	 * @return the checks if is token used
	 */
	public Boolean getIsTokenUsed() {
		return isTokenUsed;
	}

	/**
	 * Sets the checks if is token used.
	 *
	 * @param isTokenUsed the new checks if is token used
	 */
	public void setIsTokenUsed(Boolean isTokenUsed) {
		this.isTokenUsed = isTokenUsed;
	}

	/**
	 * Gets the patient id.
	 *
	 * @return the patient id
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * Sets the patient id.
	 *
	 * @param patientId the new patient id
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
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
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}	
	
	
	
}
