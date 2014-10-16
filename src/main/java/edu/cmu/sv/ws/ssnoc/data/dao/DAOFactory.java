package edu.cmu.sv.ws.ssnoc.data.dao;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;

/**
 * Singleton Factory pattern class to fetch all DAO implementations.
 */
public class DAOFactory {
	private static DAOFactory instance;

	/**
	 * Singleton instance access method to get the instance of the class to
	 * request a specific DAO implementation.
	 * 
	 * @return - DAOFactory instance
	 */
	public static final DAOFactory getInstance() {
		if (instance == null) {
			Log.info("Creating a new DAOFactory singleton instance.");
			instance = new DAOFactory();
		}

		return instance;
	}

	/**
	 * Method to get a new object implementing IUserDAO
	 * 
	 * @return - Object implementing IUserDAO
	 */
	public IUserDAO getUserDAO() {
		return new UserDAOImpl();
	}
	
	/**
	 * Method to get a new object implementing IStatusCrumbDAO
	 * 
	 * @return - Object implementing IStatusCrumbDAO
	 */
	public IStatusCrumbDAO getStatusCrumbDAO() {
		return new StatusCrumbDAOImpl();
	}
	
	/**
	 * Method to get a new object implementing ILocationCrumbDAO
	 * 
	 * @return - Object implementing ILocationCrumbDAO
	 */
	public ILocationCrumbDAO getLocationCrumbDAO() {
		return new LocationCrumbDAOImpl();
	}
	
	/**
	 * Method to get a new object implementing IMessageDAO
	 * 
	 * @return - Object implementing IMessageDAO
	 */
	public IMessageDAO getMessageDAO() {
		return new MessageDAOImpl();
	}
	
	/**
	 * Method to get a new object implementing IMemoryCrumbDAO
	 * 
	 * @return - Object implementing IMemoryCrumbDAO
	 */
	public IMemoryCrumbDAO getMemoryCrumbDAO() {
		return new MemoryCrumbDAOImpl();
	}
	
	/**
	 * Method to get a new object implementing IUserGroupsDAO
	 * 
	 * @return - Object implementing IUserGroupsDAO
	 */
	public IUserGroupDAO getUserGroupsDAO() {
		return new UserGroupDAOImpl();
	}

}
