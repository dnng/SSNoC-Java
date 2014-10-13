package edu.cmu.sv.ws.ssnoc.data.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;

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
		
		DROP_TABLE_LST = new ArrayList<String>();
		DROP_TABLE_LST.add(SQL.DROP_USERS);
		DROP_TABLE_LST.add(SQL.DROP_STATUS_CRUMBS);
		DROP_TABLE_LST.add(SQL.DROP_LOCATION_CRUMBS);
		DROP_TABLE_LST.add(SQL.DROP_MESSAGES);
	}

	/**
	 * This method will initialize the database.
	 * 
	 * @throws SQLException
	 */
	public static void initializeDatabase() throws SQLException {
		//dropTablesInDB(); // Please uncomment this for testing
		createTablesInDB();
	}
	
	/**
	 * This method will initialize the database.
	 * 
	 * @throws SQLException
	 */
	public static void reinitializeDatabase() throws SQLException {
		dropTablesInDB();
		createTablesInDB();
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
