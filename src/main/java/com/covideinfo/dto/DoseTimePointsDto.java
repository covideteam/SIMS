package com.covideinfo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.TreatmentInfo;

public class DoseTimePointsDto implements Serializable{
	private List<StudySubjects> subjects = new ArrayList<>();
	private SortedMap<Integer, String> subjectBarocodes = new TreeMap<>();  // key-Order , value=subjectno
	private Map<String, String> subjectTypes = new HashMap<>();			// key-subjecno, value = standby/not
	private Map<String, String> replacedSubjects = new HashMap<>();			// key-barcodeSubjectNo, value=finalReplacedSujectNo if any replacement happend
	private List<String> droppedSubjects = new ArrayList<>();				// drouped subject no's
	private Map<Long, String> discontinueSubjects = new HashMap<>();				// subject no's - discontinued for replace use
	
	private SortedMap<String, StudyPeriodMaster> subjectPerods = new TreeMap<>();	// key-subject no, value=periodPojo
	private SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes = new TreeMap<>();	// key-subject no, value=subjectDoseObject
	private SortedMap<String, SubjectDoseTimePoints> subjectZeroHeDoseTimes = new TreeMap<>();	// key-subject no, value=subjectDoseObject
	private SortedMap<Integer, StudyPeriodMaster> periods = new TreeMap<>();	// key-periodno, value = priodPojo
	private SortedMap<Long, DoseTimePoints> timPoints = new TreeMap<>();		// key-timePointId(PK) , value = doseTimePointObject
	private SortedMap<Long, TreatmentInfo> treatment = new TreeMap<>();		// key-treatmentId(PK) , value = treatmentId
	private Map<String, Long> studyActMap = new HashMap<>();///TimePoint, StudyActivityId
	private GlobalparameterFromDto perameters;
	private StudyMaster study;
	private Map<Long, String> replaceAvailSubjects;
	private List<StudySubjectPeriods> ssubPeriods;
	private List<DoseTimePoints> dpList;
	private List<SubjectDoseTimePoints> sdtpIdsList;
	private List<TreatmentInfo> treatmentList;
	private List<StudyPeriodMaster> periodsList;
	private Map<String, Map<String, SubjectMealsTimePointsData>> mealsDetials = new TreeMap<>();
	private List<SubjectMealsTimePointsData> mealsData;
	private GlobalActivity ga;
	private List<DoseTimePoints> allDoseTimePoints;
	private List<StudyTreatmentWiseSubjects> subjectTreatmentWiseList;
	private DosingDto dsDto;
	private List<DosingInfoDetails> drugInfiList;
	private String projectType = "";
	
	
	
	
	public Map<String, Map<String, SubjectMealsTimePointsData>> getMealsDetials() {
		return mealsDetials;
	}
	public void setMealsDetials(Map<String, Map<String, SubjectMealsTimePointsData>> mealsDetials) {
		this.mealsDetials = mealsDetials;
	}
	public SortedMap<String, SubjectDoseTimePoints> getSubjectZeroHeDoseTimes() {
		return subjectZeroHeDoseTimes;
	}
	public void setSubjectZeroHeDoseTimes(SortedMap<String, SubjectDoseTimePoints> subjectZeroHeDoseTimes) {
		this.subjectZeroHeDoseTimes = subjectZeroHeDoseTimes;
	}
	public GlobalparameterFromDto getPerameters() {
		return perameters;
	}
	public void setPerameters(GlobalparameterFromDto perameters) {
		this.perameters = perameters;
	}
	public Map<Long, String> getDiscontinueSubjects() {
		return discontinueSubjects;
	}
	public void setDiscontinueSubjects(Map<Long, String> discontinueSubjects) {
		this.discontinueSubjects = discontinueSubjects;
	}
	public Map<String, String> getSubjectTypes() {
		return subjectTypes;
	}
	public void setSubjectTypes(Map<String, String> subjectTypes) {
		this.subjectTypes = subjectTypes;
	}
	public SortedMap<Integer, String> getSubjectBarocodes() {
		return subjectBarocodes;
	}
	public void setSubjectBarocodes(SortedMap<Integer, String> subjectBarocodes) {
		this.subjectBarocodes = subjectBarocodes;
	}
	public Map<String, String> getReplacedSubjects() {
		return replacedSubjects;
	}
	public void setReplacedSubjects(Map<String, String> replacedSubjects) {
		this.replacedSubjects = replacedSubjects;
	}
	public List<String> getDroppedSubjects() {
		return droppedSubjects;
	}
	public void setDroppedSubjects(List<String> droppedSubjects) {
		this.droppedSubjects = droppedSubjects;
	}
	public SortedMap<String, StudyPeriodMaster> getSubjectPerods() {
		return subjectPerods;
	}
	public void setSubjectPerods(SortedMap<String, StudyPeriodMaster> subjectPerods) {
		this.subjectPerods = subjectPerods;
	}
	public SortedMap<String, SubjectDoseTimePoints> getSubjectDoseTimes() {
		return subjectDoseTimes;
	}
	public void setSubjectDoseTimes(SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes) {
		this.subjectDoseTimes = subjectDoseTimes;
	}
	public SortedMap<Integer, StudyPeriodMaster> getPeriods() {
		return periods;
	}
	public void setPeriods(SortedMap<Integer, StudyPeriodMaster> periods) {
		this.periods = periods;
	}
	public SortedMap<Long, DoseTimePoints> getTimPoints() {
		return timPoints;
	}
	public void setTimPoints(SortedMap<Long, DoseTimePoints> timPoints) {
		this.timPoints = timPoints;
	}
	public SortedMap<Long, TreatmentInfo> getTreatment() {
		return treatment;
	}
	public void setTreatment(SortedMap<Long, TreatmentInfo> treatment) {
		this.treatment = treatment;
	}
	public List<StudySubjects> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<StudySubjects> subjects) {
		this.subjects = subjects;
	}
	public Map<String, Long> getStudyActMap() {
		return studyActMap;
	}
	public void setStudyActMap(Map<String, Long> studyActMap) {
		this.studyActMap = studyActMap;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public Map<Long, String> getReplaceAvailSubjects() {
		return replaceAvailSubjects;
	}
	public void setReplaceAvailSubjects(Map<Long, String> replaceAvailSubjects) {
		this.replaceAvailSubjects = replaceAvailSubjects;
	}
	public List<StudySubjectPeriods> getSsubPeriods() {
		return ssubPeriods;
	}
	public void setSsubPeriods(List<StudySubjectPeriods> ssubPeriods) {
		this.ssubPeriods = ssubPeriods;
	}
	public List<DoseTimePoints> getDpList() {
		return dpList;
	}
	public List<SubjectDoseTimePoints> getSdtpIdsList() {
		return sdtpIdsList;
	}
	public void setDpList(List<DoseTimePoints> dpList) {
		this.dpList = dpList;
	}
	public void setSdtpIdsList(List<SubjectDoseTimePoints> sdtpIdsList) {
		this.sdtpIdsList = sdtpIdsList;
	}
	public List<TreatmentInfo> getTreatmentList() {
		return treatmentList;
	}
	public List<StudyPeriodMaster> getPeriodsList() {
		return periodsList;
	}
	public void setTreatmentList(List<TreatmentInfo> treatmentList) {
		this.treatmentList = treatmentList;
	}
	public void setPeriodsList(List<StudyPeriodMaster> periodsList) {
		this.periodsList = periodsList;
	}
	public List<SubjectMealsTimePointsData> getMealsData() {
		return mealsData;
	}
	public void setMealsData(List<SubjectMealsTimePointsData> mealsData) {
		this.mealsData = mealsData;
	}
	public GlobalActivity getGa() {
		return ga;
	}
	public void setGa(GlobalActivity ga) {
		this.ga = ga;
	}
	public List<DoseTimePoints> getAllDoseTimePoints() {
		return allDoseTimePoints;
	}
	public void setAllDoseTimePoints(List<DoseTimePoints> allDoseTimePoints) {
		this.allDoseTimePoints = allDoseTimePoints;
	}
	public List<StudyTreatmentWiseSubjects> getSubjectTreatmentWiseList() {
		return subjectTreatmentWiseList;
	}
	public void setSubjectTreatmentWiseList(List<StudyTreatmentWiseSubjects> subjectTreatmentWiseList) {
		this.subjectTreatmentWiseList = subjectTreatmentWiseList;
	}
	public DosingDto getDsDto() {
		return dsDto;
	}
	public void setDsDto(DosingDto dsDto) {
		this.dsDto = dsDto;
	}
	public List<DosingInfoDetails> getDrugInfiList() {
		return drugInfiList;
	}
	public void setDrugInfiList(List<DosingInfoDetails> drugInfiList) {
		this.drugInfiList = drugInfiList;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	
	
	
	
	
}
