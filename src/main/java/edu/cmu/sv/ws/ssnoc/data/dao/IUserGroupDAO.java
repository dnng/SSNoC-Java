package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface specifying the contract that all implementations will implement to
 */
public interface IUserGroupDAO {
	List<String> getAllUsers();
	Map<String, Set<String>> getAllBuddies(int timeWindowInHours);
}
