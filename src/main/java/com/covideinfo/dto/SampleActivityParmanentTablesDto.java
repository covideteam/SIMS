package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.StudyActivities;

@SuppressWarnings("serial")
public class SampleActivityParmanentTablesDto implements Serializable {
	
	private Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> twsaMap;
	private Map<String, Map<Long, Map<Long, StudyActivities>>> tsadMap;
	private Map<String, Map<Long, List<Long>>> resDefalutMap;
	public Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> getTwsaMap() {
		return twsaMap;
	}
	public Map<String, Map<Long, Map<Long, StudyActivities>>> getTsadMap() {
		return tsadMap;
	}
	public Map<String, Map<Long, List<Long>>> getResDefalutMap() {
		return resDefalutMap;
	}
	public void setTwsaMap(Map<String, Map<Long, Map<Long, List<GlobalParameter>>>> twsaMap) {
		this.twsaMap = twsaMap;
	}
	public void setTsadMap(Map<String, Map<Long, Map<Long, StudyActivities>>> tsadMap) {
		this.tsadMap = tsadMap;
	}
	public void setResDefalutMap(Map<String, Map<Long, List<Long>>> resDefalutMap) {
		this.resDefalutMap = resDefalutMap;
	}
		

}
