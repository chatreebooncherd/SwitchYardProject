package th.co.ais.mynetwork.topworst.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import th.co.ais.mynetwork.module.model.CommonResponse;

@Getter
@Setter
@ToString
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SearchTopWorstCellResponse extends CommonResponse {
	private List<TopWorstCell> data;
}
