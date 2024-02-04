package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalTimePointsData;

public class MissedTimePointsDetailsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1369619112489658187L;
	private List<StudyPeriodsDto> spmList;
	private List<ActivityDetailsDto> actList;
	private List<SubjectDoseTimePoints> sdtpList;
	private List<SubjectMealsTimePointsData> smtpList;
	private List<SubjectSampleCollectionTimePointsData> sctpList;
	private List<SubjectVitalTimePointsData> svtpList;
	private List<MealsTimePoints> mealTpsList = null;
	private List<VitalTimePointsDto> vitalList = null;
	private List<SampleTimePoints> stpList = null;
	private List<DoseTimePoints> doseList = null;
	private DosingInfo doseInfo;
	private List<Long> periodIds;
	private List<StudySubjects> subjectsList;
	private List<SubjectRandomizationDto> subradDtoList;
	private List<SubjectDoseTimePoints> pwSubDoseList;
	private Integer washoutPeriodDays=0;
	
	
	public List<StudyPeriodsDto> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodsDto> spmList) {
		this.spmList = spmList;
	}
	public List<ActivityDetailsDto> getActList() {
		return actList;
	}
	public void setActList(List<ActivityDetailsDto> actList) {
		this.actList = actList;
	}
	public List<SubjectDoseTimePoints> getSdtpList() {
		return sdtpList;
	}
	public void setSdtpList(List<SubjectDoseTimePoints> sdtpList) {
		this.sdtpList = sdtpList;
	}
	public List<SubjectMealsTimePointsData> getSmtpList() {
		return smtpList;
	}
	public void setSmtpList(List<SubjectMealsTimePointsData> smtpList) {
		this.smtpList = smtpList;
	}
	public List<SubjectSampleCollectionTimePointsData> getSctpList() {
		return sctpList;
	}
	public void setSctpList(List<SubjectSampleCollectionTimePointsData> sctpList) {
		this.sctpList = sctpList;
	}
	public List<SubjectVitalTimePointsData> getSvtpList() {
		return svtpList;
	}
	public void setSvtpList(List<SubjectVitalTimePointsData> svtpList) {
		this.svtpList = svtpList;
	}
	public List<MealsTimePoints> getMealTpsList() {
		return mealTpsList;
	}
	public void setMealTpsList(List<MealsTimePoints> mealTpsList) {
		this.mealTpsList = mealTpsList;
	}
	public List<VitalTimePointsDto> getVitalList() {
		return vitalList;
	}
	public void setVitalList(List<VitalTimePointsDto> vitalList) {
		this.vitalList = vitalList;
	}
	public List<SampleTimePoints> getStpList() {
		return stpList;
	}
	public void setStpList(List<SampleTimePoints> stpList) {
		this.stpList = stpList;
	}
	public List<DoseTimePoints> getDoseList() {
		return doseList;
	}
	public void setDoseList(List<DoseTimePoints> doseList) {
		this.doseList = doseList;
	}
	public DosingInfo getDoseInfo() {
		return doseInfo;
	}
	public void setDoseInfo(DosingInfo doseInfo) {
		this.doseInfo = doseInfo;
	}
	public List<Long> getPeriodIds() {
		return periodIds;
	}
	public void setPeriodIds(List<Long> periodIds) {
		this.periodIds = periodIds;
	}
	public List<StudySubjects> getSubjectsList() {
		return subjectsList;
	}
	public void setSubjectsList(List<StudySubjects> subjectsList) {
		this.subjectsList = subjectsList;
	}
	public List<SubjectRandomizationDto> getSubradDtoList() {
		return subradDtoList;
	}
	public void setSubradDtoList(List<SubjectRandomizationDto> subradDtoList) {
		this.subradDtoList = subradDtoList;
	}
	public List<SubjectDoseTimePoints> getPwSubDoseList() {
		return pwSubDoseList;
	}
	public void setPwSubDoseList(List<SubjectDoseTimePoints> pwSubDoseList) {
		this.pwSubDoseList = pwSubDoseList;
	}
	public Integer getWashoutPeriodDays() {
		return washoutPeriodDays;
	}
	public void setWashoutPeriodDays(Integer washoutPeriodDays) {
		this.washoutPeriodDays = washoutPeriodDays;
	}
	
	
	

}
