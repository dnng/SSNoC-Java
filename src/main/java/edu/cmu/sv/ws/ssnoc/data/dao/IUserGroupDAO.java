package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import org.eclipse.persistence.annotations.Array;

import edu.cmu.sv.ws.ssnoc.data.po.UserClusterPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * Interface specifying the contract that all implementations will implement to
 */
public interface IUserGroupDAO {
	/**
	 * This method will load all the chat buddies in a time interval
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	List<String> getChatBuddies(String userName, int timeIntervalInHours);
	
	List<UserClusterPO> getClusters();
	
	List<UserClusterPO> getClusters(int timeIntervalInHours);

}
