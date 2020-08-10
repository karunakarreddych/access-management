package com.chase.useraccessmanagement.service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.chase.useraccessmanagement.config.KeycloakAdminClientUtils;
import com.chase.useraccessmanagement.config.KeycloakClientConfig;
import com.chase.useraccessmanagement.events.UserDetailsQueue;
import com.chase.useraccessmanagement.mapper.UpdateUserMapper;
import com.chase.useraccessmanagement.model.ClientCredentials;
import com.chase.useraccessmanagement.model.ErrorMessage;
import com.chase.useraccessmanagement.request.EnableDisableRequest;
import com.chase.useraccessmanagement.request.PasswordRequest;
import com.chase.useraccessmanagement.request.UserRequest;
import com.chase.useraccessmanagement.response.UserResponse;

import io.jsonwebtoken.Claims;

@Service
public class AccessManagementService {
	private static Logger logger = LoggerFactory.getLogger(AccessManagementService.class);

	@Autowired
	private KeycloakAdminClientUtils keycloakAdminClientUtils;

	@Autowired
	private KeycloakClientConfig keycloakClientConfig;

	@Autowired
	private UserDetailsQueue userDetailsQueue;

	@Autowired
	private UpdateUserMapper updateUserMapper;

	@Value("${user.update.password.url}")
	private String updationUrl;

	@SuppressWarnings("unchecked")
	public Response createUser(UserRequest userRequest) {
		String userId = null;
		String password = null;
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		ClientCredentials clientCredentials = keycloakClientConfig.loadKeycloakConfiguration();
		Keycloak keycloak = keycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(),
				clientCredentials);
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setUsername(userRequest.getUsername());
		userRepresentation.setLastName(userRequest.getLastName());
		userRepresentation.setFirstName(userRequest.getFirstName());
		userRepresentation.setEmail(userRequest.getEmail());
		userRepresentation.setEnabled(true);
		RealmResource realmResource = keycloak.realm(clientCredentials.getKeycloakRealm());
		UsersResource userRessource = realmResource.users();
		Response response = userRessource.create(userRepresentation);
		if (response.getLocation() != null) {
			password = generatePassword();
			userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
			CredentialRepresentation passwordCredentials = new CredentialRepresentation();
			passwordCredentials.setTemporary(true);
			passwordCredentials.setType(CredentialRepresentation.PASSWORD);
			passwordCredentials.setValue(password);
			userRessource.get(userId).resetPassword(passwordCredentials);
			userDetailsQueue.sendUserDetails(userRequest, password);
		}
		return response;
	}

	private String generatePassword() {
		String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
		String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
		String numbers = RandomStringUtils.randomNumeric(2);
		String totalChars = RandomStringUtils.randomAlphanumeric(2);
		String combinedChars = upperCaseLetters.concat(lowerCaseLetters).concat(numbers).concat(totalChars);
		List<Character> pwdChars = combinedChars.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
		Collections.shuffle(pwdChars);
		String password = pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
		return "Ch@" + password;
	}

	@SuppressWarnings("unchecked")
	public UserResponse disableUser(EnableDisableRequest enableDisableRequest) {
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		ClientCredentials clientCredentials = keycloakClientConfig.loadKeycloakConfiguration();
		Keycloak keycloak = keycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(),
				clientCredentials);
		RealmResource realmResource = keycloak.realm(clientCredentials.getKeycloakRealm());
		UsersResource usersResource = realmResource.users();
		List<UserRepresentation> users = usersResource.search(enableDisableRequest.getUsername());
		String userId = users.get(0).getId();
		String name = users.get(0).getFirstName();
		String email = users.get(0).getEmail();
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setEnabled(enableDisableRequest.isActive());
		usersResource.get(userId).update(userRepresentation);
		UserResponse response = updateUserMapper.mapUpdateUserToResponse(enableDisableRequest);
		userDetailsQueue.sendDisabledUser(name, email, enableDisableRequest);
		return response;
	}

	public ResponseEntity<?> updatePassword(String authToken, PasswordRequest request) {
		Claims claims = UtilService.jwtParser(authToken);
		String[] realmArray = claims.getIssuer().split("/");
		logger.info("Realm Name in array " + realmArray[5]);
		logger.info("Updation URL :" + updationUrl);
		String realm = realmArray[5];
		String name = claims.get("name", String.class);
		String email = claims.get("email", String.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", authToken);
		try {
			HttpEntity<PasswordRequest> httpEntity = new HttpEntity<PasswordRequest>(request, headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.exchange(updationUrl, HttpMethod.POST, httpEntity,
					String.class, realm);
			userDetailsQueue.sendUpdatedPassword(name, email);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(updateUserMapper.mapUpdatePasswordToResponse(responseEntity.getStatusCode()));
		} catch (HttpStatusCodeException e) {
			logger.info("Exception printed :" + e.getMessage());
			if (e.getResponseBodyAsString().contains("invalidPasswordExistingMessage")) {
				ErrorMessage errorDetails = new ErrorMessage(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.toString(),
						"Invalid current password");
				return new ResponseEntity<>(errorDetails, org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY);
			} else if (e.getResponseBodyAsString().contains("notMatchPasswordMessage")) {
				ErrorMessage errorDetails = new ErrorMessage(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.toString(),
						"Confirm and New password does not match");
				return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
			} else if (e.getResponseBodyAsString().contains("invalidPasswordMinSpecialCharsMessage")
					|| e.getResponseBodyAsString().contains("invalidPasswordMinUpperCaseCharsMessage")
					|| e.getResponseBodyAsString().contains("invalidPasswordMinLengthMessage")
					|| e.getResponseBodyAsString().contains("invalidPasswordMinDigitsMessage")) {
				ErrorMessage errorDetails = new ErrorMessage(Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.toString(),
						"Password criteria doesnot match!");
				return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		}
		return null;
	}
}
