package com.chase.useraccessmanagement.config;

import static org.mockito.Mockito.when;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.support.converter.MessageConversionException;

import com.chase.useraccessmanagement.controller.DataBuilder;

@RunWith(MockitoJUnitRunner.class)
public class JMSMessageConverterTest {

	@InjectMocks
	JMSMessageConverter jMSMessageConverter;
	@Mock
	Session session;
	@Mock
	TextMessage textMessage;
	@Mock
	Message message;
	@Mock
	ActiveMQTextMessage activeMQTextMessage;
	
	@Test(expected=MessageConversionException.class)
	public void testToMessageWhenPassingEmptyObject() throws MessageConversionException, JMSException {
		jMSMessageConverter.toMessage(new Object(), session);
	}
	
	@Test
	public void testToMessage() throws MessageConversionException, JMSException {
		when(session.createTextMessage()).thenReturn(textMessage);
		jMSMessageConverter.toMessage(DataBuilder.getUserRequest(), session);
	}
	
	@Test
	public void testFromMessage() throws JMSException {
		jMSMessageConverter.fromMessage(message);
	}
}
