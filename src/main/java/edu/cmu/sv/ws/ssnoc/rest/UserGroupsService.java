package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleGraph;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
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

			UndirectedGraph<String, DefaultEdge> stringGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);		            
			
			// build graph from users - each user is a vertex and connection an edge
			for (String user: allUserNames) {
				stringGraph.addVertex(user);
			}
			
			try {
				
				// set up the complementary graph
				Map<String, Set<String>> allUsers = dao.getAllBuddies(timeWindowInHours);
				Log.info("AllUsers: ", allUsers);
				for (String member1 : allUserNames) {
					for (String member2 : allUserNames) {
						
						if (!member1.equals(member2) && !isConnected(member1, member2, allUsers)) {
							stringGraph.addEdge(member1, member2);
							Log.info("Edges: ",member1, member2);
						}
					}
				
				}
				
				// fetch the cliques
				BronKerboschCliqueFinder<String, DefaultEdge> complementaryCliqueFinder = new BronKerboschCliqueFinder<String, DefaultEdge>(stringGraph);
				Collection<Set<String>> unconnectedGroups = complementaryCliqueFinder.getAllMaximalCliques();
				Log.enter(unconnectedGroups);
				userGroups = convertToUserGroup(unconnectedGroups);
			}  catch (Exception e) {
				handleException(e);
			} finally {
				Log.info("Graph; ", stringGraph);
			}
		} catch (Exception e) {
			handleException (e);
		} finally {
			Log.exit(userGroups);
		}
			
			return userGroups;
	
	}
		
    private boolean isConnected(String member1, String member2, Map<String, Set<String>> allUsers) {
    	
    	boolean isConnected = false;
    	if (allUsers.get(member1) != null) {
    		isConnected = allUsers.get(member1).contains(member2);
    	} 
    	if (allUsers.get(member2) != null) {
    		isConnected = isConnected || allUsers.get(member2).contains(member1);
    	}
    	return isConnected;
	}

	private List<UserGroup> convertToUserGroup(
			Collection<Set<String>> unconnectedGroups) {
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		
		for (Set<String> entry : unconnectedGroups) {
			UserGroup group = new UserGroup();
			List<String> members = new ArrayList<String>();
			members.addAll(entry);
			group.setUserNames(members);
			userGroups.add(group);
		}
		
		return userGroups;
	}
	
}

