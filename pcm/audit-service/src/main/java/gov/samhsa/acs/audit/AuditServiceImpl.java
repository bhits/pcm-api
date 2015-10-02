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
package gov.samhsa.acs.audit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.audit.Application;
import ch.qos.logback.audit.AuditException;
import ch.qos.logback.audit.client.AuditorFacade;
import ch.qos.logback.audit.client.AuditorFactory;

/**
 * The Class AuditServiceImpl.
 */
public class AuditServiceImpl implements AuditService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The application name. */
	private String applicationName;

	/**
	 * Instantiates a new audit service impl.
	 * 
	 * @param applicationName
	 *            the application name
	 * @throws AuditException
	 *             the audit exception
	 */
	public AuditServiceImpl(String applicationName) throws AuditException {
		super();
		this.applicationName = applicationName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.audit.AuditService#audit(java.lang.Object,
	 * java.lang.String, gov.samhsa.acs.audit.AuditVerb, java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public void audit(Object auditingObject, String subject, AuditVerb verb,
			String object, Map<PredicateKey, String> predicateMap)
			throws AuditException {
		AuditorFacade af = createAuditorFacade(subject, verb, object);
		String hostAddress = null;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error("Cannot find the host of auditingObject.");
			logger.error(e.getMessage(), e);
		}
		
		Application app = createApplication(auditingObject, hostAddress);

		af.originating(app);
		af.setPredicateMap(mapKeyToString(predicateMap));
		af.audit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.audit.AuditService#createPredicateMap()
	 */
	@Override
	public Map<PredicateKey, String> createPredicateMap() {
		return new HashMap<PredicateKey, String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.audit.AuditService#getApplicationName()
	 */
	@Override
	public String getApplicationName() {
		return applicationName;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.acs.audit.AuditService#init()
	 */
	@Override
	public void init() throws AuditException {
		AuditorFactory.setApplicationName(applicationName);		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.audit.AuditService#destroy()
	 */
	@Override
	public void destroy() {
		AuditorFactory.reset();
	}
	
	/**
	 * Creates the application.
	 *
	 * @param auditingObject the auditing object
	 * @param hostAddress the host address
	 * @return the application
	 */
	Application createApplication(Object auditingObject, String hostAddress) {
		Application app;
		if (auditingObject instanceof String)
			app = new Application((String)auditingObject,
					hostAddress);
		else {
			app = new Application(auditingObject.getClass().getName(),
					hostAddress);
		}
		return app;
	}

	/**
	 * Creates the auditor facade.
	 *
	 * @param subject the subject
	 * @param verb the verb
	 * @param object the object
	 * @return the auditor facade
	 */
	AuditorFacade createAuditorFacade(String subject, AuditVerb verb,
			String object) {
		AuditorFacade af = new AuditorFacade(subject, verb.getAuditVerb(),
				object);
		return af;
	}

	/**
	 * Map key to string.
	 * 
	 * @param predicateMap
	 *            the predicate map
	 * @return the map
	 */
	private Map<String, String> mapKeyToString(
			Map<PredicateKey, String> predicateMap) {
		Map<String, String> stringMap = null;
		if (predicateMap != null) {
			stringMap = new HashMap<String, String>();
			for (PredicateKey key : predicateMap.keySet()) {
				stringMap.put(key.getPredicateKey(), predicateMap.get(key));
			}
		}
		return stringMap;
	}
}
