package com.chase.useraccessmanagement.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.chase.useraccessmanagement.config.ActiveMqConfigration;
import com.chase.useraccessmanagement.model.UserData;
import com.chase.useraccessmanagement.model.UserDetails;
import com.chase.useraccessmanagement.request.EnableDisableRequest;
import com.chase.useraccessmanagement.request.UserRequest;
import com.chase.useraccessmanagement.util.Mode;

@Component
public class UserDetailsQueue {
	
	@Autowired
	ActiveMqConfigration userDetailsQueue;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	public void sendUserDetails(UserRequest userRequest, String password) {
		UserDetails userDetails = new UserDetails();
		userDetails.setEvent_type("emp_access_created");
		userDetails.setMode(Mode.EMAIL.getCode());
		userDetails.setEmail(userRequest.getEmail());
		UserData data = new UserData();
		data.setName(userRequest.getFirstName());
		data.setUsername(userRequest.getUsername());
		data.setPassword(password);
		userDetails.setData(data);
		jmsTemplate.convertAndSend(userDetailsQueue.userDetailsJMSQueue(), userDetails);
	}

	public void sendDisabledUser(String name, String email, EnableDisableRequest enableDisableRequest) {
		UserDetails userDetails = new UserDetails();
		if(enableDisableRequest.isActive() == false) {
		userDetails.setEvent_type("emp_access_blocked");
		}else if(enableDisableRequest.isActive() == true) {
			userDetails.setEvent_type("emp_access_unblocked");
		}
		userDetails.setMode(Mode.EMAIL.getCode());
		userDetails.setEmail(email);
		UserData data = new UserData();
		data.setName(name);
		data.setUsername(enableDisableRequest.getUsername());
		userDetails.setData(data);
		jmsTemplate.convertAndSend(userDetailsQueue.userDetailsJMSQueue(), userDetails);
	}

	public void sendUpdatedPassword(String name,String email) {
		UserDetails userDetails = new UserDetails();
		userDetails.setEvent_type("password_updated");
		userDetails.setMode(Mode.EMAIL.getCode());
		userDetails.setEmail(email);
		UserData data = new UserData();
		data.setName(name);
		userDetails.setData(data);
		jmsTemplate.convertAndSend(userDetailsQueue.userDetailsJMSQueue(), userDetails);
	}
}
