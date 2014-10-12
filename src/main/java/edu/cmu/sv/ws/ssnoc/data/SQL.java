package edu.cmu.sv.ws.ssnoc.data;

/**
 * This class contains all the SQL related code that is used by the project.
 * Note that queries are grouped by their purpose and table associations for
 * easy maintenance.
 * 
 */
public class SQL {

	public static final String SSN_USERS = "SSN_USERS";
	public static final String SSN_STATUS_CRUMBS = "SSN_STATUS_CRUMBS";
	public static final String SSN_LOCATION_CRUMBS = "SSN_LOCATION_CRUMBS";
//	public static final String SSN_WALL_MESSAGES = "SSN_WALL_MESSAGES";
	public static final String SSN_MESSAGES = "SSN_MESSAGES";

	/**
	 * Query to check if a given table exists in the H2 database.
	 */
	public static final String CHECK_TABLE_EXISTS_IN_DB = "SELECT count(1) as rowCount "
			+ " FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = SCHEMA() "
			+ " AND UPPER(TABLE_NAME) = UPPER(?)";

	// ****************************************************************
	// All queries related to USERS
	// ****************************************************************
	/**
	 * Query to create the USERS table.
	 */
	public static final String CREATE_USERS = "create table IF NOT EXISTS "
			+ SSN_USERS + " ( user_id IDENTITY PRIMARY KEY,"
			+ " user_name VARCHAR(100)," + " password VARCHAR(512),"
			+ " salt VARCHAR(512),"
			+ " last_status_code_id BIGINT, last_location_id BIGINT, "
			+ " created_at TIMESTAMP," + " modified_at TIMESTAMP )";

	/**
	 * Query to load all users in the system.
	 */
	public static final String FIND_ALL_USERS = "select u.user_id, u.user_name, u.password,"
			+ " u.salt, u.last_status_code_id, ssc.status, ssc.created_at, u.last_location_id, slc.location, u.created_at, u.modified_at "
			+ " from "
			+ SSN_USERS
			+ " u "
			+ " left outer join "
			+ SSN_STATUS_CRUMBS
			+ " ssc on u.last_status_code_id = ssc.status_crumb_id "
			+ " left outer join "
			+ SSN_LOCATION_CRUMBS
			+ " slc on u.last_location_id = slc.location_crumb_id "
			+ " order by user_name";

	/**
	 * Query to find a user details depending on his name. Note that this query
	 * does a case insensitive search with the user name.
	 */
	public static final String FIND_USER_BY_NAME = "select user_id, user_name, password,"
			+ " salt, last_status_code_id, last_location_id, created_at, modified_at "
			+ " from " + SSN_USERS + " where UPPER(user_name) = UPPER(?)";

	/**
	 * Query to find a user id depending on his name. Note that this query does
	 * a case insensitive search with the user name.
	 */
	public static final String FIND_USER_ID_BY_NAME = "select user_id "
			+ " from " + SSN_USERS + " where UPPER(user_name) = UPPER(?)";

	/**
	 * Query to find a user name depending on his id.
	 */
	public static final String FIND_USER_NAME_BY_ID = "select user_name "
			+ " from " + SSN_USERS + " where user_id = ?";

	/**
	 * Query to insert a row into the users table.
	 */
	public static final String INSERT_USER = "insert into "
			+ SSN_USERS
			+ " (user_name, password, salt, created_at) values (?, ?, ?, CURRENT_TIMESTAMP() )";

	/**
	 * Query to update a row in the users table.
	 */
	public static final String UPDATE_USER_BY_ID = "update "
			+ SSN_USERS
			+ " set last_status_code_id = ?, last_location_id = ?, modified_at = CURRENT_TIMESTAMP()"
			+ " where user_id = ?";

	/**
	 * Query to update a row in the users table.
	 */
	public static final String UPDATE_USER_BY_NAME = "update "
			+ SSN_USERS
			+ " set last_status_code_id = ?, last_location_id = ?, modified_at = CURRENT_TIMESTAMP()"
			+ " where UPPER(user_name) = UPPER(?)";

	// ****************************************************************
	// All queries related to STATUS_CRUMBS
	// ****************************************************************
	/**
	 * Query to create the STATUS_CRUMBS table.
	 */
	public static final String CREATE_STATUS_CRUMBS = "create table IF NOT EXISTS "
			+ SSN_STATUS_CRUMBS
			+ " ( status_crumb_id IDENTITY PRIMARY KEY,"
			+ " user_id BIGINT,"
			+ " status VARCHAR(10), location_crumb_id BIGINT, "
			+ " created_at TIMESTAMP )";

	/**
	 * Query to load all status crumbs in the system.
	 */
	public static final String FIND_ALL_STATUS_CRUMBS = "select ssc.status_crumb_id, ssc.user_id, u.user_name, ssc.status, ssc.location_crumb_id, slc.location, "
			+ " ssc.created_at "
			+ " from "
			+ SSN_STATUS_CRUMBS
			+ " ssc "
			+ " left outer join "
			+ SSN_USERS
			+ " u on ssc.user_id = u.user_id "
			+ " left outer join "
			+ SSN_LOCATION_CRUMBS
			+ " slc on ssc.location_crumb_id = slc.location_crumb_id "
			+ "order by created_at DESC";

