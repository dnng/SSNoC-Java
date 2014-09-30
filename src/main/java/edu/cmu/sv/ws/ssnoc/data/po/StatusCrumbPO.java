package edu.cmu.sv.ws.ssnoc.data.po;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all status crumb information in the system.
 * This contains information like the user's id, his location, his status
 * and the creation time information entered by the user when updating his status. <br/>
 * Information is saved in SSN_STATUS_CRUMBS table.
 * 
 */
public class StatusCrumbPO {
	private long statusCrumbId;
	private long userId;
	private String userName;
	private String status;
	private long locationCrumbId;
	private String location;
	private Timestamp createdAt;

	public long getStatusCrumbId() {
		return statusCrumbId;
	}

	public void setStatusCrumbId(long statusCrumbId) {
		this.statusCrumbId = statusCrumbId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
