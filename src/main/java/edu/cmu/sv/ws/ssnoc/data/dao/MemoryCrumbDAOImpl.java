package edu.cmu.sv.ws.ssnoc.data.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MemoryCrumbPO;

/**
 * DAO implementation for saving User information in the H2 database.
 * 
 */
public class MemoryCrumbDAOImpl extends BaseDAOImpl implements IMemoryCrumbDAO {
	/**
	 * This method will load all the memory crumbs in the
	 * database.
	 * 
	 * @return - List of all memory crumbs.
	 */
	public List<MemoryCrumbPO> loadMemoryCrumbs()
	{
		Log.enter();

		String query = SQL.FIND_ALL_MEMORY_CRUMBS;

		List<MemoryCrumbPO> memoryCrumbs = new ArrayList<MemoryCrumbPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			memoryCrumbs = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(memoryCrumbs);
		}

		return memoryCrumbs;
	}

	private List<MemoryCrumbPO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<MemoryCrumbPO> memoryCrumbs = new ArrayList<MemoryCrumbPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				MemoryCrumbPO po = new MemoryCrumbPO();
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				
				po = new MemoryCrumbPO();
				if(colCount >=1) po.setMemoryCrumbId(rs.getLong(1));
				if(colCount >=2) po.setUsedVolatileMemory(rs.getLong(2));
				if(colCount >=3) po.setRemainingVolatileMemory(rs.getLong(3));
				if(colCount >=4) po.setUsedPersistentMemory(rs.getLong(4));
				if(colCount >=5) po.setRemainingPersistentMemory(rs.getLong(5));
				if(colCount >=6) po.setOnlineUsers(rs.getLong(6));
				if(colCount >=7) po.setCreatedAt(rs.getTimestamp(7));
				memoryCrumbs.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(memoryCrumbs);
		}

		return memoryCrumbs;
	}

	/**
	 * This method will save the information of the user into the database.
	 * 
	 * @param memoryCrumbPO
	 *            - Memory Crumb information to be saved.
	 */
	@Override
	public void save(MemoryCrumbPO memoryCrumbPO) {
		Log.enter(memoryCrumbPO);
		if (memoryCrumbPO == null) {
			Log.warn("Inside save method with memoryCrumbPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_MEMORY_CRUMB)) {
			stmt.setLong(1, memoryCrumbPO.getUsedVolatileMemory());
			stmt.setLong(2, memoryCrumbPO.getRemainingVolatileMemory());
			stmt.setLong(3, memoryCrumbPO.getUsedPersistentMemory());
			stmt.setLong(4, memoryCrumbPO.getRemainingPersistentMemory());
			stmt.setLong(5, memoryCrumbPO.getOnlineUsers());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	
	/**
	 * This method will delete all memory crumbs in the system 
	 * 
	 */
	@Override
	public void deleteAll() {
		Log.enter();

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.DELETE_ALL_MEMORY_CRUMBS)) {
			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows updated.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
		
	}

	/**
	 * This method will load all the memory crumbs in the
	 * database in the specified interval.
	 * 
	 * @return - List of all memory crumbs in the specified interval
	 */
	@Override
	public List<MemoryCrumbPO> loadMemoryCrumbsInInterval(long timeInterval) {
		Log.enter();
		long negativeTimeInterval = timeInterval * -1;

		String query = SQL.FIND_ALL_MEMORY_CRUMBS_IN_INTERVAL;

		List<MemoryCrumbPO> memoryCrumbs = new ArrayList<MemoryCrumbPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setLong(1, negativeTimeInterval);
			memoryCrumbs = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(memoryCrumbs);
		}

		return memoryCrumbs;
	}

}
