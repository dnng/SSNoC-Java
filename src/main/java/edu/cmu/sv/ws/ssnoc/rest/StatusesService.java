package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumb;

@Path("/statuses")
public class StatusesService extends BaseService {
	/**
	 * This method loads all statuses in the system.
	 * 
	 * @return - List of all statuses.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "statuses")
	public List<StatusCrumb> loadStatuses() {
		Log.enter();

		List<StatusCrumb> statusCrumbs = null;
		try {
			List<StatusCrumbPO> statusCrumbPOs = DAOFactory.getInstance().getStatusCrumbDAO().loadStatusCrumbs();

			statusCrumbs = new ArrayList<StatusCrumb>();
			for (StatusCrumbPO po : statusCrumbPOs) {
				StatusCrumb dto = ConverterUtils.convert(po);
				statusCrumbs.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(statusCrumbs);
		}

		return statusCrumbs;
	}
}
