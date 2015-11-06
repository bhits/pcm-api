package gov.samhsa.pcm.web.controller.instrumentation;

import static java.util.stream.Collectors.joining;
import gov.samhsa.acs.common.cxf.AbstractCXFLoggingConfigurerClient;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class LoggerCheckController.
 */
@Controller
@RequestMapping("/instrumentation")
public class LoggerCheckController {

	/** The PCM logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** The cxf logging configurer clients. */
	@Autowired
	private List<AbstractCXFLoggingConfigurerClient> cxfLoggingConfigurerClients;

	/**
	 * Test whether the PCM logging work.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/loggerCheck", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody String check() {

		// The following loops are used to make ch.qos.logback.classic.turbo.ReconfigureOnChangeFilter be alive to scan logback configuration changes if logback is used
		// You may request this url several times to activate the configuration changes
		for (int i = 0 ; i < 200; i++) {
			logger.trace("trace: just a test.");
		}

		logger.debug("debug: just a test.");
		logger.info("info: just a test.");
		logger.warn("warn: just a test.");
		logger.error("error: just a test.");

		String loggerLevel;

		if (logger.isDebugEnabled()) {
			loggerLevel = "Debug";
		} else if (logger.isInfoEnabled()) {
			loggerLevel = "Info";
		} else if (logger.isWarnEnabled()) {
			loggerLevel = "Warn";
		} else {
			loggerLevel = "Error";
		}

		return "<p>This page is used for logging test. And if logback is the logging library, you can request this page serveral times to activate logback configuration changes.</p><hr/>"
				+"<h3>Logger named ["+logger.getName()+ "]</h3>\r\n"+ "<h3>Logger Level is ["+ loggerLevel+ "]</h3>";
	}
	
	/**
	 * Enables or disables CXF logging interceptors of all {@link AbstractCXFLoggingConfigurerClient}s.
	 *
	 * @param enabled the enabled
	 * @return the string
	 */
	@RequestMapping(value = "/cxfLoggingInterceptors/enabled/{enabled}", method = RequestMethod.PUT, produces="text/html")
	public @ResponseBody String loggingInterceptors(@PathVariable("enabled") boolean enabled){
		this.cxfLoggingConfigurerClients
			.stream()
			.peek(client -> logger.info(
					sb()
					.append("Setting 'enableLoggingInterceptors' field of '" )
					.append(client.toString())
					.append("' to ")
					.append(enabled)
					.toString()))
			.forEach(client -> client.setEnableLoggingInterceptors(enabled));
		return
				sb()
				.append(check())
				.append("<h3>")
				.append("cxfLoggingConfigurerClients configurations:</h3><h3>")
				.append("<ol>")
				.append(this.cxfLoggingConfigurerClients
					.stream()
					.map(client -> sb()
							.append("<li>")
							.append(client.toString())
							.append(".enableLoggingInterceptors=")
							.append(client.isEnableLoggingInterceptors())
							.append("</li>").toString())
					.collect(joining()))
				.append("</ol>")
				.append("</h3>")
				.toString();		
	}
	
	/**
	 * Creates a new {@link StringBuilder} instance.
	 *
	 * @return the string builder
	 */
	private static final StringBuilder sb(){
		return new StringBuilder();
	}
}

