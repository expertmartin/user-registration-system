package com.apress.ravi.exception;

import com.apress.ravi.dto.UserDTO;

public class CustomErrorType extends UserDTO {
	private String errorMessage;

	public CustomErrorType(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}