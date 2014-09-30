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
import edu.cmu.sv.ws.ssnoc.data.po.LocationCrumbPO;
	/**
	 * DAO implementation for saving User information in the H2 database.
	 * 
	 */
	public class LocationCrumbDAOImpl extends BaseDAOImpl implements ILocationCrumbDAO {
		/**
		 * This method will load users from the DB with specified account location. If
		 * no location information (null) is provided, it will load all users.
		 * 
		 * @return - List of users
		 */
		public List<LocationCrumbPO> loadLocationCrumbs() {
			Log.enter();

			String query = SQL.FIND_ALL_LOCATION_CRUMBS;

			List<LocationCrumbPO> locationCrumbs = new ArrayList<LocationCrumbPO>();
			try (Connection conn = getConnection();
					PreparedStatement stmt = conn.prepareStatement(query);) {
				locationCrumbs = processResults(stmt);
			} catch (SQLException e) {
				handleException(e);
				Log.exit(locationCrumbs);
			}

			return locationCrumbs;
		}

		private List<LocationCrumbPO> processResults(PreparedStatement stmt) {
			Log.enter(stmt);

			if (stmt == null) {
				Log.warn("Inside processResults method with NULL statement object.");
				return null;
			}

			Log.debug("Executing stmt = " + stmt);
			List<LocationCrumbPO> users = new ArrayList<LocationCrumbPO>();
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					LocationCrumbPO po = new LocationCrumbPO();
					po = new LocationCrumbPO();
					po.setLocationCrumbId(rs.getLong(1));
					po.setUserId(rs.getLong(2));
					po.setLocation(rs.getString(3));
					po.setCreatedAt(rs.getTimestamp(4));

					users.add(po);
				}
			} catch (SQLException e) {
				handleException(e);
			} finally {
				Log.exit(users);
			}

			return users;
		}

		/**
		 * This method will save the information of the location crumb into the database.
		 * 
		 * @param locationCrumbPO
		 *            - Location crumb information to be saved.
		 */
		@Override
		public long save(LocationCrumbPO locationCrumbPO) {
			Log.enter(locationCrumbPO);
			long locationId = 0;
			if (locationCrumbPO == null) {
				Log.warn("Inside save method with locationCrumbPO == NULL");
				return locationId;
			}

			try (Connection conn = getConnection();
					PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_LOCATION_CRUMB,  Statement.RETURN_GENERATED_KEYS)) {
				stmt.setLong(1, locationCrumbPO.getUserId());
				stmt.setString(2, locationCrumbPO.getLocation());

				int rowCount = stmt.executeUpdate();
				Log.trace("Statement executed, and " + rowCount + " rows inserted.");
				
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	locationId = generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Creating location failed, no ID obtained.");
		            }
		        }
			} catch (SQLException e) {
				handleException(e);
			} finally {
				Log.exit();
			}
			return locationId;
		}

		@Override
		public List<LocationCrumbPO> loadLocationCrumbsByUserName(String userName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LocationCrumbPO loadLocationCrumbById(int locationCrumbId) {
			// TODO Auto-generated method stub
			return null;
		}
}
