package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This object contains user information that is responded as part of the REST
 * API request.
 * 
 */
public class MemoryCrumb {
	private long usedVolatileMemory;
	private long remainingVolatileMemory;
	private long usedPersistentMemory;
	private long remainingPersistentMemory;
	private long onlineUsers;
	private Timestamp createdAt;

	public long getUsedVolatileMemory() {
		return usedVolatileMemory;
	}

	public void setUsedVolatileMemory(long usedVolatileMemory) {
		this.usedVolatileMemory = usedVolatileMemory;
	}

	public long getRemainingVolatileMemory() {
		return remainingVolatileMemory;
	}

	public void setRemainingVolatileMemory(long remainingVolatileMemory) {
		this.remainingVolatileMemory = remainingVolatileMemory;
	}

	public long getUsedPersistentMemory() {
		return usedPersistentMemory;
	}

	public void setUsedPersistentMemory(long usedPersistentMemory) {
		this.usedPersistentMemory = usedPersistentMemory;
	}

	public long getRemainingPersistentMemory() {
		return remainingPersistentMemory;
	}

	public void setRemainingPersistentMemory(long remainingPersistentMemory) {
		this.remainingPersistentMemory = remainingPersistentMemory;
	}

	public long getOnlineUsers() {
		return onlineUsers;
	}

	public void setOnlineUsers(long onlineUsers) {
		this.onlineUsers = onlineUsers;
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