	/**
	 * Query to find a status_crumb depending on the user_id.
	 */
	public static final String FIND_STATUS_CRUMB_FOR_USER_ID = "select status_crumb_id, user_id, status, location_crumb_id, "
			+ " created_at "
			+ " from "
			+ SSN_STATUS_CRUMBS
			+ " where user_id = ?";

	/**
	 * Query to insert a row into the status_crumbs table.
	 */
	public static final String INSERT_STATUS_CRUMB = "insert into "
			+ SSN_STATUS_CRUMBS
			+ " (user_id, status, location_crumb_id, created_at) values (?, ?, ?, CURRENT_TIMESTAMP())";

	// ****************************************************************
	// All queries related to LOCATION_CRUMBS
	// ****************************************************************
	/**
	 * Query to create the LOCATION_CRUMBS table.
	 */
	public static final String CREATE_LOCATION_CRUMBS = "create table IF NOT EXISTS "
			+ SSN_LOCATION_CRUMBS
			+ " ( location_crumb_id IDENTITY PRIMARY KEY,"
			+ " user_id BIGINT,"
			+ " location VARCHAR(50)," + " created_at TIMESTAMP )";

	/**
	 * Query to load all location crumbs in the system.
	 */
	public static final String FIND_ALL_LOCATION_CRUMBS = "select location_crumb_id, user_id, location,"
			+ " created_at "
			+ " from "
			+ SSN_LOCATION_CRUMBS
			+ " order by created_at DESC";

	/**
	 * Query to find a location_crumb depending on the user_id.
	 */
	public static final String FIND_LOCATION_CRUMB_FOR_USER_ID = "select Location_crumb_id, user_id, location,"
			+ " created_at "
			+ " from "
			+ SSN_LOCATION_CRUMBS
			+ " where user_id = ?";

	/**
	 * Query to insert a row into the location_crumbs table.
	 */
	public static final String INSERT_LOCATION_CRUMB = "insert into "
			+ SSN_LOCATION_CRUMBS
			+ " (user_id, location, created_at) values (?, ?, CURRENT_TIMESTAMP())";

	// ****************************************************************
	// All queries related to WALL_MESSAGES
	// ****************************************************************
	/**
//	 * Query to create the WALL_MESSAGES table.
//	 */
//	public static final String CREATE_WALL_MESSAGES = "create table IF NOT EXISTS "
//			+ SSN_WALL_MESSAGES
//			+ " ( wall_message_id IDENTITY PRIMARY KEY,"
//			+ " sender_id BIGINT,"
//			+ " content VARCHAR(1024),"
//			+ " location VARCHAR(512)," + " created_at TIMESTAMP )";
//
//	/**
//	 * Query to load all wall messages in the system.
//	 */
//	public static final String FIND_ALL_WALL_MESSAGES = "select wall_message_id, sender_id,"
//			+ " content, location, created_at "
//			+ " from "
//			+ SSN_WALL_MESSAGES
//			+ " order by created_at DESC";
//
//	/**
//	 * Query to insert a new wall message into the wall_messages table.
//	 */
//	public static final String INSERT_WALL_MESSAGE = "insert into "
//			+ SSN_WALL_MESSAGES
//			+ " (sender_id, content, location, created_at) values (?, ?, ?, CURRENT_TIMESTAMP())";
//
	// ****************************************************************
	// All queries related to CHAT_MESSAGES
	// ****************************************************************
	/**
	 * Query to create the CHAT_MESSAGES table.
	 */
	public static final String CREATE_MESSAGES = "create table IF NOT EXISTS "
			+ SSN_MESSAGES
			+ " ( message_id IDENTITY PRIMARY KEY,"
			+ " author_id BIGINT,"
			+ " target_id BIGINT,"
			+ " content VARCHAR(1024),"
			+ " location VARCHAR(512),"
			+ " created_at TIMESTAMP "
			+ " message_type VARCHAR(10),)";

	/**
	 * Query to load all messages in the system.
	 */
	public static final String FIND_ALL_MESSAGES = "select message_id, sender_id, receiver_id,"
			+ " content, location, created_at "
			+ " from "
			+ SSN_MESSAGES
			+ " order by created_at DESC";
	
	/**
	 * Query to load all chat messages in the system.
	 */
	
	public static final String FIND_ALL_CHAT_MESSAGES = "select message_id, sender_id, receiver_id,"
			+ " content, location, created_at "
			+ " from "
			+ SSN_MESSAGES
			+ " where message_type=\"CHAT\" "
			+ " order by created_at DESC";

	/**
	 * Query to load all wall messages in the system.
	 */
	
	public static final String FIND_ALL_WALL_MESSAGES = "select message_id, sender_id, receiver_id,"
			+ " content, location, created_at "
			+ " from "
			+ SSN_MESSAGES
			+ SSN_MESSAGES
			+ " where message_type=\"WALL\" "
			+ " order by created_at DESC";

	/**
	 * Query to insert a new chat message into the chat_messages table.
	 */
	public static final String INSERT_CHAT_MESSAGE = "insert into "
			+ SSN_MESSAGES
			+ " (author_id, target_id, content, location, created_at, message_type) values (?, ?, ?, ?, CURRENT_TIMESTAMP(),\"CHAT\")";
	
	/**
	 * Query to insert a new chat message into the chat_messages table.
	 */
	public static final String INSERT_WALL_MESSAGE = "insert into "
			+ SSN_MESSAGES
			+ " (author_id, target_id, content, location, created_at, message_type) values (?, ?, ?, ?, CURRENT_TIMESTAMP(),\"WALL\")";
	
	
}
