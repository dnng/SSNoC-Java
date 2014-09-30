package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This object contains status crumb information that is responded as part of the REST
 * API request.
 * 
 */
public class StatusCrumb {
	private Long userId;
	private String password;
	private String statusCode;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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
