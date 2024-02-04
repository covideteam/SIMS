package com.covideinfo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudySubjects;

public class SeparationVacutinerDto implements Serializable{
	private static final long serialVersionUID = 3690095278400479417L;
	private List<SampleTimepointDto> sampleTimePoitns;
	private List<CentrifugationDataMaster> centrifugationList;
	private Map<Long, String> timePointsMap;
	private Map<Long, SampleTimePoints> timeIdTimePointMap;
	private Map<String, StudySubjects> subMap;
	private Map<String, StudySubjects> replaceSubMap;
	
	public Map<Long, SampleTimePoints> getTimeIdTimePointMap() {
		return timeIdTimePointMap;
	}

	public void setTimeIdTimePointMap(Map<Long, SampleTimePoints> timeIdTimePointMap) {
		this.timeIdTimePointMap = timeIdTimePointMap;
	}

	public Map<Long, String> getTimePointsMap() {
		return timePointsMap;
	}

	public void setTimePointsMap(Map<Long, String> timePointsMap) {
		this.timePointsMap = timePointsMap;
	}

	public List<CentrifugationDataMaster> getCentrifugationList() {
		return centrifugationList;
	}

	public void setCentrifugationList(List<CentrifugationDataMaster> centrifugationList) {
		this.centrifugationList = centrifugationList;
	}

	public List<SampleTimepointDto> getSampleTimePoitns() {
		return sampleTimePoitns;
	}

	public void setSampleTimePoitns(List<SampleTimepointDto> sampleTimePoitns) {
		this.sampleTimePoitns = sampleTimePoitns;
	}

	public Map<String, StudySubjects> getSubMap() {
		return subMap;
	}

	public void setSubMap(Map<String, StudySubjects> subMap) {
		this.subMap = subMap;
	}

	public Map<String, StudySubjects> getReplaceSubMap() {
		return replaceSubMap;
	}

	public void setReplaceSubMap(Map<String, StudySubjects> replaceSubMap) {
		this.replaceSubMap = replaceSubMap;
	}
	
	
}
