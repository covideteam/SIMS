package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;

@SuppressWarnings("serial")
public class SepatationDtoDetails implements Serializable {
   
	private StudyMaster study;
	private List<StudyPeriodMaster> periodList;
	private List<SampleTimePoints> timepoints;
	private List<CentrifugationDataMaster> centrifugationList;
	private List<StudySubjects> subjectsList;
	public StudyMaster getStudy() {
		return study;
	}
	public List<StudyPeriodMaster> getPeriodList() {
		return periodList;
	}
	public List<SampleTimePoints> getTimepoints() {
		return timepoints;
	}
	public List<CentrifugationDataMaster> getCentrifugationList() {
		return centrifugationList;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setPeriodList(List<StudyPeriodMaster> periodList) {
		this.periodList = periodList;
	}
	public void setTimepoints(List<SampleTimePoints> timepoints) {
		this.timepoints = timepoints;
	}
	public void setCentrifugationList(List<CentrifugationDataMaster> centrifugationList) {
		this.centrifugationList = centrifugationList;
	}
	public List<StudySubjects> getSubjectsList() {
		return subjectsList;
	}
	public void setSubjectsList(List<StudySubjects> subjectsList) {
		this.subjectsList = subjectsList;
	}
	
	
}
