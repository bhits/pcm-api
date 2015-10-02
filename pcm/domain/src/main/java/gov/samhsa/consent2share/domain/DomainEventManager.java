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
package gov.samhsa.consent2share.domain;

import gov.samhsa.consent2share.common.SpringContext;

import org.springframework.context.ApplicationContext;

/**
 * The Class DomainEventManager.
 */
public class DomainEventManager {

	/** The domain event service. */
	private static DomainEventService domainEventService;

	/**
	 * Raise.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param event
	 *            the event
	 */
	public static <T extends DomainEvent> void raise(T event) {
		DomainEventService domainEventService = getDomainEventService();

		if (domainEventService != null) {
			domainEventService.raise(event);
		}
	}

	/**
	 * Register.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param eventHandler
	 *            the event handler
	 */
	public static <T extends DomainEvent> void register(
			DomainEventHandler<T> eventHandler) {
		DomainEventService domainEventService = getDomainEventService();

		if (domainEventService != null) {
			domainEventService.register(eventHandler);
		}
	}

	/**
	 * Gets the domain event service.
	 * 
	 * @return the domain event service
	 */
	private static DomainEventService getDomainEventService() {
		DomainEventService domainEventService = DomainEventManager.domainEventService;

		if (domainEventService == null) {
			// Get from application context
			ApplicationContext applicationContext = SpringContext
					.getApplicationContext();

			if (applicationContext != null) {
				domainEventService = applicationContext
						.getBean(DomainEventService.class);
			}
		}

		return domainEventService;
	}

	/**
	 * Register domain event service.
	 * This method is mainly for unit testing purpose
	 * @param domainEventService the domain event service
	 */
	public static void registerDomainEventService(
			DomainEventService domainEventService) {
		DomainEventManager.domainEventService = domainEventService;
	}
}
