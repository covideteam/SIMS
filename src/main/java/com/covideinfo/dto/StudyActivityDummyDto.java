package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StudyMaster;

public class StudyActivityDummyDto implements Serializable {
	
	private GlobalActivity ga;
	private String createdBy;
	private Date createdOn;
	private StudyMaster sm;
	private String treatmentCode;
	private Integer periodNo;
	public GlobalActivity getGa() {
		return ga;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public StudyMaster getSm() {
		return sm;
	}
	public String getTreatmentCode() {
		return treatmentCode;
	}
	public Integer getPeriodNo() {
		return periodNo;
	}
	public void setGa(GlobalActivity ga) {
		this.ga = ga;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public void setSm(StudyMaster sm) {
		this.sm = sm;
	}
	public void setTreatmentCode(String treatmentCode) {
		this.treatmentCode = treatmentCode;
	}
	public void setPeriodNo(Integer periodNo) {
		this.periodNo = periodNo;
	}
	
	
	

}
