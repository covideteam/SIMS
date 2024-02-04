package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VolunteerDto implements Serializable {
	
	private Long volId;
	private String vounteerNo;
	private Long treatmentId;
	private Long periodId; 
	private String periodNumber;
	private String gender;
	private Long genderId;
	public String getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	public Long getVolId() {
		return volId;
	}
	public void setVolId(Long volId) {
		this.volId = volId;
	}
	public String getVounteerNo() {
		return vounteerNo;
	}
	public void setVounteerNo(String vounteerNo) {
		this.vounteerNo = vounteerNo;
	}
	public Long getTreatmentId() {
		return treatmentId;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getGenderId() {
		return genderId;
	}
	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}
	
	

}
