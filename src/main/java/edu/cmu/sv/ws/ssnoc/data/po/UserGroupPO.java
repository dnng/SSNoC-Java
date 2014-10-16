package edu.cmu.sv.ws.ssnoc.data.po;

import java.util.List;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all user information in the system.
 * This contains information like the user's name, his role, his account status
 * and the password information entered by the user when signing up. <br/>
 * Information is saved in SSN_USERS table.
 * 
 */
public class UserGroupPO {
	
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