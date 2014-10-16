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
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserclusterPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * DAO implementation for saving User information in the H2 database.
 * 
 */
public class UsergroupsDAOImpl extends BaseDAOImpl implements IUsergroupsDAO {
	/**
	 * This method will load users from the DB with specified account status. If
	 * no status information (null) is provided, it will load all users.
	 * 
	 * @return - List of users
	 */
		private List<UserclusterPO> processUsergroups(PreparedStatement stmt) {
		Log.enter();
		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
		return null;
		}
		Log.debug("Executing stmt = " + stmt);
		List<UserclusterPO> userscluster = new ArrayList<UserclusterPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				UserclusterPO po = new UserclusterPO();
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				po = new UserclusterPO();
				if(colCount >=1) po.setAuthor(rs.getString(1));
				if(colCount >=2) po.setAuthorId(rs.getLong(2));
				if(colCount >=3) po.setTarget(rs.getString(3));
				if(colCount >=4) po.setTargetId(rs.getLong(4));
				userscluster.add(po);
			}
			} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(userscluster);
		}
			return userscluster;
		}

	public List<UserclusterPO> loadUsergroups() {
		Log.enter();
		
	String query = SQL.FETCH_CHAT_BUDDIES_USERS;
		
	List<UserclusterPO> userclusters = new ArrayList<UserclusterPO>();
     	try (Connection conn = getConnection();
     			PreparedStatement stmt = conn.prepareStatement(query);)  {
     		userclusters= processUsergroups(stmt);			
	} catch (SQLException e) {
		handleException(e);
	Log.exit(userclusters);
	}
	 return userclusters;
	}
}

