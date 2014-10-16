package edu.cmu.sv.ws.ssnoc.data.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.annotations.Array;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.UserClusterPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * DAO implementation for saving User information in the H2 database.
 * 
 */
public class UserGroupDAOImpl extends BaseDAOImpl implements IUserGroupDAO {
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
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			users = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
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
				users.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}

	@Override
	public List<String> getChatBuddies(String username, int timeIntervalInHours) {
		Log.enter(username);

		// lookup userID for the username
		if (username == null) {
			Log.warn("Inside fetchChatBuddies method with NULL userName.");
			return null;
		}	
		
		//UserPO po = null;
		List<String> buddies = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FETCH_CHAT_BUDDIES)) {
			stmt.setString(1, username);
			stmt.setString(2, username);
			buddies = processChatBuddies(stmt);			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(buddies);
		}		

		return buddies;
	}
	
	
	private long processUserID(PreparedStatement stmt) {
		Log.enter(stmt);

		long userId = 0;
		
		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return userId;
		}

		Log.debug("Executing stmt = " + stmt);
		
		try (ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				userId = rs.getLong(1);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(userId);
		}

		return userId;
	}
	
	private List<String> processChatBuddies(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<String> users = new ArrayList<String>();
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
				//users.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}
//----Poorva

	@Override
	public List<UserClusterPO> getClusters(int timeIntervalInHours) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserClusterPO> getClusters() {
		// TODO Auto-generated method stub
		return null;
	}
	

//	public ArrayList<UserclustersPO> UnconnectedUsers(PreparedStatement stmt) {
//		Log.enter(stmt);
//		if (stmt == null) {
//			Log.warn("Inside processResults method with NULL statement object.");
//			return null;
//		}	
//		Log.debug("Executing stmt = " + stmt);
////		ArrayList <UserclustersPO[]> result = new ArrayList<UserclustersPO[]>();
//		try (Connection conn = getConnection();
//		PreparedStatement = conn.prepareStatement(SQL.FIND_ALL_USERS)) {
//			result = processResults();
//		} catch (SQLException e) {
//			handleException(e);
//			Log.exit(users);
//		}
//
//		return users;
//	}
//	
	
	}
