package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectWithdrawDetails;
import com.covideinfo.model.TreatmentInfo;

@SuppressWarnings("serial")
public class CommonTimePointDto implements Serializable {
	private Date plannedDoseTime;
	private StudyMaster study;
	private Map<String, StudySubjects> subMap; //subjNo, studySubject
	private Map<String, StudyPeriodMaster> stdPeriodMap; //subjectNo, periodPojo
	private Map<String, Map<Long, List<TreatmentInfo>>> subperiodwiseTreatMap; //subjectNo, periodId, treatmentPojo
	private Map<String, StudySubjects> dropedSubMap; //subjectNo, StudySubject
	private Map<String, StudySubjects> replaceSubMap; //subjectNo, StudySubject
	private Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> pwdoseMap;//subjectNo, periodId, treatment, subjectDose
	private Map<Long, TreatmentInfo> treatmentMap;
	private Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap;//subjectNo, periodId, treatement subjectDosePojo
	private Map<String, Map<Long, SubjectDoseTimePoints>> dosedWithoutTreatmentMap;//subjectNo, periodId, subjectDosePojo
	private List<String> dropOutSubList; //subjectNo
	private Map<String, SubjectWithdrawDetails> subWithdrawnMap;//SubjectNo, SubjectWithdrawDetails
	private Long minTreatmentId;
	private Map<String, Long> subVolIdsMap;//Subject, volId
	private Map<Long, Long> saActIdsMap; //StudyActivitiesId, PeriodId
	private Map<Long, Map<Long, String>> subCannulaMap; //SubjectNo, periodId, Done/NotDone;
	
	
	public Date getPlannedDoseTime() {
		return plannedDoseTime;
	}
	public void setPlannedDoseTime(Date plannedDoseTime) {
		this.plannedDoseTime = plannedDoseTime;
	}
	public Map<String, SubjectWithdrawDetails> getSubWithdrawnMap() {
		return subWithdrawnMap;
	}
	public void setSubWithdrawnMap(Map<String, SubjectWithdrawDetails> subWithdrawnMap) {
		this.subWithdrawnMap = subWithdrawnMap;
	}
	private DosingDto dsDto;
	public StudyMaster getStudy() {
		return study;
	}
	public Map<String, StudySubjects> getSubMap() {
		return subMap;
	}
	public Map<String, StudyPeriodMaster> getStdPeriodMap() {
		return stdPeriodMap;
	}
	public Map<String, StudySubjects> getDropedSubMap() {
		return dropedSubMap;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setSubMap(Map<String, StudySubjects> subMap) {
		this.subMap = subMap;
	}
	public void setStdPeriodMap(Map<String, StudyPeriodMaster> stdPeriodMap) {
		this.stdPeriodMap = stdPeriodMap;
	}
	public void setDropedSubMap(Map<String, StudySubjects> dropedSubMap) {
		this.dropedSubMap = dropedSubMap;
	}
	public Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> getPwdoseMap() {
		return pwdoseMap;
	}
	public void setPwdoseMap(Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> pwdoseMap) {
		this.pwdoseMap = pwdoseMap;
	}
	public Map<String, StudySubjects> getReplaceSubMap() {
		return replaceSubMap;
	}
	public void setReplaceSubMap(Map<String, StudySubjects> replaceSubMap) {
		this.replaceSubMap = replaceSubMap;
	}
	public Map<String, Map<Long, List<TreatmentInfo>>> getSubperiodwiseTreatMap() {
		return subperiodwiseTreatMap;
	}
	public void setSubperiodwiseTreatMap(Map<String, Map<Long, List<TreatmentInfo>>> subperiodwiseTreatMap) {
		this.subperiodwiseTreatMap = subperiodwiseTreatMap;
	}
	public Map<Long, TreatmentInfo> getTreatmentMap() {
		return treatmentMap;
	}
	public void setTreatmentMap(Map<Long, TreatmentInfo> treatmentMap) {
		this.treatmentMap = treatmentMap;
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
	public Map<String, Map<Long, SubjectDoseTimePoints>> getDosedWithoutTreatmentMap() {
		return dosedWithoutTreatmentMap;
	}
	public void setDosedWithoutTreatmentMap(Map<String, Map<Long, SubjectDoseTimePoints>> dosedWithoutTreatmentMap) {
		this.dosedWithoutTreatmentMap = dosedWithoutTreatmentMap;
	}
	public Long getMinTreatmentId() {
		return minTreatmentId;
	}
	public void setMinTreatmentId(Long minTreatmentId) {
		this.minTreatmentId = minTreatmentId;
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
	public Map<Long, Map<Long, String>> getSubCannulaMap() {
		return subCannulaMap;
	}
	public void setSubCannulaMap(Map<Long, Map<Long, String>> subCannulaMap) {
		this.subCannulaMap = subCannulaMap;
	}
	
	
	

}
