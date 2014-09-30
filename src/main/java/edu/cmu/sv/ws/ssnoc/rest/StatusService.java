package edu.cmu.sv.ws.ssnoc.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.ILocationCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IStatusCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.LocationCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumb;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to status.
 * 
 */

@Path("/status")
public class StatusService extends BaseService {
	/**
	 * This method posts a new status
	 * 
	 * @param user
	 *            - An object of type User
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/status")
	public Response addStatusCrumb(StatusCrumb statusCrumb) {
		Log.enter(statusCrumb);
		StatusCrumb resp = new StatusCrumb();

		try {
			//Step 0: do some validations - check for null user name, null status, null location
			
			
			
			//Step 1: Get the existing user id from user name
			IUserDAO uDao = DAOFactory.getInstance().getUserDAO();
			UserPO existingUser = uDao.findByName(statusCrumb.getUserName());
			long userId = existingUser.getUserId();
			
			//Step 2: Insert a new location and get back the location id
			ILocationCrumbDAO lDao = DAOFactory.getInstance().getLocationCrumbDAO();
			LocationCrumbPO lcpo = new LocationCrumbPO();
			lcpo.setUserId(userId);
			lcpo.setLocation(statusCrumb.getLocation());
			long locationId = lDao.save(lcpo);
			
			//Step 3: Insert a new status crumb and get back the status crumb id
			IStatusCrumbDAO scDao = DAOFactory.getInstance().getStatusCrumbDAO();
			StatusCrumbPO scpo = new StatusCrumbPO();
			scpo.setUserId(userId);
			scpo.setLocationCrumbId(locationId);
			scpo.setStatus(statusCrumb.getStatus());
			long statusId = scDao.save(scpo);
			
			//Step 4: Update the user with the new status id, location crumb id and modified at time
			UserPO upo = new UserPO();
			upo.setUserId(userId);
			upo.setLastStatusCrumbId(statusId);
			upo.setLastLocationCrumbId(locationId);
			uDao.update(upo);
			
			//Step 5: send a response back
			resp = ConverterUtils.convert(scpo);
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}

}
