package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This object contains message information that is responded as part of the REST
 * API request.
 *
 */
public class Message implements Comparable<Message> {
	private long messageId;
	private long authorId;
	private String author;
	private long targetId;
	private String target;
	private String content;
	private long locationId;
	private String location;
	private String messageType;
	private String status;
	private Timestamp postedAt;

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Timestamp postedAt) {
		this.postedAt = postedAt;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	@Override
	public int compareTo(Message o) {
		return getPostedAt().compareTo(o.getPostedAt());
	}
}
