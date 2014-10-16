package edu.cmu.sv.ws.ssnoc.rest;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.UserClusterPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.UserGroup;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to a message.
 * 
 */

@Path("/usergroups")
public class UserGroupsService extends BaseService {
	/**
	 * This method loads up the clusters who have not chatted within each other in the past 
	 * 
	 * @return - array of all active users
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "usergroups")
	@Path("/unconnected")
	public UserGroup loadUnconnectedClusters(@PathParam("timeWindowInMinutes") int timeWindowInMinutes) {
		Log.enter();
		
		UserGroup users = null;
		try
		{
			// confirm if timeWindow is to be relative to now. Using start, end times for now.
			Timestamp fromTime = new Timestamp(2014, 10, 14, 16, 01, 51, 922), toTime = new Timestamp(2014, 10, 15, 16, 01, 51, 922);
			
			// fetch all users
			List<UserPO> allUsers = DAOFactory.getInstance().getUserDAO().loadUsers();
			
			
			// fetch the buddy map
			List<UserClusterPO> userPOs = DAOFactory.getInstance().getUserGroupsDAO().loadUsergroups(fromTime, toTime);
			
			//prepare the complement graph
			for (UserClusterPO po : userPOs) {
				 
			}

			
			// find cliques in the complement graph			
			
		} catch (Exception e) {
			
		} finally {
			
		}
		
		return users;
	}
		
	
}
