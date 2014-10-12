package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;

/**
 * This object contains message information that is responded as part of the REST
 * API request.
 * 
 */
public class Message {
	private long chatMessageId;
	private long senderId;
	private long receiverId;
	private String content;
	private String location;
	private Timestamp createdAt;
	public long getChatMessageId() {
		return chatMessageId;
	}
	public void setChatMessageId(long chatMessageId) {
		this.chatMessageId = chatMessageId;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
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
	
	
}
