package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

		String query = SQL.FIND_ALL_STATUS_CRUMBS;

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
	public void save(StatusCrumbPO statusCrumbPO) {
		Log.enter(statusCrumbPO);
		if (statusCrumbPO == null) {
			Log.warn("Inside save method with statusCrumbPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_STATUS_CRUMB)) {
			stmt.setLong(1, statusCrumbPO.getUserId());
			stmt.setString(2, statusCrumbPO.getStatus());
			stmt.setLong(3, statusCrumbPO.getLocationCrumbId());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	@Override
	public List<StatusCrumbPO> loadStatusCrumbsByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusCrumbPO loadStatusCrumbById(int statusCrumbId) {
		// TODO Auto-generated method stub
		return null;
	}

}
