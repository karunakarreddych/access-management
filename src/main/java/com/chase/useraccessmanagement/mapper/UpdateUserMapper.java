package com.chase.useraccessmanagement.mapper;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.chase.useraccessmanagement.request.EnableDisableRequest;
import com.chase.useraccessmanagement.response.UserResponse;

@Service
public class UpdateUserMapper {

	public UserResponse mapUpdateUserToResponse(EnableDisableRequest enableDisableRequest) {
		UserResponse response = new UserResponse();
		if(enableDisableRequest.isActive() == false) {
		response.setMessage(enableDisableRequest.getUsername()+" User disabled Successfully");
		}else if(enableDisableRequest.isActive() == true) {
			response.setMessage(enableDisableRequest.getUsername()+" User enabled Successfully");
		}
		return response;
	}

	public UserResponse mapUpdatePasswordToResponse(HttpStatus statusCode) {
		UserResponse response = new UserResponse();
		response.setMessage(HttpStatus.OK+" Password updated Successfully");
		return response;
	}

	
}
