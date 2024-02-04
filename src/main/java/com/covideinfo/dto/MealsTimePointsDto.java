package com.covideinfo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.TreatmentInfo;

public class MealsTimePointsDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3811438106489425276L;
//	private Map<String, SubjectMealsTimePointsData> collectedDataMap = new HashMap<>(); // key-subjetNo,periodId,mealTimepointId , value = subjectMealObject
	private Map<String, RealTimeCommunicationDto> collectedDataMap = new HashMap<>(); // key-subjetNo,periodId,mealTimepointId , value = subjectMealObject
	private StudyMaster study;
	private Map<String, StudyPeriodMaster> subjectPerods; //SubjectNo, periodmaster
	private Map<Long, TreatmentInfo> treatmentMap;
	private Map<String, String> replacedSubjects = new HashMap<>();			// key-barcodeSubjectNo, value=finalReplacedSujectNo if any replacement happend
	private List<String> droppedSubjects = new ArrayList<>();				// drouped subject no's
	private Map<Long, String> discontinueSubjects = new HashMap<>();				// subject no's - discontinued for replace use
	private Map<String, Long> subjectTreatment;
	private Map<Long, List<MealsTimePoints>> preDoseMap; //treatmentId, mealsList
	private Map<Long, List<MealsTimePoints>> postDoseMap;// treatmentId, mealsList
