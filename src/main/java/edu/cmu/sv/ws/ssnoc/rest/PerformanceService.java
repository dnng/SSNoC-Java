package edu.cmu.sv.ws.ssnoc.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.util.ConnectionPoolFactory;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;
import edu.cmu.sv.ws.ssnoc.data.util.IConnectionPool;
import edu.cmu.sv.ws.ssnoc.dto.PerformanceCrumb;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to status.
 * 
 */

@Path("/performance")
public class PerformanceService extends BaseService {
	/**
	 * This method sets up the performance testing hook
	 * 
	 * @param performanceCrumb
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/setup")
	public Response setupPerformanceTest(PerformanceCrumb performanceCrumb) {
		Log.enter(performanceCrumb);
		PerformanceCrumb resp = new PerformanceCrumb();

		try {
			IConnectionPool cp = ConnectionPoolFactory.getInstance()
					.getH2ConnectionPool();
			cp.switchConnectionToTest();
			
			DBUtils.reinitializeDatabase();

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}
	
	/**
	 * This method tears down  the performance testing hook
	 * 
	 * @param performanceCrumb
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/teardown")
	public Response teardownPerformanceTest(PerformanceCrumb performanceCrumb) {
		Log.enter(performanceCrumb);
		PerformanceCrumb resp = new PerformanceCrumb();

		try {
			IConnectionPool cp = ConnectionPoolFactory.getInstance()
					.getH2ConnectionPool();
			cp.switchConnectionToLive(); 

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}
	
	/**
	 * This method deletes all the existing posts when the post count reaches 1000
	 * 
	 * 
	 * @return - An object of type Response with the status of the request
	 */
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteTestPosts() {
		Log.enter();
		PerformanceCrumb resp = new PerformanceCrumb();

		try {
			IConnectionPool cp = ConnectionPoolFactory.getInstance()
					.getH2ConnectionPool();
			if(cp.isTest()) {
				IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();
				dao.truncateMessages();
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}
}
