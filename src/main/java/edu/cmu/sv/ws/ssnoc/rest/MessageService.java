package edu.cmu.sv.ws.ssnoc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.User;


/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to a message.
 * 
 */

@Path("/message")
public class MessageService extends BaseService {
	/**
	 * This method fetches all message information by the given message ID if present
	 * 
	 * @param messageID
	 *            - messageID to fetch
	 * @return - An object of type Response with the status of the request
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{messageID}")
	public MessagePO loadUser(@PathParam("messageID") Long messageID) {
		Log.enter(messageID);

		MessagePO message = null;
		try {
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(message);
		}

		return message;
	}
	
	private MessagePO loadExistingMessage(Long messageID) {
		MessagePO message = new MessagePO();
		return message;
	}

}
