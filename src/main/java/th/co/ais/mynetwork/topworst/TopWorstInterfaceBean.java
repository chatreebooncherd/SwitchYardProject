package th.co.ais.mynetwork.topworst;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.switchyard.component.bean.Service;
import th.co.ais.mynetwork.module.enumeration.ExceptionType;
import th.co.ais.mynetwork.module.log.ErrorLog;
import th.co.ais.mynetwork.module.log.TransactionLog;
import th.co.ais.mynetwork.module.util.ExceptionHandle;
import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellResponse;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellResponse;
import th.co.ais.mynetwork.topworst.service.TopWorstCellService;

@Service(value = TopWorstInterface.class, name = "TopWorstInterface")
public class TopWorstInterfaceBean implements TopWorstInterface {
	
	//@Inject
	//@Reference("TopWorstRestResource")
	//TopWorstRestResource topWorstRest;
	
	private static final Logger LOGGER = Logger.getLogger(TopWorstInterfaceBean.class);
	
	public TopWorstInterfaceBean() {
		LOGGER.info("Log4j appender configuration is successful !!");
		//PropertyConfigurator.configure("/app/www/configuration/writeoff/log4j.properties");
//		PropertyConfigurator.configure("D:\\WFH\\WriteOff\\config\\log4j.properties"); // for Test local
		LOGGER.debug("Log4j appender configuration is successful !!");
	}	
	
	@Override
	public SearchTopWorstCellResponse getTopWorstCell(SearchTopWorstCellRequest request) {
		TransactionLog log = new TransactionLog(Thread.currentThread().getStackTrace()[1].getMethodName(), request);
		SearchTopWorstCellResponse response = new SearchTopWorstCellResponse();
		TopWorstCellService service = new TopWorstCellService();
		
		try {
			response = service.searchTopWorstCell(request);
			response.setData(response.getData());
		} catch (ExceptionHandle e) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getErrorType().getMessage()));
			response.setError(e.getErrorType());
		} catch (Exception ex) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, ex.getMessage()));
			response.setError((new ExceptionHandle(ExceptionType.SystemError, ExceptionHandle.findRootCause(ex)).getErrorType()));
		}
		//LOGGER.info(log.end(response));
		return response;
	}
	
	@Override
	public SaveTopWorstCellResponse saveTopWorstCell(SaveTopWorstCellRequest request) {
		TransactionLog log = new TransactionLog(Thread.currentThread().getStackTrace()[1].getMethodName(), request);
		SaveTopWorstCellResponse response = new SaveTopWorstCellResponse();
		TopWorstCellService service = new TopWorstCellService();
		
		try {
			response = service.saveTopWorstCell(request);
		} catch (ExceptionHandle e) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getErrorType().getMessage()));
			response.setError(e.getErrorType());
		} catch (Exception ex) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, ex.getMessage()));
			response.setError((new ExceptionHandle(ExceptionType.SystemError, ExceptionHandle.findRootCause(ex)).getErrorType()));
		}
		LOGGER.info(log.end(response));
		return response;
	}
	
}
