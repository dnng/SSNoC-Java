package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMemoryCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MemoryCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.MemoryCrumb;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to status.
 * 
 */

@Path("/memory")
public class MemoryService extends BaseService {
	/**
	 * This method sets up the measure memory use case
	 * 
	 * @param performanceCrumb
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/start")
	public Response startMemoryMeasurement(MemoryCrumb memoryCrumb) {
		Log.enter(memoryCrumb);
		MemoryCrumb resp = new MemoryCrumb();

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
	public Response stopMemoryMeasurement(MemoryCrumb memoryCrumb) {
		Log.enter(memoryCrumb);
		MemoryCrumb resp = new MemoryCrumb();

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
	 * This method deletes all the existing memory crumbs
	 * 
	 * 
	 * @return - An object of type Response with the status of the request
	 */
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteMemoryCrumbs() {
		Log.enter();
		MemoryCrumb resp = new MemoryCrumb();

		try {
			IMemoryCrumbDAO dao = DAOFactory.getInstance().getMemoryCrumbDAO();
			dao.deleteAll();
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}
	
	/**
	 * This method deletes all the existing memory crumbs
	 * 
	 * 
	 * @return - An object of type Response with the status of the request
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<MemoryCrumb> getMemoryCrumbsFor24Hours() {
		Log.enter();
		List<MemoryCrumb> memoryCrumbs = null;
		try {
			List<MemoryCrumbPO> memoryCrumbPOs = DAOFactory.getInstance().getMemoryCrumbDAO().loadMemoryCrumbs(); 

			memoryCrumbs = new ArrayList<MemoryCrumb>();
			for (MemoryCrumbPO po : memoryCrumbPOs) {
				MemoryCrumb dto = ConverterUtils.convert(po);
				memoryCrumbs.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(memoryCrumbs);
		}
		
		return memoryCrumbs;
	}


}
