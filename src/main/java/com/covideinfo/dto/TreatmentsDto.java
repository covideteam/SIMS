package com.covideinfo.dto;

import java.io.Serializable;

public class TreatmentsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2831631216936089077L;
	private Long id;
	private String treatmentName;
	private String randomizationCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	public String getRandomizationCode() {
		return randomizationCode;
	}
	public void setRandomizationCode(String randomizationCode) {
		this.randomizationCode = randomizationCode;
	}
	

}
