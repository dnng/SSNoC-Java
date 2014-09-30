package edu.cmu.sv.ws.ssnoc.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.SSNCipher;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.ILocationCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IStatusCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.LocationCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.LocationCrumb;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumb;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to location.
 * 
 */

@Path("/location")
public class LocationService extends BaseService {
	/**
	 * This method posts a new location
	 * 
	 * @param user
	 *            - An object of type User
	 * @return - An object of type Response with the location of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/location")
	public Response addLocationCrumb(LocationCrumb locationCrumb) {
		Log.enter(locationCrumb);
		LocationCrumb resp = new LocationCrumb();

		try {
			//Step 0: do some validations - check for null user name, null status, null location
			
			
			//Step 1: Get the existing user id from user name
			IUserDAO uDao = DAOFactory.getInstance().getUserDAO();
			UserPO existingUser = uDao.findByName(locationCrumb.getUserName());
			long userId = existingUser.getUserId();
			
			//Step 2: Insert a new location and get back the location id
			ILocationCrumbDAO lDao = DAOFactory.getInstance().getLocationCrumbDAO();
			LocationCrumbPO lcpo = new LocationCrumbPO();
			lcpo.setUserId(userId);
			lcpo.setLocation(locationCrumb.getLocation());
			
			
			//Step 5: send a response back
			resp = ConverterUtils.convert(lcpo);
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}

}