//	private Map<Long, Map<Long, Map<String, SubjectMealsTimePointsData>>> smtpMap; // periodId,  mealId, subject, subdoseDonePojo
	private Map<Long, Map<Long, Map<String, RealTimeCommunicationDto>>> smtpMap; // periodId,  mealId, subject, subdoseDonePojo
	private Map<String, Map<Long, MealsTimePoints>> twmtpMap; //subjectNo, treatment, mealtimepoint
	private Map<String, StudySubjects> subMap; //subjectNo, SubjectPojo
	private Map<Long, MealsTimePoints> mealsTimpointsMap;
	private Map<String, Map<Long, List<Long>>> twsubMap;
	private Map<String, Map<Long, String>> subjectDoseMap; //Subject, periodId, Done/NotDone
	private List<TreatmentInfo> tinfList;
	private Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap; //subjectNo, periodId, treatement, SubjectDosePojo
	private boolean msgFlag = false;
	private String mealsMsg;
	private DosingDto dsDto;
	private Long minTreatmentId;
	private Boolean treatmentSpecificMeals;
	private PlannedTimeDetailsDto ptdDto;
	private Map<Long, Map<String, Map<String, Integer>>> pwMealAllowMap;//PeriodId, timepoint, subjects, allowMinutes
	private Map<String, Map<Long, Map<String, Long>>> subEndTpsMap;//timePoint, PeriodId, bar code, treatmentId
	
	public Map<String, StudyPeriodMaster> getSubjectPerods() {
		return subjectPerods;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public Map<Long, TreatmentInfo> getTreatmentMap() {
		return treatmentMap;
	}
	public Map<String, Long> getSubjectTreatment() {
		return subjectTreatment;
	}
	public Map<Long, List<MealsTimePoints>> getPreDoseMap() {
		return preDoseMap;
	}
	public Map<Long, List<MealsTimePoints>> getPostDoseMap() {
		return postDoseMap;
	}
	public void setSubjectPerods(SortedMap<String, StudyPeriodMaster> subjectPerods) {
		this.subjectPerods = subjectPerods;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setTreatmentMap(Map<Long, TreatmentInfo> treatmentMap) {
		this.treatmentMap = treatmentMap;
	}
	public void setSubjectTreatment(Map<String, Long> subjectTreatment) {
		this.subjectTreatment = subjectTreatment;
	}
	public void setPreDoseMap(Map<Long, List<MealsTimePoints>> preDoseMap) {
		this.preDoseMap = preDoseMap;
	}
	public void setPostDoseMap(Map<Long, List<MealsTimePoints>> postDoseMap) {
		this.postDoseMap = postDoseMap;
	}
	public Map<String, RealTimeCommunicationDto> getCollectedDataMap() {
		return collectedDataMap;
	}
	public void setCollectedDataMap(Map<String, RealTimeCommunicationDto> collectedDataMap) {
		this.collectedDataMap = collectedDataMap;
	}
	public Map<String, Map<Long, MealsTimePoints>> getTwmtpMap() {
		return twmtpMap;
	}
	public void setTwmtpMap(Map<String, Map<Long, MealsTimePoints>> twmtpMap) {
		this.twmtpMap = twmtpMap;
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
	public void setReplacedSubjects(Map<String, String> replacedSubjects) {
		this.replacedSubjects = replacedSubjects;
	}
	public void setDroppedSubjects(List<String> droppedSubjects) {
		this.droppedSubjects = droppedSubjects;
	}
	public void setDiscontinueSubjects(Map<Long, String> discontinueSubjects) {
		this.discontinueSubjects = discontinueSubjects;
	}
	public Map<String, StudySubjects> getSubMap() {
		return subMap;
	}
	public void setSubMap(Map<String, StudySubjects> subMap) {
		this.subMap = subMap;
	}
	public void setSubjectPerods(Map<String, StudyPeriodMaster> subjectPerods) {
		this.subjectPerods = subjectPerods;
	}
	public List<TreatmentInfo> getTinfList() {
		return tinfList;
	}
	public void setTinfList(List<TreatmentInfo> tinfList) {
		this.tinfList = tinfList;
	}
	public Map<Long, MealsTimePoints> getMealsTimpointsMap() {
		return mealsTimpointsMap;
	}
	public void setMealsTimpointsMap(Map<Long, MealsTimePoints> mealsTimpointsMap) {
		this.mealsTimpointsMap = mealsTimpointsMap;
	}
	public Map<String, Map<Long, List<Long>>> getTwsubMap() {
		return twsubMap;
	}
	public void setTwsubMap(Map<String, Map<Long, List<Long>>> twsubMap) {
		this.twsubMap = twsubMap;
	}
	public Map<String, Map<Long, String>> getSubjectDoseMap() {
		return subjectDoseMap;
	}
	public void setSubjectDoseMap(Map<String, Map<Long, String>> subjectDoseMap) {
		this.subjectDoseMap = subjectDoseMap;
	}
	public String getMealsMsg() {
		return mealsMsg;
	}
	public void setMealsMsg(String mealsMsg) {
		this.mealsMsg = mealsMsg;
	}
	public boolean isMsgFlag() {
		return msgFlag;
	}
	public void setMsgFlag(boolean msgFlag) {
		this.msgFlag = msgFlag;
	}
	public Map<Long, Map<Long, Map<String, RealTimeCommunicationDto>>> getSmtpMap() {
		return smtpMap;
	}
	public void setSmtpMap(Map<Long, Map<Long, Map<String, RealTimeCommunicationDto>>> smtpMap) {
		this.smtpMap = smtpMap;
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
	public void setDsDto(DosingDto dsDto) {
		this.dsDto = dsDto;
	}
	public Long getMinTreatmentId() {
		return minTreatmentId;
	}
	public void setMinTreatmentId(Long minTreatmentId) {
		this.minTreatmentId = minTreatmentId;
	}
	public Boolean getTreatmentSpecificMeals() {
		return treatmentSpecificMeals;
	}
	public void setTreatmentSpecificMeals(Boolean treatmentSpecificMeals) {
		this.treatmentSpecificMeals = treatmentSpecificMeals;
	}
	public PlannedTimeDetailsDto getPtdDto() {
		return ptdDto;
	}
	public void setPtdDto(PlannedTimeDetailsDto ptdDto) {
		this.ptdDto = ptdDto;
	}
	public Map<Long, Map<String, Map<String, Integer>>> getPwMealAllowMap() {
		return pwMealAllowMap;
	}
	public void setPwMealAllowMap(Map<Long, Map<String, Map<String, Integer>>> pwMealAllowMap) {
		this.pwMealAllowMap = pwMealAllowMap;
	}
	public Map<String, Map<Long, Map<String, Long>>> getSubEndTpsMap() {
		return subEndTpsMap;
	}
	public void setSubEndTpsMap(Map<String, Map<Long, Map<String, Long>>> subEndTpsMap) {
		this.subEndTpsMap = subEndTpsMap;
	}
	
}
