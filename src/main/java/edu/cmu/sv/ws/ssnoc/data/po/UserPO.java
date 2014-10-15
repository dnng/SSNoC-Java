package edu.cmu.sv.ws.ssnoc.data.po;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all user information in the system.
 * This contains information like the user's name, his role, his account status
 * and the password information entered by the user when signing up. <br/>
 * Information is saved in SSN_USERS table.
 * 
 */
public class UserPO {
	private long userId;
	private String userName;
	private String password;
	private String salt;
	private long lastStatusCrumbId;
	private String lastStatus;
	private long lastLocationCrumbId;
	private String lastLocation;
	private Timestamp lastStatusTime;
	private Timestamp createdAt;
	private Timestamp modifiedAt;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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
