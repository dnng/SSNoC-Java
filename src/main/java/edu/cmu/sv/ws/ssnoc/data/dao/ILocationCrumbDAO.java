package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.LocationCrumbPO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of Location Crumb information in the system.
 * 
 */
public interface ILocationCrumbDAO {
	/**
	 * This method will save the information of the location crumb into the database.
	 * 
	 * @param locationCrumbPO
	 *            - Location Crumb information to be saved.
	 */
	long save(LocationCrumbPO locationCrumbPO);

	/**
	 * This method will load all the location crumbs in the
	 * database.
	 * 
	 * @return - List of all location crumbs.
	 */
	List<LocationCrumbPO> loadLocationCrumbs();
	
	/**
	 * This method will load all the location crumbs for a user using his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - List of all location crumbs.
	 */
	List<LocationCrumbPO> loadLocationCrumbsByUserName(String userName);
	
	/**
	 * This method will load a particular location crumb by it's id.
	 * 
	 * @param locationCrumbId
	 *            - location crumb id to search for.
	 * 
	 * @return - location crumb.
	 */
	LocationCrumbPO loadLocationCrumbById(int locationCrumbId);

}
