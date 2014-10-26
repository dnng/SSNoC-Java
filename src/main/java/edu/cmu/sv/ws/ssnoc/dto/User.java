package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

/**
 * This object contains user information that is responded as part of the REST
 * API request.
 *
 */
public class User {
	List<String> userRoles = Arrays.asList("Administrator", "Coordinator", "Monitor", "Citizen");
	List<String> accountStates = Arrays.asList("active", "inactive");
	private String accountStatus;
	private String privilegeLevel;
	private String userName;
	private String password;
	private long lastStatusCrumbId;
	private String lastStatus;
	private long lastLocationCrumbId;
	private String lastLocation;
	private Timestamp lastStatusTime;
	private Timestamp createdAt;
	private Timestamp modifiedAt;

	public String getPrivilegeLevel() {
		return privilegeLevel;
	}

	public void setPrivilegeLevel(String privilegeLevel) {
		if (userRoles.contains(privilegeLevel)) {
			this.privilegeLevel = privilegeLevel;
		} else {
			this.privilegeLevel = null;
		}
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		if (accountStates.contains(accountStatus)) {
			this.accountStatus = accountStatus;
		} else {
			this.accountStatus  = null;
		}
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public long getLastStatusCrumbId() {
		return lastStatusCrumbId;
	}

	public void setLastStatusCrumbId(long lastStatusCrumbId) {
		this.lastStatusCrumbId = lastStatusCrumbId;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	public long getLastLocationCrumbId() {
		return lastLocationCrumbId;
	}

	public void setLastLocationCrumbId(long lastLocationCrumbId) {
		this.lastLocationCrumbId = lastLocationCrumbId;
	}


	public String getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(String lastLocation) {
		this.lastLocation = lastLocation;
	}

	public Timestamp getLastStatusTime() {
		return lastStatusTime;
	}

	public void setLastStatusTime(Timestamp lastStatusTime) {
		this.lastStatusTime = lastStatusTime;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
