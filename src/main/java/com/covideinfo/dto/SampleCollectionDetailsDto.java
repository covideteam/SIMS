package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SampleCollectionDetailsDto implements Serializable {
	
	private Long sampleDataId;
	private String timePoint;
	private String periodName;
	private String collectionTime;
	public Long getSampleDataId() {
		return sampleDataId;
	}
	public void setSampleDataId(Long sampleDataId) {
		this.sampleDataId = sampleDataId;
	}
	
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	
	

}
