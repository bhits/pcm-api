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
package gov.samhsa.pcm.service.consent;

import gov.samhsa.pcm.domain.consent.Consent;
import gov.samhsa.pcm.domain.consent.ConsentRepository;
import gov.samhsa.pcm.domain.systemnotification.SystemNotification;
import gov.samhsa.pcm.domain.systemnotification.SystemNotificationRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ConsentEffectiveAndToBeExpiredPollingService.
 */
@Transactional
public class ConsentEffectiveAndToBeExpiredPollingService {

	/**
	 * Instantiates a new consent effective and to be expired polling service.
	 */
	public ConsentEffectiveAndToBeExpiredPollingService() {
		super();
	}
	/**
	 * Instantiates a new consent effective and to be expired polling service.
	 *
	 * @param daysToBeExpiredToSendNotification
	 *            the days to be expired to send notification
	 * @param notificationEffectiveMessage
	 *            the notification effective message
	 * @param notificationExpireMessage
	 *            the notification expire message
	 * @param consentRepository
	 *            the consent repository
	 * @param systemNotificationRepository
	 *            the system notification repository
	 */
	public ConsentEffectiveAndToBeExpiredPollingService(
			String daysToBeExpiredToSendNotification,
			String notificationEffectiveMessage,
			String notificationExpireMessage,
			ConsentRepository consentRepository,
			SystemNotificationRepository systemNotificationRepository) {
		super();
		this.daysToBeExpiredToSendNotification = Integer
				.parseInt(daysToBeExpiredToSendNotification);
		this.notificationEffectiveMessage = notificationEffectiveMessage;
		this.notificationExpireMessage = notificationExpireMessage;
		this.consentRepository = consentRepository;
		this.systemNotificationRepository = systemNotificationRepository;
	}

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Signed_ staus. */
	private final String Signed_Staus = "SIGNED";

	/** The days to be expired to send notification. */
	private int daysToBeExpiredToSendNotification;

	/** The notification effective message. */
	private String notificationEffectiveMessage;

	/** The notification expire message. */
	private String notificationExpireMessage;

	/** The consent repository. */
	private ConsentRepository consentRepository;

	/** The notification repository. */
	private SystemNotificationRepository systemNotificationRepository;

	/**
	 * Consent send notification.
	 *
	 * @param consent
	 *            the consent
	 * @param notificationMessage
	 *            the notification message
	 * @param notificationType
	 *            the notification type
	 */
	public void consentSendNotification(Consent consent,
			String notificationMessage, String notificationType) {
		SystemNotification notification = new SystemNotification();
		notification.setConsentId(consent.getId());
		notification.setPatientId(consent.getPatient().getId());
		if (notificationType.equals("consent_expires"))
			notification.setNotificationMessage(notificationMessage.replaceAll(
					"30",
					((Integer) Days.daysBetween(new DateTime(new Date()),
							new DateTime(consent.getEndDate())).getDays())
							.toString()));
		else
			notification.setNotificationMessage(notificationMessage);
		notification.setNotificationType(notificationType);
		notification.setSendDate(new Date());

		systemNotificationRepository.save(notification);
	}

	/**
	 * Poll.
	 * @return
	 */
	@Scheduled(cron="${taskScheduler.consentToBeExpiredPolling.cron}")
	public void poll() {
		try {

			Set<Consent> consentSet = new HashSet<Consent>();
			consentSet
			.addAll(consentRepository
					.findAllBySignedPdfConsentDocumentSignedStatus(Signed_Staus));

			consentSet
			.removeAll(consentRepository
					.findAllBySignedPdfConsentRevokeDocumentSignedStatus(Signed_Staus));

			for (Consent consent : consentSet) {

				if (consent.getStartDate().before(new Date())
						&& consent.getEndDate().after(new Date())) {
					SystemNotification sy=systemNotificationRepository
					.findByConsentIdAndNotificationType(
							consent.getId(), "consent_is_effective");
							
					if (systemNotificationRepository
							.findByConsentIdAndNotificationType(
									consent.getId(), "consent_is_effective") == null)
						consentSendNotification(consent,
								notificationEffectiveMessage,
								"consent_is_effective");

					if (Days.daysBetween(new DateTime(new Date()),
							new DateTime(consent.getEndDate())).getDays() <= daysToBeExpiredToSendNotification
							&& systemNotificationRepository
							.findByConsentIdAndNotificationType(
									consent.getId(), "consent_expires") == null)
						consentSendNotification(consent,
								notificationExpireMessage, "consent_expires");

				}

			}
		} catch (Exception e) {
			logger.error(new Date().toString());
			logger.error("Error occurred:", e);
		}
	}
}
