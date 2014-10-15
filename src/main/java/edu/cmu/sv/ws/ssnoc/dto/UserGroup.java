package edu.cmu.sv.ws.ssnoc.dto;

import java.util.List;

import com.google.gson.Gson;

/**
 * This object contains user information that is responded as part of the REST
 * API request.
 * 
 */
public class UserGroup {
	private List<String> userNames;
	
	public List<String> getUserNames() {
		return userNames;
	}

	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
