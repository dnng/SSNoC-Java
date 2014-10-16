package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.UserGroupPO;

/**
 * Interface specifying the contract that all implementations will implement to
 */
public interface IUserGroupDAO {
	List<String> loadUserBuddies(String userName);
	List<String> loadUserBuddies(String userName, long timeInHours);
	List<String> getAllUsers();
	
	List<UserGroupPO> loadUserGroups();
	List<UserGroupPO> loadUserGroups(long fromTimeInHours);
}
