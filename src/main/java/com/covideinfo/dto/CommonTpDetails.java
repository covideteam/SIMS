package com.covideinfo.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectWithdrawDetails;
import com.covideinfo.model.TreatmentInfo;

@SuppressWarnings("serial")
public class CommonTpDetails implements Serializable {
	
	private StudyMaster study;
	private List<StudySubjects> subList;
	private List<SubjectRandamization> subRzList;
	private List<TreatmentInfo> treatmentList;
	private Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> pwdoseMap;//subjectNo, periodId, treatment, subjectDose
	private List<StudySubjectPeriods> studySubjectsPeriodList;
	private Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap;
	private List<String> dropOutSubList;
	private DosingDto dsDto;
	private Map<String, Map<Long, SubjectDoseTimePoints>> dosedWithoutTreatmentMap;//subjectNo, periodId, subjectDosePojo
	private List<SubjectWithdrawDetails> swdrList;
	private Map<String, Long> subVolIdsMap;//Subject, volId
	private Map<Long, Long> saActIdsMap; //StudyActivitiesId, PeriodId
	private List<StudyActivityDataDto> sadataDtoList;
	public Map<String, Map<Long, SubjectDoseTimePoints>> getDosedWithoutTreatmentMap() {
		return dosedWithoutTreatmentMap;
	}
	public void setDosedWithoutTreatmentMap(Map<String, Map<Long, SubjectDoseTimePoints>> dosedWithoutTreatmentMap) {
		this.dosedWithoutTreatmentMap = dosedWithoutTreatmentMap;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public List<StudySubjects> getSubList() {
		return subList;
	}
	public List<SubjectRandamization> getSubRzList() {
		return subRzList;
	}
	public List<TreatmentInfo> getTreatmentList() {
		return treatmentList;
	}
	public List<StudySubjectPeriods> getStudySubjectsPeriodList() {
		return studySubjectsPeriodList;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setSubList(List<StudySubjects> subList) {
		this.subList = subList;
	}
	public void setSubRzList(List<SubjectRandamization> subRzList) {
		this.subRzList = subRzList;
	}
	public void setTreatmentList(List<TreatmentInfo> treatmentList) {
		this.treatmentList = treatmentList;
	}
	public void setStudySubjectsPeriodList(List<StudySubjectPeriods> studySubjectsPeriodList) {
		this.studySubjectsPeriodList = studySubjectsPeriodList;
	}
	public Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> getPwdoseMap() {
		return pwdoseMap;
	}
	public void setPwdoseMap(Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> pwdoseMap) {
		this.pwdoseMap = pwdoseMap;
	}
	public Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> getDosedMap() {
		return dosedMap;
	}
	public void setDosedMap(Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap) {
		this.dosedMap = dosedMap;
	}
	public List<String> getDropOutSubList() {
		return dropOutSubList;
	}
	public void setDropOutSubList(List<String> dropOutSubList) {
		this.dropOutSubList = dropOutSubList;
	}
	public DosingDto getDsDto() {
		return dsDto;
	}
	public void setDsDto(DosingDto dsDto) {
		this.dsDto = dsDto;
	}
	public List<SubjectWithdrawDetails> getSwdrList() {
		return swdrList;
	}
	public void setSwdrList(List<SubjectWithdrawDetails> swdrList) {
		this.swdrList = swdrList;
	}
	public Map<String, Long> getSubVolIdsMap() {
		return subVolIdsMap;
	}
	public void setSubVolIdsMap(Map<String, Long> subVolIdsMap) {
		this.subVolIdsMap = subVolIdsMap;
	}
	public Map<Long, Long> getSaActIdsMap() {
		return saActIdsMap;
	}
	public void setSaActIdsMap(Map<Long, Long> saActIdsMap) {
		this.saActIdsMap = saActIdsMap;
	}
	public List<StudyActivityDataDto> getSadataDtoList() {
		return sadataDtoList;
	}
	public void setSadataDtoList(List<StudyActivityDataDto> sadataDtoList) {
		this.sadataDtoList = sadataDtoList;
	}
	
	
	
	

}
