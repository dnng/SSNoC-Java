package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.UserGroupPO;

/**
 * DAO implementation for saving User information in the H2 database.
 * 
 */
public class UserGroupDAOImpl extends BaseDAOImpl implements IUserGroupDAO {
	
	private List<String> processUserNames(PreparedStatement stmt) {
		Log.enter();
		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}
		Log.debug("Executing stmt = " + stmt);
		List<String> userNames = new ArrayList<String>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				if (colCount >= 1)
					userNames.add(rs.getString(1));
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(userNames);
		}
		return userNames;
	}

	

	@Override
	public List<String> loadUserBuddies(String userName) {
		Log.enter();

		String query = SQL.FETCH_CHAT_BUDDIES_SNA;

		List<String> userNames = new ArrayList<String>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1,  userName);
			stmt.setString(2,  userName);
			userNames = processUserNames(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(userNames);
		}
		
		HashSet uniqueUserNames = new HashSet();
		uniqueUserNames.addAll(userNames);
		userNames.clear();
		userNames.addAll(uniqueUserNames);
		Collections.sort(userNames);
		
		return userNames;
	}

	@Override
	public List<String> loadUserBuddies(String userName, long timeInHours) {
		Log.enter();

		String query = SQL.FETCH_CHAT_BUDDIES_SNA;

		List<String> userNames = new ArrayList<String>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1,  userName);
			stmt.setLong(2, timeInHours * -1 );
			stmt.setString(3,  userName);
			stmt.setLong(4, timeInHours * -1 );
			userNames = processUserNames(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(userNames);
		}
		return userNames;
	}

	@Override
	public List<String> getAllUsers() {
		Log.enter();

		String query = SQL.FIND_ALL_USER_NAMES;

		List<String> userNames = new ArrayList<String>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			userNames = processUserNames(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(userNames);
		}
		return userNames;
	}

	@Override
	public List<UserGroupPO> loadUserGroups() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<UserGroupPO> loadUserGroups(long fromTimeInHours) {
		return null;
	}

}