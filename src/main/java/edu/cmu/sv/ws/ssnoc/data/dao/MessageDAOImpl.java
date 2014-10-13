package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;


public class MessageDAOImpl extends BaseDAOImpl implements IMessageDAO{

	@Override
	public MessagePO loadExistingMessage(long messageID) {
		Log.enter();

		

		MessagePO messagePO = new MessagePO();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.FIND_MESSAGE_BY_ID)) {
			stmt.setLong(1, messageID);
			messagePO = processResults(stmt);
				
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messagePO);
		}

		return messagePO;
	}
	
	private MessagePO processResults(PreparedStatement stmt) {
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
