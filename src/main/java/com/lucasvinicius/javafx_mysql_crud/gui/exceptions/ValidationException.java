package com.lucasvinicius.javafx_mysql_crud.gui.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends Exception {
	private static final long serialVersionUID = 1L;

	Map<String, String> errors = new HashMap<>();
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
	
}
