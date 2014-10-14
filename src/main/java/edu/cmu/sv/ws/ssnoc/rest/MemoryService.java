package edu.cmu.sv.ws.ssnoc.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.dto.PerformanceCrumb;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to status.
 * 
 */

@Path("/memory")
public class MemoryService extends BaseService {
	/**
	 * This method sets up the performance testing hook
	 * 
	 * @param performanceCrumb
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/start")
	public Response setupPerformanceTest(PerformanceCrumb performanceCrumb) {
		Log.enter(performanceCrumb);
		PerformanceCrumb resp = new PerformanceCrumb();

		try {
			//Step 1: Create test DB, setup limits for tests?
			
			//Step 2: Switch database connection to test DB
			
			//Step 3: Note down request time and start time and 
			

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
	@Path("/stop")
	public Response teardownPerformanceTest(PerformanceCrumb performanceCrumb) {
		Log.enter(performanceCrumb);
		PerformanceCrumb resp = new PerformanceCrumb();

		try {
			//Step 1: Create test DB, setup limits for tests?
			
			//Step 2: Switch database connection to test DB
			
			//Step 3: Note down request time and start time and 

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}


}
