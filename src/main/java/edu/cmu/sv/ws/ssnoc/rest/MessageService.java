package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.ILocationCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IStatusCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.LocationCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumb;
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
	 * @param user - An object of type User
	 * 
	 * @return - An object of type Response with the message of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public Response addMessage(Message message) {
		Log.enter(message);
		Message resp = new Message();

		try {
			// Step 0: do some validations - check for null user name, null
			// status, null location

			// Step 1: Get the existing user id from user name
			IUserDAO uDao = DAOFactory.getInstance().getUserDAO();
			UserPO existingUser = uDao.findByName(message.getUserName());
			long userId = 0;
			if(existingUser != null)
				userId = existingUser.getUserId();

			// Step 2: Insert a new location and get back the location id
			ILocationCrumbDAO lDao = DAOFactory.getInstance()
					.getLocationCrumbDAO();
			LocationCrumbPO lcpo = new LocationCrumbPO();
			lcpo.setUserId(userId);
			lcpo.setLocation(message.getLocation());
			long locationId = lDao.save(lcpo);

			// Step 3: Insert a new wall message and get back the message
			// id
			IMessageDAO mDao = DAOFactory.getInstance()
					.getMessageDAO();
			MessagePO mpo = new MessagePO();
			mpo.setAuthorId(userId);
			mpo.setLocationId(locationId);
			mpo.setMessage(message.getMessage());
		//	long messageId = mDao.saveWallMessage(mpo);

			// Step 4: Update the user with the new message, location crumb id
			// and modified at time
//			UserPO upo = new UserPO();
//			upo.setUserId(userId);
//			upo.setLastStatusCrumbId(statusId);
//			upo.setLastLocationCrumbId(locationId);
//			uDao.update(upo);

			// Step 5: send a response back
			resp = ConverterUtils.convert(mpo);
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}
	@Path("/wall")
		/**
		 * This method loads all message in the system.
		 * 
		 * @return - List of all messages.
		 */
		@GET
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@XmlElementWrapper(name = "messages")
		public List<Message> loadMessage() {
			Log.enter();

			List<Message> message = null;
			try {
				List<MessagePO> messagePO = DAOFactory.getInstance().getMessageDAO().loadMessage();

				message = new ArrayList<Message>();
				for (MessagePO po : messagePO) {
					Message dto = ConverterUtils.convert(po);
					message.add(dto);
				}
			} catch (Exception e) {
				handleException(e);
			} finally {
				Log.exit(message);
			}

			return message;
		}
	/*
	 * This method fetches all message information by the given message ID if
	 * present
	 * 
	 * @param messageID - messageID to fetch
	 * 
	 * @return - An object of type Response with the status of the request
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "message")
	public Message loadUser(@PathParam("messageID") Long messageID) {
		Log.enter();

		Message message = null;
		try {
			//MessagePO msgPO = DAOFactory.getInstance().getMessageDAO().loadExistingMessage(messageID);
			MessagePO msgPO = new MessagePO();
			msgPO.setContent("blah!");
			msgPO.setLocation("somewhere");


			message = ConverterUtils.convert(msgPO);

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(message);
		}

		return message;
	}
}
