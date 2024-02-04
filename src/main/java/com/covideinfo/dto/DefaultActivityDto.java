package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.DefaultActivityParameters;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.StudyPhases;

@SuppressWarnings("serial")
public class DefaultActivityDto implements Serializable {

	private List<GlobalActivity> gaList;
	private List<GlobalParameter> gpList;
	private List<StudyPhases> phaseList;
	private List<DefaultActivitys> daList;
	private Map<Long, List<DefaultActivityParameters>> dapMap;
	public List<GlobalActivity> getGaList() {
		return gaList;
	}
	public List<GlobalParameter> getGpList() {
		return gpList;
	}
	public List<StudyPhases> getPhaseList() {
		return phaseList;
	}
	public List<DefaultActivitys> getDaList() {
		return daList;
	}
	public void setGaList(List<GlobalActivity> gaList) {
		this.gaList = gaList;
	}
	public void setGpList(List<GlobalParameter> gpList) {
		this.gpList = gpList;
	}
	public void setPhaseList(List<StudyPhases> phaseList) {
		this.phaseList = phaseList;
	}
	public void setDaList(List<DefaultActivitys> daList) {
		this.daList = daList;
	}
	public Map<Long, List<DefaultActivityParameters>> getDapMap() {
		return dapMap;
	}
	public void setDapMap(Map<Long, List<DefaultActivityParameters>> dapMap) {
		this.dapMap = dapMap;
	}
	
	
}
