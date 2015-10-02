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
package gov.samhsa.consent2share.service.notification;

import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

/**
 * The Class NotificationServiceImpl.
 */
@Transactional
public class NotificationServiceImpl implements NotificationService {

	/** The patient repository. */
	private PatientRepository patientRepository;

	/**
	 * Instantiates a new notification service impl.
	 *
	 * @param patientRepository
	 *            the patient repository
	 */
	public NotificationServiceImpl(PatientRepository patientRepository) {
		super();
		this.patientRepository = patientRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.notification.NotificationService#
	 * notificationStage(java.lang.String, java.lang.String)
	 */
	@Override
	public String notificationStage(String username, String notify) {
		Patient patient = patientRepository.findByUsername(username);
		int providerscount = patient.getIndividualProviders().size()
				+ patient.getOrganizationalProviders().size();
		int consentscount = patient.getConsents().size();
		boolean consentReviewStatus = checkConsentReviewStatus(patient
				.getConsents());
		boolean consentSignedStatus = checkConsentSignedStatus(patient
				.getConsents());

		switch (consentscount) {
		case 0: {
			switch (providerscount) {
			case 0:
				return "notification_add_provider";
			case 1: {
				if (notify != null) {
					if (notify.equals("add"))
						return "notification_add_one_provider_successed";
				} else
					return "notification_add_second_provider";
			}
			case 2: {
				if (notify != null) {
					if (notify.equals("add"))
						return "notification_add_second_provider_successed";
				} else
					return "notification_add_consent";
			}
			default:
				return "notification_add_consent";

			}
		}
		case 1: {
			if (providerscount < 2)
				return "notification_add_provider";
			else {
				if (notify != null) {
					if (notify.equals("add"))
						return "notification_add_consent_successed";
				} else if (consentReviewStatus == false)
					return "notification_review_sign_consent";
				else {
					if (consentSignedStatus == false)
						return "notification_sign_consent";
				}
			}
		}
		default: {
			if (providerscount < 2)
				return "notification_add_provider";
			else {
				if (consentReviewStatus == false)
					return "notification_review_sign_consent";
				else {
					if (consentSignedStatus == false)
						return "notification_sign_consent";
				}

				return null;
			}
		}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.notification.NotificationService#
	 * checkConsentReviewStatus(java.util.Set)
	 */
	@Override
	public boolean checkConsentReviewStatus(Set<Consent> consents) {
		if (consents.size() == 0)
			return false;
		for (Consent consent : consents) {
			if (consent.getSignedPdfConsent() != null)
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.notification.NotificationService#
	 * checkConsentSignedStatus(java.util.Set)
	 */
	@Override
	public boolean checkConsentSignedStatus(Set<Consent> consents) {
		if (consents.size() == 0)
			return false;
		for (Consent consent : consents) {
			if (consent.getSignedPdfConsent() != null) {
				if (consent.getSignedPdfConsent().getSignedPdfConsentContent() != null)
					return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.notification.NotificationService#
	 * notificationValuesetMgmt(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String notificationValuesetMgmt(String username, String notify,
			String message) {

		String msg = (notify != null) ? message : "";

		return msg;
	}
}
