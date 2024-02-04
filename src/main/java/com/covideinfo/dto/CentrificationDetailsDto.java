package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;

@SuppressWarnings("serial")
public class CentrificationDetailsDto implements Serializable {
	
	private List<SubjectSampleCollectionTimePointsData> ssctpdList;
	private StudyMaster sm;
	private List<Long> spmIdsList;
	public List<SubjectSampleCollectionTimePointsData> getSsctpdList() {
		return ssctpdList;
	}
	public StudyMaster getSm() {
		return sm;
	}
	public void setSsctpdList(List<SubjectSampleCollectionTimePointsData> ssctpdList) {
		this.ssctpdList = ssctpdList;
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
