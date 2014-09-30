package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of Status Crumb information in the system.
 * 
 */
public interface IStatusCrumbDAO {
	/**
	 * This method will save the information of the status crumb into the database.
	 * 
	 * @param statusCrumbPO
	 *            - Status Crumb information to be saved.
	 */
	void save(StatusCrumbPO statusCrumbPO);

	/**
	 * This method will load all the status crumbs in the
	 * database.
	 * 
	 * @return - List of all status crumbs.
	 */
	List<StatusCrumbPO> loadStatusCrumbs();
	
	/**
	 * This method will load all the status crumbs for a user using his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - List of all status crumbs.
	 */
	List<StatusCrumbPO> loadStatusCrumbsByUserName(String userName);
	
	/**
	 * This method will load a particular status crumb by it's id.
	 * 
	 * @param statusCrumbId
	 *            - status crumb id to search for.
	 * 
	 * @return - status crumb.
	 */
	StatusCrumbPO loadStatusCrumbById(int statusCrumbId);

}
