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
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.LocationCrumb;
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
	public Response addLocationCrumb(User user, LocationCrumb locationCrumb) {
		Log.enter(user + " " + locationCrumb);
		LocationCrumb resp = new LocationCrumb();

		try {
			IUserDAO uDao = DAOFactory.getInstance().getUserDAO();
			UserPO existingUser = uDao.findByName(user.getUserName());
			
			ILocationCrumbDAO scDao = DAOFactory.getInstance().getLocationCrumbDAO();

//			// Validation to check that user name should be unique
//			// in the system. If a new users tries to register with
//			// an existing userName, notify that to the user.
//			if (existingUser != null) {
//				Log.trace("User name provided already exists. Validating if it is same password ...");
//				if (!validateUserPassword(user.getPassword(), existingUser)) {
//					Log.warn("Password is different for the existing user name.");
//					throw new ValidationException("User name already taken");
//				} else {
//					Log.debug("Yay!! Password is same for the existing user name.");
//
//					resp.setUserName(existingUser.getUserName());
//					return ok(resp);
//				}
//			}

			UserPO po = ConverterUtils.convert(user);
			po = SSNCipher.encryptPassword(po);

			
//			dao.save(po);
//			resp = ConverterUtils.convert(po);
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}

}
