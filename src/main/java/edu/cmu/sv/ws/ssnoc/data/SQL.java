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
	public static final String SSN_MESSAGES = "SSN_MESSAGES";
	public static final String SSN_MEMORY_CRUMBS = "SSN_MEMORY_CRUMBS";

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
	 * Query to drop the USERS table.
	 */
	public static final String DROP_USERS = "drop table if exists " + SSN_USERS;

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
	 * Query to drop the STATUS_CRUMBS table.
	 */
	public static final String DROP_STATUS_CRUMBS = "drop table if exists "
			+ SSN_STATUS_CRUMBS;

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
	 * Query to drop the LOCATION_CRUMBS table.
	 */
	public static final String DROP_LOCATION_CRUMBS = "drop table if exists "
			+ SSN_LOCATION_CRUMBS;

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
	// All queries related to MESSAGES
	// ****************************************************************
	/**
	 * Query to create the MESSAGES table.
	 */
	public static final String CREATE_MESSAGES = "create table IF NOT EXISTS "
			+ SSN_MESSAGES + " ( message_id IDENTITY PRIMARY KEY,"
			+ " author_id BIGINT, target_id BIGINT,  location_id BIGINT,"
			+ " content VARCHAR(1024), message_type VARCHAR(10),"
			+ " created_at TIMESTAMP )";

	/**
	 * Query to drop the MESSAGES table.
	 */
	public static final String DROP_MESSAGES = "drop table if exists "
			+ SSN_MESSAGES;

	/**
	 * Query to load message by the specific message ID
	 */
	public static final String FIND_MESSAGE_BY_ID = "select ssm.message_id, ssm.author_id, sa.user_name, ssm.target_id, sb.user_name, ssm.location_id, slc.location,  ssm.content, ssm.message_type, ssm.created_at "
			+ "from "
			+ SSN_MESSAGES
			+ " ssm "
			+ "left outer join "
			+ SSN_USERS
			+ " sa on ssm.author_id=sa.user_id "
			+ "left outer join "
			+ SSN_USERS
			+ " sb on ssm.target_id=sa.user_id "
			+ "left outer join "
			+ SSN_LOCATION_CRUMBS
			+ " slc on sa.last_location_id=slc.location_crumb_id "
			+ "where ssm.message_id = ? " + "order by ssm.created_at DESC";

	/**
	 * Query to load all messages in the system.
	 */
	public static final String FIND_ALL_MESSAGES = "select ssm.message_id, ssm.author_id, sa.user_name, ssm.target_id,"
			+ " sb.user_name, ssm.location_id, slc.location, "
			+ " ssm.content, ssm.message_type, ssm.created_at "
			+ " from "
			+ SSN_MESSAGES
			+ " ssm "
			+ " left outer join "
			+ SSN_USERS
			+ " sa on ssm.author_id=sa.user_id "
			+ " left outer join "
			+ SSN_USERS
			+ " sb on ssm.target_id=sa.user_id "
			+ " left outer join "
			+ SSN_LOCATION_CRUMBS
			+ " slc on ssm.location_id=slc.location_crumb_id"
			+ " order by ssm.created_at DESC";

	/**
	 * Query to load all chat messages in the system.
	 */
	public static final String FIND_ALL_CHAT_MESSAGES = "select ssm.message_id, ssm.author_id, sa.user_name, ssm.target_id,"
			+ " sb.user_name, ssm.location_id, slc.location, "
			+ " ssm.content, ssm.message_type, ssm.created_at "
			+ " from "
			+ SSN_MESSAGES
			+ " ssm "
			+ " left outer join "
			+ SSN_USERS
			+ " sa on ssm.author_id=sa.user_id "
			+ " left outer join "
			+ SSN_USERS
			+ " sb on ssm.target_id=sa.user_id "
			+ " left outer join "
			+ SSN_LOCATION_CRUMBS
			+ " slc on ssm.location_id=slc.location_crumb_id"
			+ " where ssm.message_type=\'CHAT\'"
			+ " order by ssm.created_at DESC";

	/**
	 * Query to load all wall messages in the system.
	 */
	public static final String FIND_ALL_WALL_MESSAGES = "select ssm.message_id, ssm.author_id, sa.user_name, ssm.target_id,"
			+ " sb.user_name, ssm.location_id, slc.location, "
			+ " ssm.content, ssm.message_type, ssm.created_at "
			+ " from "
			+ SSN_MESSAGES
			+ " ssm "
			+ " left outer join "
			+ SSN_USERS
			+ " sa on ssm.author_id=sa.user_id "
			+ " left outer join "
			+ SSN_USERS
			+ " sb on ssm.target_id=sa.user_id "
			+ " left outer join "
			+ SSN_LOCATION_CRUMBS
			+ " slc on ssm.location_id=slc.location_crumb_id"
			+ " where ssm.message_type=\'WALL\'"
			+ " order by ssm.created_at DESC";

	/**
	 * Query to insert a new chat message into the chat_messages table.
	 */
	public static final String INSERT_CHAT_MESSAGE = "insert into "
			+ SSN_MESSAGES
			+ " (author_id, target_id, content, message_type, location_id, created_at) values (?, ?, ?, ?, ?,CURRENT_TIMESTAMP())";

	/**
	 * Query to insert a new chat message into the chat_messages table.
	 */
	public static final String INSERT_WALL_MESSAGE = "insert into "
			+ SSN_MESSAGES
			+ " (author_id, target_id, content, message_type, location_id, created_at) values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP())";

	/**
	 * Query to fetch chat buddies
	 */
	public static final String FETCH_CHAT_BUDDIES = "select u.user_id, user_name, created_at, modified_at, last_status_code_id "
			+ "from SSN_USERS u "
			+ "join "
			+ "(select distinct target_id as user_id "
			+ "from SSN_MESSAGES m1 "
			+ "join SSN_USERS u1 on m1.author_id = u1.user_id "
			+ "where u1.user_name = ? "
			+ "and m1.message_type = \'CHAT\' "
			+ "union "
			+ "select distinct author_id as user_id "
			+ "from SSN_MESSAGES m2 "
			+ "join SSN_USERS u2 on m2.target_id = u2.user_id "
			+ "where u2.user_name = ? "
			+ "and m2.message_type = \'CHAT\' "
			+ ") m " + "on  u.user_id = m.user_id ";

	public static final String FIND_PEER_CHAT_MESSAGES = "select * from SSN_MESSAGES";

	// ****************************************************************
	// All queries related to MEMORY_CRUMBS
	// ****************************************************************
	/**
	 * Query to create the MEMORY_CRUMBS table.
	 */
	public static final String CREATE_MEMORY_CRUMBS = "create table IF NOT EXISTS "
			+ SSN_MEMORY_CRUMBS
			+ " ( memory_crumb_id IDENTITY PRIMARY KEY,"
			+ " used_volatile_memory BIGINT,"
			+ " remaining_volatile_memory BIGINT,"
			+ " used_persistent_memory BIGINT,"
			+ " remaining_persistent_memory BIGINT,"
			+ " online_users BIGINT,"
			+ " created_at TIMESTAMP )";

	/**
	 * Query to drop the MEMORY_CRUMBS table.
	 */
	public static final String DROP_MEMORY_CRUMBS = "drop table if exists "
			+ SSN_MEMORY_CRUMBS;

	/**
	 * Query to load all memory crumbs in the system.
	 */
	public static final String FIND_ALL_MEMORY_CRUMBS = "select memory_crumb_id, used_volatile_memory, remaining_volatile_memory, "
			+ " used_persistent_memory, remaining_persistent_memory, online_users, created_at "
			+ " from " + SSN_MEMORY_CRUMBS + " order by created_at DESC";

	/**
	 * Query to load all memory crumbs in the specified time interval.
	 */
	public static final String FIND_ALL_MEMORY_CRUMBS_IN_INTERVAL = "select memory_crumb_id, used_volatile_memory, remaining_volatile_memory, "
			+ " used_persistent_memory, remaining_persistent_memory, online_users, created_at "
			+ " from "
			+ SSN_MEMORY_CRUMBS
			+ " where created_at between DATEADD(hh, ?, current_timestamp()) and current_timestamp()"
			+ " order by created_at DESC";

	/**
	 * Query to delete all memory crumbs in the system.
	 */
	public static final String DELETE_ALL_MEMORY_CRUMBS = "delete from "
			+ SSN_MEMORY_CRUMBS;

	/**
	 * Query to insert a row into the memory_crumbs table.
	 */
	public static final String INSERT_MEMORY_CRUMB = "insert into "
			+ SSN_MEMORY_CRUMBS
			+ " (used_volatile_memory, remaining_volatile_memory, used_persistent_memory, remaining_persistent_memory, "
			+ " online_users, created_at) values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP())";

	// /=======Poorva

	// get the user names of all the chat buddies of a user
	// get the user names of all the chat buddies of a user for the last 'x'
	// hours
	// get a list of all the user names in the db
	
	public static final String FETCH_CHAT_BUDDIES_USERS = " Select Author,Author_id, Target, Target_id"
			+ " from "
			+ "(selct c.user_name Author, m.author_id from SSN_USERS c"
			+ " join "
			+ "(Select distinct Author_id from SSN_MESSAGES m"
			+ " join  SSN_USER on m.author_id =c.user_id)"
			+ "(selct u.user_name, m.target_idfrom SSN_USERS c"
			+ " join "
			+ " select c.user_name, c.user_idfrom SSN_USERS c"
			+ " join "
			+ "(Select distinct target_id from SSN_MESSAGES m"
			+ " join"
			+ "SSN_USER on m.author_id =c.user_id)";
			
// ----Social Network Analysis query-----//	
	public static final String FETCH_CHAT_BUDDIES_USERS1 = "select a.Author_id, b.user_name author,"
			+ " a. target_id,c.user_name target"
			+ "from SSN_MESSAGES a, SSN_USERS b, SSN_USERS c"
			+ " where a.author_id = b.user_id" 
			+ "and a.target_id=c.user_id";
}
