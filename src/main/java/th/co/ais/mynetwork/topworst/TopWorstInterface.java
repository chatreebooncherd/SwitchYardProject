package th.co.ais.mynetwork.topworst;

import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellResponse;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellResponse;

public interface TopWorstInterface extends TopWorstRestResource {
	public SearchTopWorstCellResponse getTopWorstCell(SearchTopWorstCellRequest request);
	public SaveTopWorstCellResponse saveTopWorstCell(SaveTopWorstCellRequest request);
}
