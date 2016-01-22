package gov.samhsa.mhc.pcm.web.di;

import gov.samhsa.mhc.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.mhc.pcm.infrastructure.EmailSenderImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
public class EmailSenderConfig {

    @Bean
    public EmailSender emailSender(JavaMailSender mailSender,
                                   SpringTemplateEngine templateEngine,
                                   MessageSource messageSource) {
        return new EmailSenderImpl(mailSender, templateEngine, messageSource);
    }
}
