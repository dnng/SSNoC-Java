package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

public class MessageDAOImpl extends BaseDAOImpl implements IMessageDAO {

	@Override
	public List<MessagePO> loadWallMessage() {
		Log.enter();
		String query = SQL.FIND_ALL_WALL_MESSAGES;
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
	public MessagePO loadExistingMessage(long messageID) {
		Log.enter();

		

		MessagePO messagePO = new MessagePO();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.FIND_MESSAGE_BY_ID)) {
			stmt.setLong(1, messageID);
			messagePO = processResult(stmt);
				
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messagePO);
		}

		return messagePO;
	}
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
	public MessagePO loadMessageById(long messageId) {
		Log.enter();

		String query = SQL.FIND_MESSAGE_BY_ID;

		MessagePO message = new MessagePO();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setLong(1, messageId);
			message = processMessageResult(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(message);
		}
		return message;
	}
	
	private MessagePO processMessageResult(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		MessagePO po = new MessagePO();
		try (ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
							
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();	
				
					
				if(colCount >=1) po.setMessageId(rs.getLong(1));
				if(colCount >=2) po.setAuthorId(rs.getLong(2));
				if(colCount >=3) po.setAuthorName(rs.getString(3));
				if(colCount >=4) po.setTargetId(rs.getLong(4));
				if(colCount >=6) po.setLocationId(rs.getLong(6));
				if(colCount >=7) po.setLocation(rs.getString(7));
				if(colCount >=9) po.setContent(rs.getString(8));
				if(colCount >=10)po.setCreatedAt(rs.getTimestamp(9));
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(po);
		}

		return po;
	}
	
	@Override
	public List<MessagePO> loadMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private MessagePO processResult(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		MessagePO message = new MessagePO();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				MessagePO po = new MessagePO();
				po = new MessagePO();
				
				po.setMessageId(rs.getLong(1));
				po.setAuthorId(rs.getLong(2));
				po.setAuthorName(rs.getString(3));
				po.setTargetId(rs.getLong(4));
				po.setLocation(rs.getString(6));
				po.setContent(rs.getString(7));
				po.setCreatedAt(rs.getTimestamp(8));				
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(message);
		}

		return message;
	}



}

