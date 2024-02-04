package com.covideinfo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;

@SuppressWarnings("serial")
public class DoseTimePointsDetailsDto implements Serializable {
	
	private StudyMaster study;
	private SortedMap<Integer, String> subjectBarocodes = new TreeMap<>();  // key-Order , value=subjectno
	private Map<String, String> subjectTypes = new HashMap<>();			// key-subjecno, value = standby/not
	private Map<String, String> replacedSubjects = new HashMap<>();			// key-barcodeSubjectNo, value=finalReplacedSujectNo if any replacement happend
	private List<String> droppedSubjects = new ArrayList<>();				// drouped subject no's
	private Map<Long, String> discontinueSubjects = new HashMap<>();				// subject no's - discontinued for replace use
	private Map<Long, String> replaceAvailSubjects;
	private Map<String, Map<String, SubjectMealsTimePointsData>> mealsDetials = new TreeMap<>();
	private SortedMap<String, Map<Long, Map<Long, Map<Long, DoseTimePoints>>>> timePoints;
	private Map<String, StudyPeriodMaster> subjectPeriodsMap;
	private Map<Long, String> treatment; //tereatmentId, radamizationCode
	private Map<Long, Long> firstDoseMap; //treatmentId, firstdoseId
	private Map<Long, SubjectDoseTimePoints> dosedTimePoint;
	private GlobalparameterFromDto perameters;
	private Map<Integer, Long> doseInfoMap;
	private Map<Long, String> allDoseMap;
	private Map<Long, Integer> orderedDoseMap;
	private Map<Long, List<Long>> trwDoseIdsMap; //treatmentId, ListofdoseIds
	private Map<String, Map<Long, List<Long>>> subwTrMap; //subjectNo, treatementId
	private List<DoseTimePoints> doseList;
	private Map<String, Map<Long, List<RealTimeCommunicationDto>>> sdtpMap; //subject, doseTimePointId, SubjectDoseTimePointsList;
	private Map<Long, DoseTimePoints> dpMap;
	private DosingDto dsDto;
	private String mealType;
	private Map<String, StudySubjects> subMap; //subjectNo, StudySubjects
	private Map<String, DrugDispansingDto> drugInfoMap;
	private String projectType; //Fast, Fed, Fast & Fed
	private Map<Long, String> treatmentProductInformation;
	
