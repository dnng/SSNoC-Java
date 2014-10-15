package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
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
			List<MessagePO> messagePO = DAOFactory.getInstance()
					.getMessageDAO().loadWallMessages();

			if (messagePO != null) {
				for (MessagePO po : messagePO) {
					Message dto = ConverterUtils.convert(po);
					messages.add(dto);
				}
			}
		} catch (Exception e) {
			handleException(e);
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
	@Path("/wallstatus")
	public List<Message> loadWallAndStatusMessages() {
		Log.enter();

		//Get Wall Messages
		List<Message> messages = new ArrayList<Message>();
		try {
			List<MessagePO> messagePO = DAOFactory.getInstance()
					.getMessageDAO().loadWallMessages();

			if (messagePO != null) {
				for (MessagePO po : messagePO) {
					Message dto = ConverterUtils.convert(po);
					messages.add(dto);
				}
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}
		
		//Get Status Messages
		List<StatusCrumb> statusCrumbs = null;
		try {
			List<StatusCrumbPO> statusCrumbPOs = DAOFactory.getInstance().getStatusCrumbDAO().loadStatusCrumbs();

			statusCrumbs = new ArrayList<StatusCrumb>();
			for (StatusCrumbPO po : statusCrumbPOs) {
				StatusCrumb dto = ConverterUtils.convert(po);
				statusCrumbs.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(statusCrumbs);
		}
		
		//Merge them
		for(StatusCrumb status:statusCrumbs)
		{
			messages.add(ConverterUtils.convertStatusToMessage(status));
		}
		
		Collections.sort(messages);
		Collections.reverse(messages);

		return messages;
	}


	
}
