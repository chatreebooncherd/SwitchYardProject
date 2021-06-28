package th.co.ais.mynetwork.topworst.service;

import org.apache.log4j.Logger;
import th.co.ais.mynetwork.module.log.ErrorLog;
import th.co.ais.mynetwork.module.log.FunctionLog;
import th.co.ais.mynetwork.module.util.ExceptionHandle;
import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellResponse;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellResponse;
import th.co.ais.mynetwork.topworst.repository.WorkFlowRepository;

public class TopWorstCellService {
	private static final Logger LOGGER = Logger.getLogger(TopWorstCellService.class);
	
	public SearchTopWorstCellResponse searchTopWorstCell(SearchTopWorstCellRequest request) throws Exception {
		
		FunctionLog functionLog = new FunctionLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		SearchTopWorstCellResponse response = new SearchTopWorstCellResponse();
		WorkFlowRepository repository = new WorkFlowRepository();
		
		try {
			response.setData(repository.getTopWorstCell(request));
		} catch (ExceptionHandle e) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getErrorType().getMessage()));
			response.setError(e.getErrorType());
		}
		
		LOGGER.info(functionLog.end());
		return response;
	}
	
	public SaveTopWorstCellResponse saveTopWorstCell(SaveTopWorstCellRequest request) throws Exception {
		
		FunctionLog functionLog = new FunctionLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		SaveTopWorstCellResponse response = new SaveTopWorstCellResponse();
		WorkFlowRepository repository = new WorkFlowRepository();
		
		try {
			response.setResult(repository.saveTopWorstCell(request));
		} catch (ExceptionHandle e) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getErrorType().getMessage()));
			response.setError(e.getErrorType());
		}
		
		LOGGER.info(functionLog.end());
		return response;
	}
	
}
