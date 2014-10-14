package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.MemoryCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of Memory Crumb information in the system.
 * 
 */
public interface IMemoryCrumbDAO {
	/**
	 * This method will save the information of the user into the database.
	 * 
	 * @param memoryCrumbPO
	 *            - Memory Crumb information to be saved.
	 */
	void save(MemoryCrumbPO memoryCrumbPO);

	/**
	 * This method will load all the memory crumbs in the
	 * database.
	 * 
	 * @return - List of all memory crumbs.
	 */
	List<MemoryCrumbPO> loadMemoryCrumbs();
	
	/**
	 * This method will load all the memory crumbs in the
	 * database in the specified interval.
	 * 
	 * @return - List of all memory crumbs in the specified interval
	 */
	List<MemoryCrumbPO> loadMemoryCrumbsInInterval(long timeInterval);
	
	/**
	 * This method will delete all memory crumbs in the system 
	 * 
	 */
	void deleteAll();
	
}
