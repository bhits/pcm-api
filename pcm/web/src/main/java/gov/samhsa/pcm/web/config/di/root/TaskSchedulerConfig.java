package gov.samhsa.pcm.web.config.di.root;

import gov.samhsa.pcm.domain.consent.ConsentRepository;
import gov.samhsa.pcm.domain.systemnotification.SystemNotificationRepository;
import gov.samhsa.pcm.esignaturepolling.EchoSignPollingService;
import gov.samhsa.pcm.esignaturepolling.EsignaturePollingService;
import gov.samhsa.pcm.infrastructure.EchoSignSignatureService;
import gov.samhsa.pcm.service.consent.ConsentEffectiveAndToBeExpiredPollingService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Refer to Java Doc for @org.springframework.scheduling.annotation.EnableScheduling to see the detailed explanation.
 *
 */

@Configuration
@EnableScheduling
public class TaskSchedulerConfig implements SchedulingConfigurer {
	@Value("${daysToBeExpiredToSendNotification}")
	private String daysToBeExpiredToSendNotification;

	@Value("${notification_consent_is_effective}")
	private String notification_consent_is_effective;

	@Value("${notification_consent_expires_30_days}")
	private String notification_consent_expires_30_days;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskScheduler() {
		return Executors.newScheduledThreadPool(10);

	}

	// This bean's poll method has @Scheduled annotation
	//TODO: Don’t use @Scheduled inside code other than in @Configuration classes
	@Bean
	public EsignaturePollingService esignaturePollingService(ConsentRepository consentRepository, EchoSignSignatureService echoSignSignatureService) {
		EchoSignPollingService esignaturePollingService = new EchoSignPollingService(consentRepository, echoSignSignatureService);
		return esignaturePollingService;
	}

	// This bean's poll method has @Scheduled annotation
	//TODO: Don’t use @Scheduled inside code other than in @Configuration classes
	@Bean
	public ConsentEffectiveAndToBeExpiredPollingService consentEffectiveAndToBeExpiredPollingService(ConsentRepository consentRepository,
			SystemNotificationRepository systemNotificationRepository) {
		ConsentEffectiveAndToBeExpiredPollingService consentEffectiveAndToBeExpiredPollingService = new ConsentEffectiveAndToBeExpiredPollingService(
				daysToBeExpiredToSendNotification,
				notification_consent_is_effective,
				notification_consent_expires_30_days, consentRepository,
				systemNotificationRepository);
		return consentEffectiveAndToBeExpiredPollingService;
	}
}
