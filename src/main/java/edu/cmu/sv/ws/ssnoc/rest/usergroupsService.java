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
import edu.cmu.sv.ws.ssnoc.data.po.UserclusterPO;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumb;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to a message.
 * 
 */

@Path("/usergroups")
public class usergroupsService extends BaseService {
	/**
	 * This method loads up the users those have chatted with the specified user in the past
	 * 
	 * @return - array of all active users
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "usergroups")
	@Path("/unconnected")

	public ArrayList<User> loadChatBuddiesUsers(@PathParam("username") String userName) {
		Log.enter();
		
		ArrayList<User> users = null;
		try
		{
			ArrayList<UserclusterPO> userPOs = DAOFactory.getInstance().
			
			users = new ArrayList<User>();
			for (UserPO po : userPOs) {
				User dto = ConverterUtils.convert(po);
				users.add(dto);
			}
			
			
		} catch (Exception e) {
			
		} finally {
			
		}
		
		return users;
	}
	@Path("/users")
	public class UsersService extends BaseService {
		/**
		 * This method loads all active users in the system.
		 * 
		 * @return - List of all active users.
		 */
		@GET
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@XmlElementWrapper(name = "users")
		public List<User> loadUsers() {
			Log.enter();

			List<User> users = null;
			try {
				List<UserPO> userPOs = DAOFactory.getInstance().getUserDAO().loadUsers();

				users = new ArrayList<User>();
				for (UserPO po : userPOs) {
					User dto = ConverterUtils.convert(po);
					users.add(dto);
				}
			} catch (Exception e) {
				handleException(e);
			} finally {
				Log.exit(users);
			}

			return users;
		}
}
