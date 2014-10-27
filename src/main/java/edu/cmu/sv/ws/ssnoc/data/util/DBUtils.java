package edu.cmu.sv.ws.ssnoc.data.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.PropertyUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.SSNCipher;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IStatusCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * This is a utility class to provide common functions to access and handle
 * Database operations.
 *
 */
public class DBUtils {
	private static boolean DB_TABLES_EXIST = false;
	private static List<String> CREATE_TABLE_LST;
	private static List<String> DROP_TABLE_LST;

	static {
		CREATE_TABLE_LST = new ArrayList<String>();
		CREATE_TABLE_LST.add(SQL.CREATE_USERS);
		CREATE_TABLE_LST.add(SQL.CREATE_STATUS_CRUMBS);
		CREATE_TABLE_LST.add(SQL.CREATE_LOCATION_CRUMBS);
		CREATE_TABLE_LST.add(SQL.CREATE_MESSAGES);
		CREATE_TABLE_LST.add(SQL.CREATE_MEMORY_CRUMBS);

		DROP_TABLE_LST = new ArrayList<String>();
		DROP_TABLE_LST.add(SQL.DROP_USERS);
		DROP_TABLE_LST.add(SQL.DROP_STATUS_CRUMBS);
		DROP_TABLE_LST.add(SQL.DROP_LOCATION_CRUMBS);
		DROP_TABLE_LST.add(SQL.DROP_MESSAGES);
		DROP_TABLE_LST.add(SQL.DROP_MEMORY_CRUMBS);
	}

	/**
	 * This method will initialize the database.
	 *
	 * @throws SQLException
	 */
	public static void initializeDatabase() throws SQLException {
		dropTablesInDB(); // Please uncomment this for testing
		createTablesInDB();
	}

	/**
	 * This method will initialize the database.
	 *
	 * @throws SQLException
	 */
	public static void reinitializeDatabase() throws SQLException {
		dropTablesInDB();
		recreateTablesInDB();
	}

	/**
	 * This method will create necessary tables in the database.
	 *
	 * @throws SQLException
	 */
	protected static void createTablesInDB() throws SQLException {
		Log.enter();
		if (DB_TABLES_EXIST) {
			return;
		}

		final String CORE_TABLE_NAME = SQL.SSN_USERS;

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();) {
			if (!doesTableExistInDB(conn, CORE_TABLE_NAME)) {
				Log.info("Creating tables in database ...");

				for (String query : CREATE_TABLE_LST) {
					Log.debug("Executing query: " + query);
					boolean status = stmt.execute(query);
					Log.debug("Query execution completed with status: "
							+ status);
				}

				Log.info("Tables created successfully");

				createAdminUser();
				Log.info("Admin user created successfully");
			} else {
				Log.info("Tables already exist in database. Not performing any action.");
			}

			DB_TABLES_EXIST = true;
		}
		Log.exit();
	}

	/**
	 * This method will create necessary tables in the database.
	 *
	 * @throws SQLException
	 */
	protected static void recreateTablesInDB() throws SQLException {
		Log.enter();

		final String CORE_TABLE_NAME = SQL.SSN_USERS;

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();) {
			if (!doesTableExistInDB(conn, CORE_TABLE_NAME)) {
				Log.info("Creating tables in database ...");

				for (String query : CREATE_TABLE_LST) {
					Log.debug("Executing query: " + query);
					boolean status = stmt.execute(query);
					Log.debug("Query execution completed with status: "
							+ status);
				}

				Log.info("Tables created successfully");
				
				createAdminUser();
				Log.info("Admin user created successfully");
				
			} else {
				Log.info("Tables already exist in database. Not performing any action.");
			}

			DB_TABLES_EXIST = true;
		}
		Log.exit();
	}

	/**
	 * This method will drop all tables in the database.
	 *
	 * @throws SQLException
	 */
	protected static void dropTablesInDB() throws SQLException {
		Log.enter();

		final String CORE_TABLE_NAME = SQL.SSN_USERS;

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();) {
			if (doesTableExistInDB(conn, CORE_TABLE_NAME)) {
				Log.info("Dropping tables in database ...");

				for (String query : DROP_TABLE_LST) {
					Log.debug("Executing query: " + query);
					boolean status = stmt.execute(query);
					Log.debug("Query execution completed with status: "
							+ status);
				}

				Log.info("Tables dropped successfully");
			} else {
				Log.info("Tables do not exist in database. Not performing any action.");
			}

			DB_TABLES_EXIST = false;
		}
		Log.exit();
	}

	/**
	 * This method will check if the table exists in the database.
	 *
	 * @param conn
	 *            - Connection to the database
	 * @param tableName
	 *            - Table name to check.
	 *
	 * @return - Flag whether the table exists or not.
	 *
	 * @throws SQLException
	 */
	public static boolean doesTableExistInDB(Connection conn, String tableName)
			throws SQLException {
		Log.enter(tableName);

		if (conn == null || tableName == null || "".equals(tableName.trim())) {
			Log.error("Invalid input parameters. Returning doesTableExistInDB() method with FALSE.");
			return false;
		}

		boolean tableExists = false;

		final String SELECT_QUERY = SQL.CHECK_TABLE_EXISTS_IN_DB;

		ResultSet rs = null;
		try (PreparedStatement selectStmt = conn.prepareStatement(SELECT_QUERY)) {
			selectStmt.setString(1, tableName.toUpperCase());
			rs = selectStmt.executeQuery();
			int tableCount = 0;
			if (rs.next()) {
				tableCount = rs.getInt(1);
			}

			if (tableCount > 0) {
				tableExists = true;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		Log.exit(tableExists);

		return tableExists;
	}
	
	/**
	 * This method will create the primary admin user in the database.
	 *
	 * @throws SQLException
	 */
	protected static void createAdminUser() {
		Log.enter();
		
		User adminUser = new User();
		adminUser.setUserName(PropertyUtils.ADMIN_USER_NAME);
		adminUser.setPassword(PropertyUtils.ADMIN_PASSWORD);
		adminUser.setPrivilegeLevel(PropertyUtils.ADMIN_PRIVILEGE_LEVEL);
		adminUser.setAccountStatus(PropertyUtils.ADMIN_ACCOUNT_STATUS);
		adminUser.setLastStatus(PropertyUtils.ADMIN_LAST_STATUS);
		
		
		//Create the Admin User
		IUserDAO uDao = DAOFactory.getInstance().getUserDAO();
		UserPO po = ConverterUtils.convert(adminUser);
		po = SSNCipher.encryptPassword(po);
		uDao.save(po);
		
		//Get the created Admin Users user id
		UserPO createdAdminUser = uDao.findByName(adminUser.getUserName());
		long userId = createdAdminUser.getUserId();
		
		//Set the status of the Admin User
		IStatusCrumbDAO scDao = DAOFactory.getInstance().getStatusCrumbDAO();
		StatusCrumbPO scpo = new StatusCrumbPO();
		scpo.setUserId(userId);
		scpo.setStatus(adminUser.getLastStatus());
		long statusId = scDao.save(scpo);
		
		//Step 4: Update the admin user with the new status id
		createdAdminUser.setLastStatusCrumbId(statusId);
		uDao.update(createdAdminUser);
		
		Log.exit();
	}

	/**
	 * This method returns a database connection from the Hikari CP Connection
	 * Pool
	 *
	 * @return - Connection to the H2 database
	 *
	 * @throws SQLException
	 */
	public static final Connection getConnection() throws SQLException {
		IConnectionPool cp = ConnectionPoolFactory.getInstance()
				.getH2ConnectionPool();
		return cp.getConnection();
	}
}
