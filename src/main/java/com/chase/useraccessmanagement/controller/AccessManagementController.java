package com.chase.useraccessmanagement.controller;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chase.useraccessmanagement.exceptions.InvalidDataException;
import com.chase.useraccessmanagement.request.EnableDisableRequest;
import com.chase.useraccessmanagement.request.PasswordRequest;
import com.chase.useraccessmanagement.request.UserRequest;
import com.chase.useraccessmanagement.response.UserResponse;
import com.chase.useraccessmanagement.service.AccessManagementService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/access-management")
@Validated
public class AccessManagementController {

	@Autowired
	AccessManagementService accessManagementService;

	@PostMapping(value="/users",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Creating user in keycloak")
	public ResponseEntity<Response> createUser(@RequestHeader("authorization") String authToken,
			@RequestBody UserRequest userRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(accessManagementService.createUser(userRequest));
	}
	
	@PutMapping(value="/users/{username}",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Disable or Enable user and revoking or evoking all the access tokens")
	public ResponseEntity<UserResponse> enableOrdisableUser(@RequestHeader("authorization") String authToken,
			@PathVariable(value="username") String username,
			@RequestBody EnableDisableRequest enableDisableRequest){
		enableDisableRequest.setUsername(username);
		return ResponseEntity.status(HttpStatus.OK).body(accessManagementService.disableUser(enableDisableRequest));
	}
	
	@PostMapping(value="/users/password",consumes=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Updating Password of a user")
	public ResponseEntity<?> updatePassword(@RequestHeader("authorization") String authToken,
			@RequestBody @Valid PasswordRequest request,BindingResult result){
		if (result.hasErrors()) {
            throw new InvalidDataException(result);
        } else {
		return accessManagementService.updatePassword(authToken,request);
        }
	}
}
