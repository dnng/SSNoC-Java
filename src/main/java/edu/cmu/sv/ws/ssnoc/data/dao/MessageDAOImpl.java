package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;

/**
 * DAO implementation for saving Message information in the H2 database.
 * 
 */
public class MessageDAOImpl extends BaseDAOImpl implements IMessageDAO {
	/**
	 * This method will load message from the DB. If
	 * no message type(null) is provided, it will load all messages.
	 * 
	 * @return - List of messages
	 */
//----------Poorva--------
	@Override
	public List<MessagePO> loadWallMessage() {
		// TODO Auto-generated method stub
	//	public List<StatusCrumbPO> loadStatusCrumbs() {
		Log.enter();
	//
		String query = SQL.FIND_ALL_WALL_MESSAGES;
	//
		List<MessagePO> message = new ArrayList<MessagePO>();
			try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);) {
			message = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
		Log.exit(message);
	}

			return message;
	}
	//
	@Override
	public List<MessagePO> loadChatMessage() {
		// TODO Auto-generated method stub
		Log.enter();
		String query = SQL.FIND_ALL_CHAT_MESSAGES;
		List<MessagePO> message = new ArrayList<MessagePO>();
			try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);) {
			message = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
		Log.exit(message);
	}

			return message;
	}

	private List<MessagePO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<MessagePO> messages = new ArrayList<MessagePO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				MessagePO po = new MessagePO();
				po = new MessagePO();
				po.setMessageId(rs.getLong(1));
				po.setAuthorId(rs.getLong(2));
				po.setAuthorName(rs.getString(3));
				po.setTargetId(rs.getLong(4));
				po.setTargetName(rs.getString(5));
				po.setContent(rs.getString(6));
				po.setLocationId(rs.getLong(7));
				po.setLocation(rs.getString(8));
				po.setCreatedAt(rs.getTimestamp(9));

				messages.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
	//Chat Save//
	
	@Override
	public long saveChatMessage(MessagePO messagePO) {
		// TODO Auto-generated method stub
			Log.enter(messagePO);
			long messageId = 0;
			if (messagePO == null) {
				Log.warn("Inside save method with messagePO == NULL");
				return messageId;
			}

			try (Connection conn = getConnection();
					PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_CHAT_MESSAGE,  Statement.RETURN_GENERATED_KEYS)) {
				stmt.setLong(1, messagePO.getAuthorId());
				stmt.setLong(2, messagePO.getTargetId());
				stmt.setString(3, messagePO.getContent());
				stmt.setLong(4, messagePO.getLocationId());
				int rowCount = stmt.executeUpdate();
				Log.trace("Statement executed, and " + rowCount + " rows inserted.");
				
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	messageId = generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Creating messageId failed, no ID obtained.");
		            }
		        }
				
			} catch (SQLException e) {
				handleException(e);
			} finally {
				Log.exit();
			}
			return messageId;
		}
	//Wall Save//	
	
	@Override
	public long saveWallMessage(MessagePO messagePO) {
		// TODO Auto-generated method stub
			Log.enter(messagePO);
			long messageId = 0;
			if (messagePO == null) {
				Log.warn("Inside save method with messagePO == NULL");
				return messageId;
			}

			try (Connection conn = getConnection();
					PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_WALL_MESSAGE,  Statement.RETURN_GENERATED_KEYS)) {
				stmt.setLong(1, messagePO.getAuthorId());
				//stmt.setLong(2, messagePO.getTargetId());
				stmt.setString(2, messagePO.getContent());
				stmt.setLong(3, messagePO.getLocationId());
				int rowCount = stmt.executeUpdate();
				Log.trace("Statement executed, and " + rowCount + " rows inserted.");
				
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	messageId = generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Creating messageId failed, no ID obtained.");
		            }
		        }
				
			} catch (SQLException e) {
				handleException(e);
			} finally {
				Log.exit();
			}
			return messageId;
		}
		

	@Override
	public List<MessagePO> loadMessageByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessagePO loadMessageById(long message_id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<MessagePO> loadMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}

