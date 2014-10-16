package edu.cmu.sv.ws.ssnoc.data.po;

import java.util.List;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all user information in the system.
 * This contains information like the user's name, his role, his account status
 * and the password information entered by the user when signing up. <br/>
 * Information is saved in SSN_USERS table.
 * 
 */
public class UserClusterPO {
	
	private List<String> Author;
	private long AuthorId;
	private List<String> target;
	private long targetId;
	
	public List<String> getAuthor() {
		return Author;
	}


	public void setAuthor(List<String> author) {
		Author = author;
	}


	public long getAuthorId() {
		return AuthorId;
	}


	public void setAuthorId(long authorId) {
		AuthorId = authorId;
	}


	public List<String> getTarget() {
		return target;
	}


	public void setTarget(List<String> target) {
		this.target = target;
	}


	public long getTargetId() {
		return targetId;
	}


	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
}