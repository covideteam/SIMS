package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class VilaRackDtoDetails implements Serializable {
	
	private UserMaster user;
	private Map<Long, SampleTimePoints> mapda;
	private StudyPeriodMaster periodpojo;
	private Deepfreezer deepreezer;
	private StudyMaster study;
	private List<StudySubjects> subjectsList;
	private List<SampleTimePoints> sampList;
	public UserMaster getUser() {
		return user;
	}
	public Map<Long, SampleTimePoints> getMapda() {
		return mapda;
	}
	public StudyPeriodMaster getPeriodpojo() {
		return periodpojo;
	}
	public Deepfreezer getDeepreezer() {
		return deepreezer;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public void setMapda(Map<Long, SampleTimePoints> mapda) {
		this.mapda = mapda;
	}
	public void setPeriodpojo(StudyPeriodMaster periodpojo) {
		this.periodpojo = periodpojo;
	}
	public void setDeepreezer(Deepfreezer deepreezer) {
		this.deepreezer = deepreezer;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public List<StudySubjects> getSubjectsList() {
		return subjectsList;
	}
	public void setSubjectsList(List<StudySubjects> subjectsList) {
		this.subjectsList = subjectsList;
	}
	public List<SampleTimePoints> getSampList() {
		return sampList;
	}
	public void setSampList(List<SampleTimePoints> sampList) {
		this.sampList = sampList;
	}
	
	
	
	

}
