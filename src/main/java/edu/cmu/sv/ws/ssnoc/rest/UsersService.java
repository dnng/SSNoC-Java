package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

@Path("/users")
public class UsersService extends BaseService {
	/**
	 * This method loads all users in the system.
	 *
	 * @return - List of all users.
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
			Log.error("Could not load users!");
			this.handleException(e);
		} finally {
			Log.exit(users);
		}
		return users;
	}

	/**
	 * This method loads up the users those have chatted with the specified user
	 * in the past
	 *
	 * @return - array of all active users
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{username}/chatbuddies")
	public List<User> loadChatBuddies(@PathParam("username") String userName) {
		Log.enter();
		List<User> users = null;
		try {
			List<UserPO> userPOs = DAOFactory.getInstance().getUserDAO().loadChatBuddies(userName);
			users = new ArrayList<User>();
			for (UserPO po : userPOs) {
				User dto = ConverterUtils.convert(po);
				users.add(dto);
			}
		} catch (Exception e) {
			Log.error("Could not load chat buddies!");
			this.handleException(e);
		} finally {
			Log.exit();
		}
		return users;
	}

	/**
	 * This method loads all active users in the system
	 *
	 * @return - array of all active users
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/active")
	public List<User> loadActiveUsers() {
		Log.enter();
		List<User> users = this.loadUsers();
		try {
			if (users != null) {
				for (User u : users) {
					if (u.getAccountStatus() != "active") {
						users.remove(u);
					}
				}
			}
		} catch (Exception e) {
			Log.error("Could not iterate users list");
			this.handleException(e);
		} finally {
			Log.exit();
		}
		return users;
	}
}
