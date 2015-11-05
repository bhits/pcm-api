package gov.samhsa.pcm.web.config.di.root;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbitMQ.host}")
	private String rabbitMQHost;

	@Value("${rabbitMQ.port}")
	private String rabbitMQPort;

	@Value("${rabbitMQ.username}")
	private String rabbitMQUsername;

	@Value("${rabbitMQ.password}")
	private String rabbitMQPassword;

	@Value("${rabbitMQ.exchangeName}")
	private String rabbitMQExchangeName;

	@Value("${rabbitMQ.queueName}")
	private String rabbitMQQueueName;

	// Provides connection to the RabbitMQ broker
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(rabbitMQHost);
		factory.setPort(Integer.parseInt(rabbitMQPort));
		factory.setUsername(rabbitMQUsername);
		factory.setPassword(rabbitMQPassword);

		return factory;
	}

	// A template for sending messages and performing other commands to RabbitMQ
	@Bean
	public RabbitTemplate amqpTemplate() {
		RabbitTemplate amqpTemplate = new RabbitTemplate(connectionFactory());
		// Set channel-transacted to true to tell the framework to use a transactional channel
		// and to end all operations (send or receive) with a commit or rollback depending on the outcome,
		// with an exception signaling a rollback.
		amqpTemplate.setChannelTransacted(true);
		return amqpTemplate;
	}

	// This helps in configuring the AMQP broker, like creating a new queue
	@Bean
	public RabbitAdmin rabbitAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public Queue queue() {
		return new Queue(rabbitMQQueueName, true, false, false);
	}

	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(rabbitMQExchangeName, true, false);
	}

	@Bean
	Binding fanoutExchangeBinding(FanoutExchange fanoutExchange, Queue queue) {
		return BindingBuilder.bind(queue).to(fanoutExchange);
	}
}
