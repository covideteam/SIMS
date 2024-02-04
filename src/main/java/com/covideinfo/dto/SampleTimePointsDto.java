package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.TreatmentInfo;

public class SampleTimePointsDto implements Serializable, Comparable<SampleTimePointsDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -582190232667646742L;
	private StudyMaster study;
	private TreatmentInfo treatmentInfo;
	private String timePoint = "";
	private FromStaticData timePointType ;
	private String sign = "";
	private String windowPeriodSign = "";
	private int windowPeriod;
	private FromStaticData windowPeriodType;
	private int vacutainerNo;
	private int noOfVacutainer;
	private int timePointNo;
	private int noOfVials;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	private String updateReason;
	private Double stp;
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
	public FromStaticData getWindowPeriodType() {
		return windowPeriodType;
	}
	public void setWindowPeriodType(FromStaticData windowPeriodType) {
		this.windowPeriodType = windowPeriodType;
	}
	public int getVacutainerNo() {
		return vacutainerNo;
	}
	public void setVacutainerNo(int vacutainerNo) {
		this.vacutainerNo = vacutainerNo;
	}
	public int getNoOfVacutainer() {
		return noOfVacutainer;
	}
	public void setNoOfVacutainer(int noOfVacutainer) {
		this.noOfVacutainer = noOfVacutainer;
	}
	public int getTimePointNo() {
		return timePointNo;
	}
	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}
	public int getNoOfVials() {
		return noOfVials;
	}
	public void setNoOfVials(int noOfVials) {
		this.noOfVials = noOfVials;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	public Double getStp() {
		return stp;
	}
	public void setStp(Double stp) {
		this.stp = stp;
	}
	@Override
	public int compareTo(SampleTimePointsDto o) {
		return Double.compare(this.stp, o.stp);
	}
	
	

}
