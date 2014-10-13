package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This object contains user information that is responded as part of the REST
 * API request.
 * 
 */
public class PerformanceTest {
	private long testDurationInSecs;
	private Timestamp requestedAt;
	private Timestamp startedAt;
	private Timestamp endedAt;
	private long getsPerSecond;
	private long postsPerSecond;
	
	public long getTestDurationInSecs() {
		return testDurationInSecs;
	}

	public void setTestDurationInSecs(long testDurationInSecs) {
		this.testDurationInSecs = testDurationInSecs;
	}

	public Timestamp getRequestedAt() {
		return requestedAt;
	}

	public void setRequestedAt(Timestamp requestedAt) {
		this.requestedAt = requestedAt;
	}

	public Timestamp getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Timestamp startedAt) {
		this.startedAt = startedAt;
	}

	public Timestamp getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Timestamp endedAt) {
		this.endedAt = endedAt;
	}

	public long getGetsPerSecond() {
		return getsPerSecond;
	}

	public void setGetsPerSecond(long getsPerSecond) {
		this.getsPerSecond = getsPerSecond;
	}

	public long getPostsPerSecond() {
		return postsPerSecond;
	}

	public void setPostsPerSecond(long postsPerSecond) {
		this.postsPerSecond = postsPerSecond;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
