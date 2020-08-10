package com.chase.useraccessmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private BindingResult result;

    public InvalidDataException(BindingResult result) {
        super();
        this.setResult(result);
    }

    public BindingResult getResult() {
        return result;
    }

    public void setResult(BindingResult result) {
        this.result = result;
    }
}