	public SortedMap<Integer, String> getSubjectBarocodes() {
		return subjectBarocodes;
	}
	public Map<String, String> getSubjectTypes() {
		return subjectTypes;
	}
	public Map<String, String> getReplacedSubjects() {
		return replacedSubjects;
	}
	public List<String> getDroppedSubjects() {
		return droppedSubjects;
	}
	public Map<Long, String> getDiscontinueSubjects() {
		return discontinueSubjects;
	}
	public Map<Long, String> getReplaceAvailSubjects() {
		return replaceAvailSubjects;
	}
	public Map<String, Map<String, SubjectMealsTimePointsData>> getMealsDetials() {
		return mealsDetials;
	}
	public void setSubjectBarocodes(SortedMap<Integer, String> subjectBarocodes) {
		this.subjectBarocodes = subjectBarocodes;
	}
	public void setSubjectTypes(Map<String, String> subjectTypes) {
		this.subjectTypes = subjectTypes;
	}
	public void setReplacedSubjects(Map<String, String> replacedSubjects) {
		this.replacedSubjects = replacedSubjects;
	}
	public void setDroppedSubjects(List<String> droppedSubjects) {
		this.droppedSubjects = droppedSubjects;
	}
	public void setDiscontinueSubjects(Map<Long, String> discontinueSubjects) {
		this.discontinueSubjects = discontinueSubjects;
	}
	public void setReplaceAvailSubjects(Map<Long, String> replaceAvailSubjects) {
		this.replaceAvailSubjects = replaceAvailSubjects;
	}
	public void setMealsDetials(Map<String, Map<String, SubjectMealsTimePointsData>> mealsDetials) {
		this.mealsDetials = mealsDetials;
	}
	public SortedMap<String, Map<Long, Map<Long, Map<Long, DoseTimePoints>>>> getTimePoints() {
		return timePoints;
	}
	public void setTimePoints(SortedMap<String, Map<Long, Map<Long, Map<Long, DoseTimePoints>>>> timePoints) {
		this.timePoints = timePoints;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public Map<String, StudyPeriodMaster> getSubjectPeriodsMap() {
		return subjectPeriodsMap;
	}
	public void setSubjectPeriodsMap(Map<String, StudyPeriodMaster> subjectPeriodsMap) {
		this.subjectPeriodsMap = subjectPeriodsMap;
	}
	public Map<Long, String> getTreatment() {
		return treatment;
	}
	public void setTreatment(Map<Long, String> treatment) {
		this.treatment = treatment;
	}
	
	public Map<Long, Long> getFirstDoseMap() {
		return firstDoseMap;
	}
	public void setFirstDoseMap(Map<Long, Long> firstDoseMap) {
		this.firstDoseMap = firstDoseMap;
	}
	public Map<Long, SubjectDoseTimePoints> getDosedTimePoint() {
		return dosedTimePoint;
	}
	public void setDosedTimePoint(Map<Long, SubjectDoseTimePoints> dosedTimePoint) {
		this.dosedTimePoint = dosedTimePoint;
	}
	public GlobalparameterFromDto getPerameters() {
		return perameters;
	}
	public void setPerameters(GlobalparameterFromDto perameters) {
		this.perameters = perameters;
	}
	public Map<Integer, Long> getDoseInfoMap() {
		return doseInfoMap;
	}
	public void setDoseInfoMap(Map<Integer, Long> doseInfoMap) {
		this.doseInfoMap = doseInfoMap;
	}
	public Map<Long, String> getAllDoseMap() {
		return allDoseMap;
	}
	public void setAllDoseMap(Map<Long, String> allDoseMap) {
		this.allDoseMap = allDoseMap;
	}
	public Map<Long, Integer> getOrderedDoseMap() {
		return orderedDoseMap;
	}
	public void setOrderedDoseMap(Map<Long, Integer> orderedDoseMap) {
		this.orderedDoseMap = orderedDoseMap;
	}
	public Map<Long, List<Long>> getTrwDoseIdsMap() {
		return trwDoseIdsMap;
	}
	public void setTrwDoseIdsMap(Map<Long, List<Long>> trwDoseIdsMap) {
		this.trwDoseIdsMap = trwDoseIdsMap;
	}
	
	public Map<String, Map<Long, List<Long>>> getSubwTrMap() {
		return subwTrMap;
	}
	public void setSubwTrMap(Map<String, Map<Long, List<Long>>> subwTrMap) {
		this.subwTrMap = subwTrMap;
	}
	public List<DoseTimePoints> getDoseList() {
		return doseList;
	}
	public void setDoseList(List<DoseTimePoints> doseList) {
		this.doseList = doseList;
	}
	
	public Map<String, Map<Long, List<RealTimeCommunicationDto>>> getSdtpMap() {
		return sdtpMap;
	}
	public void setSdtpMap(Map<String, Map<Long, List<RealTimeCommunicationDto>>> sdtpMap) {
		this.sdtpMap = sdtpMap;
	}
	public Map<Long, DoseTimePoints> getDpMap() {
		return dpMap;
	}
	public void setDpMap(Map<Long, DoseTimePoints> dpMap) {
		this.dpMap = dpMap;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public DosingDto getDsDto() {
		return dsDto;
	}
	public void setDsDto(DosingDto dsDto) {
		this.dsDto = dsDto;
	}
	public Map<String, StudySubjects> getSubMap() {
		return subMap;
	}
	public void setSubMap(Map<String, StudySubjects> subMap) {
		this.subMap = subMap;
	}
	public Map<String, DrugDispansingDto> getDrugInfoMap() {
		return drugInfoMap;
	}
	public void setDrugInfoMap(Map<String, DrugDispansingDto> drugInfoMap) {
		this.drugInfoMap = drugInfoMap;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public Map<Long, String> getTreatmentProductInformation() {
		return treatmentProductInformation;
	}
	public void setTreatmentProductInformation(Map<Long, String> treatmentProductInformation) {
		this.treatmentProductInformation = treatmentProductInformation;
	}
	
	

}
