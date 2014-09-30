package edu.cmu.sv.ws.ssnoc.data.po;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all wall message information in the system.
 * This contains information like the user's id, his location, his wall message
 * and the creation time information entered by the user when posting a wall message. <br/>
 * Information is saved in SSN_WALL_MESSAGES table.
 * 
 */
public class WallMessagePO {
	private long wallMessageId;
	private long senderId;
	private String content;
	private String location;
	private Timestamp createdAt;

	public long getWallMessageId() {
		return wallMessageId;
	}

	public void setWallMessageId(long wallMessageId) {
		this.wallMessageId = wallMessageId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
