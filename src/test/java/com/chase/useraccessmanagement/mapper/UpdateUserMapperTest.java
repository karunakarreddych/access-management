package com.chase.useraccessmanagement.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.chase.useraccessmanagement.controller.DataBuilder;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUserMapperTest {

	@InjectMocks
	UpdateUserMapper updateUserMapper;
	
	@Test
	public void testMapUpdateUserToResponseWhenUserIsDisabled() {
		assertEquals("psri User disabled Successfully", updateUserMapper.mapUpdateUserToResponse(DataBuilder.getDisableRequest()).getMessage());
	}
	
	@Test
	public void testMapUpdateUserToResponseWhenUserIsEnabled() {
		assertEquals("psri User enabled Successfully", updateUserMapper.mapUpdateUserToResponse(DataBuilder.getEnableRequest()).getMessage());
	}
	
	@Test
	public void testMapUpdatePasswordToResponse() {
		assertEquals("200 OK Password updated Successfully", updateUserMapper.mapUpdatePasswordToResponse(HttpStatus.OK).getMessage());
	}
}
