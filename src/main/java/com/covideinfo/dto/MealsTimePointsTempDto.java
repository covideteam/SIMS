package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.TreatmentInfo;

public  class MealsTimePointsTempDto implements Serializable, Comparable<MealsTimePointsTempDto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4938144640875212682L;
	private StudyMaster study;
	private TreatmentInfo treatmentInfo;
	private String timePoint = "";
	private FromStaticData timePointType;
	private String sign = "";
	private String windowPeriodSign = "";
	private int windowPeriod;
	private FromStaticData mealsType;
	private FromStaticData completion;
	private int completionTime;
	private int timePointNo;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	private Double mealsTp;
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public TreatmentInfo getTreatmentInfo() {
		return treatmentInfo;
	}
	public void setTreatmentInfo(TreatmentInfo treatmentInfo) {
		this.treatmentInfo = treatmentInfo;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public FromStaticData getTimePointType() {
		return timePointType;
	}
	public void setTimePointType(FromStaticData timePointType) {
		this.timePointType = timePointType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getWindowPeriodSign() {
		return windowPeriodSign;
	}
	public void setWindowPeriodSign(String windowPeriodSign) {
		this.windowPeriodSign = windowPeriodSign;
	}
	public int getWindowPeriod() {
		return windowPeriod;
	}
	public void setWindowPeriod(int windowPeriod) {
		this.windowPeriod = windowPeriod;
	}
	public FromStaticData getMealsType() {
		return mealsType;
	}
	public void setMealsType(FromStaticData mealsType) {
		this.mealsType = mealsType;
	}
	public FromStaticData getCompletion() {
		return completion;
	}
	public void setCompletion(FromStaticData completion) {
		this.completion = completion;
	}
	public int getCompletionTime() {
		return completionTime;
	}
	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}
	public int getTimePointNo() {
		return timePointNo;
	}
	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Double getMealsTp() {
		return mealsTp;
	}
	public void setMealsTp(Double mealsTp) {
		this.mealsTp = mealsTp;
	}
	@Override
	public int compareTo(MealsTimePointsTempDto o) {
		return Double.compare(this.mealsTp, o.mealsTp);
	}
	
	
}
