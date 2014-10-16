package edu.cmu.sv.ws.ssnoc.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
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
	public List<UserGroup> loadUnconnectedClusters() {
		Log.enter();
		
		List<UserGroup> users = null;
		try
		{
//			List<UserPO> userPOs = DAOFactory.getInstance().getUserGroupsDAO().getClusters(); 
//			
//			users = new ArrayList<User>();
//			for (UserPO po : userPOs) {
//				User dto = ConverterUtils.convert(po);
//				users.add(dto);
//			}
			
			
		} catch (Exception e) {
			
		} finally {
			
		}
		
		return users;
	}
	
}
