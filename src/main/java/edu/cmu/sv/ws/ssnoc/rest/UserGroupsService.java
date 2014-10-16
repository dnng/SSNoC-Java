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
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserGroupDAO;
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
	@Path("/unconnected/{timeWindowInHours}")
	public List<UserGroup> loadUnconnectedClusters(@PathParam("timeWindowInHours") int timeWindowInHours) {
		Log.enter();
		
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		try
		{
			IUserGroupDAO dao = DAOFactory.getInstance().getUserGroupsDAO();
			
			//fetch all user names
			List<String> allUserNames = dao.getAllUsers();
			
			//For each user name - get his buddies, and the cluster is everyone except those buddies. Rinse and repeat over all the users. 
			for(String user : allUserNames)
			{
				List<String> buddies = (timeWindowInHours == 0) ? dao.loadUserBuddies(user) 
						: dao.loadUserBuddies(user, timeWindowInHours);
				
				//Super complex algorithm goes here!
				List<String> temp = new ArrayList<String>(allUserNames);
				temp.removeAll(buddies);
				
				if(temp.size() > 0)
				{
					UserGroup aUserGroup = new UserGroup();
					aUserGroup.setUserNames(temp);
					
					userGroups.add(aUserGroup);
				}
			}		
			
		} catch (Exception e) {
			
		} finally {
			
		}
		
		return userGroups;
	}
		
	
}
