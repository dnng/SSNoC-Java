package edu.cmu.sv.ws.ssnoc.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.rest.SSNoCAppInitializer;

/**
 * This file loads the properties that can be configured for the SSNoC
 * application. Properties are loaded from the sysconfig.properties file<br/>
 * 1. DB_CONN_URL = "jdbc:h2:file:~/h2db" <br/>
 * 2. DB_USERNAME = "ssnoc_user" <br/>
 * 3. DB_PASSWORD // Commented out for obvious reasons. <br/>
 * 4. DB_CONNECTION_POOL_SIZE = 20 <br/>
 * 5. invalidNameSet - A set of names that are not valid
 * 
 */
public class PropertyUtils {
	/**
	 * Database Connection URL.
	 */
	public static String DB_CONN_URL;

	/**
	 * User name used to connect to the database.
	 */
	public static String DB_USERNAME;

	/**
	 * Password to connect to the database.
	 */
	public static String DB_PASSWORD;

	/**
	 * Database connection Pool size parameter used by HikariCP, to maintain a
	 * pool of connections that all DAOs making database requests will use.
	 */
	public static int DB_CONNECTION_POOL_SIZE;
	
	/**
	 * Test Database Connection URL.
	 */
	public static String TEST_DB_CONN_URL;

	/**
	 * Test User name used to connect to the database.
	 */
	public static String TEST_DB_USERNAME;

	/**
	 * Test Password to connect to the database.
	 */
	public static String TEST_DB_PASSWORD;

	/**
	 * Test Database connection Pool size parameter used by HikariCP, to maintain a
	 * pool of connections that all DAOs making database requests will use.
	 */
	public static int TEST_DB_CONNECTION_POOL_SIZE;
	
	/**
	 * Default sampling frequency for memory measurement
	 */
	public static int MEMORY_SAMPLING_FREQUENCY;
	
	/**
	 * Default reporting period for memory measurement
	 */
	public static int MEMORY_REPORTING_PERIOD;
	
	/**
	 * Should we measure memory at startup
	 */
	public static boolean MEASURE_MEMORY ;

	/**
	 * List of invalid names
	 */
	public static Set<String> INVALID_NAMES = new HashSet<String>();

	/**
	 * Admin code that will be provided by a user, to authenticate as an admin.
	 */
	public static String ADMIN_CODE;

	static {
		Log.trace("Loading properties files ...");
		try (InputStream input = SSNoCAppInitializer.class.getClassLoader()
				.getResourceAsStream("/sysconfig.properties");) {
			if (input != null) {
				// Load a properties file
				Properties prop = new Properties();
				prop.load(input);

				DB_CONN_URL = prop.getProperty("dbConnURL");
				DB_USERNAME = prop.getProperty("dbUsername");
				DB_PASSWORD = prop.getProperty("dbPassword");
				DB_CONNECTION_POOL_SIZE = Integer.parseInt(prop
						.getProperty("dbConnPoolSize"));
				TEST_DB_CONN_URL = prop.getProperty("testDbConnURL");
				TEST_DB_USERNAME = prop.getProperty("testDbUsername");
				TEST_DB_PASSWORD = prop.getProperty("testDbPassword");
				TEST_DB_CONNECTION_POOL_SIZE = Integer.parseInt(prop
						.getProperty("testDbConnPoolSize"));
				MEMORY_SAMPLING_FREQUENCY = Integer.parseInt(prop.getProperty("memSamplingFrequency"));
				MEMORY_REPORTING_PERIOD = Integer.parseInt(prop.getProperty("memReportingPeriod"));
				MEASURE_MEMORY = Boolean.parseBoolean(prop.getProperty("measureMemory"));
				ADMIN_CODE = prop.getProperty("adminCode");

				// Load the list of invalid names into a Set
				String invalidNames = prop.getProperty("invalidNames");
				if (invalidNames != null) {
					String[] nameList = invalidNames.split(" ");

					for (int i = 0; i < nameList.length; i++) {
						INVALID_NAMES.add(nameList[i]);
					}
				} else {
					Log.warn("!!! Could not find banned user names in the properties files !!!");
				}
			} else {
				Log.warn("Could not load the properties file. "
						+ "Will try to initialize with system default values ...");
			}
		} catch (IOException ex) {
			Log.error("Error when loading properties file : ", ex);
		} finally {
			initializeWithDefaultValuesIfNeeded();
		}
	}

	/**
	 * This method will initialize the static variables with default values for
	 * the system to use if we are unable to load the properties file for some
	 * reason.
	 */
	private static void initializeWithDefaultValuesIfNeeded() {
		if (DB_CONN_URL == null) {
			Log.warn("Initializing DB_CONN_URL to system default values ...");
			DB_CONN_URL = "jdbc:h2:file:~/h2db";
		}

		if (DB_USERNAME == null) {
			Log.warn("Initializing DB_USERNAME to system default values ...");
			DB_USERNAME = "ssnoc_user";
		}

		if (DB_PASSWORD == null) {
			Log.warn("Initializing DB_USERNAME to system default values ...");
			DB_PASSWORD = "bHGSR87#%1x";
		}

		if (DB_CONNECTION_POOL_SIZE == 0) {
			Log.warn("Initializing DB_CONNECTION_POOL_SIZE to system default values ...");
			DB_CONNECTION_POOL_SIZE = 20;
		}
		
		if (TEST_DB_CONN_URL == null) {
			Log.warn("Initializing TEST_DB_CONN_URL to system default values ...");
			TEST_DB_CONN_URL = "jdbc:h2:file:~/testh2db";
		}

		if (TEST_DB_USERNAME == null) {
			Log.warn("Initializing TEST_DB_USERNAME to system default values ...");
			TEST_DB_USERNAME = "ssnoc_user";
		}

		if (TEST_DB_PASSWORD == null) {
			Log.warn("Initializing TEST_DB_USERNAME to system default values ...");
			TEST_DB_PASSWORD = "bHGSR87#%1x";
		}

		if (TEST_DB_CONNECTION_POOL_SIZE == 0) {
			Log.warn("Initializing TEST_DB_CONNECTION_POOL_SIZE to system default values ...");
			TEST_DB_CONNECTION_POOL_SIZE = 20;
		}

		if (MEMORY_SAMPLING_FREQUENCY == 0) {
			Log.warn("Initializing MEMORY_SAMPLING_FREQUENCY to system default values ...");
			MEMORY_SAMPLING_FREQUENCY = 1;
		}
		
		if (MEMORY_REPORTING_PERIOD == 0) {
			Log.warn("Initializing MEMORY_REPORTING_PERIOD to system default values ...");
			MEMORY_REPORTING_PERIOD = 60;
		}
		
		if (MEASURE_MEMORY == false) {
			Log.warn("Initializing MEASURE_MEMORY to system default values ...");
			MEASURE_MEMORY = false;
		}
		
		if (ADMIN_CODE == null) {
			Log.warn("Initializing ADMIN_CODE to system default values ...");
			ADMIN_CODE = "5830";
		}
	}
}
