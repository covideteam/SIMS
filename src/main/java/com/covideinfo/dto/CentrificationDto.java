package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;

@SuppressWarnings("serial")
public class CentrificationDto implements Serializable {
	
	private Map<String, Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>>> ssctpMap;//subject , period, timepoint
	private StudyMaster sm;
	private List<Long> spmIdsList;

	public Map<String, Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>>> getSsctpMap() {
		return ssctpMap;
	}

	public void setSsctpMap(Map<String, Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>>> ssctpMap) {
		this.ssctpMap = ssctpMap;
	}

	public StudyMaster getSm() {
		return sm;
	}

	public void setSm(StudyMaster sm) {
		this.sm = sm;
	}

	public List<Long> getSpmIdsList() {
		return spmIdsList;
	}

	public void setSpmIdsList(List<Long> spmIdsList) {
		this.spmIdsList = spmIdsList;
	}

}
