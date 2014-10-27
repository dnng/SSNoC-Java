package edu.cmu.sv.ws.ssnoc.data.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * DAO implementation for saving User information in the H2 database.
 *
 */
public class UserDAOImpl extends BaseDAOImpl implements IUserDAO {
	/**
	 * This method will load users from the DB with specified account status. If
	 * no status information (null) is provided, it will load all users.
	 *
	 * @return - List of users
	 */
	public List<UserPO> loadUsers() {
		Log.enter();

		String query = SQL.FIND_ALL_USERS;

		List<UserPO> users = new ArrayList<UserPO>();
		try (Connection conn = this.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			users = this.processResults(stmt);
		} catch (SQLException e) {
			this.handleException(e);
			Log.exit(users);
		}

		return users;
	}

	private List<UserPO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<UserPO> users = new ArrayList<UserPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				UserPO po = new UserPO();

				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();

				po = new UserPO();
				if(colCount >=1) po.setUserId(rs.getLong(1));
				if(colCount >=2) po.setUserName(rs.getString(2));
				if(colCount >=3) po.setPassword(rs.getString(3));
				if(colCount >=4) po.setSalt(rs.getString(4));
				if(colCount >=6) po.setLastStatus(rs.getString(6));
				if(colCount >=7) po.setLastStatusTime(rs.getTimestamp(7));
				if(colCount >=9) po.setLastLocation(rs.getString(9));
				if(colCount >=10) po.setPrivilegeLevel(rs.getString(10));
				if(colCount >=11) po.setAccountStatus(rs.getString(11));
				users.add(po);
			}
		} catch (SQLException e) {
			this.handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}

	/**
	 * This method with search for a user by his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 *
	 * @param userName
	 *            - User name to search for.
	 *
	 * @return - UserPO with the user information if a match is found.
	 */
	@Override
	public UserPO findByName(String userName) {
		Log.enter(userName);

		if (userName == null) {
			Log.warn("Inside findByName method with NULL userName.");
			return null;
		}

		UserPO po = null;
		try (Connection conn = this.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_USER_BY_NAME)) {
			stmt.setString(1, userName.toUpperCase());

			List<UserPO> users = this.processResults(stmt);

			if (users.size() == 0) {
				Log.debug("No user account exists with userName = " + userName);
			} else {
				po = users.get(0);
			}
		} catch (SQLException e) {
			this.handleException(e);
			Log.exit(po);
		}

		return po;
	}

	/**
	 * This method will save the information of the user into the database.
	 *
	 * @param userPO
	 *            - User information to be saved.
	 */
	@Override
	public void save(UserPO userPO) {
		Log.enter(userPO);
		if (userPO == null) {
			Log.warn("Inside save method with userPO == NULL");
			return;
		}

		try (Connection conn = this.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_USER)) {
			stmt.setString(1, userPO.getUserName());
			stmt.setString(2, userPO.getPassword());
			stmt.setString(3, userPO.getSalt());
			stmt.setString(4, userPO.getPrivilegeLevel());
			stmt.setString(5, userPO.getAccountStatus());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			this.handleException(e);
		} finally {
			Log.exit();
		}
	}


	/**
	 * This method will update the information of the user into the database.
	 *
	 * @param userPO
	 *            - User information to be saved.
	 */

	@Override
	public void update(UserPO userPO) {
		Log.enter(userPO);
		if (userPO == null) {
			Log.warn("Inside update method with userPO == NULL");
			return;
		}

		try (Connection conn = this.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.UPDATE_USER_BY_ID)) {
			stmt.setLong(1, userPO.getLastStatusCrumbId());
			stmt.setLong(2, userPO.getLastLocationCrumbId());
			stmt.setString(3, userPO.getPrivilegeLevel());
			stmt.setString(4, userPO.getAccountStatus());
			stmt.setString(5, userPO.getUserName());
			stmt.setString(6, userPO.getPassword());
			stmt.setString(7, userPO.getSalt());
			stmt.setLong(8, userPO.getUserId());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows updated.");
		} catch (SQLException e) {
			this.handleException(e);
		} finally {
			Log.exit();
		}

	}

	@Override
	public List<UserPO> loadChatBuddies(String username) {
		Log.enter(username);

		// lookup userID for the username
		if (username == null) {
			Log.warn("Inside fetchChatBuddies method with NULL userName.");
			return null;
		}

		//UserPO po = null;
		List<UserPO> buddies = null;
		try (Connection conn = this.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FETCH_CHAT_BUDDIES)) {
			stmt.setString(1, username);
			stmt.setString(2, username);
			buddies = this.processChatBuddies(stmt);
		} catch (SQLException e) {
			this.handleException(e);
			Log.exit(buddies);
		}

		return buddies;
	}

	private List<UserPO> processChatBuddies(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<UserPO> users = new ArrayList<UserPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				UserPO po = new UserPO();

				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();

				//u.user_id, user_name, created_at, modified_at, last_status_code_id

				po = new UserPO();
				if(colCount >=2) po.setUserName(rs.getString(2));
				if(colCount >=3) po.setCreatedAt(rs.getTimestamp(3));
				if(colCount >=4) po.setModifiedAt(rs.getTimestamp(4));
				if(colCount >=5) po.setLastStatus(rs.getString(5));
				users.add(po);
			}
		} catch (SQLException e) {
			this.handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}


}
