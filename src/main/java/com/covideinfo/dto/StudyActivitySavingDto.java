package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.TreatmentInfo;

@SuppressWarnings("serial")
public class StudyActivitySavingDto implements Serializable {
	
	private List<GlobalActivity> actList;
	private Map<Long, GlobalParameter> gpMap;
	private List<TreatmentInfo> tminfoList;
	private List<StudyPeriodMaster> spList;
	private List<GlobalValues> gvList;
	private Long defaultIdsForIncAndExcId;
	public List<GlobalActivity> getActList() {
		return actList;
	}
	public Map<Long, GlobalParameter> getGpMap() {
		return gpMap;
	}
	public void setActList(List<GlobalActivity> actList) {
		this.actList = actList;
	}
	public void setGpMap(Map<Long, GlobalParameter> gpMap) {
		this.gpMap = gpMap;
	}
	public List<TreatmentInfo> getTminfoList() {
		return tminfoList;
	}
	public void setTminfoList(List<TreatmentInfo> tminfoList) {
		this.tminfoList = tminfoList;
	}
	public List<GlobalValues> getGvList() {
		return gvList;
	}
	public void setGvList(List<GlobalValues> gvList) {
		this.gvList = gvList;
	}
	public List<StudyPeriodMaster> getSpList() {
		return spList;
	}
	public void setSpList(List<StudyPeriodMaster> spList) {
		this.spList = spList;
	}
	public Long getDefaultIdsForIncAndExcId() {
		return defaultIdsForIncAndExcId;
	}
	public void setDefaultIdsForIncAndExcId(Long defaultIdsForIncAndExcId) {
		this.defaultIdsForIncAndExcId = defaultIdsForIncAndExcId;
	}
	
	
	
	
	
	

}
