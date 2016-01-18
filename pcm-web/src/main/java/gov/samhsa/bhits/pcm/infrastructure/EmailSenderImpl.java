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
package gov.samhsa.bhits.pcm.infrastructure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import gov.samhsa.bhits.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.bhits.pcm.domain.commondomainservices.EmailType;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * The Class EmailSenderImpl.
 */
public class EmailSenderImpl implements EmailSender {

	/** The mail sender. */
	private JavaMailSender mailSender;

	/** The template engine. */
	private TemplateEngine templateEngine;

	/** The message properties. */
	private MessageSource messageProperties;

	/**
	 * Instantiates a new email sender impl.
	 *
	 * @param mailSender
	 *            the mail sender
	 * @param templateEngine
	 *            the template engine
	 * @param messageProperties
	 *            the message properties
	 */
	public EmailSenderImpl(JavaMailSender mailSender,
			TemplateEngine templateEngine, MessageSource messageProperties) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
		this.messageProperties = messageProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.domain.commondomainservices.EmailSender#sendMessage
	 * (java.lang.String, java.lang.String,
	 * gov.samhsa.consent2share.infrastructure.EmailType, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void sendMessage(final String recipientName, final String mailTo, EmailType type, String linkUrl, String token)
			throws MessagingException {
		// Prepare the evaluation context
		final Context ctx = new Context();
		String htmlContent = null;

		ctx.setVariable("recipientName", recipientName);
		ctx.setVariable("c2sLogoUrl",
				String.format("%s/resources/img/c2s-logo.png", linkUrl));

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
				"UTF-8");

		switch (type) {
		case SIGNUP_VERIFICATION:
			ctx.setVariable("linkUrl",
					String.format("%s/verifyLink.html%s", linkUrl, token));

			message.setFrom(messageProperties.getMessage(
					"Signup.email.emailFrom", null, null));
			message.setSubject(messageProperties.getMessage(
					"Signup.email.subject", null, null));

			htmlContent = this.templateEngine.process(
					"verification-signup-template.html", ctx);
			break;
		case SIGNUP_CONFIRMATION:
			message.setFrom(messageProperties.getMessage(
					"Signup.email.emailFrom", null, null));
			message.setSubject(messageProperties.getMessage(
					"Signup.email.subject", null, null));

			htmlContent = this.templateEngine.process(
					"confirm-signup-template.html", ctx);
			break;
		case PASSWORD_RESET_REQUEST:
			ctx.setVariable("linkUrl", String.format("%s/resetPasswordLink.html?token=%s", linkUrl, token));

			message.setFrom(messageProperties.getMessage(
					"Signup.email.emailFrom", null, null));
			message.setSubject(messageProperties.getMessage(
					"PasswordReset.email.subject", null, null));

			htmlContent = this.templateEngine.process(
					"newpassword-requested-template.html", ctx);
			break;
		case PASSWORD_CONFIRMATION:
			message.setFrom(messageProperties.getMessage(
					"Signup.email.emailFrom", null, null));
			message.setSubject(messageProperties.getMessage(
					"PasswordReset.email.subject", null, null));

			htmlContent = this.templateEngine.process(
					"account-updated-template.html", ctx);
			break;
		case USER_PROFILE_CHANGE:
			message.setFrom(messageProperties.getMessage(
					"UserProfileUpdate.email.emailFrom", null, null));
			message.setSubject(messageProperties.getMessage(
					"UserProfileUpdate.email.subject", null, null));
			htmlContent = this.templateEngine.process(
					"user-profile-updated-template.html", ctx);
			break;

		case NEW_LOGIN_ACCOUNT:
			ctx.setVariable("linkUrl", String.format(
					"%s/verifyNewAccountLink.html?token=%s", linkUrl, token));

			message.setFrom(messageProperties.getMessage(
					"Signup.email.emailFrom", null, null));
			message.setSubject(messageProperties.getMessage(
					"SignupNewAccount.email.subject", null, null));

			htmlContent = this.templateEngine.process(
					"verification-new-accountsignup-template.html", ctx);
			break;		
		default:
			message.setFrom("support@consent2share.com");
			message.setSubject("Support");
			break;
		}

		message.setTo(mailTo);

		message.setText(htmlContent, true);

		this.mailSender.send(mimeMessage);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * 
	 */
	@Override
	public void sendContactMessage(EmailType type, String firtName, String lastName, String email, String phoneNumber, String option) throws MessagingException {
		// Prepare the evaluation context
		final Context ctx = new Context();
		String htmlContent = null;

		ctx.setVariable("c2sLogoUrl",String.format("%s/resources/img/c2s-logo.png", ""));

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");

		switch (type) {
			case CONFIRM_REQUEST_MORE_INFORMATION:
				
				message.setTo(email);
				ctx.setVariable("linkUrl",messageProperties.getMessage("Contact.email.pgLink", null, null));
				message.setFrom(messageProperties.getMessage("Contact.email.emailFrom", null, null));
				message.setSubject(messageProperties.getMessage("Contact.email.subject2", null, null));
				
				htmlContent = this.templateEngine.process("request-info-visitor-notification-template.html", ctx);
				
				break;
			
			case REQUEST_MORE_INFORMATION:
				
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();			
				
				ctx.setVariable("messageDate", dateFormat.format(date));
				ctx.setVariable("messageTime", timeFormat.format(date));
				ctx.setVariable("firstName", firtName);
				ctx.setVariable("lastName", lastName);
				ctx.setVariable("phoneNumber", phoneNumber);
				ctx.setVariable("email", email);
				ctx.setVariable("option", option);
				
				message.setTo(messageProperties.getMessage("Contact.email.emailTo", null, null));
				message.setFrom(messageProperties.getMessage("Contact.email.emailFrom", null, null));
				message.setSubject(messageProperties.getMessage("Contact.email.subject1", null, null));
				
				htmlContent = this.templateEngine.process("request-info-admin-notification-template.html", ctx);
						
				break;	
		}

		message.setText(htmlContent, true);
		
		this.mailSender.send(mimeMessage);
	}
}
