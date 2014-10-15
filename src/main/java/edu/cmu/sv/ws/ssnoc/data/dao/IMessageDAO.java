package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import org.apache.logging.log4j.message.Message;

import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
//import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
//import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of Status Crumb information in the system.
 * 
 */
public interface IMessageDAO {
	/**
	 * This method will save the information of the message into the database.
	 * 
	 * @param MessagePO
	 *            - Status Crumb information to be saved.
	 */
	long saveChatMessage(MessagePO messagePO);
	long saveWallMessage(MessagePO messagePO);

	/**
	 * This method will load all the message in the
	 * database.
	 * 
	 * @return - List of all messages.
	 */
	List<MessagePO> loadMessage();
	
	/**
	 * This method will load all wall message in the
	 * database.
	 * 
	 * @return - List of all  wall messages.
	 */
	List<MessagePO> loadWallMessage();
	

	/**
	 * This method will load all chat message in the
	 * database.
	 * 
	 * @return - List of all  chat messages.
	 */
	List<MessagePO> loadChatMessage();
	
	/**
	 * This method will load all the messages for a user in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - List of all messages.
	 */
	List<MessagePO> loadMessageByUserName(String userName);
	
	/**
	 * This method will load a particular message by it's id.
	 * 
	 * @param statusCrumbId
	 *            - status crumb id to search for.
	 * 
	 * @return - message.
	 */
	MessagePO loadMessageById(long message_id);
	//MessagePO loadExistingMessage(long messageID);
}
