package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class PlanedTimingTimePointsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8915892007314576221L;
	private Map<String, Date> pwptMap;
	private int diffDatys;
	public Map<String, Date> getPwptMap() {
		return pwptMap;
	}
	public void setPwptMap(Map<String, Date> pwptMap) {
		this.pwptMap = pwptMap;
	}
	public int getDiffDatys() {
		return diffDatys;
	}
	public void setDiffDatys(int diffDatys) {
		this.diffDatys = diffDatys;
	}

	
}
