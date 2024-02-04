package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;

public class MealsDataSavingDto implements Serializable {
	
	private static final long serialVersionUID = 1596878422520449326L;
	private StudyMaster study;
	private List<StudyPeriodMaster> spmList;
	private List<StudySubjects> subjectsList;
	private List<TreatmentInfo> treatmentList;
	private List<MealsTimePoints> mealsList;
	private List<SubjectMealsTimePointsData> smtpDataList;
	private UserMaster user;
	public StudyMaster getStudy() {
		return study;
	}
	public List<StudyPeriodMaster> getSpmList() {
		return spmList;
	}
	public List<StudySubjects> getSubjectsList() {
		return subjectsList;
	}
	public List<TreatmentInfo> getTreatmentList() {
		return treatmentList;
	}
	public List<MealsTimePoints> getMealsList() {
		return mealsList;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setSpmList(List<StudyPeriodMaster> spmList) {
		this.spmList = spmList;
	}
	public void setSubjectsList(List<StudySubjects> subjectsList) {
		this.subjectsList = subjectsList;
	}
	public void setTreatmentList(List<TreatmentInfo> treatmentList) {
		this.treatmentList = treatmentList;
	}
	public void setMealsList(List<MealsTimePoints> mealsList) {
		this.mealsList = mealsList;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public List<SubjectMealsTimePointsData> getSmtpDataList() {
		return smtpDataList;
	}
	public void setSmtpDataList(List<SubjectMealsTimePointsData> smtpDataList) {
		this.smtpDataList = smtpDataList;
	}
	
	
	

}
