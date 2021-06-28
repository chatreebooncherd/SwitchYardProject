package th.co.ais.mynetwork.topworst.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TopWorstCell {
	private int id;
	private String cell_type;
	private String severity;
	private String rncname;
	private String nodebname;
	private String cellname;
	private String site_code;
}
