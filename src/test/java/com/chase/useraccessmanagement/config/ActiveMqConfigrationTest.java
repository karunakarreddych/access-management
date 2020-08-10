package com.chase.useraccessmanagement.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ActiveMqConfigrationTest {

	@InjectMocks
	ActiveMqConfigration activeMqConfigration;
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(activeMqConfigration, "activeMqBrokenUrl", "tcp://10.8.0.119:61616");
		ReflectionTestUtils.setField(activeMqConfigration, "activeMqQueueName", "notification-queue");
		ReflectionTestUtils.setField(activeMqConfigration, "activeMqUsername", "admin");
		ReflectionTestUtils.setField(activeMqConfigration, "activeMqpassword", "admin");
	}
	
	@Test
	public void testUserDetailsJMSQueue() {
		activeMqConfigration.userDetailsJMSQueue();
	}
	
	@Test
	public void testJmsListenerContainerFactory() {
		activeMqConfigration.jmsListenerContainerFactory();
	}
}
