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
	
	public boolean equals(UserGroup ug){
	    boolean isEqual= false;
	    List<String> temp = ug.getUserNames();
	    
	    if (temp.size() != this.getUserNames().size())
	        return false;
	    
	    for (String user: temp){
	        if(this.getUserNames().toString().contains(user))
	            isEqual = true;
	        else{
	            isEqual = false;
	            break;
	        }
	    }    
	    return isEqual;
	}
	

}
