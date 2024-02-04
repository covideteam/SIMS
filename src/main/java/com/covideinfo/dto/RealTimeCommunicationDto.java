package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class RealTimeCommunicationDto implements Serializable {
	
	private String subjectNo;
	private Long periodId;
	private Long treatmentId;
	private Long timePointId;
	private Long subjectVitalId;
	private String timePoint;
	private Date actualTime;
	private Date mealsSatartTime;
	private Date mealsEndTime;
	private Long studyId;
	private String collectedPosition;
	
	private Long replacedSubjectId;
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public Long getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}
	public Long getTimePointId() {
		return timePointId;
	}
	public void setTimePointId(Long timePointId) {
		this.timePointId = timePointId;
	}
	public Long getSubjectVitalId() {
		return subjectVitalId;
	}
	public void setSubjectVitalId(Long subjectVitalId) {
		this.subjectVitalId = subjectVitalId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public Date getActualTime() {
		return actualTime;
	}
	public void setActualTime(Date actualTime) {
		this.actualTime = actualTime;
	}
	public Long getReplacedSubjectId() {
		return replacedSubjectId;
	}
	public void setReplacedSubjectId(Long replacedSubjectId) {
		this.replacedSubjectId = replacedSubjectId;
	}
	public Date getMealsSatartTime() {
		return mealsSatartTime;
	}
	public void setMealsSatartTime(Date mealsSatartTime) {
		this.mealsSatartTime = mealsSatartTime;
	}
	public Date getMealsEndTime() {
		return mealsEndTime;
	}
	public void setMealsEndTime(Date mealsEndTime) {
		this.mealsEndTime = mealsEndTime;
	}
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public String getCollectedPosition() {
		return collectedPosition;
	}
	public void setCollectedPosition(String collectedPosition) {
		this.collectedPosition = collectedPosition;
	}
	
	
	
	

}
