package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;

/**
 * This object contains message information that is responded as part of the REST
 * API request.
 *
 */
public class Message {
	private long MessageId;
	private long targetId;
	private long authorId;
	private String content;
	private String location;
	private String message;
	private Timestamp createdAt;


	public long getMessageId() {
		return MessageId;
	}
	public void setMessageId(long messageId) {
		MessageId = messageId;
	}


	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String setUserName() {
		// TODO Auto-generated method stub
		return null;
	}



}
