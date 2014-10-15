package edu.cmu.sv.ws.ssnoc.data.po;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all chat message information in the system.
 * This contains information like the user's id, his location, his wall message
 * and the creation time information entered by the user when posting a chat message. <br/>
 * Information is saved in SSN_CHAT_MESSAGES table.
 * 
 */
public class MessagePO {
	private long MessageId;
	private long AuthorId;
	private String AuthorName;
	private long TargetId;
	private String TargetName;
	private long LocationId;
	private String location;
	private String content;
	private String messageType;
	private Timestamp createdAt;

	public long getMessageId() {
		return MessageId;
	}

	public void setMessageId(long messageId) {
		MessageId = messageId;
	}

	public long getAuthorId() {
		return AuthorId;
	}

	public void setAuthorId(long authorId) {
		this.AuthorId = authorId;
	}

	public String getAuthorName() {
		return AuthorName;
	}


	public void setAuthorName(String string) {
		AuthorName = string;
	}

	public long getTargetId() {
		return TargetId;
	}

	public void setTargetId(long targetId) {
		this.TargetId = targetId;
	}

	public String getTargetName() {
		return TargetName;
	}

	public void setTargetName(String targetName) {
		TargetName = targetName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getLocationId() {
		return LocationId;
	}
	public void setLocationId(long LocationId) {
		this.TargetId = LocationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
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
