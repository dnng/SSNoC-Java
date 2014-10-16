package edu.cmu.sv.ws.ssnoc.data.dao;
import java.util.List;
import edu.cmu.sv.ws.ssnoc.data.po.UserClusterPO;


/**
 * Interface specifying the contract that all implementations will implement to
 */
public interface IUsergroupsDAO {
	List<UserClusterPO> loadUsergroups();
	

}
