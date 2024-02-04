package com.covideinfo.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyActivityRules;

@SuppressWarnings("serial")
public class StudyActivitiesSavingDto implements Serializable {

	private Map<StudyActivities, List<StudyActivityParameters>> saMap;
	private Map<String, Map<Long, Map<Long, StudyActivityRules>>> saruleMap;
	private Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> twsapMap;
	private Map<String, Map<Long, Map<Long, StudyActivities>>> tsadMap;
	private List<StudyActivities> defalutSaList;
	public Map<StudyActivities, List<StudyActivityParameters>> getSaMap() {
		return saMap;
	}
	public Map<String, Map<Long, Map<Long, StudyActivityRules>>> getSaruleMap() {
		return saruleMap;
	}
	public void setSaMap(Map<StudyActivities, List<StudyActivityParameters>> saMap) {
		this.saMap = saMap;
	}
	public void setSaruleMap(Map<String, Map<Long, Map<Long, StudyActivityRules>>> saruleMap) {
		this.saruleMap = saruleMap;
	}
	public Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> getTwsapMap() {
		return twsapMap;
	}
	public Map<String, Map<Long, Map<Long, StudyActivities>>> getTsadMap() {
		return tsadMap;
	}
	public void setTwsapMap(Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> twsapMap) {
		this.twsapMap = twsapMap;
	}
	public void setTsadMap(Map<String, Map<Long, Map<Long, StudyActivities>>> tsadMap) {
		this.tsadMap = tsadMap;
	}
	public List<StudyActivities> getDefalutSaList() {
		return defalutSaList;
	}
	public void setDefalutSaList(List<StudyActivities> defalutSaList) {
		this.defalutSaList = defalutSaList;
	}
	
}
