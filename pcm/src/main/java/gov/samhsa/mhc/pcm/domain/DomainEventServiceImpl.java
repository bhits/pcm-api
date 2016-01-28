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
package gov.samhsa.mhc.pcm.domain;


import gov.samhsa.mhc.pcm.config.SpringContext;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Class DomainEventServiceImpl.
 */
public class DomainEventServiceImpl implements DomainEventService {

	/** The registered event handlers. */
	private Set<DomainEventHandler> registeredEventHandlers;

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.domain.DomainEventService#raise(gov.samhsa.consent2share.domain.DomainEvent)
	 */
	@Override
	public <T extends DomainEvent> void raise(T event) {
		// TODO: SpringContext is in infrastructure, shouldn't be used here.
		// Should create an DI container abstraction to be used here
		// Or we could think that Spring DI is our core which can be referenced
		// in Domain. 
		// The 2nd options may be better.
		ApplicationContext applicationContext = SpringContext
				.getApplicationContext();

		if (applicationContext != null) {
			Map<String, DomainEventHandler> eventHandlersMap = applicationContext
					.getBeansOfType(DomainEventHandler.class);

			for (DomainEventHandler eventHandler : eventHandlersMap.values()) {
				handleEvent(eventHandler, event);
			}
		}

		if (registeredEventHandlers != null) {
			for (DomainEventHandler eventHandler : registeredEventHandlers) {
				handleEvent(eventHandler, event);
			}
		}
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.domain.DomainEventService#register(gov.samhsa.consent2share.domain.DomainEventHandler)
	 */
	@Override
	public <T extends DomainEvent> void register(
			DomainEventHandler<T> eventHandler) {
		if (registeredEventHandlers == null) {
			registeredEventHandlers = new HashSet();
		}
		registeredEventHandlers.add(eventHandler);
	}

	/**
	 * Handle event.
	 *
	 * @param eventHandler the event handler
	 * @param event the event
	 */
	private void handleEvent(DomainEventHandler eventHandler, DomainEvent event) {
		if (eventHandler.canHandle(event)) {
			eventHandler.handle(event);
		}
	}
}
