package edu.cmu.sv.ws.ssnoc.rest;

import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.h2.util.StringUtils;

import edu.cmu.sv.ws.ssnoc.common.exceptions.ServiceException;
import edu.cmu.sv.ws.ssnoc.common.exceptions.UnauthorizedUserException;
import edu.cmu.sv.ws.ssnoc.common.exceptions.ValidationException;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.SSNCipher;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to users.
 *
 */

@Path("/user")
public class UserService extends BaseService {
	/**
	 * This method checks the validity of the user name and if it is valid, adds
	 * it to the database
	 *
	 * @param user
	 *            - An object of type User
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/signup")
	public Response addUser(User user) {
		Log.enter(user);
		User resp = new User();

		try {
			IUserDAO dao = DAOFactory.getInstance().getUserDAO();
			UserPO existingUser = dao.findByName(user.getUserName());

			// Validation to check that user name should be unique
			// in the system. If a new users tries to register with
			// an existing userName, notify that to the user.
			if (existingUser != null) {
				Log.trace("User name provided already exists. Validating if it is same password ...");
				if (!this.validateUserPassword(user.getPassword(), existingUser)) {
					Log.warn("Password is different for the existing user name.");
					throw new ValidationException("User name already taken");
				} else {
					Log.debug("Yay!! Password is same for the existing user name.");

					resp.setUserName(existingUser.getUserName());
					return this.ok(resp);
				}
			}

			UserPO po = ConverterUtils.convert(user);
			po = SSNCipher.encryptPassword(po);
			
			//Set default privilegeLevel and accountStatus
			if(po.getPrivilegeLevel() == null) po.setPrivilegeLevel("Citizen");
			if(po.getAccountStatus() == null) po.setAccountStatus("Active");

			dao.save(po);
			resp = ConverterUtils.convert(po);
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit();
		}

		return this.created(resp);
	}

	/**
	 * This method is used to login a user.
	 *
	 * @param user
	 *            - User information to login
	 *
	 * @return - Status 200 when successful login. Else other status.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}/authenticate")
	public Response loginUser(@PathParam("userName") String userName,
			User user) {
		Log.enter(userName, user);

		try {
			UserPO po = this.loadExistingUser(userName);
			if (!this.validateUserPassword(user.getPassword(), po)) {
				throw new UnauthorizedUserException(userName);
			}
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit();
		}

		return this.ok();
	}

	/**
	 * This method will validate the user's password based on what information
	 * is sent from the UI, versus the information retrieved for that user from
	 * the database.
	 *
	 * @param password
	 *            - Encrypted Password
	 * @param po
	 *            - User info from DB
	 *
	 * @return - Flag specifying YES or NO
	 */
	private boolean validateUserPassword(String password, UserPO po) {
		try {
			SecretKey key = SSNCipher.getKey(StringUtils.convertHexToBytes(po
					.getSalt()));
			if (password.equals(SSNCipher.decrypt(
					StringUtils.convertHexToBytes(po.getPassword()), key))) {
				return true;
			}
		} catch (Exception e) {
			Log.error("An Error occured when trying to decrypt the password", e);
			throw new ServiceException("Error when trying to decrypt password",
					e);
		}

		return false;
	}

	/**
	 * All all information related to a particular userName.
	 *
	 * @param userName
	 *            - User Name
	 *
	 * @return - Details of the User
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public User loadUser(@PathParam("userName") String userName) {
		Log.enter(userName);

		User user = null;
		try {
			UserPO po = this.loadExistingUser(userName);
			user = ConverterUtils.convert(po);
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit(user);
		}

		return user;
	}

	/**
	 * Updates a user profile
	 *
	 *
	 * @param user
	 * 				- An object of the user type
	 *
	 * @return - 201 if username is updated; 200 if username is not updated
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public Response updateUserRecord(@PathParam("userName") String userName, User userInfo) {
		Log.enter(userName, userInfo);
		User resp = new User();
		IUserDAO dao = DAOFactory.getInstance().getUserDAO();
		UserPO existingUser = new UserPO();
		boolean userUpdated = false;

		try {
			existingUser = dao.findByName(userName);
			if (existingUser != null) {
				if (userInfo.getAccountStatus() != null &&
						!userInfo.getAccountStatus().isEmpty() &&
						!existingUser.getAccountStatus().equals(userInfo.getAccountStatus()) ) {
					existingUser.setAccountStatus(userInfo.getAccountStatus());
					userUpdated = true;
				}
				if (userInfo.getPrivilegeLevel() != null &&
						!userInfo.getPrivilegeLevel().isEmpty() &&
						!existingUser.getPrivilegeLevel().equals(userInfo.getPrivilegeLevel()) ) {
					existingUser.setPrivilegeLevel(userInfo.getPrivilegeLevel());
					userUpdated = true;
				}
				if (userInfo.getUserName() != null &&
						!userInfo.getUserName().isEmpty() &&
						!existingUser.getUserName().equals(userInfo.getUserName()) ) {
					existingUser.setUserName(userInfo.getUserName());
					userUpdated = true;
				}
				//TODO: This needs to be tested because we are checking a hashed password with an unhashed new password
				if (userInfo.getPassword() != null &&
						!userInfo.getPassword().isEmpty() &&
						!existingUser.getPassword().equals(userInfo.getPassword()) ) {
					existingUser.setPassword(userInfo.getPassword());
					existingUser = SSNCipher.encryptPassword(existingUser);
					userUpdated = true;
				}
			} else {
				Log.debug("UserName not found");
				resp = null;
			}
		} catch (Exception e) {
			this.handleException(e);
		} finally {
			Log.exit();
		}
		if (userUpdated) {
			dao.update(existingUser);
			resp = ConverterUtils.convert(existingUser);
			return this.created(resp);
		} else {
			return this.ok();
		}
	}

}
