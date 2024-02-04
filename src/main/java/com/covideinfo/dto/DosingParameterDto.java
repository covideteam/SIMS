package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DosingParameterDto implements Serializable {
	
		private static final long serialVersionUID = 5468960056487388794L;
	private Map<Integer, List<Long>> timePointParmMap;
	private Map<Integer, List<Long>> nonTimePointParmMap;
	public Map<Integer, List<Long>> getTimePointParmMap() {
		return timePointParmMap;
	}
	public Map<Integer, List<Long>> getNonTimePointParmMap() {
		return nonTimePointParmMap;
	}
	public void setTimePointParmMap(Map<Integer, List<Long>> timePointParmMap) {
		this.timePointParmMap = timePointParmMap;
	}
	public void setNonTimePointParmMap(Map<Integer, List<Long>> nonTimePointParmMap) {
		this.nonTimePointParmMap = nonTimePointParmMap;
	}
	
	
	
	

}
