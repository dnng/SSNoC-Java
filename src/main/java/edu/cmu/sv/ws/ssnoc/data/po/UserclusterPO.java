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
public class UserclusterPO {
	private String Author;
	private long AuthorId;
	private String Target;
	private long TargetId;{
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public long getAuthorId() {
		return AuthorId;
	}
	public void setAuthorId(long authorId) {
		AuthorId = authorId;
	}
	public String getTarget() {
		return Target;
	}
	public void setTarget(String target) {
		Target = target;
	}
	public long getTargetId() {
		return TargetId;
	}
	public void setTargetId(long targetId) {
		TargetId = targetId;
	}
}