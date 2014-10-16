package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.annotations.Array;

import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserclusterPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * Interface specifying the contract that all implementations will implement to
 */
public interface IUsergroupsDAO {
	List<UserclusterPO> loadUsergroups();
	

}
