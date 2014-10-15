package edu.cmu.sv.ws.ssnoc.rest;

import java.sql.Timestamp;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.exceptions.UnauthorizedUserException;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.ILocationCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.LocationCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to a message.
 * 
 */

@Path("/message")
public class MessageService extends BaseService {

	/*
	 * This method posts a new message on wall
	 * 
	 * @param message - An object of type Message
	 * 
	 * @return - An object of type Response with the message of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public Response addWallMessage(@PathParam("userName") String userName, Message message) {
		Log.enter(userName, message);
		Message resp = new Message();

		try {
			// Step 0: do some validations - check for null user name, null
			// status, null location

			// Step 1: Get the existing user id from user name
			IUserDAO uDao = DAOFactory.getInstance().getUserDAO();
			UserPO existingUser = uDao.findByName(userName);
			long userId = 0;
			if (existingUser != null)
				userId = existingUser.getUserId();

			//TODO: If no location is passed in, then skip this step
			// Step 2: Insert a new location and get back the location id
			ILocationCrumbDAO lDao = DAOFactory.getInstance()
					.getLocationCrumbDAO();
			LocationCrumbPO lcpo = new LocationCrumbPO();
			lcpo.setUserId(userId);
			lcpo.setLocation(message.getLocation());
			long locationId = lDao.save(lcpo);

			// Step 3: Insert a new wall message and get back the message id
			MessagePO mpo = new MessagePO();
			mpo.setAuthorId(userId);
			mpo.setLocationId(locationId);
			mpo.setContent(message.getContent());
			long messageId = DAOFactory.getInstance().getMessageDAO().saveWallMessage(mpo);

			// Step 4: Update the user with the new message, location crumb id
			// and modified at time
			UserPO upo = new UserPO();
			upo.setUserId(userId);
			upo.setLastLocationCrumbId(locationId);
			uDao.update(upo);

			// Step 5: send a response back
			resp = ConverterUtils.convert(mpo);
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}
	
	/**
	 * This method fetches all message information by the given message ID if present
	 * 
	 * @param messageID
	 *            - messageID to fetch
	 * @return - An object of type Response with the status of the request
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "message")
	public Message loadMessage(@PathParam("messageID") long messageID) {
		Log.enter();

		Message message = null;
		try {
			IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();
			Log.enter(messageID);
			MessagePO msgPO = dao.loadMessageById(messageID);
			message = ConverterUtils.convert(msgPO);

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(message);
		}

		return message;	
	}
	
	/**
	 * This method sends a chat message to another user
	 * 
	 * @return a success status if message created
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{sendingUserName}/{receivingUserName}")
	public Response postPrivateChatMessage(@PathParam("sendingUserName") String sendingUserName, 
			@PathParam("receivingUserName") String receivingUserName,
			Message message) {
		Log.enter(sendingUserName, receivingUserName);
		try {
			if (sendingUserName != null && receivingUserName != null && message.getContent() != null && message.getPostedAt() != null)
			{
				boolean success = sendPrivateMessage(sendingUserName, receivingUserName, message.getContent(), message.getPostedAt(), message.getLocation());
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return ok();
	}
	
	private boolean sendPrivateMessage(String sendingUserName, String receivingUserName, String content, Timestamp postedAt, String location)	{
		boolean status = false;
		return status;
	}
}
