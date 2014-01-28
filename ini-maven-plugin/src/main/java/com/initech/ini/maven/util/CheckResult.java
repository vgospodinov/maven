package com.initech.ini.maven.util;

import java.util.ArrayList;
import java.util.List;

public class CheckResult {

	private boolean success;
	private List<String> errors = new ArrayList<String>();
	
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
}
