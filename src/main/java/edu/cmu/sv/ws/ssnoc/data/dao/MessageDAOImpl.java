package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;

public class MessageDAOImpl extends BaseDAOImpl implements IMessageDAO {

	@Override
	public List<MessagePO> loadWallMessages() {
		Log.enter();
		String query = SQL.FIND_ALL_ACTIVE_WALL_MESSAGES;
		List<MessagePO> messages = new ArrayList<MessagePO>();
			try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);) {
			messages = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
		Log.exit(messages);
	}

			return messages;
	}
	
	@Override
	public List<MessagePO> loadChatMessages() {
		Log.enter();
		String query = SQL.FIND_ALL_CHAT_MESSAGES;
		List<MessagePO> messages = new ArrayList<MessagePO>();
			try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);) {
			messages = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
		Log.exit(messages);
	}

			return messages;
	}
	
	@Override
	public List<MessagePO> loadAnnouncementMessages() {
		Log.enter();
		String query = SQL.FIND_ALL_ACTIVE_ANNOUNCEMENT_MESSAGES;
		List<MessagePO> messages = new ArrayList<MessagePO>();
			try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);) {
			messages = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
		Log.exit(messages);
	}
			return messages;
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
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				MessagePO po = new MessagePO();
				if(colCount >=1) po.setMessageId(rs.getLong(1));
				if(colCount >=2) po.setAuthorId(rs.getLong(2));
				if(colCount >=3) po.setAuthorName(rs.getString(3));
				if(colCount >=4) po.setTargetId(rs.getLong(4));
				if(colCount >=5) po.setTargetName(rs.getString(5));
				if(colCount >=6) po.setLocationId(rs.getLong(6));
				if(colCount >=7) po.setLocation(rs.getString(7));
				if(colCount >=8) po.setContent(rs.getString(8));
				if(colCount >=9) po.setMessageType(rs.getString(9));
				if(colCount >=10) po.setCreatedAt(rs.getTimestamp(10));	

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
	public long saveChatMessage(MessagePO messagePO) {

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
				stmt.setString(4, "CHAT");
				stmt.setLong(5, messagePO.getLocationId());
				stmt.setTimestamp(6, messagePO.getCreatedAt());
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
			Log.enter(messagePO);
			long messageId = 0;
			if (messagePO == null) {
				Log.warn("Inside save method with messagePO == NULL");
				return messageId;
			}

			try (Connection conn = getConnection();
					PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_WALL_MESSAGE,  Statement.RETURN_GENERATED_KEYS)) {
				stmt.setLong(1, messagePO.getAuthorId());
				stmt.setLong(2, messagePO.getTargetId());
				stmt.setString(3, messagePO.getContent());
				stmt.setString(4, "WALL");
				stmt.setLong(5, messagePO.getLocationId());
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
	public MessagePO loadMessageById(long messageId) {
		Log.enter();

		String query = SQL.FIND_MESSAGE_BY_ID;

		MessagePO message = new MessagePO();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setLong(1, messageId);
			message = processResult(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(message);
		}
		return message;
	}
		
	private MessagePO processResult(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResult method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		MessagePO message = new MessagePO();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				MessagePO po = new MessagePO();
				if(colCount >=1) po.setMessageId(rs.getLong(1));
				if(colCount >=2) po.setAuthorId(rs.getLong(2));
				if(colCount >=3) po.setAuthorName(rs.getString(3));
				if(colCount >=4) po.setTargetId(rs.getLong(4));
				if(colCount >=5) po.setTargetName(rs.getString(5));
				if(colCount >=6) po.setLocationId(rs.getLong(6));
				if(colCount >=7) po.setLocation(rs.getString(7));
				if(colCount >=8) po.setContent(rs.getString(8));
				if(colCount >=9) po.setMessageType(rs.getString(9));
				if(colCount >=10) po.setCreatedAt(rs.getTimestamp(10));				
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(message);
		}

		return message;
	}
	
	public List<MessagePO> getAllChatMessagesForPeers(String userName1, String userName2) {
		Log.enter();
		// m.created_at, m.location_id, m.message_type, m.content, m.target_id, m.author_id, m.message_id, sa.user_name as author, st.user_name as target

		String query = SQL.FIND_PEER_CHAT_MESSAGES;

		List<MessagePO> peerChatMessages = new ArrayList<MessagePO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, "CHAT");
			stmt.setString(2, userName1);
			stmt.setString(3, userName2);
			stmt.setString(4, userName2);
			stmt.setString(5, userName1);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();
					MessagePO po = new MessagePO();
					if(colCount >=1) po.setCreatedAt(rs.getTimestamp(1));
					if(colCount >=2) po.setLocationId(rs.getLong(2));
					if(colCount >=3) po.setMessageType(rs.getString(3));
					if(colCount >=4) po.setContent(rs.getString(4));
					if(colCount >=5) po.setTargetId(rs.getLong(5));
					if(colCount >=6) po.setAuthorId(rs.getLong(6));
					if(colCount >=7) po.setMessageId(rs.getLong(7));
					if(colCount >=8) po.setAuthorName(rs.getString(8));
					if(colCount >=9) po.setTargetName(rs.getString(9));
					peerChatMessages.add(po);
				}
			} catch (SQLException e) {
				handleException(e);
			} finally {
				Log.exit();
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(peerChatMessages);
		}
		return peerChatMessages;
	}
	
	
	public void savePrivateChatMessage(String senderName, String receiverName, MessagePO po) {
		Log.enter(po);
		if (po == null) {
			Log.warn("Inside save private chat method with messagePO == NULL");
			return;
		}

		
		//"(created_at, location_id, message_type, content, target_id, author_id) "		+ "values (CURRENT_TIMESTAMP(), ?, \'CHAT\', ?, ?, ?)";
		// fetch sender user Id	
		long senderId =  DAOFactory.getInstance().getUserDAO().findByName(senderName).getUserId();
		long receiverId = DAOFactory.getInstance().getUserDAO().findByName(receiverName).getUserId();
		
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_PRIVATE_CHAT_MESSAGE)) {
			if(po.getCreatedAt() == null) 
			{
			    po.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			}  
			stmt.setTimestamp(1, po.getCreatedAt());
		    stmt.setLong(2, po.getLocationId());
			stmt.setString(3, po.getContent());
			stmt.setLong(4, receiverId);
			stmt.setLong(5, senderId);
		
			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	@Override
	public void truncateMessages() {
		Log.enter();
		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.TRUNCATE_MESSAGES)) {
			Log.trace("Truncate Statement executed");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}
	
	@Override
	public long saveAnnouncementMessage(MessagePO messagePO) {
			Log.enter(messagePO);
			long messageId = 0;
			if (messagePO == null) {
				Log.warn("Inside save method with messagePO == NULL");
				return messageId;
			}

			try (Connection conn = getConnection();
					PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_ANNOUNCEMENT_MESSAGE,  Statement.RETURN_GENERATED_KEYS)) {
				stmt.setLong(1, messagePO.getAuthorId());
				stmt.setLong(2, messagePO.getTargetId());
				stmt.setString(3, messagePO.getContent());
				stmt.setString(4, "ANNOUNCEMENT");
				stmt.setLong(5, messagePO.getLocationId());
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
	
	public void cleanUpAllMessages() {
		String query = SQL.CLEAN_UP_MSGS;
	
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			boolean  rs = stmt.execute();
			System.out.println(rs);
		} catch (SQLException e) {
			handleException(e);
		}
	}

}

