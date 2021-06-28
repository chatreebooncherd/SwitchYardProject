package th.co.ais.mynetwork.topworst;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellResponse;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellResponse;

@Path("/")

public interface TopWorstRestResource {
	@POST
	@Path("/getTopWorstCell")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SearchTopWorstCellResponse getTopWorstCell(SearchTopWorstCellRequest request);
	//http://localhost:8080/MyNetwork/sevice/getTopWorstCell

	@POST
	@Path("/saveTopWorstCell")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SaveTopWorstCellResponse saveTopWorstCell(SaveTopWorstCellRequest request);
	//http://localhost:8080/MyNetwork/sevice/saveTopWorstCell
	
}


