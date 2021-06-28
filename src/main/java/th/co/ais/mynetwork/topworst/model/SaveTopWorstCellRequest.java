package th.co.ais.mynetwork.topworst.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import th.co.ais.mynetwork.module.model.CommonRequest;

@Getter
@Setter
@ToString
public class SaveTopWorstCellRequest extends CommonRequest {
	private int id;
	private String cell_type;
	private String severity;
	private String rncname;
	private String nodebname;
	private String cellname;
	private String site_code;
	private List<TopWorstCell> topWorstCellList;
}
