package edu.cmu.sv.ws.ssnoc.data.dao;
import edu.cmu.sv.ws.ssnoc.data.po.UserClusterPO;

import java.util.List;


/**
 * Interface specifying the contract that all implementations will implement to
 */
public interface IUserGroupDAO {
	List<UserClusterPO> loadUsergroups();


}
