package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.TreatmentInfo;

public class VitalTimePointsTempDto implements Serializable, Comparable<VitalTimePointsTempDto> {

	private static final long serialVersionUID = -1937634408600314246L;
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name = "vital_position")
	private FromStaticData vitalPosition;
	@ManyToOne
	@JoinColumn(name = "treatmentInfoId")
	private TreatmentInfo treatmentInfo;
	private String timePoint = "";
	@ManyToOne
	@JoinColumn(name = "timePointType")
	private FromStaticData timePointType;
	private String sign = "";
	private String windowPeriodSign = "";
	private int windowPeriod;
	private FromStaticData windowPeriodType;
	private int timePointNo;
	private boolean orthostatic;
	private FromStaticData orthostaticPosition;
	private String parameterIds;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	private String updateReason;
	private Double vtpVal;

	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public FromStaticData getVitalPosition() {
		return vitalPosition;
	}
	public void setVitalPosition(FromStaticData vitalPosition) {
		this.vitalPosition = vitalPosition;
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
	public int getTimePointNo() {
		return timePointNo;
	}
	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}
	public boolean isOrthostatic() {
		return orthostatic;
	}
	public void setOrthostatic(boolean orthostatic) {
		this.orthostatic = orthostatic;
	}
	public FromStaticData getOrthostaticPosition() {
		return orthostaticPosition;
	}
	public void setOrthostaticPosition(FromStaticData orthostaticPosition) {
		this.orthostaticPosition = orthostaticPosition;
	}
	public String getParameterIds() {
		return parameterIds;
	}
	public void setParameterIds(String parameterIds) {
		this.parameterIds = parameterIds;
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
	public Double getVtpVal() {
		return vtpVal;
	}
	public void setVtpVal(Double vtpVal) {
		this.vtpVal = vtpVal;
	}
	@Override
	public int compareTo(VitalTimePointsTempDto o) {
		return Double.compare(this.vtpVal, o.vtpVal);
	}
}
