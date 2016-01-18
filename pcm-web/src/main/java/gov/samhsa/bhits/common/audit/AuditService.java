/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
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
package gov.samhsa.bhits.common.audit;

import ch.qos.logback.audit.AuditException;

import java.util.Map;

/**
 * The Interface AuditService.
 */
public interface AuditService {

    /**
     * Audit.
     *
     * @param auditingObject
     *            the auditing object
     * @param subject
     *            the subject
     * @param verb
     *            the verb
     * @param object
     *            the object
     * @param predicateMap
     *            the predicate map
     * @throws AuditException
     *             the audit exception
     */
    public abstract void audit(Object auditingObject, String subject,
                               AuditVerb verb, String object,
                               Map<PredicateKey, String> predicateMap) throws AuditException;

    /**
     * Creates the predicate map.
     *
     * @return the map
     */
    public abstract Map<PredicateKey, String> createPredicateMap();

    /**
     * Gets the application name.
     *
     * @return the application name
     */
    public abstract String getApplicationName();

    /**
     * Initialize.
     * @throws AuditException
     */
    public abstract void init() throws AuditException;

    /**
     * Destroy.
     */
    public abstract void destroy();

}
