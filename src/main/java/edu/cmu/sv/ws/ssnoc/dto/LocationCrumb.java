package edu.cmu.sv.ws.ssnoc.dto;
	import java.sql.Timestamp;

import com.google.gson.Gson;

	/**
	 * This object contains status crumb information that is responded as part of the REST
	 * API request.
	 * public static final String CREATE_LOCATION_CRUMBS = "create table IF NOT EXISTS "
	+ SSN_LOCATION_CRUMBS + " ( location_crumb_id IDENTITY PRIMARY KEY,"
	+ " user_id BIGINT," + " location VARCHAR(50),"
	+ " created_at TIMESTAMP )";
	 */
	public class LocationCrumb {
		private Long locationCrumbId;
		private Long userId;
		private String location;
		private Timestamp createdAt;
		
		public Long getlocationCrumbId() {
			return locationCrumbId;
		}

		public void setlocationCrumbId(Long locationCrumbId) {
			this.locationCrumbId = locationCrumbId;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getlocation() {
			return location;
		}

		public void setlocation(String location) {
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

