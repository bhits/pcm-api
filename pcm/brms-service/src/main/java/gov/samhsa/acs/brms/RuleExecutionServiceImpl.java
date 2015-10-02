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
package gov.samhsa.acs.brms;

import gov.samhsa.acs.brms.domain.ClinicalFact;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.guvnor.GuvnorService;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.rule.Rule;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * The Class RuleExecutionServiceImpl.
 */
public class RuleExecutionServiceImpl implements RuleExecutionService {

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The guvnor service. */
	private final GuvnorService guvnorService;

	/** The marshaller. */
	private final SimpleMarshaller marshaller;

	/**
	 * Instantiates a new rule execution service impl.
	 *
	 * @param guvnorService
	 *            the guvnor service
	 * @param marshaller
	 *            the marshaller
	 */
	public RuleExecutionServiceImpl(GuvnorService guvnorService,
			SimpleMarshaller marshaller) {
		super();
		this.guvnorService = guvnorService;
		this.marshaller = marshaller;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.acs.brms.RuleExecutionService#assertAndExecuteClinicalFacts
	 * (gov.samhsa.acs.brms.domain.FactModel)
	 */
	@Override
	public AssertAndExecuteClinicalFactsResponse assertAndExecuteClinicalFacts(
			FactModel factModel) {
		RuleExecutionContainer executionResponseContainer = null;
		final AssertAndExecuteClinicalFactsResponse assertAndExecuteResponse = new AssertAndExecuteClinicalFactsResponse();
		String executionResponseContainerXMLString = null;
		final Set<String> firedRuleNames = new HashSet<String>();

		final StatefulKnowledgeSession session = createStatefulKnowledgeSession();
		try {
			session.insert(factModel.getXacmlResult());
			for (final ClinicalFact clinicalFact : factModel
					.getClinicalFactList()) {
				session.insert(clinicalFact);
			}

			session.addEventListener(new DefaultAgendaEventListener() {
				@Override
				public void afterActivationFired(AfterActivationFiredEvent event) {
					super.afterActivationFired(event);
					final Rule rule = event.getActivation().getRule();
					firedRuleNames.add(rule.getName());
				}
			});

			session.fireAllRules();

			logger.debug(() -> "Fired rules: " + firedRuleNames.toString());

			executionResponseContainer = (RuleExecutionContainer) session
					.getGlobal("ruleExecutionContainer");

			// Marshal rule execution response
			executionResponseContainerXMLString = marshaller
					.marshal(executionResponseContainer);
			if (firedRuleNames.size() > 0) {
				assertAndExecuteResponse.setRulesFired(firedRuleNames
						.toString());
			}
		} catch (final Throwable e) {
			logger.error(e.getMessage(), e);
		} finally {
			firedRuleNames.clear();
			if (session != null) {
				session.dispose();
			}
		}
		assertAndExecuteResponse
		.setRuleExecutionResponseContainer(executionResponseContainerXMLString);
		return assertAndExecuteResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.acs.brms.RuleExecutionService#assertAndExecuteClinicalFacts
	 * (java.lang.String)
	 */
	@Override
	public AssertAndExecuteClinicalFactsResponse assertAndExecuteClinicalFacts(
			String factModelXmlString) {
		FactModel factModel = null;
		try {
			factModel = marshaller.unmarshalFromXml(FactModel.class,
					factModelXmlString);
		} catch (final JAXBException e) {
			logger.error(e.getMessage(), e);
		}
		return assertAndExecuteClinicalFacts(factModel);
	}

	/**
	 * Creates the stateful knowledge session.
	 *
	 * @return the stateful knowledge session
	 */
	StatefulKnowledgeSession createStatefulKnowledgeSession() {
		StatefulKnowledgeSession session = null;
		try {
			final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();

			final String casRules = guvnorService
					.getVersionedRulesFromPackage();

			kbuilder.add(
					ResourceFactory.newByteArrayResource(casRules.getBytes()),
					ResourceType.DRL);

			final KnowledgeBuilderErrors errors = kbuilder.getErrors();
			if (errors.size() > 0) {
				for (final KnowledgeBuilderError error : errors) {
					logger.error(error.toString());
				}
			}

			final KnowledgeBase knowledgeBase = KnowledgeBaseFactory
					.newKnowledgeBase();
			knowledgeBase.addKnowledgePackages(kbuilder.getKnowledgePackages());

			session = knowledgeBase.newStatefulKnowledgeSession();
			session.setGlobal("ruleExecutionContainer",
					new RuleExecutionContainer());
		} catch (final Exception e) {
			logger.error(e.toString(), e);
		}
		return session;
	}
}
