package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyActivityRules;

@SuppressWarnings("serial")
public class StudyDynamicActDetailsDto implements Serializable {
	
	private Map<StudyActivities, List<StudyActivityParameters>> map;
	private Map<String, Map<Long, Map<Long, StudyActivityRules>>> saruleMap;
	
	public Map<StudyActivities, List<StudyActivityParameters>> getMap() {
		return map;
	}
	public Map<String, Map<Long, Map<Long, StudyActivityRules>>> getSaruleMap() {
		return saruleMap;
	}
	public void setMap(Map<StudyActivities, List<StudyActivityParameters>> map) {
		this.map = map;
	}
	public void setSaruleMap(Map<String, Map<Long, Map<Long, StudyActivityRules>>> saruleMap) {
		this.saruleMap = saruleMap;
	}
	
	
}
