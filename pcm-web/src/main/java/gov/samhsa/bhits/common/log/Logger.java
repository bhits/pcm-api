/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.bhits.common.log;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import javax.xml.ws.WebServiceContext;

/**
 * The Class Logger.
 */
public class Logger {

	public static final String LOG_MESSAGE_ID_KEY = "LOG_MESSAGE_ID_KEY";

	/** The context. */
	private static Optional<WebServiceContext> context = Optional.empty();

	/** The logger. */
	private final org.slf4j.Logger logger;

	/**
	 * Instantiates a new acs logger.
	 *
	 * @param logger
	 *            the logger
	 */
	public Logger(org.slf4j.Logger logger) {
		super();
		this.logger = logger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#debug(java.lang.String)
	 */
	public void debug(String message) {
		final String logMessage = createLogMessage(message);
		logger.debug(logMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String message, Throwable exception) {
		final String logMessage = createLogMessage(message);
		logger.debug(logMessage, exception);
	}

	/**
	 * Debug.
	 *
	 * @param message
	 *            the message
	 */
	public void debug(Supplier<String> message) {
		if (logger.isDebugEnabled()) {
			final String logMessage = createLogMessage(message.get());
			logger.debug(logMessage);
		}
	}

	/**
	 * Debug.
	 *
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public void debug(Supplier<String> message, Throwable exception) {
		if (logger.isDebugEnabled()) {
			final String logMessage = createLogMessage(message.get());
			logger.debug(logMessage, exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String)
	 */
	public void error(String message) {
		final String logMessage = createLogMessage(message);
		logger.error(logMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String message, Throwable exception) {
		final String logMessage = createLogMessage(message);
		logger.error(logMessage, exception);
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 */
	public void error(Supplier<String> message) {
		if (logger.isErrorEnabled()) {
			final String logMessage = createLogMessage(message.get());
			logger.error(logMessage);
		}
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public void error(Supplier<String> message, Throwable exception) {
		if (logger.isErrorEnabled()) {
			final String logMessage = createLogMessage(message.get());
			logger.error(logMessage, exception);
		}
	}

	/**
	 * Gets the logger.
	 *
	 * @return the logger
	 */
	public org.slf4j.Logger getLogger() {
		return logger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#info(java.lang.String)
	 */
	public void info(String message) {
		final String logMessage = createLogMessage(message);
		logger.info(logMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String message, Throwable exception) {
		final String logMessage = createLogMessage(message);
		logger.info(logMessage, exception);
	}

	/**
	 * Info.
	 *
	 * @param message
	 *            the message
	 */
	public void info(Supplier<String> message) {
		if (logger.isInfoEnabled()) {
			final String logMessage = createLogMessage(message.get());
			logger.info(logMessage);
		}
	}

	/**
	 * Info.
	 *
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public void info(Supplier<String> message, Throwable exception) {
		if (logger.isInfoEnabled()) {
			final String logMessage = createLogMessage(message.get());
			logger.info(logMessage, exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String)
	 */
	public void warn(String message) {
		final String logMessage = createLogMessage(message);
		logger.warn(logMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Throwable)
	 */
	public void warn(String message, Throwable exception) {
		final String logMessage = createLogMessage(message);
		logger.warn(logMessage, exception);
	}

	/**
	 * Warn.
	 *
	 * @param message
	 *            the message
	 */
	public void warn(Supplier<String> message) {
		if (logger.isWarnEnabled()) {
			final String logMessage = createLogMessage(message.get());
			logger.warn(logMessage);
		}
	}

	/**
	 * Warn.
	 *
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public void warn(Supplier<String> message, Throwable exception) {
		if (logger.isWarnEnabled()) {
			final String logMessage = createLogMessage(message.get());
			logger.warn(logMessage, exception);
		}
	}

	/**
	 * Creates the log message.
	 *
	 * @param message
	 *            the message
	 * @return the string
	 */
	private String createLogMessage(String message) {
		final Optional<String> messageId = Logger.context
				.map(WebServiceContext::getMessageContext)
				.map(context -> context.get(LOG_MESSAGE_ID_KEY))
				.filter(key -> key instanceof String).map(key -> (String) key);
		if (messageId.isPresent()) {
			final StringBuilder builder = new StringBuilder();
			builder.append(messageId.get());
			builder.append(" : ");
			builder.append(message);
			message = builder.toString();
		}
		return message;
	}

	/**
	 * Gets the context as optional.
	 *
	 * @return the context as optional
	 */
	public static Optional<WebServiceContext> getContextAsOptional() {
		return Logger.context;
	}

	/**
	 * Inits the message id in request context.
	 *
	 * @return the string
	 */
	public static String initMessageIdInRequestContext() {
		final String messageId = createMessageId();
		return initMessageIdInRequestContext(messageId);
	}

	/**
	 * Inits the message id in request context.
	 *
	 * @param messageId
	 *            the message id
	 * @return the string
	 */
	public static String initMessageIdInRequestContext(String messageId) {
		final String finalMessageId = Optional.ofNullable(messageId).orElseGet(
				Logger::createMessageId);
		getContextAsOptional().map(WebServiceContext::getMessageContext)
		.ifPresent(
				context -> context.put(LOG_MESSAGE_ID_KEY,
						finalMessageId));
		return finalMessageId;
	}

	/**
	 * Sets the context.
	 *
	 * @param context
	 *            the new context
	 */
	public static void setContext(WebServiceContext context) {
		Logger.context = Optional.of(context);
	}

	/**
	 * Creates the message id.
	 *
	 * @return the string
	 */
	private static String createMessageId() {
		return UUID.randomUUID().toString();
	}
}
