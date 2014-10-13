package edu.cmu.sv.ws.ssnoc.data.dao;

import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of Message information in the system.
 * 
 */
public interface IMessageDAO {

	/**
	 * This method will fetch the information of the Message into the database.
	 * 
	 * @param messageID
	 *            - Message to be retrieved
	 */
	MessagePO loadExistingMessage(long messageID);
	
}
