package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;

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
		
		HashSet<String> uniqueUserNames = new HashSet<String>();
		uniqueUserNames.addAll(userNames);
		userNames.clear();
		userNames.addAll(uniqueUserNames);
		Collections.sort(userNames);
		
		return userNames;
	}

	@Override
	public List<String> loadUserBuddies(String userName, long timeInHours) {
		Log.enter();

		String query = SQL.FETCH_CHAT_BUDDIES_SNA_TIME;

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
	
	public Map<String, Set<String>> getAllBuddies(int timeWindowInHours) {
		Log.enter();
		
		String query = SQL.FETCH_CHAT_BUDDIES_USERS;
		
		Map<String, Set<String>> chatPeers = new HashMap<String, Set<String>>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			
			long currentTime = System.currentTimeMillis();
			Timestamp fromTimestamp = new Timestamp(currentTime - (60 * 60 * 1000 * timeWindowInHours)); 
			Timestamp toTimestamp = new Timestamp(currentTime);
			stmt.setTimestamp(1, fromTimestamp);
			stmt.setTimestamp(2, toTimestamp );
			Log.info("SQL Stmt ", query, fromTimestamp, toTimestamp);
			chatPeers = processChatPeers(stmt);
		} 
		catch (SQLException e) {
			handleException(e);
			Log.exit(chatPeers);
		}
		return chatPeers;
	}



	private Map<String, Set<String>> processChatPeers(PreparedStatement stmt) {		

		Log.enter();
		if (stmt == null) {
			Log.warn("Inside processChatPeers method with NULL statement object.");
			return null;
		}
		Log.debug("Executing stmt = " + stmt);
		
		Map<String, Set<String>> chatPeers = new HashMap<String, Set<String>>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				
				String author = rs.getString(2);
				String target = rs.getString(4);
				if (!chatPeers.containsKey(author)) {
					Set<String> peers = new HashSet<String>();
					chatPeers.put(author, peers);
					peers.add(target);
				} else {
					chatPeers.get(author).add(target);
				}
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(chatPeers);
		}
		return chatPeers;
	}

}