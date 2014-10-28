package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumb;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to a message.
 *
 */

@Path("/messages")
public class MessagesService extends BaseService {
	/**
	 * This method loads all message in the system.
	 *
	 * @return - List of all messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "messages")
	@Path("/wall")
	public List<Message> loadWallMessages() {
		Log.enter();

		List<Message> messages = new ArrayList<Message>();
		try {
			List<MessagePO> messagePO = DAOFactory.getInstance().getMessageDAO().loadWallMessages();

			if (messagePO != null) {
				for (MessagePO po : messagePO) {
					Message dto = ConverterUtils.convert(po);
					messages.add(dto);
				}
			}
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}

	/**
	 * This method loads all visible message in the system.
	 *
	 * @return - List of all messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "messages")
	@Path("/wall/visible")
	public List<Message> loadVisibleWallMessages() {
		Log.enter();
		IUserDAO dao = DAOFactory.getInstance().getUserDAO();
		UserPO existingUser = new UserPO();
		List<Message> messages = new ArrayList<Message>();

		messages = this.loadWallMessages();

		if (messages != null) {
			for (Message m : messages) {
				String authorName = m.getAuthor();
				existingUser = dao.findByName(authorName);
				if (existingUser.getAccountStatus() != "active") {
					messages.remove(m);
				}

			}
		}
		return messages;
	}

	/**
	 * This method loads all message in the system.
	 *
	 * @return - List of all messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "messages")
	@Path("/wallstatus")
	public List<Message> loadAllMessages() {
		Log.enter();

		// Get Wall Messages
		List<Message> messages = new ArrayList<Message>();
		try {
			List<MessagePO> messagePO = DAOFactory.getInstance().getMessageDAO().loadWallMessages();

			if (messagePO != null) {
				for (MessagePO po : messagePO) {
					Message dto = ConverterUtils.convert(po);
					messages.add(dto);
				}
			}
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit(messages);
		}

		// Get Status Messages
		List<StatusCrumb> statusCrumbs = null;
		try {
			List<StatusCrumbPO> statusCrumbPOs = DAOFactory.getInstance().getStatusCrumbDAO().loadStatusCrumbs();

			statusCrumbs = new ArrayList<StatusCrumb>();
			for (StatusCrumbPO po : statusCrumbPOs) {
				StatusCrumb dto = ConverterUtils.convert(po);
				statusCrumbs.add(dto);
			}
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit(statusCrumbs);
		}

		// Merge them
		for (StatusCrumb status : statusCrumbs) {
			messages.add(ConverterUtils.convertStatusToMessage(status));
		}

		Collections.sort(messages);
		Collections.reverse(messages);

		return messages;
	}

	/**
	 * This method fetches chat messages sent between two users
	 *
	 * @return - list of messages
	 */
	@GET
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName1}/{userName2}")
	public List<Message> getAllChatMessagesForPeers(@PathParam("userName1") String userName1, @PathParam("userName2") String userName2) {
		Log.enter(userName1, userName2);
		List<Message> messages = null;
		try {
			if (userName1 != null && userName2 != null) {
				List<MessagePO> peerMessages = DAOFactory.getInstance().getMessageDAO().getAllChatMessagesForPeers(userName1, userName2);
				if (peerMessages != null) {
					messages = new ArrayList<Message>();
					for (MessagePO po : peerMessages) {
						Message dto = ConverterUtils.convert(po);
						messages.add(dto);
					}
				}
			}
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}

	/**
	 * This method fetches only visible chat messages sent between two users
	 *
	 * @return - list of visible chat messages
	 */
	@GET
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName1}/{userName2}/visible")
	public List<Message> getAllVisibleChatMessagesForPeers(
			@PathParam("userName1") String userName1, @PathParam("userName2") String userName2) {
		Log.enter(userName1, userName2);
		List<Message> messages = this.getAllChatMessagesForPeers(userName1, userName2);
		try {
			if (messages != null) {
				for (Message m : messages) {
					if (m.getStatus() != "visible") {
						messages.remove(m);
					}
				}
			}
		} catch (Exception e) {
			Log.error("Could not load list of visible chat messages");
			this.handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}

	/**
	 * This method loads all message in the system.
	 *
	 * @return - List of all messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "messages")
	@Path("/announcement")
	public List<Message> loadAnnouncementMessages() {
		Log.enter();

		List<Message> messages = new ArrayList<Message>();
		try {
			List<MessagePO> messagePO = DAOFactory.getInstance().getMessageDAO().loadAnnouncementMessages();

			if (messagePO != null) {
				for (MessagePO po : messagePO) {
					Message dto = ConverterUtils.convert(po);
					messages.add(dto);
				}
			}
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}

	/**
	 * This method loads all visible messages in the system.
	 *
	 * @return - List of all visible messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "messages")
	@Path("/announcement/visible")
	public List<Message> loadVisibleAnnouncementMessages() {
		Log.enter();
		List<Message> messages = this.loadAnnouncementMessages();
		try {
			if (messages != null) {
				for (Message m : messages) {
					if (m.getStatus() != "visible") {
						messages.remove(m);
					}
				}
			}
		} catch (Exception e) {
			Log.error("Could not load visible messages");
			this.handleException(e);
		} finally {
			Log.exit(messages);
		}
		return messages;
	}

}
