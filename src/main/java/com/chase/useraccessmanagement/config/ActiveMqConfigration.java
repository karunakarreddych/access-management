package com.chase.useraccessmanagement.config;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
public class ActiveMqConfigration {
	
	@Value("${spring.activemq.notificationQueueName}")
	private String activeMqQueueName;
	
	@Value("${spring.activemq.broker-url}")
	private String activeMqBrokenUrl;

	@Value("${spring.activemq.user}")
	private String activeMqUsername;

	@Value("${spring.activemq.password}")
	private String activeMqpassword;


	@Bean
    public Queue userDetailsJMSQueue() {
        return new ActiveMQQueue(activeMqQueueName);
    }
	
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setMessageConverter(jacksonJmsMessageConverter());
		return factory;
	}

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(activeMqBrokenUrl);
		connectionFactory.setPassword(activeMqpassword);
		connectionFactory.setUserName(activeMqUsername);
		connectionFactory.setTrustAllPackages(true); // all packages are considered as trusted
		return connectionFactory;
	}

	MessageConverter jacksonJmsMessageConverter() {
		JMSMessageConverter converter = new JMSMessageConverter();
		return converter;
   	}
	
}
