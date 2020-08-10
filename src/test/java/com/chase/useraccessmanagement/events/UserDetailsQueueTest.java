package com.chase.useraccessmanagement.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import com.chase.useraccessmanagement.config.ActiveMqConfigration;
import com.chase.useraccessmanagement.controller.DataBuilder;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsQueueTest {
	
	@InjectMocks
	UserDetailsQueue userDetailsQueue;
	
	@Mock
	ActiveMqConfigration activeMqConfigration;
	
	@Mock
	JmsTemplate jmsTemplate;
	
	@Test
	public void testSendUserDetails() {
		userDetailsQueue.sendUserDetails(DataBuilder.getUserRequest(), "password");
	}
	
	@Test
	public void testSendDisabledUserWhenUserIsEnabled() {
		userDetailsQueue.sendDisabledUser("chase", "chase@gmail.com", DataBuilder.getEnableRequest());
	}
	
	@Test
	public void testSendDisabledUserWhenUserIsDisabled() {
		userDetailsQueue.sendDisabledUser("chase", "chase@gmail.com", DataBuilder.getDisableRequest());
	}
	
	@Test
	public void testSendUpdatedPassword() {
		userDetailsQueue.sendUpdatedPassword("chase", "chase@gmail.com");
	}

}
