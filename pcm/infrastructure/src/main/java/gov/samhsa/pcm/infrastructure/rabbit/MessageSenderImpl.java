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
package gov.samhsa.pcm.infrastructure.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * The Class MessageSenderImpl.
 */
public class MessageSenderImpl implements MessageSender {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The rabbit template. */
	private RabbitTemplate rabbitTemplate;

	/** The amqp admin. */
	private AmqpAdmin amqpAdmin;

	/** The queue. */
	private Queue queue;

	/** The exchange. */
	private Exchange exchange;

	/**
	 * Instantiates a new message sender impl.
	 *
	 * @param rabbitTemplate
	 *            the rabbit template
	 * @param amqpAdmin
	 *            the amqp admin
	 * @param queue
	 *            the queue
	 * @param exchange
	 *            the exchange
	 */
	public MessageSenderImpl(RabbitTemplate rabbitTemplate,
			AmqpAdmin amqpAdmin, Queue queue, Exchange exchange) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.amqpAdmin = amqpAdmin;
		this.queue = queue;
		this.exchange = exchange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.rabbit.MessageSender#send(java
	 * .lang.String, org.springframework.amqp.core.MessageProperties)
	 */
	@Override
	public void send(String text, MessageProperties properties) {
		// Assign to broker
		amqpAdmin.declareQueue(queue);
		amqpAdmin.declareExchange(exchange);

		// Wrapped our custom text and properties as a Message
		Message message = new Message(text.getBytes(), properties);

		rabbitTemplate.send(exchange.getName(), "", message);
		logger.debug("Message sent: " + text);
	}
}
