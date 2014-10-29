package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;

/**
 * DAO implementation for saving Status Crumb information in the H2 database.
 * 
 */
public class StatusCrumbDAOImpl extends BaseDAOImpl implements IStatusCrumbDAO {
	/**
	 * This method will load users from the DB with specified account status. If
	 * no status information (null) is provided, it will load all users.
	 * 
	 * @return - List of users
	 */
	public List<StatusCrumbPO> loadStatusCrumbs() {
		Log.enter();

		String query = SQL.FIND_ALL_ACTIVE_STATUS_CRUMBS;

		List<StatusCrumbPO> statusCrumbs = new ArrayList<StatusCrumbPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			statusCrumbs = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(statusCrumbs);
		}

		return statusCrumbs;
	}

	private List<StatusCrumbPO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<StatusCrumbPO> statuses = new ArrayList<StatusCrumbPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				StatusCrumbPO po = new StatusCrumbPO();
				po = new StatusCrumbPO();
				po.setStatusCrumbId(rs.getLong(1));
				po.setUserId(rs.getLong(2));
				po.setUserName(rs.getString(3));
				po.setStatus(rs.getString(4));
				po.setLocationCrumbId(rs.getLong(5));
				po.setLocation(rs.getString(6));
				po.setCreatedAt(rs.getTimestamp(7));

				statuses.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(statuses);
		}

		return statuses;
	}

	/**
	 * This method will save the information of the status crumb into the database.
	 * 
	 * @param statusCrumbPO
	 *            - Status crumb information to be saved.
	 */
	@Override
	public long save(StatusCrumbPO statusCrumbPO) {
		Log.enter(statusCrumbPO);
		long statusId = 0;
		if (statusCrumbPO == null) {
			Log.warn("Inside save method with statusCrumbPO == NULL");
			return statusId;
		}

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_STATUS_CRUMB,  Statement.RETURN_GENERATED_KEYS)) {
			stmt.setLong(1, statusCrumbPO.getUserId());
			stmt.setString(2, statusCrumbPO.getStatus());
			stmt.setLong(3, statusCrumbPO.getLocationCrumbId());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	statusId = generatedKeys.getLong(1);
	            }
	            else {
	                throw new SQLException("Creating status failed, no ID obtained.");
	            }
	        }
			
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
		return statusId;
	}

	@Override
	public List<StatusCrumbPO> loadStatusCrumbsByUserName(String userName) {
		Log.enter(userName);

		if (userName == null) {
			Log.warn("Inside loadStatusCrumbsByUserName method with NULL userName.");
			return null;
		}
		List<StatusCrumbPO> statusCrumbs = new ArrayList<StatusCrumbPO>();
		try (Connection conn = this.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_STATUS_CRUMBS_BY_USER_NAME)) {
			stmt.setString(1, userName.toUpperCase());

			statusCrumbs = processResults(stmt);
		} catch (SQLException e) {
			this.handleException(e);
			Log.exit(statusCrumbs);
		}

		return statusCrumbs;
	}

	@Override
	public StatusCrumbPO loadStatusCrumbById(int statusCrumbId) {
		Log.enter(statusCrumbId);

		if (statusCrumbId == 0) {
			Log.warn("Inside loadStatusCrumbById method with 0 statusCrumbId.");
			return null;
		}
		
		List<StatusCrumbPO> statusCrumbs = new ArrayList<StatusCrumbPO>();
		StatusCrumbPO po = null;
		try (Connection conn = this.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_STATUS_CRUMB_BY_ID)) {
			stmt.setLong(1, statusCrumbId);

			statusCrumbs = processResults(stmt);
			
			if (statusCrumbs.size() == 0) {
				Log.debug("No status crumbs exist with id = " + statusCrumbId);
			} else {
				po = statusCrumbs.get(0);
			}
		} catch (SQLException e) {
			this.handleException(e);
			Log.exit(statusCrumbs);
		}

		return po;
	}

}
