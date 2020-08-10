package com.chase.useraccessmanagement.config;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JMSMessageConverter implements MessageConverter {

	Logger logger = LoggerFactory.getLogger(JMSMessageConverter.class);

	private ObjectMapper mapper;

	public JMSMessageConverter() {
		mapper = new ObjectMapper();
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		String json;

		try {
			json = mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new MessageConversionException("Message cannot be parsed. ", e);
		}

		TextMessage message = session.createTextMessage();
		message.setText(json);

		return message;
	}
	

	@Override
	public Object fromMessage(Message message) throws JMSException {
		if (message instanceof ActiveMQTextMessage) {
			ActiveMQTextMessage originalMessage = (ActiveMQTextMessage) message;
			String payload = originalMessage.getText();
			logger.info("payload data in string : " + payload);
			return payload;
		}
		return null;	
	}
}
