package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class DrugDispansingDto implements Serializable {
	
	private String noOfUnits;
	private String nameOfIp;
	private String batchNo;
	private Date expDate;
	private Long treatmentId;
	private String treatmentCode;
	private String studyNumber;
	public String getNoOfUnits() {
		return noOfUnits;
	}
	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}
	public String getNameOfIp() {
		return nameOfIp;
	}
	public void setNameOfIp(String nameOfIp) {
		this.nameOfIp = nameOfIp;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public Long getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}
	public String getTreatmentCode() {
		return treatmentCode;
	}
	public void setTreatmentCode(String treatmentCode) {
		this.treatmentCode = treatmentCode;
	}
	public String getStudyNumber() {
		return studyNumber;
	}
	public void setStudyNumber(String studyNumber) {
		this.studyNumber = studyNumber;
	}
	
	
	

}
