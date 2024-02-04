package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;

@SuppressWarnings("serial")
public class StudyWithDrawDto implements Serializable{
	
	private Map<String, List<StaticActivityDetails>> actMap;
	private Map<Long, List<StaticActivityValueDetails>> actValMap;

	public Map<Long, List<StaticActivityValueDetails>> getActValMap() {
		return actValMap;
	}

	public void setActValMap(Map<Long, List<StaticActivityValueDetails>> actValMap) {
		this.actValMap = actValMap;
	}

	public Map<String, List<StaticActivityDetails>> getActMap() {
		return actMap;
	}

	public void setActMap(Map<String, List<StaticActivityDetails>> actMap) {
		this.actMap = actMap;
	}
	
	
	

}
