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
public class MemoryCrumbPO {
	private long memoryCrumbId;
	private long usedVolatileMemory;
	private long remainingVolativeMemory;
	private long usedPersistentMemory;
	private long remainingPersistentMemory;
	private long onlineUsers;
	private Timestamp createdAt;

	public long getMemoryCrumbId() {
		return memoryCrumbId;
	}

	public void setMemoryCrumbId(long memoryCrumbId) {
		this.memoryCrumbId = memoryCrumbId;
	}

	public long getUsedVolatileMemory() {
		return usedVolatileMemory;
	}

	public void setUsedVolatileMemory(long usedVolatileMemory) {
		this.usedVolatileMemory = usedVolatileMemory;
	}

	public long getRemainingVolativeMemory() {
		return remainingVolativeMemory;
	}

	public void setRemainingVolativeMemory(long remainingVolativeMemory) {
		this.remainingVolativeMemory = remainingVolativeMemory;
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
