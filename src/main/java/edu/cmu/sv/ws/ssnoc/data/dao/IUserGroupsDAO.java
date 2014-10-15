package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import org.eclipse.persistence.annotations.Array;

import edu.cmu.sv.ws.ssnoc.data.po.UserClusterPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * Interface specifying the contract that all implementations will implement to
 */
public interface IUserGroupsDAO {
	/**
	 * This method will save the information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	void save(UserPO userPO);

	/**
	 * This method will load all the users in the
	 * database.
	 * 
	 * @return - List of all users.
	 */
	List<UserPO> loadUsers();

	/**
	 * This method with search for a user by his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - UserPO with the user information if a match is found.
	 */
	UserPO findByName(String userName);
	
	/**
	 * This method will update the information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	void update(UserPO userPO);
	
	/**
	 * This method returns the Users the given @user has had a 'CHAT' conversation with before
	 * @return - list of Users
	 */
	List<UserPO> loadChatBuddies(String userName);
	
	List<UserClusterPO>loadUnconnectedUsers(Array userName);

}
