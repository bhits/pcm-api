package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.bhits.pcm.infrastructure.EmailSenderImpl;
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
