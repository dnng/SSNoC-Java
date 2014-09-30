package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This object contains status crumb information that is responded as part of the REST
 * API request.
 * 
 */
public class StatusCrumb {
	private long statusCrumbId;
	private String status;
	private long userId;
	private String userName;
	private long locationCrumbId;
	private String location;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	public long getStatusCrumbId() {
		return statusCrumbId;
	}

	public void setStatusCrumbId(long statusCrumbId) {
		this.statusCrumbId = statusCrumbId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
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

	public long getLocationCrumbId() {
		return locationCrumbId;
	}

	public void setLocationCrumbId(long locationCrumbId) {
		this.locationCrumbId = locationCrumbId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
