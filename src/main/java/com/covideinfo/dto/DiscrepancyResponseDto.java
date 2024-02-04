package com.covideinfo.dto;

import java.io.Serializable;

import com.covideinfo.model.SubjectDoseTimePoints;

@SuppressWarnings("serial")
public class DiscrepancyResponseDto implements Serializable {
	
	private SubjectDoseTimePoints sdtp;
	private String result="Failed";
	public SubjectDoseTimePoints getSdtp() {
		return sdtp;
	}
	public void setSdtp(SubjectDoseTimePoints sdtp) {
		this.sdtp = sdtp;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	

}
