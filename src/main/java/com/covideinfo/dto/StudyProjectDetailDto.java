package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.covideinfo.dummy.ClinicalInfomation;
import com.covideinfo.dummy.SampleProcessingAndStorage;
import com.covideinfo.model.AdditionalAssesment;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTimePoints;


public class StudyProjectDetailDto implements Serializable {
	private static final long serialVersionUID = 401814473280348566L;
	private Projects projects;
	private List<DoseTimePoints> doseTimePoints;
	private Map<Integer, List<Long>> nonTimePointDosingParamMap;
	private ClinicalInfomation clinicalInfomation;
	private List<AdditionalAssesment> additionalAssesmentTimePoints;
	private Map<Integer, List<ProjectsDetails>> restrictionComplains;
	private Map<Integer, List<ProjectsDetails>> inclusionCriteria;
	private Map<Integer, List<ProjectsDetails>> exclusionCriteria;
	private StudyMaster studyMaster;
	private List<StudyPeriodMaster> studyPeriodMasterList;
	private List<TreatmentInfo> treatmentInfoList;
	private SampleProcessingAndStorage sampleProcessingAndStorage;
	private List<ProjectsDetails> proList;
	private Map<Long, List<Long>> defaultActMap;
	//New
	private Map<Integer, TreeMap<Integer, DoseTimePoints>> doseMap;
	private Map<Integer, TreeMap<Integer, SampleTimePoints>> sampleMap;
	private Map<Integer, TreeMap<Integer, MealsTimePoints>> finalMealsMap;
	private Map<Integer, TreeMap<Integer, VitalTimePoints>> vitalMap;
	private Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> ecgMap;
	private Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> skinSensitivityMap;
	private Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> skinAdhitionMap;
	private Map<Integer, TreatmentInfo> treatmentNoMap;
	
	public Projects getProjects() {
		return projects;
	}
	public List<DoseTimePoints> getDoseTimePoints() {
		return doseTimePoints;
	}
	public Map<Integer, List<Long>> getNonTimePointDosingParamMap() {
		return nonTimePointDosingParamMap;
	}
	public ClinicalInfomation getClinicalInfomation() {
		return clinicalInfomation;
	}
	public List<AdditionalAssesment> getAdditionalAssesmentTimePoints() {
		return additionalAssesmentTimePoints;
	}
	public Map<Integer, List<ProjectsDetails>> getRestrictionComplains() {
		return restrictionComplains;
	}
	public Map<Integer, List<ProjectsDetails>> getInclusionCriteria() {
		return inclusionCriteria;
	}
	public Map<Integer, List<ProjectsDetails>> getExclusionCriteria() {
		return exclusionCriteria;
	}
	public StudyMaster getStudyMaster() {
		return studyMaster;
	}
	public List<StudyPeriodMaster> getStudyPeriodMasterList() {
		return studyPeriodMasterList;
	}
	public List<TreatmentInfo> getTreatmentInfoList() {
		return treatmentInfoList;
	}
	public SampleProcessingAndStorage getSampleProcessingAndStorage() {
		return sampleProcessingAndStorage;
	}
	public List<ProjectsDetails> getProList() {
		return proList;
	}
	public void setProjects(Projects projects) {
		this.projects = projects;
	}
	public void setDoseTimePoints(List<DoseTimePoints> doseTimePoints) {
		this.doseTimePoints = doseTimePoints;
	}
	public void setNonTimePointDosingParamMap(Map<Integer, List<Long>> nonTimePointDosingParamMap) {
		this.nonTimePointDosingParamMap = nonTimePointDosingParamMap;
	}
	public void setClinicalInfomation(ClinicalInfomation clinicalInfomation) {
		this.clinicalInfomation = clinicalInfomation;
	}
	public void setAdditionalAssesmentTimePoints(List<AdditionalAssesment> additionalAssesmentTimePoints) {
		this.additionalAssesmentTimePoints = additionalAssesmentTimePoints;
	}
	public void setRestrictionComplains(Map<Integer, List<ProjectsDetails>> restrictionComplains) {
		this.restrictionComplains = restrictionComplains;
	}
	public void setInclusionCriteria(Map<Integer, List<ProjectsDetails>> inclusionCriteria) {
		this.inclusionCriteria = inclusionCriteria;
	}
	public void setExclusionCriteria(Map<Integer, List<ProjectsDetails>> exclusionCriteria) {
		this.exclusionCriteria = exclusionCriteria;
	}
	public void setStudyMaster(StudyMaster studyMaster) {
		this.studyMaster = studyMaster;
	}
	public void setStudyPeriodMasterList(List<StudyPeriodMaster> studyPeriodMasterList) {
		this.studyPeriodMasterList = studyPeriodMasterList;
	}
	public void setTreatmentInfoList(List<TreatmentInfo> treatmentInfoList) {
		this.treatmentInfoList = treatmentInfoList;
	}
	public void setSampleProcessingAndStorage(SampleProcessingAndStorage sampleProcessingAndStorage) {
		this.sampleProcessingAndStorage = sampleProcessingAndStorage;
	}
	public void setProList(List<ProjectsDetails> proList) {
		this.proList = proList;
	}
	public Map<Long, List<Long>> getDefaultActMap() {
		return defaultActMap;
	}
	public void setDefaultActMap(Map<Long, List<Long>> defaultActMap) {
		this.defaultActMap = defaultActMap;
	}
	public Map<Integer, TreeMap<Integer, DoseTimePoints>> getDoseMap() {
		return doseMap;
	}
	public Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> getEcgMap() {
		return ecgMap;
	}
	public void setEcgMap(Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> ecgMap) {
		this.ecgMap = ecgMap;
	}
	public Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> getSkinSensitivityMap() {
		return skinSensitivityMap;
	}
	public void setSkinSensitivityMap(Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> skinSensitivityMap) {
		this.skinSensitivityMap = skinSensitivityMap;
	}
	public Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> getSkinAdhitionMap() {
		return skinAdhitionMap;
	}
	public void setSkinAdhitionMap(Map<Integer, TreeMap<Integer, StudyActivityTimpointsSavingDto>> skinAdhitionMap) {
		this.skinAdhitionMap = skinAdhitionMap;
	}
	public void setDoseMap(Map<Integer, TreeMap<Integer, DoseTimePoints>> doseMap) {
		this.doseMap = doseMap;
	}
	public Map<Integer, TreeMap<Integer, SampleTimePoints>> getSampleMap() {
		return sampleMap;
	}
	public void setSampleMap(Map<Integer, TreeMap<Integer, SampleTimePoints>> sampleMap) {
		this.sampleMap = sampleMap;
	}
	public Map<Integer, TreeMap<Integer, MealsTimePoints>> getFinalMealsMap() {
		return finalMealsMap;
	}
	public void setFinalMealsMap(Map<Integer, TreeMap<Integer, MealsTimePoints>> finalMealsMap) {
		this.finalMealsMap = finalMealsMap;
	}
	public Map<Integer, TreeMap<Integer, VitalTimePoints>> getVitalMap() {
		return vitalMap;
	}
	public void setVitalMap(Map<Integer, TreeMap<Integer, VitalTimePoints>> vitalMap) {
		this.vitalMap = vitalMap;
	}
	public Map<Integer, TreatmentInfo> getTreatmentNoMap() {
		return treatmentNoMap;
	}
	public void setTreatmentNoMap(Map<Integer, TreatmentInfo> treatmentNoMap) {
		this.treatmentNoMap = treatmentNoMap;
	}
	
	
	
	
	
	
	
	

}
