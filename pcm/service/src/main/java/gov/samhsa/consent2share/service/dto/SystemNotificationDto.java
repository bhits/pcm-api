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
package gov.samhsa.consent2share.service.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class SystemNotificationDto.
 */
public class SystemNotificationDto {

	/** The id. */
	private long id;

	/** The notification message. */
	private String notificationMessage;

	/** The notification type. */
	private String notificationType;

	/** The patient id. */
	private long patientId;

	/** The consent id. */
	private long consentId;

	/** The dismissed. */
	private boolean dismissed;

	/** The triggered. */
	private boolean triggered;

	/** The send date. */
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date sendDate;

	/**
	 * Gets the notification message.
	 *
	 * @return the notification message
	 */
	public String getNotificationMessage() {
		return notificationMessage;
	}

	/**
	 * Sets the notification message.
	 *
	 * @param notificationMessage
	 *            the new notification message
	 */
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	/**
	 * Checks if is dismissed.
	 *
	 * @return true, if is dismissed
	 */
	public boolean isDismissed() {
		return dismissed;
	}

	/**
	 * Sets the dismissed.
	 *
	 * @param dismissed
	 *            the new dismissed
	 */
	public void setDismissed(boolean dismissed) {
		this.dismissed = dismissed;
	}

	/**
	 * Checks if is triggered.
	 *
	 * @return true, if is triggered
	 */
	public boolean isTriggered() {
		return triggered;
	}

	/**
	 * Sets the triggered.
	 *
	 * @param triggered
	 *            the new triggered
	 */
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	/**
	 * Gets the send date.
	 *
	 * @return the send date
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * Sets the send date.
	 *
	 * @param sendDate
	 *            the new send date
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
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
	 * @param patientId
	 *            the new patient id
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	/**
	 * Gets the consent id.
	 *
	 * @return the consent id
	 */
	public long getConsentId() {
		return consentId;
	}

	/**
	 * Sets the consent id.
	 *
	 * @param consentId
	 *            the new consent id
	 */
	public void setConsentId(long consentId) {
		this.consentId = consentId;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the notification type.
	 *
	 * @return the notification type
	 */
	public String getNotificationType() {
		return notificationType;
	}

	/**
	 * Sets the notification type.
	 *
	 * @param notificationType
	 *            the new notification type
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

}
