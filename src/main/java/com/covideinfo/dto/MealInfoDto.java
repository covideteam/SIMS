package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.dto.AllowStudySubjectMealsDto;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;

@SuppressWarnings("serial")
public class MealInfoDto implements Serializable {
	
	private StudyMaster study;
	private List<StudySubjects> subjectsList;
	private List<StudySubjectPeriods> ssubPeriodList;
	private List<MealsTimePoints> timepoints;
	boolean doseDone = false;
	private Map<Long, MealsTimePoints> preDoseMap;
	private Map<Long, MealsTimePoints> postDoseMap;
	private List<Long> mealDoneIds;
	
	private List<SubjectMealsTimePointsData> submDataList;
	private List<TreatmentInfo> treatmentList;
	private List<SubjectRandamization> srmzList;
	private List<SubjectMealsTimePointsData> subColDatalList;
	private Map<String, Map<Long, String>> subjectDoseMap;
	private Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap;
	private DosingDto dsDto;
	private Boolean treatmentSpecificMeals;
	private List<StudyPeriodMaster> spmList= null;
	private List<AllowStudySubjectMealsDto> alsmList = null;
	private Long projectId = null;
	public List<SubjectRandamization> getSrmzList() {
		return srmzList;
	}
	public void setSrmzList(List<SubjectRandamization> srmzList) {
		this.srmzList = srmzList;
	}
	public List<MealsTimePoints> getTimepoints() {
		return timepoints;
	}
	public boolean isDoseDone() {
		return doseDone;
	}
	public Map<Long, MealsTimePoints> getPreDoseMap() {
		return preDoseMap;
	}
	public Map<Long, MealsTimePoints> getPostDoseMap() {
		return postDoseMap;
	}
	public List<Long> getMealDoneIds() {
		return mealDoneIds;
	}
	public void setTimepoints(List<MealsTimePoints> timepoints) {
		this.timepoints = timepoints;
	}
	public void setDoseDone(boolean doseDone) {
		this.doseDone = doseDone;
	}
	public void setPreDoseMap(Map<Long, MealsTimePoints> preDoseMap) {
		this.preDoseMap = preDoseMap;
	}
	public void setPostDoseMap(Map<Long, MealsTimePoints> postDoseMap) {
		this.postDoseMap = postDoseMap;
	}
	public void setMealDoneIds(List<Long> mealDoneIds) {
		this.mealDoneIds = mealDoneIds;
	}
	
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public List<SubjectMealsTimePointsData> getSubmDataList() {
		return submDataList;
	}
	public void setSubmDataList(List<SubjectMealsTimePointsData> submDataList) {
		this.submDataList = submDataList;
	}
	public List<TreatmentInfo> getTreatmentList() {
		return treatmentList;
	}
	public void setTreatmentList(List<TreatmentInfo> treatmentList) {
		this.treatmentList = treatmentList;
	}
	public List<StudySubjects> getSubjectsList() {
		return subjectsList;
	}
	public void setSubjectsList(List<StudySubjects> subjectsList) {
		this.subjectsList = subjectsList;
	}
	public List<StudySubjectPeriods> getSsubPeriodList() {
		return ssubPeriodList;
	}
	public void setSsubPeriodList(List<StudySubjectPeriods> ssubPeriodList) {
		this.ssubPeriodList = ssubPeriodList;
	}
	public List<SubjectMealsTimePointsData> getSubColDatalList() {
		return subColDatalList;
	}
	public void setSubColDatalList(List<SubjectMealsTimePointsData> subColDatalList) {
		this.subColDatalList = subColDatalList;
	}
	public Map<String, Map<Long, String>> getSubjectDoseMap() {
		return subjectDoseMap;
	}
	public void setSubjectDoseMap(Map<String, Map<Long, String>> subjectDoseMap) {
		this.subjectDoseMap = subjectDoseMap;
	}
	public Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> getDosedMap() {
		return dosedMap;
	}
	public void setDosedMap(Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap) {
		this.dosedMap = dosedMap;
	}
	public DosingDto getDsDto() {
		return dsDto;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public List<StudyPeriodMaster> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodMaster> spmList) {
		this.spmList = spmList;
	}
	public void setDsDto(DosingDto dsDto) {
		this.dsDto = dsDto;
	}
	public Boolean getTreatmentSpecificMeals() {
		return treatmentSpecificMeals;
	}
	public void setTreatmentSpecificMeals(Boolean treatmentSpecificMeals) {
		this.treatmentSpecificMeals = treatmentSpecificMeals;
	}
	public List<AllowStudySubjectMealsDto> getAlsmList() {
		return alsmList;
	}
	public void setAlsmList(List<AllowStudySubjectMealsDto> alsmList) {
		this.alsmList = alsmList;
	}
	
	

}
