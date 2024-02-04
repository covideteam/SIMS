package com.covideinfo.dto;

import java.io.Serializable;

public class StudySampleProcessingDetailsDto implements Serializable {
	
	private static final long serialVersionUID = -5978404238638066925L;
	private int aliquotVolume;
	private Long conditon; //single conditionId
	public int getAliquotVolume() {
		return aliquotVolume;
	}
	public Long getConditon() {
		return conditon;
	}
	public void setAliquotVolume(int aliquotVolume) {
		this.aliquotVolume = aliquotVolume;
	}
	public void setConditon(Long conditon) {
		this.conditon = conditon;
	}
	
	

}
